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

class MeansOfTransportFromEntryPointBaseMapperTest {

  private MeansOfTransportFromEntryPointBaseMapper meansOfTransportFromEntryPointBaseMapper;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() {
    meansOfTransportFromEntryPointBaseMapper = new MeansOfTransportFromEntryPointBaseMapper();
    objectMapper = TestUtils.initObjectMapper();
  }

  @Test
  void map_ReturnsConcatenatedIdentificationString_WhenIdAndUsedSpsTransportMeansNonNull() throws JsonProcessingException {
    MainCarriageSpsTransportMovement firstMainCarriageSpsTransportMovement = getFirstMainCarriageSpsTransportMovement();

    assertThat(meansOfTransportFromEntryPointBaseMapper.map(firstMainCarriageSpsTransportMovement).getId())
        .isEqualTo("Voyage N° 1, Ocean Vessel: Green Opal");
  }

  @Test
  void map_ReturnsIdValue_WhenNullUsedSpsTransportMeans() throws JsonProcessingException {
    MainCarriageSpsTransportMovement firstMainCarriageSpsTransportMovement = getFirstMainCarriageSpsTransportMovement();
    firstMainCarriageSpsTransportMovement.setUsedSpsTransportMeans(null);

    assertThat(meansOfTransportFromEntryPointBaseMapper.map(firstMainCarriageSpsTransportMovement).getId())
        .isEqualTo("Voyage N° 1");
  }

  @Test
  void map_ReturnsIdValue_WhenBlankUsedSpsTransportMeans() throws JsonProcessingException {
    MainCarriageSpsTransportMovement firstMainCarriageSpsTransportMovement = getFirstMainCarriageSpsTransportMovement();
    firstMainCarriageSpsTransportMovement.getUsedSpsTransportMeans().getName().setValue(" ");

    assertThat(meansOfTransportFromEntryPointBaseMapper.map(firstMainCarriageSpsTransportMovement).getId())
        .isEqualTo("Voyage N° 1");
  }

  @Test
  void map_ReturnsUsedSpsTransportMeansName_WhenIdIsNullAndUsedSpsTransportMeansIsNotNull() throws JsonProcessingException {
    MainCarriageSpsTransportMovement firstMainCarriageSpsTransportMovement = getFirstMainCarriageSpsTransportMovement();
    firstMainCarriageSpsTransportMovement.getId().setValue(null);

    assertThat(meansOfTransportFromEntryPointBaseMapper.map(firstMainCarriageSpsTransportMovement).getId())
        .isEqualTo("Ocean Vessel: Green Opal");
  }

  @Test
  void map_ReturnsUsedSpsTransportMeansName_WhenIdIsBlankAndUsedSpsTransportMeansIsNotNull() throws JsonProcessingException {
    MainCarriageSpsTransportMovement firstMainCarriageSpsTransportMovement = getFirstMainCarriageSpsTransportMovement();
    firstMainCarriageSpsTransportMovement.getId().setValue(" ");

    assertThat(meansOfTransportFromEntryPointBaseMapper.map(firstMainCarriageSpsTransportMovement).getId())
        .isEqualTo("Ocean Vessel: Green Opal");
  }

  @Test
  void map_ReturnsNull_WhenIdValueAndUsedSpsTransportMeansIsNull() throws JsonProcessingException {
    MainCarriageSpsTransportMovement firstMainCarriageSpsTransportMovement = getFirstMainCarriageSpsTransportMovement();
    firstMainCarriageSpsTransportMovement.getId().setValue(null);
    firstMainCarriageSpsTransportMovement.setUsedSpsTransportMeans(null);

    assertThat(meansOfTransportFromEntryPointBaseMapper.map(firstMainCarriageSpsTransportMovement).getId())
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
