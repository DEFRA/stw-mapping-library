package uk.gov.defra.stw.mapping.toipaffs.chedd.commodities;

import java.util.Map;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsClause;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.CommodityIntention;

@Component
public class CommodityIntendedForMapper implements Mapper<SpsCertificate, CommodityIntention> {
  private static final Map<String, CommodityIntention> COMMODITY_INTENTION_MAP = Map.of(
      "HUMAN_CONSUMPTION", CommodityIntention.HUMAN,
      "FEEDING_STUFF", CommodityIntention.FEEDINGSTUFF,
      "OTHER", CommodityIntention.OTHER,
      "FURTHER_PROCESS", CommodityIntention.FURTHER);

  @Override
  public CommodityIntention map(SpsCertificate spsCertificate) {
    return spsCertificate.getSpsExchangedDocument()
        .getSignatorySpsAuthentication()
        .get(0)
        .getIncludedSpsClause()
        .stream()
        .filter(clause -> clause.getId().getValue().equals("GOODS_CERTIFIED_AS"))
        .findAny()
        .map(IncludedSpsClause::getContent)
        .map(contents -> contents.get(0))
        .map(TextType::getValue)
        .map(COMMODITY_INTENTION_MAP::get)
        .orElse(null);
  }
}
