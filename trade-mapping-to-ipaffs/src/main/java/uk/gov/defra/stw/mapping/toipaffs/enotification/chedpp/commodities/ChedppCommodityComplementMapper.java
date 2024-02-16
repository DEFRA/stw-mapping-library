package uk.gov.defra.stw.mapping.toipaffs.enotification.chedpp.commodities;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.ApplicableSpsClassification;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.enotification.chedpp.commodities.enums.ChedppCommodityComplementKeysEnum;
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
        .map(ChedppCommodityComplementMapper::buildCommodityComplement)
        .collect(Collectors.toList());
  }

  private static CommodityComplement buildCommodityComplement(final IncludedSpsTradeLineItem item) {
    return CommodityComplement.builder()
        .commodityID(mapCommodityId(item.getApplicableSpsClassification()))
        .eppoCode(mapEppoCode(item.getApplicableSpsClassification()))
        .complementID(item.getSequenceNumeric().getValue())
        .commodityDescription(mapDescription(item.getApplicableSpsClassification()))
        .complementName(mapScientificName(item.getScientificName()))
        .speciesName(mapScientificName(item.getScientificName()))
        .speciesNomination(mapScientificName(item.getScientificName()))
        .build();
  }

  private static String mapCommodityId(
      List<ApplicableSpsClassification> applicableSpsClassifications) {
    return applicableSpsClassifications.stream()
            .filter(ChedppCommodityComplementMapper::isCombinedNomenclature)
            .findFirst()
            .map(ApplicableSpsClassification::getClassCode)
            .map(CodeType::getValue)
            .orElseThrow(() -> new CommoditiesMapperException("Unable to map to the commodityID"));
  }

  private static String mapEppoCode(
      List<ApplicableSpsClassification> applicableSpsClassifications) {
    return applicableSpsClassifications.stream()
            .filter(ChedppCommodityComplementMapper::hasEppoCode)
            .findFirst()
            .map(ApplicableSpsClassification::getClassCode)
            .map(CodeType::getValue)
            .orElse(null);
  }

  private static String mapScientificName(List<TextType> scientificNames) {
    return scientificNames.stream()
        .filter(Objects::nonNull)
        .findFirst()
        .map(TextType::getValue)
        .orElse(StringUtils.EMPTY);
  }

  private static String mapDescription(
      List<ApplicableSpsClassification> applicableSpsClassifications) {
    return applicableSpsClassifications.stream()
            .filter(ChedppCommodityComplementMapper::hasDescription)
            .findFirst()
            .map(ApplicableSpsClassification::getClassName)
            .filter(textTypes -> !textTypes.isEmpty())
            .map(textTypes -> textTypes.get(0))
            .map(TextType::getValue)
            .orElseThrow(() -> new CommoditiesMapperException(
                "Commodity Description not found in the line item"));
  }

  private static boolean hasDescription(
      final ApplicableSpsClassification applicableSpsClassification) {
    return ChedppCommodityComplementKeysEnum.COMBINED_NOMENCLATURE.getValue()
            .equals(applicableSpsClassification.getSystemName().get(0).getValue())
            && applicableSpsClassification.getClassName().get(0).getValue() != null;
  }

  private static boolean isCombinedNomenclature(
      final ApplicableSpsClassification applicableSpsClassification) {
    return ChedppCommodityComplementKeysEnum.COMBINED_NOMENCLATURE.getValue()
            .equals(applicableSpsClassification.getSystemName().get(0).getValue())
            && applicableSpsClassification.getClassCode() != null;
  }

  private static boolean hasEppoCode(
      final ApplicableSpsClassification applicableSpsClassification) {
    return ChedppCommodityComplementKeysEnum.EPPO.getValue()
        .equals(returnEmptyStringForNullIdType(applicableSpsClassification.getSystemID()))
        && applicableSpsClassification.getClassCode() != null;
  }

  private static String returnEmptyStringForNullIdType(IDType idType) {
    if (idType != null && idType.getValue() != null) {
      return idType.getValue();
    }
    return StringUtils.EMPTY;
  }
}
