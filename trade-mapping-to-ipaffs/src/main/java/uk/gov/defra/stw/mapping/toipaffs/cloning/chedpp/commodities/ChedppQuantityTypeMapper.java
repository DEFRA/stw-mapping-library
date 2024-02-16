package uk.gov.defra.stw.mapping.toipaffs.cloning.chedpp.commodities;

import java.util.Map;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

@Component
public class ChedppQuantityTypeMapper implements
    Mapper<IncludedSpsTradeLineItem, ComplementParameterSetKeyDataPair> {

  private static final Map<String, String> quantityTypeMap = Map.of(
      "KGM", "Kilograms",
      "H87", "Pieces");

  @Override
  public ComplementParameterSetKeyDataPair map(IncludedSpsTradeLineItem data)
      throws NotificationMapperException {
    if (data.getNetWeightMeasure() == null
        || (quantityTypeMap.get(data.getNetWeightMeasure().getUnitCode()) == null)) {
      return null;
    }

    return ComplementParameterSetKeyDataPair.builder()
        .key("type_quantity")
        .data(quantityTypeMap.get(data.getNetWeightMeasure().getUnitCode()))
        .build();
  }
}
