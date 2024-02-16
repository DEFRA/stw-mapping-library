package uk.gov.defra.stw.mapping.toipaffs.chedp;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.ForNonConformingEnum.CUSTOMS_WAREHOUSE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.NON_CONFORMING_CONSIGNMENTS;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.utils.PurposeHelper;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;

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
