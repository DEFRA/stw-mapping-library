package uk.gov.defra.stw.mapping.toipaffs.chedp;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsAuthenticationType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum;

@Component
public class InternalMarketPurposeMapper implements PurposeMapper {

  private static final String GOODS_CERTIFIED_AS = "GOODS_CERTIFIED_AS";

  @Override
  public Purpose map(SpsCertificate spsCertificate) {
    List<SpsAuthenticationType> spsAuthenticationTypeList =
        spsCertificate.getSpsExchangedDocument().getSignatorySpsAuthentication();

    InternalMarketPurpose internalMarketPurpose =
        getGoodsCertifiedAs(spsAuthenticationTypeList)
            .map(InternalMarketPurpose::valueOf)
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
        .filter(Objects::nonNull)
        .filter(spsClause -> GOODS_CERTIFIED_AS.equals(spsClause.getId().getValue()))
        .findFirst()
        .filter(includedSpsClause -> !includedSpsClause.getContent().isEmpty())
        .map(includedSpsClause -> includedSpsClause.getContent().get(0).getValue());
  }
}
