package uk.gov.defra.stw.mapping.toipaffs.common.commodities;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.MeasureType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;

@Component
public class TotalNetWeightMapper implements Mapper<SpsCertificate, BigDecimal> {

  @Override
  public BigDecimal map(SpsCertificate spsCertificate) {
    return spsCertificate.getSpsConsignment().getIncludedSpsConsignmentItem().stream()
        .flatMap(consignmentItem -> consignmentItem.getIncludedSpsTradeLineItem().stream())
        .map(IncludedSpsTradeLineItem::getNetWeightMeasure)
        .map(MeasureType::getValue)
        .map(BigDecimal::valueOf)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
