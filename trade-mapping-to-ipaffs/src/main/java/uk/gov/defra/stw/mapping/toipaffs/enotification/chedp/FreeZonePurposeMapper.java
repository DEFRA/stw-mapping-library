package uk.gov.defra.stw.mapping.toipaffs.enotification.chedp;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.ForNonConformingEnum.FREE_ZONE_OR_FREE_WAREHOUSE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.NON_CONFORMING_CONSIGNMENTS;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.utils.PurposeHelper;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;

@Component
public class FreeZonePurposeMapper implements PurposeMapper {

  @Override
  public Purpose map(SpsCertificate spsCertificate) {
    String registrationNumber =
        PurposeHelper.getRegistrationNumber(
            spsCertificate.getSpsExchangedDocument().getIncludedSpsNote()).orElse(null);
    return Purpose.builder()
        .purposeGroup(NON_CONFORMING_CONSIGNMENTS)
        .forNonConforming(FREE_ZONE_OR_FREE_WAREHOUSE)
        .regNumber(registrationNumber)
        .build();
  }
}
