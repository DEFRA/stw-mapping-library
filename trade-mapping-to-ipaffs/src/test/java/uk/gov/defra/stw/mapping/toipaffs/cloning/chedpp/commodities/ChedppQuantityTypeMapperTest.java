package uk.gov.defra.stw.mapping.toipaffs.cloning.chedpp.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.MeasureType;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

class ChedppQuantityTypeMapperTest {

  private ChedppQuantityTypeMapper quantityTypeMapper;

  @BeforeEach
  void setup() {
    quantityTypeMapper = new ChedppQuantityTypeMapper();
  }

  @Test
  void map_ReturnsNull_WhenNoKnownQuantityType() throws NotificationMapperException {
    IncludedSpsTradeLineItem item = createTradeLineItem("Unknown");
    assertThat(quantityTypeMapper.map(item)).isNull();
  }

  @Test
  void map_ReturnsNull_WhenNullNetWeightMeasure() throws NotificationMapperException {
    assertThat(quantityTypeMapper.map(new IncludedSpsTradeLineItem())).isNull();
  }

  @Test
  void map_ReturnsKilograms_WhenQuantityTypeIsKGM() throws NotificationMapperException {
    IncludedSpsTradeLineItem item = createTradeLineItem("KGM");
    assertThat(quantityTypeMapper.map(item)).isEqualTo(buildKeyDataPair("Kilograms"));
  }

  @Test
  void map_ReturnsPieces_WhenQuantityTypeIsH87() throws NotificationMapperException {
    IncludedSpsTradeLineItem item = createTradeLineItem("H87");
    assertThat(quantityTypeMapper.map(item)).isEqualTo(buildKeyDataPair("Pieces"));
  }

  private IncludedSpsTradeLineItem createTradeLineItem(String unitCode) {
    IncludedSpsTradeLineItem item = new IncludedSpsTradeLineItem();
    MeasureType measureType = new MeasureType();
    measureType.setUnitCode(unitCode);
    item.setNetWeightMeasure(measureType);
    return item;
  }

  private ComplementParameterSetKeyDataPair buildKeyDataPair(String data) {
    return ComplementParameterSetKeyDataPair.builder()
        .key("type_quantity")
        .data(data)
        .build();
  }

}
