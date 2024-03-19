package uk.gov.defra.stw.mapping.toipaffs.cheda.commodities;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsClause;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;

@Component
public class IncludeNonAblactedAnimalsMapper implements Mapper<SpsCertificate, Boolean> {

  @Override
  public Boolean map(SpsCertificate spsCertificate) {
    return spsCertificate.getSpsExchangedDocument()
        .getSignatorySpsAuthentication()
        .get(0)
        .getIncludedSpsClause()
        .stream()
        .filter(clause -> clause.getId().getValue().equals("INCLUDES_NON_ABLACTED_ANIMALS"))
        .findAny()
        .map(IncludedSpsClause::getContent)
        .map(contents -> contents.get(0))
        .map(TextType::getValue)
        .map(Boolean::parseBoolean)
        .orElse(null);
  }
}
