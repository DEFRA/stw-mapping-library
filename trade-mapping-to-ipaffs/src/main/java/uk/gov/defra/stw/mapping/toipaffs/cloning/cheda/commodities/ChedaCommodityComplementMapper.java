package uk.gov.defra.stw.mapping.toipaffs.cloning.cheda.commodities;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.commodities.CommodityComplementFieldMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;

@Component
public class ChedaCommodityComplementMapper implements
    Mapper<SpsCertificate, List<CommodityComplement>> {

  private final CommodityComplementFieldMapper commodityComplementFieldMapper;

  @Autowired
  public ChedaCommodityComplementMapper(
      CommodityComplementFieldMapper commodityComplementFieldMapper) {
    this.commodityComplementFieldMapper = commodityComplementFieldMapper;
  }

  @Override
  public List<CommodityComplement> map(SpsCertificate data) throws NotificationMapperException {
    return data.getSpsConsignment().getIncludedSpsConsignmentItem().stream()
        .flatMap(item -> item.getIncludedSpsTradeLineItem().stream())
        .filter(item -> item.getSequenceNumeric().getValue() != 0)
        .map(item ->
            CommodityComplement.builder()
                .commodityID(commodityComplementFieldMapper.mapCommodityIdFromIpaffsCode(
                    item.getApplicableSpsClassification()))
                .complementID(item.getSequenceNumeric().getValue())
                .commodityDescription(commodityComplementFieldMapper.mapDescription(
                    item.getApplicableSpsClassification()))
                .complementName(item.getScientificName().get(0).getValue())
                .speciesName(item.getScientificName().get(0).getValue())
                .speciesNomination(item.getScientificName().get(0).getValue())
                .build()
        ).collect(Collectors.toList());
  }
}
