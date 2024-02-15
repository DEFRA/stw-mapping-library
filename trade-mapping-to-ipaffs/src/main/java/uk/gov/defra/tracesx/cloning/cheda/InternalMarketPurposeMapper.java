package uk.gov.defra.tracesx.cloning.cheda;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose.APPROVED_PREMISES;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose.BREEDING;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose.COMMERCIAL_SALE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose.COMPANION_ANIMAL;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose.FATTENING;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose.PRODUCTION;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose.RACING;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose.REGISTERED_HORSES;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose.RESEARCH;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose.SLAUGHTER;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum;
import uk.gov.defra.tracesx.trade.dto.SpsAuthenticationType;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

@Component
public class InternalMarketPurposeMapper implements Mapper<SpsCertificate, Purpose> {

  private static final String GOODS_CERTIFIED_AS = "GOODS_CERTIFIED_AS";

  private static final Map<String, InternalMarketPurpose> internalMarketPurposeMap = Map.ofEntries(
      Map.entry("APPROVED_BODIES", APPROVED_PREMISES),
      Map.entry("BREEDING", BREEDING),
      Map.entry("FATTENING", FATTENING),
      Map.entry("PETS", COMPANION_ANIMAL),
      Map.entry("REGISTERED_EQUIDAE", REGISTERED_HORSES),
      Map.entry("SLAUGHTER", SLAUGHTER),
      Map.entry("PRODUCTION", PRODUCTION),
      Map.entry("PRODUCTION_OF_PETFOOD", PRODUCTION),
      Map.entry("SALES", COMMERCIAL_SALE),
      Map.entry("RACING", RACING),
      Map.entry("COMPETITION", RACING),
      Map.entry("ORNAMENTAL_USE_RESEARCH", RESEARCH));

  @Override
  public Purpose map(SpsCertificate spsCertificate) {
    List<SpsAuthenticationType> spsAuthenticationTypeList =
        spsCertificate.getSpsExchangedDocument().getSignatorySpsAuthentication();

    InternalMarketPurpose internalMarketPurpose =
        getGoodsCertifiedAs(spsAuthenticationTypeList)
            .map(internalMarketPurposeMap::get)
            .orElse(null);

    return Purpose.builder()
        .purposeGroup(PurposeGroupEnum.IMPORT)
        .internalMarketPurpose(internalMarketPurpose)
        .build();
  }

  private Optional<String> getGoodsCertifiedAs(
      List<SpsAuthenticationType> spsAuthenticationTypeList) {
    return spsAuthenticationTypeList.stream()
        .flatMap(spsAuthenticationType -> spsAuthenticationType.getIncludedSpsClause().stream())
        .filter(spsClause -> GOODS_CERTIFIED_AS.equals(spsClause.getId().getValue()))
        .findFirst()
        .filter(includedSpsClause -> !includedSpsClause.getContent().isEmpty())
        .map(includedSpsClause -> includedSpsClause.getContent().get(0).getValue());
  }
}
