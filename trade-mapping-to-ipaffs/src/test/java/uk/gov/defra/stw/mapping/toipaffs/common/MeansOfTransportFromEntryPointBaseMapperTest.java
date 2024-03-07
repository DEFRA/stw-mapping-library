package uk.gov.defra.stw.mapping.toipaffs.common;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.dto.ModeCode;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.dto.UsedSpsTransportMeans;

class MeansOfTransportFromEntryPointBaseMapperTest {

  private MeansOfTransportFromEntryPointBaseMapper mapper;

  private MainCarriageSpsTransportMovement transportMovement;

  @BeforeEach
  void setup() {
    mapper = new MeansOfTransportFromEntryPointBaseMapper();
    transportMovement = new MainCarriageSpsTransportMovement()
        .withId(new IDType()
            .withValue("Identification")
            .withSchemeID("ship_imo_number_before_bcp"))
        .withModeCode(new ModeCode().withValue("1"))
        .withUsedSpsTransportMeans(new UsedSpsTransportMeans()
            .withName(new TextType()
                .withValue("Transport means")));
  }

  @Test
  void map_ReturnsConcatenatedIdentificationString_WhenIdAndUsedSpsTransportMeansNonNull() {
    assertThat(mapper.map(transportMovement).getId()).isEqualTo("Identification, Transport means");
  }

  @Test
  void map_ReturnsIdValue_WhenNullUsedSpsTransportMeans() {
    transportMovement.setUsedSpsTransportMeans(null);

    assertThat(mapper.map(transportMovement).getId()).isEqualTo("Identification");
  }

  @Test
  void map_ReturnsIdValue_WhenBlankUsedSpsTransportMeans() {
    transportMovement.getUsedSpsTransportMeans().getName().setValue(" ");

    assertThat(mapper.map(transportMovement).getId()).isEqualTo("Identification");
  }

  @Test
  void map_ReturnsUsedSpsTransportMeansName_WhenIdIsNullAndUsedSpsTransportMeansIsNotNull() {
    transportMovement.getId().setValue(null);

    assertThat(mapper.map(transportMovement).getId()).isEqualTo("Transport means");
  }

  @Test
  void map_ReturnsUsedSpsTransportMeansName_WhenIdIsBlankAndUsedSpsTransportMeansIsNotNull() {
    transportMovement.getId().setValue(" ");

    assertThat(mapper.map(transportMovement).getId()).isEqualTo("Transport means");
  }

  @Test
  void map_ReturnsNull_WhenIdValueAndUsedSpsTransportMeansIsNull() {
    transportMovement.getId().setValue(null);
    transportMovement.setUsedSpsTransportMeans(null);

    assertThat(mapper.map(transportMovement).getId()).isNull();
  }
}
