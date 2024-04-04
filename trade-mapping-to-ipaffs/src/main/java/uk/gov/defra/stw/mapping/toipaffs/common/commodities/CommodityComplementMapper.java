package uk.gov.defra.stw.mapping.toipaffs.common.commodities;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;

@Component
public class CommodityComplementMapper implements
    Mapper<SpsCertificate, List<CommodityComplement>> {

  private final CommodityComplementUtil commodityComplementUtil;

  @Autowired
  public CommodityComplementMapper(CommodityComplementUtil commodityComplementUtil) {
    this.commodityComplementUtil = commodityComplementUtil;
  }

  @Override
  public List<CommodityComplement> map(SpsCertificate spsCertificate) {
    return spsCertificate.getSpsConsignment().getIncludedSpsConsignmentItem().stream()
        .flatMap(item -> item.getIncludedSpsTradeLineItem().stream())
        .filter(item -> item.getSequenceNumeric().getValue() != 0)
        .map(tradeLineItem ->
            commodityComplementUtil.createCommonCommodityComplement(tradeLineItem).build())
        .toList();
  }
}
