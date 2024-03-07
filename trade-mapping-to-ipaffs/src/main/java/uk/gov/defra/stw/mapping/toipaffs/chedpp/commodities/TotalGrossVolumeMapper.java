package uk.gov.defra.stw.mapping.toipaffs.chedpp.commodities;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.MeasureType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;

@Component
public class TotalGrossVolumeMapper implements Mapper<SpsCertificate, BigDecimal> {

  @Override
  public BigDecimal map(SpsCertificate spsCertificate) {
    return spsCertificate.getSpsConsignment().getIncludedSpsConsignmentItem().stream()
        .flatMap(consignmentItem -> consignmentItem.getIncludedSpsTradeLineItem().stream())
        .map(IncludedSpsTradeLineItem::getGrossVolumeMeasure)
        .map(MeasureType::getValue)
        .map(BigDecimal::valueOf)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
