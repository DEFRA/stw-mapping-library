package uk.gov.defra.stw.mapping.toipaffs.cheda.commodities;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.MeasureType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;

@Component
public class NumberOfAnimalsMapper implements Mapper<SpsCertificate, Integer> {

  @Override
  public Integer map(SpsCertificate spsCertificate) {
    return spsCertificate.getSpsConsignment().getIncludedSpsConsignmentItem().stream()
        .flatMap(consignmentItem -> consignmentItem.getIncludedSpsTradeLineItem().stream())
        .map(IncludedSpsTradeLineItem::getNetVolumeMeasure)
        .map(MeasureType::getValue)
        .map(Double::intValue)
        .reduce(0, Integer::sum);
  }
}
