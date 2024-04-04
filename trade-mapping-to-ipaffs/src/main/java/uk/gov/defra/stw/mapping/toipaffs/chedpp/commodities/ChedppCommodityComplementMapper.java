package uk.gov.defra.stw.mapping.toipaffs.chedpp.commodities;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.ApplicableSpsClassification;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.chedpp.commodities.enums.ChedppCommodityComplementKeysEnum;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.CommodityComplementUtil;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;

@Component
public class ChedppCommodityComplementMapper implements
    Mapper<SpsCertificate, List<CommodityComplement>> {

  private final CommodityComplementUtil commodityComplementUtil;

  @Autowired
  public ChedppCommodityComplementMapper(
      CommodityComplementUtil commodityComplementUtil) {
    this.commodityComplementUtil = commodityComplementUtil;
  }

  @Override
  public List<CommodityComplement> map(SpsCertificate data) {
    return data.getSpsConsignment().getIncludedSpsConsignmentItem().stream()
        .flatMap(item -> item.getIncludedSpsTradeLineItem().stream())
        .filter(item -> item.getSequenceNumeric().getValue() != 0)
        .map(tradeLineItem ->
            commodityComplementUtil.createCommonCommodityComplement(tradeLineItem)
                .eppoCode(mapEppoCode(tradeLineItem.getApplicableSpsClassification()))
                .build())
        .toList();
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
