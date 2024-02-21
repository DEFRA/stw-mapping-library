package uk.gov.defra.stw.mapping.toipaffs.cloning.chedp;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsClause;
import uk.gov.defra.stw.mapping.dto.SpsAuthenticationType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.PurposeMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose;

@Component
public class ChedpPurposeMapper implements Mapper<SpsCertificate, Purpose> {

  private static final String GOODS_CERTIFIED_AS = "GOODS_CERTIFIED_AS";
  private static final String ANIMAL_FEEDING_STUFF = "ANIMAL_FEEDINGSTUFF";
  private static final String HUMAN_CONSUMPTION = "HUMAN_CONSUMPTION";
  private static final String PHARMACEUTICAL_USE = "PHARMACEUTICAL_USE";
  private static final String TECHNICAL_USE = "TECHNICAL_USE";
  private static final String OTHER = "OTHER";
  private static final char OLD_DELIMITER = '_';
  private static final char NEW_DELIMITER = ' ';

  private final PurposeMapper purposeMapper;

  private static final List<String> goodsCertifiedAsList = List.of(ANIMAL_FEEDING_STUFF,
      HUMAN_CONSUMPTION, PHARMACEUTICAL_USE, TECHNICAL_USE);

  @Autowired
  public ChedpPurposeMapper(PurposeMapper purposeMapper) {
    this.purposeMapper = purposeMapper;
  }

  @Override
  public Purpose map(SpsCertificate spsCertificate)
      throws NotificationMapperException {
    List<SpsAuthenticationType> spsAuthenticationTypeList = spsCertificate.getSpsExchangedDocument()
        .getSignatorySpsAuthentication();
    for (SpsAuthenticationType spsAuthenticationType : spsAuthenticationTypeList) {
      Purpose purpose = purposeMapper.map(spsAuthenticationType);
      if (purpose != null) {
        List<IncludedSpsClause> goodsCertifiedAsClause =
            getGoodsCertifiedAsClause(spsAuthenticationType.getIncludedSpsClause());

        if (!goodsCertifiedAsClause.isEmpty()) {
          purpose.setConformsToEU(Boolean.TRUE);
          purpose.setInternalMarketPurpose(transformInternalMarketPurpose(goodsCertifiedAsClause));

          return purpose;
        }
      }
    }

    throw new NotificationMapperException("Unable to map to the Purpose");
  }

  private List<IncludedSpsClause> getGoodsCertifiedAsClause(
      List<IncludedSpsClause> includedSpsClauseList) {
    return includedSpsClauseList.stream()
        .filter(p -> p.getId() != null)
        .filter(p -> p.getId().getValue().equals(GOODS_CERTIFIED_AS))
        .filter(p -> !p.getContent().isEmpty())
        .filter(p -> p.getContent().get(0).getValue() != null)
        .map(this::mapIncludedSpsClauseContent)
        .collect(Collectors.toList());
  }

  private IncludedSpsClause mapIncludedSpsClauseContent(IncludedSpsClause spsClause) {
    TextType clauseContent = spsClause.getContent().get(0);
    if (!goodsCertifiedAsList.contains(clauseContent.getValue())) {
      clauseContent.setValue(OTHER);
    }
    return spsClause;
  }

  private InternalMarketPurpose transformInternalMarketPurpose(List<IncludedSpsClause> spsClauses) {
    String internalMarketPurpose = spsClauses.get(0).getContent().get(0).getValue();

    if (internalMarketPurpose.equals(ANIMAL_FEEDING_STUFF)) {
      return InternalMarketPurpose.ANIMAL_FEEDING_STUFF;
    }

    internalMarketPurpose =
        internalMarketPurpose.replace(OLD_DELIMITER, NEW_DELIMITER);

    return InternalMarketPurpose.fromValue(internalMarketPurpose);
  }
}
