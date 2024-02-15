package uk.gov.defra.tracesx.enotification.chedp;

import static uk.gov.defra.tracesx.enumeration.SubjectCode.NON_CONFORMING_GOODS_DESTINATION_SHIP_NAME;
import static uk.gov.defra.tracesx.enumeration.SubjectCode.NON_CONFORMING_GOODS_DESTINATION_SHIP_PORT;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.ForNonConformingEnum.SHIP;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.NON_CONFORMING_CONSIGNMENTS;
import static uk.gov.defra.tracesx.utils.SpsNoteTypeHelper.findSpsNoteByType;

import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;
import uk.gov.defra.tracesx.trade.dto.SpsNoteType;

@Component
public class ShipPurposeMapper implements PurposeMapper {

  @Override
  public Purpose map(SpsCertificate spsCertificate) {
    List<SpsNoteType> spsNoteTypes = spsCertificate.getSpsExchangedDocument().getIncludedSpsNote();

    return Purpose.builder()
        .purposeGroup(NON_CONFORMING_CONSIGNMENTS)
        .forNonConforming(SHIP)
        .shipName(getShipName(spsNoteTypes))
        .shipPort(getShipPort(spsNoteTypes))
        .build();
  }

  private String getShipName(List<SpsNoteType> spsNoteTypes) {
    return findSpsNoteByType(spsNoteTypes, NON_CONFORMING_GOODS_DESTINATION_SHIP_NAME.getValue())
        .map(spsNoteType -> spsNoteType.getContent().get(0).getValue())
        .orElse(null);
  }

  private String getShipPort(List<SpsNoteType> spsNoteTypes) {
    return findSpsNoteByType(spsNoteTypes, NON_CONFORMING_GOODS_DESTINATION_SHIP_PORT.getValue())
        .map(spsNoteType -> spsNoteType.getContent().get(0).getValue())
        .orElse(null);
  }
}
