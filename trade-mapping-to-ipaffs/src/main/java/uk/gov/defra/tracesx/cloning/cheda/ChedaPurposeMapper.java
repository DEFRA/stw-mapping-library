package uk.gov.defra.tracesx.cloning.cheda;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.TRANSIT_TO_3RD_COUNTRY;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.trade.dto.IDType;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsClause;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;
import uk.gov.defra.tracesx.trade.dto.SpsCountryType;
import uk.gov.defra.tracesx.trade.dto.TextType;

@Component
public class ChedaPurposeMapper implements Mapper<SpsCertificate, Purpose> {

  private static final String PURPOSE = "PURPOSE";
  private static final String DIRECT_TRANSIT = "DIRECT_TRANSIT";
  private static final String GB = "GB";

  private final InternalMarketPurposeMapper internalMarketPurposeMapper;

  @Autowired
  public ChedaPurposeMapper(InternalMarketPurposeMapper internalMarketPurposeMapper) {
    this.internalMarketPurposeMapper = internalMarketPurposeMapper;
  }

  @Override
  public Purpose map(SpsCertificate spsCertificate) throws NotificationMapperException {
    if (!isPurposeDirectTransit(spsCertificate)) {
      return internalMarketPurposeMapper.map(spsCertificate);
    }
    List<SpsCountryType> transitSpsCountry = spsCertificate.getSpsConsignment()
        .getTransitSpsCountry();
    return Purpose.builder()
        .purposeGroup(TRANSIT_TO_3RD_COUNTRY)
        .transitThirdCountries(getTransitioningCountries(transitSpsCountry))
        .thirdCountry(spsCertificate.getSpsConsignment()
            .getImportSpsCountry().getId().getValue())
        .build();
  }

  private boolean isPurposeDirectTransit(SpsCertificate spsCertificate) {
    IncludedSpsClause includedSpsClause = spsCertificate.getSpsExchangedDocument()
        .getSignatorySpsAuthentication().get(0).getIncludedSpsClause().get(0);
    for (TextType textType : includedSpsClause.getContent()) {
      IDType id = includedSpsClause.getId();
      if (id != null && id.getValue().equals(PURPOSE)
              && textType.getValue().equals(DIRECT_TRANSIT)) {
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
