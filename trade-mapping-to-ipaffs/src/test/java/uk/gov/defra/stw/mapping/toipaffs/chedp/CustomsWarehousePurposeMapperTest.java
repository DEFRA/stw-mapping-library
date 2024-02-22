package uk.gov.defra.stw.mapping.toipaffs.chedp;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.stw.mapping.toipaffs.testutils.TestConstants.NON_CONFORMING_GOODS_DESTINATION_REGISTERED_NUMBER;
import static uk.gov.defra.stw.mapping.toipaffs.testutils.TestHelper.buildSpsNoteType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.ForNonConformingEnum;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum;

class CustomsWarehousePurposeMapperTest {

  private static final String REGISTERED_NUMBER = "12345";

  private SpsCertificate spsCertificate;

  private final CustomsWarehousePurposeMapper mapper = new CustomsWarehousePurposeMapper();

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

    // When
    Purpose actual = mapper.map(spsCertificate);

    // Then
    Purpose expected =
        Purpose.builder()
            .regNumber(REGISTERED_NUMBER)
            .purposeGroup(PurposeGroupEnum.NON_CONFORMING_CONSIGNMENTS)
            .forNonConforming(ForNonConformingEnum.CUSTOMS_WAREHOUSE)
            .build();

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void map_ReturnsCorrectPurpose_WhenRegistrationNumberIsNotPresent() {
    // Given / When
    Purpose actual = mapper.map(spsCertificate);

    // Then
    Purpose expected =
        Purpose.builder()
            .regNumber(null)
            .purposeGroup(PurposeGroupEnum.NON_CONFORMING_CONSIGNMENTS)
            .forNonConforming(ForNonConformingEnum.CUSTOMS_WAREHOUSE)
            .build();

    assertThat(actual).isEqualTo(expected);
  }
}
