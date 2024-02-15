package uk.gov.defra.tracesx.enotification.chedp;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.tracesx.testutils.TestConstants.NON_CONFORMING_GOODS_DESTINATION_REGISTERED_NUMBER;
import static uk.gov.defra.tracesx.testutils.TestHelper.buildSpsNoteType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.ForNonConformingEnum;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;
import uk.gov.defra.tracesx.trade.dto.SpsExchangedDocument;

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
