package uk.gov.defra.tracesx.cloning.cheda;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.Route;
import uk.gov.defra.tracesx.trade.dto.IDType;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsClause;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;
import uk.gov.defra.tracesx.trade.dto.SpsCountryType;
import uk.gov.defra.tracesx.trade.dto.TextType;

@Component
public class ChedaRouteMapper implements Mapper<SpsCertificate, Route> {

  private static final String PURPOSE = "PURPOSE";
  private static final String INTERNAL_MARKET = "INTERNAL_MARKET";
  private static final String GB = "GB";

  @Override
  public Route map(SpsCertificate spsCertificate) {
    IncludedSpsClause includedSpsClause = spsCertificate.getSpsExchangedDocument()
            .getSignatorySpsAuthentication().get(0).getIncludedSpsClause().get(0);
    List<SpsCountryType> transitSpsCountry = spsCertificate.getSpsConsignment()
        .getTransitSpsCountry();
    if (isPurposeInternalMarket(includedSpsClause) && !transitSpsCountry.isEmpty()) {
      List<String> transitingStates = new ArrayList<>();
      transitSpsCountry.forEach(spsCountryType -> transitingStates
          .add(spsCountryType.getId().getValue()));
      return Route.builder().transitingStates(getTransitioningCountries(transitSpsCountry)).build();
    }
    return null;
  }

  private boolean isPurposeInternalMarket(IncludedSpsClause includedSpsClause) {
    for (TextType textType : includedSpsClause.getContent()) {
      IDType id = includedSpsClause.getId();
      if (id != null && id.getValue().equals(PURPOSE)
          && textType.getValue().equals(INTERNAL_MARKET)) {
        return true;
      }
    }
    return false;
  }

  private List<String> getTransitioningCountries(List<SpsCountryType> transitSpsCountry) {
    return transitSpsCountry.stream()
        .filter(spsCountryType -> !spsCountryType.getId().getValue().equals(GB))
        .map(spsCountryType -> spsCountryType.getId().getValue())
        .collect(Collectors.toList());
  }
}
