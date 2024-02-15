package uk.gov.defra.tracesx.enotification.chedp;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.ForNonConformingEnum.CUSTOMS_WAREHOUSE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.NON_CONFORMING_CONSIGNMENTS;

import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;
import uk.gov.defra.tracesx.utils.PurposeHelper;

@Component
public class CustomsWarehousePurposeMapper implements PurposeMapper {

  @Override
  public Purpose map(SpsCertificate spsCertificate) {
    String registrationNumber =
        PurposeHelper.getRegistrationNumber(
            spsCertificate.getSpsExchangedDocument().getIncludedSpsNote()).orElse(null);

    return Purpose.builder()
        .purposeGroup(NON_CONFORMING_CONSIGNMENTS)
        .forNonConforming(CUSTOMS_WAREHOUSE)
        .regNumber(registrationNumber)
        .build();
  }

}
