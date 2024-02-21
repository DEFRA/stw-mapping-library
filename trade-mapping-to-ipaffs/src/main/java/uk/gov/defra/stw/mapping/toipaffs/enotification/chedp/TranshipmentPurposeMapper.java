package uk.gov.defra.stw.mapping.toipaffs.enotification.chedp;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.TRANSHIPMENT_TO;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.dto.SpsCountryType;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;

@Component
public class TranshipmentPurposeMapper implements PurposeMapper {

  @Override
  public Purpose map(SpsCertificate spsCertificate) {

    SpsConsignment spsConsignment = spsCertificate.getSpsConsignment();
    String finalBip = getFinalBip(spsConsignment.getTransitSpsCountry()).orElse(null);

    return Purpose.builder()
        .purposeGroup(TRANSHIPMENT_TO)
        .finalBIP(finalBip)
        .thirdCountryTranshipment(spsConsignment.getImportSpsCountry().getId().getValue())
        .build();
  }

  private Optional<String> getFinalBip(List<SpsCountryType> spsCountryTypeList) {
    return spsCountryTypeList.stream()
        .flatMap(country -> country.getSubordinateSpsCountrySubDivision().stream())
        .flatMap(
            spsCountrySubDivisionType ->
                spsCountrySubDivisionType.getActivityAuthorizedSpsParty().stream())
        .map(spsPartyType -> spsPartyType.getId().getValue())
        .findFirst();
  }
}
