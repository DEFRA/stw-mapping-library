package uk.gov.defra.tracesx.enotification.chedp;

import static java.util.function.Predicate.not;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.TRANSIT_TO_3RD_COUNTRY;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.trade.dto.IDType;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;
import uk.gov.defra.tracesx.trade.dto.SpsConsignment;
import uk.gov.defra.tracesx.trade.dto.SpsCountryType;

@Component
public class TransitPurposeMapper implements PurposeMapper {

  private static final String GB = "GB";

  @Override
  public Purpose map(SpsCertificate spsCertificate) {

    SpsConsignment spsConsignment = spsCertificate.getSpsConsignment();
    String exitBip = getExitBip(spsConsignment.getTransitSpsCountry()).orElse(null);
    List<String> transitThirdCountries =
        getTransitThirdCountries(spsConsignment.getTransitSpsCountry());

    return Purpose.builder()
        .purposeGroup(TRANSIT_TO_3RD_COUNTRY)
        .exitBIP(exitBip)
        .transitThirdCountries(transitThirdCountries)
        .thirdCountry(spsConsignment.getImportSpsCountry().getId().getValue())
        .build();
  }

  private Optional<String> getExitBip(List<SpsCountryType> spsCountryTypes) {
    return spsCountryTypes.stream()
        .filter(spsCountryType -> spsCountryType.getId().getValue().equals(GB))
        .flatMap(spsCountryType -> spsCountryType.getSubordinateSpsCountrySubDivision().stream())
        .flatMap(subDivisionType -> subDivisionType.getActivityAuthorizedSpsParty().stream())
        .findFirst()
        .map(spsPartyType -> spsPartyType.getId().getValue());
  }

  private List<String> getTransitThirdCountries(List<SpsCountryType> spsCountryTypes) {
    return spsCountryTypes.stream()
        .map(SpsCountryType::getId)
        .map(IDType::getValue)
        .filter(not(GB::equals))
        .collect(Collectors.toList());
  }
}
