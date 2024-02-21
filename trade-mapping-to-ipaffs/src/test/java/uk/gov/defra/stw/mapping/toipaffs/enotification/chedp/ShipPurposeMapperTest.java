package uk.gov.defra.stw.mapping.toipaffs.enotification.chedp;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.stw.mapping.toipaffs.testutils.TestConstants.NON_CONFORMING_GOODS_DESTINATION_SHIP_NAME;
import static uk.gov.defra.stw.mapping.toipaffs.testutils.TestConstants.NON_CONFORMING_GOODS_DESTINATION_SHIP_PORT;
import static uk.gov.defra.stw.mapping.toipaffs.testutils.TestHelper.buildSpsNoteType;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.ForNonConformingEnum;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum;

class ShipPurposeMapperTest {

  private static final String SHIP_NAME = "name";
  private static final String SHIP_PORT = "port";

  private SpsCertificate spsCertificate;

  private final ShipPurposeMapper mapper = new ShipPurposeMapper();

  @BeforeEach
  void setup() {
    spsCertificate = new SpsCertificate().withSpsExchangedDocument(new SpsExchangedDocument());
  }

  @Test
  void map_ReturnsCorrectPurpose_WhenShipNameAndShipPortArePresent() {
    // Given
    spsCertificate
        .getSpsExchangedDocument()
        .getIncludedSpsNote()
        .addAll(
            List.of(
                buildSpsNoteType(SHIP_NAME, NON_CONFORMING_GOODS_DESTINATION_SHIP_NAME),
                buildSpsNoteType(SHIP_PORT, NON_CONFORMING_GOODS_DESTINATION_SHIP_PORT)));

    // When
    Purpose actual = mapper.map(spsCertificate);

    // Then
    Purpose expected =
        Purpose.builder()
            .shipPort(SHIP_PORT)
            .shipName(SHIP_NAME)
            .forNonConforming(ForNonConformingEnum.SHIP)
            .purposeGroup(PurposeGroupEnum.NON_CONFORMING_CONSIGNMENTS)
            .build();

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void map_ReturnsCorrectPurpose_WhenShipNameAndShipPortAreNotPresent() {
    // Given / When
    Purpose actual = mapper.map(spsCertificate);

    // Then
    Purpose expected =
        Purpose.builder()
            .shipPort(null)
            .shipName(null)
            .forNonConforming(ForNonConformingEnum.SHIP)
            .purposeGroup(PurposeGroupEnum.NON_CONFORMING_CONSIGNMENTS)
            .build();

    assertThat(actual).isEqualTo(expected);
  }
}
