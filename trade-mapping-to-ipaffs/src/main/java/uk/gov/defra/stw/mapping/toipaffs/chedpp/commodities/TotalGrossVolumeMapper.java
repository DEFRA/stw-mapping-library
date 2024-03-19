package uk.gov.defra.stw.mapping.toipaffs.chedpp.commodities;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.MeasureType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;

@Component
public class TotalGrossVolumeMapper implements Mapper<SpsCertificate, BigDecimal> {

  @Override
  public BigDecimal map(SpsCertificate spsCertificate) {
    List<BigDecimal> grossVolumes = spsCertificate.getSpsConsignment()
        .getIncludedSpsConsignmentItem()
        .stream()
        .flatMap(consignmentItem -> consignmentItem.getIncludedSpsTradeLineItem().stream())
        .flatMap(tradeLineItem -> Optional.of(tradeLineItem)
            .map(IncludedSpsTradeLineItem::getGrossVolumeMeasure)
            .map(MeasureType::getValue)
            .map(BigDecimal::valueOf)
            .stream())
        .toList();
    return !grossVolumes.isEmpty()
        ? grossVolumes.stream().reduce(BigDecimal.ZERO, BigDecimal::add)
        : null;
  }
}
