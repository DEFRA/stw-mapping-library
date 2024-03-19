package uk.gov.defra.stw.mapping.toipaffs.chedpp.commodities;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.MeasureType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;

@Component
public class TotalGrossVolumeUnitMapper implements Mapper<SpsCertificate, String> {

  private static final Map<String, String> UNIT_TYPE_MAP = Map.of(
      "LTR", "litres",
      "MTQ", "metres cubed"
  );

  @Override
  public String map(SpsCertificate spsCertificate) throws NotificationMapperException {
    List<String> grossVolumeUnits = spsCertificate.getSpsConsignment()
        .getIncludedSpsConsignmentItem()
        .stream()
        .flatMap(consignmentItem -> consignmentItem.getIncludedSpsTradeLineItem().stream())
        .flatMap(tradeLineItem -> Optional.of(tradeLineItem)
            .map(IncludedSpsTradeLineItem::getGrossVolumeMeasure)
            .map(MeasureType::getUnitCode)
            .stream())
        .toList();

    if (grossVolumeUnits.isEmpty()) {
      return null;
    }

    if (grossVolumeUnits.stream().allMatch(unit -> unit.equals(grossVolumeUnits.get(0)))) {
      String unitType = grossVolumeUnits.get(0);
      return UNIT_TYPE_MAP.get(unitType);
    } else {
      throw new NotificationMapperException("Gross volume units are not all the same");
    }
  }
}
