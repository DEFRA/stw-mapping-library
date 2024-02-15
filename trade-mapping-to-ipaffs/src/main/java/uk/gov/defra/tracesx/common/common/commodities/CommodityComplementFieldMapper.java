package uk.gov.defra.tracesx.common.common.commodities;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.exceptions.CommoditiesMapperException;
import uk.gov.defra.tracesx.trade.dto.ApplicableSpsClassification;
import uk.gov.defra.tracesx.trade.dto.CodeType;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.tracesx.trade.dto.TextType;

@Component
public class CommodityComplementFieldMapper {

  private static final String SPECIES_TYPE_NAME = "IPAFFS_CCT";
  private static final String SPECIES_FAMILY_NAME = "IPAFFS_CCF";
  private static final String SPECIES_CLASS_NAME = "IPAFFS_CCC";
  private static final String COMMODITY_CODE = "IPAFFS_CC";
  private static final String COMBINED_NOMENCLATURE = "CN Code (Combined Nomenclature)";

  public String mapDescription(List<ApplicableSpsClassification> applicableSpsClassifications) {
    return applicableSpsClassifications.stream()
        .filter(CommodityComplementFieldMapper::hasDescription)
        .findFirst()
        .map(ApplicableSpsClassification::getClassName)
        .map(textTypes -> textTypes.get(0))
        .map(TextType::getValue)
        .orElseThrow(() -> new CommoditiesMapperException(
            "Commodity Description not found in the line item"));
  }

  public String mapSpeciesTypeName(
      List<ApplicableSpsClassification> applicableSpsClassifications) {
    return applicableSpsClassifications.stream()
        .filter(CommodityComplementFieldMapper::hasSystemNameCct)
        .findFirst()
        .map(ApplicableSpsClassification::getClassName)
        .map(textTypes -> textTypes.get(0))
        .map(TextType::getValue)
        .orElse(null);
  }

  public String mapSpeciesClassName(
      List<ApplicableSpsClassification> applicableSpsClassifications) {
    return applicableSpsClassifications.stream()
        .filter(CommodityComplementFieldMapper::hasSystemNameCcc)
        .findFirst()
        .map(ApplicableSpsClassification::getClassName)
        .map(textTypes -> textTypes.get(0))
        .map(TextType::getValue)
        .orElse(null);
  }

  public String mapSpeciesFamilyName(
      List<ApplicableSpsClassification> applicableSpsClassifications) {
    return applicableSpsClassifications.stream()
        .filter(CommodityComplementFieldMapper::hasSystemNameCcf)
        .findFirst()
        .map(ApplicableSpsClassification::getClassName)
        .map(textTypes -> textTypes.get(0))
        .map(TextType::getValue)
        .orElse(null);
  }

  public String mapCommodityIdFromCnCode(
      List<ApplicableSpsClassification> applicableSpsClassification) {
    return mapCommodityId(applicableSpsClassification, this::isCombinedNomenclature);
  }

  public String mapCommodityIdFromIpaffsCode(
      List<ApplicableSpsClassification> applicableSpsClassification) {
    return mapCommodityId(applicableSpsClassification, this::isIpaffsCommodityCode);
  }

  public String mapCommodityId(IncludedSpsTradeLineItem includedSpsTradeLineItem) {
    boolean isIpaffsCode = includedSpsTradeLineItem.getApplicableSpsClassification().stream()
        .anyMatch(
            applicableSpsClassification -> applicableSpsClassification.getSystemName().get(0)
                .getValue()
                .equals(COMMODITY_CODE));

    return isIpaffsCode ? mapCommodityIdFromIpaffsCode(
        includedSpsTradeLineItem.getApplicableSpsClassification()) : mapCommodityIdFromCnCode(
        includedSpsTradeLineItem.getApplicableSpsClassification());
  }

  private static String mapCommodityId(
      List<ApplicableSpsClassification> applicableSpsClassification,
      Predicate<ApplicableSpsClassification> filterPredicate) {
    return applicableSpsClassification.stream()
        .filter(filterPredicate)
        .findFirst()
        .map(ApplicableSpsClassification::getClassCode)
        .map(CodeType::getValue)
        .orElseThrow(() -> new CommoditiesMapperException("Unable to map the commodityID"));
  }

  public String mapScientificName(List<TextType> scientificNames) {
    return scientificNames.stream()
        .filter(Objects::nonNull)
        .findFirst()
        .map(TextType::getValue)
        .orElse(StringUtils.EMPTY);
  }

  private static boolean hasSystemNameCct(
      final ApplicableSpsClassification applicableSpsClassification) {
    return applicableSpsClassification.getSystemName().stream()
        .filter(Objects::nonNull)
        .anyMatch(systemName -> SPECIES_TYPE_NAME.equals(systemName.getValue()));
  }

  private static boolean hasSystemNameCcc(
      final ApplicableSpsClassification applicableSpsClassification) {
    return applicableSpsClassification.getSystemName().stream()
        .filter(Objects::nonNull)
        .anyMatch(systemName -> SPECIES_CLASS_NAME.equals(systemName.getValue()));
  }

  private static boolean hasSystemNameCcf(
      final ApplicableSpsClassification applicableSpsClassification) {
    return applicableSpsClassification.getSystemName().stream()
        .filter(Objects::nonNull)
        .anyMatch(systemName -> SPECIES_FAMILY_NAME.equals(systemName.getValue()));
  }

  private static boolean hasDescription(
      final ApplicableSpsClassification applicableSpsClassification) {
    return applicableSpsClassification.getClassName().get(0).getValue() != null;
  }

  private boolean isCombinedNomenclature(
      ApplicableSpsClassification applicableSpsClassification) {
    return applicableSpsClassification.getSystemName().get(0).getValue()
        .equals(COMBINED_NOMENCLATURE);
  }

  private boolean isIpaffsCommodityCode(
      ApplicableSpsClassification applicableSpsClassification) {
    return applicableSpsClassification.getSystemName().get(0).getValue()
        .equals(COMMODITY_CODE);
  }
}
