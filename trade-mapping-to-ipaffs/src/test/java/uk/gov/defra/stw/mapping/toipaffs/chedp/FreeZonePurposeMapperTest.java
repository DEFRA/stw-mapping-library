package uk.gov.defra.stw.mapping.toipaffs.chedp;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.stw.mapping.toipaffs.testutils.TestConstants.NON_CONFORMING_GOODS_DESTINATION_REGISTERED_NUMBER;
import static uk.gov.defra.stw.mapping.toipaffs.testutils.TestHelper.buildSpsNoteType;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.ForNonConformingEnum.FREE_ZONE_OR_FREE_WAREHOUSE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.NON_CONFORMING_CONSIGNMENTS;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;

class FreeZonePurposeMapperTest {

  private static final String REGISTERED_NUMBER = "12345";
  private final FreeZonePurposeMapper mapper = new FreeZonePurposeMapper();
  private SpsCertificate spsCertificate;

  @BeforeEach
  void setup() {
    spsCertificate = new SpsCertificate().withSpsExchangedDocument(new SpsExchangedDocument());
  }

  @Test
  void map_ReturnsCorrectPurpose_WhenRegistrationNumberIsPresent() {
    // Given
    spsCertificate
        .getSpsExchangedDocument()
        .getIncludedSpsNote()
        .add(
            buildSpsNoteType(
                REGISTERED_NUMBER, NON_CONFORMING_GOODS_DESTINATION_REGISTERED_NUMBER));

    Purpose expected = Purpose.builder()
        .purposeGroup(NON_CONFORMING_CONSIGNMENTS)
        .forNonConforming(FREE_ZONE_OR_FREE_WAREHOUSE).regNumber(REGISTERED_NUMBER).build();

    // When
    Purpose purpose = mapper.map(spsCertificate);

    // Then
    assertThat(purpose).isEqualTo(expected);
  }

  @Test
  void map_ReturnsCorrectPurpose_WhenRegistrationNumberIsNotPresent() {
    // Given / When
    Purpose expected = Purpose.builder()
        .purposeGroup(NON_CONFORMING_CONSIGNMENTS)
        .forNonConforming(FREE_ZONE_OR_FREE_WAREHOUSE).build();

    Purpose purpose = mapper.map(spsCertificate);

    // Then
    assertThat(purpose).isEqualTo(expected);
  }
}
