package uk.gov.defra.stw.mapping.toipaffs.chedpp.commodities;

import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.MeasureType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;

@Component
public class TotalGrossVolumeUnitMapper implements Mapper<SpsCertificate, String> {

  @Override
  public String map(SpsCertificate spsCertificate) throws NotificationMapperException {
    List<String> grossVolumeUnits = spsCertificate.getSpsConsignment()
        .getIncludedSpsConsignmentItem()
        .stream()
        .flatMap(consignmentItem -> consignmentItem.getIncludedSpsTradeLineItem().stream())
        .map(IncludedSpsTradeLineItem::getGrossVolumeMeasure)
        .map(MeasureType::getUnitCode)
        .toList();

    if (grossVolumeUnits.stream().allMatch(unit -> unit.equals(grossVolumeUnits.get(0)))) {
      return grossVolumeUnits.get(0);
    } else {
      throw new NotificationMapperException("Gross volume units are not all the same");
    }
  }
}
