package uk.gov.defra.stw.mapping.toipaffs.common.commodities;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.ApplicableSpsClassification;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.CommoditiesMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement.CommodityComplementBuilder;

@Component
public class CommodityComplementUtil {

  private static final String IPAFFS_CCT = "IPAFFS_CCT";
  private static final String IPAFFS_CCF = "IPAFFS_CCF";
  private static final String IPAFFS_CCC = "IPAFFS_CCC";
  private static final String COMBINED_NOMENCLATURE = "CN Code (Combined Nomenclature)";

  public CommodityComplementBuilder createCommonCommodityComplement(
      IncludedSpsTradeLineItem tradeLineItem) {
    return CommodityComplement.builder()
        .complementID(tradeLineItem.getSequenceNumeric().getValue())
        .commodityID(mapCommodityId(tradeLineItem))
        .commodityDescription(mapDescription(tradeLineItem))
        .speciesName(mapScientificName(tradeLineItem))
        .speciesTypeName(mapSpeciesTypeName(tradeLineItem))
        .speciesClassName(mapSpeciesClassName(tradeLineItem))
        .speciesFamilyName(mapSpeciesFamilyName(tradeLineItem))
        .speciesNomination(mapScientificName(tradeLineItem))
        .complementName(mapScientificName(tradeLineItem));
  }

  private String mapCommodityId(IncludedSpsTradeLineItem tradeLineItem) {
    return tradeLineItem.getApplicableSpsClassification().stream()
        .filter(this::hasSystemNameCnCode)
        .findFirst()
        .map(ApplicableSpsClassification::getClassCode)
        .map(CodeType::getValue)
        .orElseThrow(() -> new CommoditiesMapperException("Unable to map the commodityID"));
  }

  private String mapDescription(IncludedSpsTradeLineItem tradeLineItem) {
    return tradeLineItem.getApplicableSpsClassification().stream()
        .filter(this::hasSystemNameCnCode)
        .findFirst()
        .map(ApplicableSpsClassification::getClassName)
        .filter(Predicate.not(List::isEmpty))
        .map(textTypes -> textTypes.get(0))
        .map(TextType::getValue)
        .orElseThrow(() -> new CommoditiesMapperException(
            "Unable to map the commodity description"));
  }

  private String mapSpeciesTypeName(IncludedSpsTradeLineItem tradeLineItem) {
    return tradeLineItem.getApplicableSpsClassification().stream()
        .filter(this::hasSystemNameIpaffsCct)
        .findFirst()
        .map(ApplicableSpsClassification::getClassName)
        .filter(Predicate.not(List::isEmpty))
        .map(textTypes -> textTypes.get(0))
        .map(TextType::getValue)
        .orElse(null);
  }

  private String mapSpeciesClassName(IncludedSpsTradeLineItem tradeLineItem) {
    return tradeLineItem.getApplicableSpsClassification().stream()
        .filter(this::hasSystemNameIpaffsCcc)
        .findFirst()
        .map(ApplicableSpsClassification::getClassName)
        .filter(Predicate.not(List::isEmpty))
        .map(textTypes -> textTypes.get(0))
        .map(TextType::getValue)
        .orElse(null);
  }

  private String mapSpeciesFamilyName(IncludedSpsTradeLineItem tradeLineItem) {
    return tradeLineItem.getApplicableSpsClassification().stream()
        .filter(this::hasSystemNameIpaffsCcf)
        .findFirst()
        .map(ApplicableSpsClassification::getClassName)
        .filter(Predicate.not(List::isEmpty))
        .map(textTypes -> textTypes.get(0))
        .map(TextType::getValue)
        .orElse(null);
  }

  private String mapScientificName(IncludedSpsTradeLineItem tradeLineItem) {
    return tradeLineItem.getScientificName().stream()
        .filter(Objects::nonNull)
        .findFirst()
        .map(TextType::getValue)
        .orElse(StringUtils.EMPTY);
  }

  private boolean hasSystemName(ApplicableSpsClassification applicableSpsClassification,
      String systemName) {
    return applicableSpsClassification.getSystemName().stream()
        .filter(Objects::nonNull)
        .anyMatch(actualSystemName -> Objects.equals(systemName, actualSystemName.getValue()));
  }

  private boolean hasSystemNameCnCode(ApplicableSpsClassification applicableSpsClassification) {
    return hasSystemName(applicableSpsClassification, COMBINED_NOMENCLATURE);
  }

  private boolean hasSystemNameIpaffsCct(ApplicableSpsClassification applicableSpsClassification) {
    return hasSystemName(applicableSpsClassification, IPAFFS_CCT);
  }

  private boolean hasSystemNameIpaffsCcc(ApplicableSpsClassification applicableSpsClassification) {
    return hasSystemName(applicableSpsClassification, IPAFFS_CCC);
  }

  private boolean hasSystemNameIpaffsCcf(ApplicableSpsClassification applicableSpsClassification) {
    return hasSystemName(applicableSpsClassification, IPAFFS_CCF);
  }
}
