package uk.gov.defra.stw.mapping.toipaffs.common;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;

class MeansOfTransportMapperTest {

  private MeansOfTransportMapper meansOfTransportMapper;

  @BeforeEach
  void setup() {
    meansOfTransportMapper = new MeansOfTransportMapper();
  }

  @Test
  void map_ReturnsString_WhenComplete() throws JsonProcessingException {
    MainCarriageSpsTransportMovement firstMainCarriageSpsTransportMovement = getFirstMainCarriageSpsTransportMovement();

    assertThat(meansOfTransportMapper.map(firstMainCarriageSpsTransportMovement).getId())
        .isEqualTo("Identification");
  }

  @Test
  void map_ReturnsString_WhenNullUsedSpsTransportMeans() throws JsonProcessingException {
    MainCarriageSpsTransportMovement firstMainCarriageSpsTransportMovement = getFirstMainCarriageSpsTransportMovement();
    firstMainCarriageSpsTransportMovement.setUsedSpsTransportMeans(null);

    assertThat(meansOfTransportMapper.map(firstMainCarriageSpsTransportMovement).getId())
        .isEqualTo("Identification");
  }


  @Test
  void map_ReturnsNull_WhenIdValueIsNull() throws JsonProcessingException {
    MainCarriageSpsTransportMovement firstMainCarriageSpsTransportMovement = getFirstMainCarriageSpsTransportMovement();
    firstMainCarriageSpsTransportMovement.getId().setValue(null);

    assertThat(meansOfTransportMapper.map(firstMainCarriageSpsTransportMovement).getId())
        .isNull();
  }

  @Test
  void map_ReturnsNull_WhenIdValueIsBlank() throws JsonProcessingException {
    MainCarriageSpsTransportMovement firstMainCarriageSpsTransportMovement = getFirstMainCarriageSpsTransportMovement();
    firstMainCarriageSpsTransportMovement.getId().setValue(" ");

    assertThat(meansOfTransportMapper.map(firstMainCarriageSpsTransportMovement).getId())
        .isNull();
  }

  private MainCarriageSpsTransportMovement getFirstMainCarriageSpsTransportMovement() throws JsonProcessingException {
    return JsonDeserializer
        .get("chedpp/chedpp_ehc_complete.json", SpsCertificate.class)
        .getSpsConsignment()
        .getMainCarriageSpsTransportMovement()
        .get(0);
  }
}
