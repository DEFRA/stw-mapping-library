package uk.gov.defra.stw.mapping.toipaffs.cheda.purpose;

import static java.util.function.Predicate.not;
import static uk.gov.defra.stw.mapping.toipaffs.cheda.purpose.ChedaPurposeUtils.APHA_BCP_ID;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.TRANSIT_TO_3RD_COUNTRY;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsCountryType;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose.PurposeBuilder;

@Component
public class ChedaTransitPurposeMapper implements Mapper<SpsCertificate, Purpose> {

  private static final String GB = "GB";

  @Override
  public Purpose map(SpsCertificate spsCertificate) {
    Optional<String> portOfExit = ChedaPurposeUtils.getPortOfExit(spsCertificate);
    PurposeBuilder purposeBuilder = Purpose.builder()
        .purposeGroup(TRANSIT_TO_3RD_COUNTRY)
        .thirdCountry(ChedaPurposeUtils.getDestinationCountry(spsCertificate))
        .transitThirdCountries(getTransitedCountries(spsCertificate));
    if (portOfExit.isPresent()) {
      return purposeBuilder
          .exitBIP(APHA_BCP_ID)
          .build();
    } else {
      return purposeBuilder
          .exitBIP(ChedaPurposeUtils.getExitBcp(spsCertificate))
          .build();
    }
  }

  private List<String> getTransitedCountries(SpsCertificate spsCertificate) {
    return spsCertificate.getSpsConsignment().getTransitSpsCountry().stream()
        .map(SpsCountryType::getId)
        .map(IDType::getValue)
        .filter(not(GB::equals))
        .toList();
  }
}
