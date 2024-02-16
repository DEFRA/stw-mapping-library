package uk.gov.defra.stw.mapping.toipaffs.common.common;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;

class MeansOfTransportMapperTest {

  private MeansOfTransportMapper meansOfTransportMapper;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() {
    meansOfTransportMapper = new MeansOfTransportMapper();
    objectMapper = TestUtils.initObjectMapper();
  }

  @Test
  void map_ReturnsString_WhenComplete() throws JsonProcessingException {
    MainCarriageSpsTransportMovement firstMainCarriageSpsTransportMovement = getFirstMainCarriageSpsTransportMovement();

    assertThat(meansOfTransportMapper.map(firstMainCarriageSpsTransportMovement).getId())
        .isEqualTo("Voyage N° 1, Ocean Vessel: Green Opal");
  }

  @Test
  void map_ReturnsString_WhenNullUsedSpsTransportMeans() throws JsonProcessingException {
    MainCarriageSpsTransportMovement firstMainCarriageSpsTransportMovement = getFirstMainCarriageSpsTransportMovement();
    firstMainCarriageSpsTransportMovement.setUsedSpsTransportMeans(null);

    assertThat(meansOfTransportMapper.map(firstMainCarriageSpsTransportMovement).getId())
        .isEqualTo("Voyage N° 1");
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
        .get(SpsCertificate.class, "cloning/chedpp/chedpp_ehc_complete.json", objectMapper)
        .getSpsConsignment()
        .getMainCarriageSpsTransportMovement()
        .get(0);
  }
}
