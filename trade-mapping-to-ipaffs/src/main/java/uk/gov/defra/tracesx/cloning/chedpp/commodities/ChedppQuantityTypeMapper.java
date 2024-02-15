package uk.gov.defra.tracesx.cloning.chedpp.commodities;

import java.util.Map;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;

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
