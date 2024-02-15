package uk.gov.defra.tracesx.cloning.chedpp.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.tracesx.trade.dto.MeasureType;

class ChedppQuantityMapperTest {

  @Test
  void map_ReturnsComplementParameterSetKeyDataPairWithQuantity_WhenNetWeightIsDecimalNumberAndKilograms() {
    IncludedSpsTradeLineItem includedSpsTradeLineItem = createTradeLineItem(523.26, "KGM");
    ComplementParameterSetKeyDataPair expectedComplementParameterSetKeyDataPair = createKeyDataPair("523.26");

    assertThat(new ChedppQuantityMapper().map(includedSpsTradeLineItem))
        .isEqualTo(expectedComplementParameterSetKeyDataPair);
  }

  @Test
  void map_ReturnsComplementParameterSetKeyDataPairWithQuantity_WhenNetWeightIsDecimalNumberWithOneTrailingZeroAndKilograms() {
    IncludedSpsTradeLineItem includedSpsTradeLineItem = createTradeLineItem(523.0, "KGM");
    ComplementParameterSetKeyDataPair expectedComplementParameterSetKeyDataPair = createKeyDataPair("523.0");

    assertThat(new ChedppQuantityMapper().map(includedSpsTradeLineItem))
        .isEqualTo(expectedComplementParameterSetKeyDataPair);
  }

  @Test
  void map_ReturnsComplementParameterSetKeyDataPairWithQuantity_WhenNetWeightIsDecimalNumberWithManyTrailingZerosAndKilograms() {
    IncludedSpsTradeLineItem includedSpsTradeLineItem = createTradeLineItem(523.0003415, "KGM");
    ComplementParameterSetKeyDataPair expectedComplementParameterSetKeyDataPair = createKeyDataPair("523.0003415");

    assertThat(new ChedppQuantityMapper().map(includedSpsTradeLineItem))
        .isEqualTo(expectedComplementParameterSetKeyDataPair);
  }

  @Test
  void map_ReturnsComplementParameterSetKeyDataPairWithQuantity_WhenNetWeightIsDecimalNumberAndPieces() {
    IncludedSpsTradeLineItem includedSpsTradeLineItem = createTradeLineItem(523.26, "H87");
    ComplementParameterSetKeyDataPair expectedComplementParameterSetKeyDataPair = createKeyDataPair("523.26");

    assertThat(new ChedppQuantityMapper().map(includedSpsTradeLineItem))
        .isEqualTo(expectedComplementParameterSetKeyDataPair);
  }

  @Test
  void map_ReturnsComplementParameterSetKeyDataPairWithQuantity_WhenNetWeightIsDecimalNumberWithOneTrailingZeroAndPieces() {
    IncludedSpsTradeLineItem includedSpsTradeLineItem = createTradeLineItem(523.0, "H87");
    ComplementParameterSetKeyDataPair expectedComplementParameterSetKeyDataPair = createKeyDataPair("523");

    assertThat(new ChedppQuantityMapper().map(includedSpsTradeLineItem))
        .isEqualTo(expectedComplementParameterSetKeyDataPair);
  }

  @Test
  void map_ReturnsComplementParameterSetKeyDataPairWithQuantity_WhenNetWeightIsDecimalNumberWithManyTrailingZerosAndPieces() {
    IncludedSpsTradeLineItem includedSpsTradeLineItem = createTradeLineItem(523.0003415, "H87");
    ComplementParameterSetKeyDataPair expectedComplementParameterSetKeyDataPair = createKeyDataPair("523.0003415");

    assertThat(new ChedppQuantityMapper().map(includedSpsTradeLineItem))
        .isEqualTo(expectedComplementParameterSetKeyDataPair);
  }

  @Test
  void map_ReturnsNull_WhenMissingNetWeightMeasure() {
    assertThat(new ChedppQuantityMapper().map(new IncludedSpsTradeLineItem())).isNull();
  }

  @Test
  void map_ReturnsComplementParameterSetKeyDataPairWithQuantity_WhenNetWeightIsDecimalNumberButUnknownQuantityType() {
    IncludedSpsTradeLineItem includedSpsTradeLineItem = createTradeLineItem(523.26, "unknown");

    assertThat(new ChedppQuantityMapper().map(includedSpsTradeLineItem)).isNull();
  }

  private IncludedSpsTradeLineItem createTradeLineItem(double value, String unitCode) {
    return new IncludedSpsTradeLineItem()
        .withNetWeightMeasure(new MeasureType()
            .withValue(value)
            .withUnitCode(unitCode));
  }

  private ComplementParameterSetKeyDataPair createKeyDataPair(String data) {
    return ComplementParameterSetKeyDataPair.builder()
        .key("quantity")
        .data(data)
        .build();
  }
}
