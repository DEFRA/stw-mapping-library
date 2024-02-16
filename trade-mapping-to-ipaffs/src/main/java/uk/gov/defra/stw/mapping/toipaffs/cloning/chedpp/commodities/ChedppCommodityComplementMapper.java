package uk.gov.defra.stw.mapping.toipaffs.cloning.chedpp.commodities;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.ApplicableSpsClassification;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.toipaffs.cloning.chedpp.commodities.enums.ChedppCommodityComplementKeysEnum;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.CommoditiesMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;

@Component
public class ChedppCommodityComplementMapper implements
    Mapper<SpsCertificate, List<CommodityComplement>> {

  @Override
  public List<CommodityComplement> map(SpsCertificate data) {
    return data.getSpsConsignment().getIncludedSpsConsignmentItem().stream()
        .flatMap(item -> item.getIncludedSpsTradeLineItem().stream())
        .filter(item -> item.getSequenceNumeric().getValue() != 0)
        .map(this::buildCommodityComplement)
        .collect(Collectors.toList());
  }

  private CommodityComplement buildCommodityComplement(IncludedSpsTradeLineItem item) {
    return CommodityComplement.builder()
        .commodityID(mapCommodityId(item.getApplicableSpsClassification()))
        .complementID(item.getSequenceNumeric().getValue())
        .commodityDescription(mapDescription(item.getApplicableSpsClassification()))
        .complementName(mapScientificName(item.getScientificName()))
        .speciesName(mapScientificName(item.getScientificName()))
        .speciesNomination(mapScientificName(item.getScientificName()))
        .build();
  }

  private String mapCommodityId(List<ApplicableSpsClassification> applicableSpsClassifications) {
    return applicableSpsClassifications.stream()
        .filter(this::isIpaffsCommodityCode)
        .findFirst()
        .map(item -> item.getClassCode().getValue())
        .orElseThrow(() -> new CommoditiesMapperException("Unable to map to the commodityID"));
  }

  private String mapDescription(List<ApplicableSpsClassification> applicableSpsClassifications) {
    return applicableSpsClassifications.stream()
        .filter(this::hasDescription)
        .findFirst()
        .map(item -> item.getClassName().get(0).getValue())
        .orElseThrow(() -> new CommoditiesMapperException(
            "Commodity Description not found in the line item"));
  }

  private String mapScientificName(List<TextType> scientificNames) {
    return scientificNames.stream()
        .filter(Objects::nonNull)
        .findFirst()
        .map(TextType::getValue)
        .orElse(StringUtils.EMPTY);
  }

  private boolean hasDescription(
      final ApplicableSpsClassification applicableSpsClassification) {
    return ChedppCommodityComplementKeysEnum.COMMODITY_CODE.getValue()
            .equals(applicableSpsClassification.getSystemName().get(0).getValue())
            && applicableSpsClassification.getClassName().get(0).getValue() != null;
  }

  private boolean isIpaffsCommodityCode(
      final ApplicableSpsClassification applicableSpsClassification) {
    return ChedppCommodityComplementKeysEnum.COMMODITY_CODE.getValue()
            .equals(applicableSpsClassification.getSystemName().get(0).getValue())
            && applicableSpsClassification.getClassCode() != null;
  }
}
