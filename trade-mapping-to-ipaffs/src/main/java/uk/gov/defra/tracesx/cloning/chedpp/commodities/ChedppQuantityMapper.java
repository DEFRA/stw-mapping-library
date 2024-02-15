package uk.gov.defra.tracesx.cloning.chedpp.commodities;

import java.util.Map;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.tracesx.trade.dto.MeasureType;

@Component
public class ChedppQuantityMapper implements
    Mapper<IncludedSpsTradeLineItem, ComplementParameterSetKeyDataPair> {

  private static final String TRAILING_ZERO = ".0";

  private static final Map<String, String> quantityTypeMap = Map.of(
      "KGM", "Kilograms",
      "H87", "Pieces");

  @Override
  public ComplementParameterSetKeyDataPair map(IncludedSpsTradeLineItem data) {
    MeasureType tradeLineItemData = data.getNetWeightMeasure();

    if (tradeLineItemData == null
        || quantityTypeMap.get(tradeLineItemData.getUnitCode()) == null) {
      return null;
    }

    return ComplementParameterSetKeyDataPair.builder()
        .key("quantity")
        .data(getFormattedNetWeightMeasure(data))
        .build();
  }

  private String getFormattedNetWeightMeasure(IncludedSpsTradeLineItem data) {
    String quantityType = quantityTypeMap.get(data.getNetWeightMeasure().getUnitCode());
    if (quantityType.equals("Pieces")) {
      return stripTrailingZero(data.getNetWeightMeasure().getValue());
    }
    return data.getNetWeightMeasure().getValue().toString();
  }

  private String stripTrailingZero(Double value) {
    String valueString = String.valueOf(value);
    if (valueString.endsWith(TRAILING_ZERO)) {
      return valueString.replace(TRAILING_ZERO, "");
    }
    return valueString;
  }
}
