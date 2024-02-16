package uk.gov.defra.stw.mapping.toipaffs.chedp.commodities;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.CommodityComplementFieldMapper;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;

@Component
public class ChedpCommodityComplementMapper implements
    Mapper<SpsCertificate, List<CommodityComplement>> {

  private final CommodityComplementFieldMapper commodityComplementFieldMapper;

  @Autowired
  public ChedpCommodityComplementMapper(
      CommodityComplementFieldMapper commodityComplementFieldMapper) {
    this.commodityComplementFieldMapper = commodityComplementFieldMapper;
  }

  @Override
  public List<CommodityComplement> map(SpsCertificate spsCertificate) {
    return spsCertificate.getSpsConsignment().getIncludedSpsConsignmentItem().stream()
        .flatMap(item -> item.getIncludedSpsTradeLineItem().stream())
        .filter(item -> item.getSequenceNumeric().getValue() != 0)
        .map(item ->
            CommodityComplement.builder()
                .commodityID(commodityComplementFieldMapper.mapCommodityIdFromCnCode(
                    item.getApplicableSpsClassification()))
                .complementID(item.getSequenceNumeric().getValue())
                .commodityDescription(commodityComplementFieldMapper.mapDescription(
                    item.getApplicableSpsClassification()))
                .complementName(commodityComplementFieldMapper
                    .mapScientificName(item.getScientificName()))
                .speciesName(commodityComplementFieldMapper
                    .mapScientificName(item.getScientificName()))
                .speciesTypeName(commodityComplementFieldMapper.mapSpeciesTypeName(
                    item.getApplicableSpsClassification()))
                .speciesClassName(commodityComplementFieldMapper.mapSpeciesClassName(
                    item.getApplicableSpsClassification()))
                .speciesFamilyName(commodityComplementFieldMapper.mapSpeciesFamilyName(
                    item.getApplicableSpsClassification()))
                .speciesNomination(commodityComplementFieldMapper
                    .mapScientificName(item.getScientificName()))
                .build()
        ).collect(Collectors.toList());
  }
}
