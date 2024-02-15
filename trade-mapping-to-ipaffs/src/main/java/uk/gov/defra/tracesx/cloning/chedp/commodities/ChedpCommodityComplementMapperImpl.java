package uk.gov.defra.tracesx.cloning.chedp.commodities;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.chedp.commodities.ChedpCommodityComplementMapper;
import uk.gov.defra.tracesx.common.common.commodities.CommodityComplementFieldMapper;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

@Component
public class ChedpCommodityComplementMapperImpl implements ChedpCommodityComplementMapper {

  private final CommodityComplementFieldMapper commodityComplementFieldMapper;

  @Autowired
  public ChedpCommodityComplementMapperImpl(
      CommodityComplementFieldMapper commodityComplementFieldMapper) {
    this.commodityComplementFieldMapper = commodityComplementFieldMapper;
  }

  @Override
  public List<CommodityComplement> map(SpsCertificate data) {
    return data.getSpsConsignment().getIncludedSpsConsignmentItem().stream()
        .flatMap(item -> item.getIncludedSpsTradeLineItem().stream())
        .filter(item -> item.getSequenceNumeric().getValue() != 0)
        .map(item ->
            CommodityComplement.builder()
                .commodityID(commodityComplementFieldMapper.mapCommodityId(item))
                .complementID(item.getSequenceNumeric().getValue())
                .commodityDescription(commodityComplementFieldMapper.mapDescription(
                    item.getApplicableSpsClassification()))
                .complementName(item.getScientificName().get(0).getValue())
                .speciesName(item.getScientificName().get(0).getValue())
                .speciesNomination(item.getScientificName().get(0).getValue())
                .speciesTypeName(commodityComplementFieldMapper.mapSpeciesTypeName(
                    item.getApplicableSpsClassification()))
                .build()
        ).collect(Collectors.toList());
  }
}
