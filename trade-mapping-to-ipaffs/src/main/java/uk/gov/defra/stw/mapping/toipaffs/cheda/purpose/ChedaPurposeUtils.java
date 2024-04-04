package uk.gov.defra.stw.mapping.toipaffs.cheda.purpose;

import java.util.Optional;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsCountrySubDivisionType;
import uk.gov.defra.stw.mapping.dto.SpsCountryType;
import uk.gov.defra.stw.mapping.dto.SpsPartyType;

public class ChedaPurposeUtils {

  public static final String APHA_BCP_ID = "GBAPHA1A";

  public static Optional<String> getPortOfExit(SpsCertificate spsCertificate) {
    return spsCertificate.getSpsConsignment().getTransitSpsCountry().stream()
        .filter(country -> country.getId().getValue().equals("GB"))
        .filter(ChedaPurposeUtils::hasPortOfExit)
        .findAny()
        .map(SpsCountryType::getSubordinateSpsCountrySubDivision)
        .map(subDivisions -> subDivisions.get(0))
        .map(SpsCountrySubDivisionType::getActivityAuthorizedSpsParty)
        .map(activityAuthorizedParties -> activityAuthorizedParties.get(0))
        .map(SpsPartyType::getId)
        .map(IDType::getValue);
  }

  public static String getExitBcp(SpsCertificate spsCertificate) {
    return spsCertificate.getSpsConsignment().getTransitSpsCountry().stream()
        .filter(country -> country.getId().getValue().equals("GB"))
        .findAny()
        .map(SpsCountryType::getSubordinateSpsCountrySubDivision)
        .map(subDivisions -> subDivisions.get(0))
        .map(SpsCountrySubDivisionType::getActivityAuthorizedSpsParty)
        .map(activityAuthorizedParties -> activityAuthorizedParties.get(0))
        .map(SpsPartyType::getId)
        .map(IDType::getValue)
        .orElse(null);
  }

  public static String getDestinationCountry(SpsCertificate spsCertificate) {
    return spsCertificate.getSpsConsignment().getImportSpsCountry().getId().getValue();
  }

  private static boolean hasPortOfExit(SpsCountryType country) {
    return Optional.of(country)
        .map(SpsCountryType::getSubordinateSpsCountrySubDivision)
        .map(subDivisions -> subDivisions.get(0))
        .map(SpsCountrySubDivisionType::getId)
        .map(IDType::getValue)
        .map(id -> id.equals("PORT_OF_EXIT"))
        .orElse(false);
  }
}
