package uk.gov.defra.stw.mapping.toipaffs.cloning.chedpp.commodities;

import java.util.Map;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.MeasureType;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

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
