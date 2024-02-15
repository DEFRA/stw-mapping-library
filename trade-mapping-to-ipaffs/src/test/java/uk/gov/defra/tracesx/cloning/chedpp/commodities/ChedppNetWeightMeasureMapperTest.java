package uk.gov.defra.tracesx.cloning.chedpp.commodities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.tracesx.common.common.commodities.NetWeightMeasureKeyDataMapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;

@ExtendWith(MockitoExtension.class)
class ChedppNetWeightMeasureMapperTest {

  @Mock
  private ChedppQuantityTypeMapper quantityTypeMapper;

  @Mock
  private NetWeightMeasureKeyDataMapper netWeightMeasureKeyDataMapper;

  private IncludedSpsTradeLineItem includedSpsTradeLineItem;
  private ChedppNetWeightMeasureMapper chedppNetWeightMeasureMapper;

  @BeforeEach
  void setup() {
    includedSpsTradeLineItem = new IncludedSpsTradeLineItem();
    chedppNetWeightMeasureMapper =
        new ChedppNetWeightMeasureMapper(netWeightMeasureKeyDataMapper, quantityTypeMapper);
  }

  @Test
  void map_ReturnsNull_WhenQuantityTypeIsPieces() throws NotificationMapperException {
    when(quantityTypeMapper.map(includedSpsTradeLineItem)).thenReturn(buildKeyDataPair("type_quantity", "Pieces"));
    assertThat(chedppNetWeightMeasureMapper.map(includedSpsTradeLineItem)).isNull();
  }

  @Test
  void map_ReturnsNull_WhenQuantityTypeIsUnknown() throws NotificationMapperException {
    when(quantityTypeMapper.map(includedSpsTradeLineItem)).thenReturn(null);
    assertThat(chedppNetWeightMeasureMapper.map(includedSpsTradeLineItem)).isNull();
  }

  @Test
  void map_ReturnsNull_WhenNetWeightMapperReturnsNull() throws NotificationMapperException {
    when(quantityTypeMapper.map(includedSpsTradeLineItem)).thenReturn(buildKeyDataPair("type_quantity","Kilograms"));
    when(netWeightMeasureKeyDataMapper.map(includedSpsTradeLineItem)).thenReturn(null);
    assertThat(chedppNetWeightMeasureMapper.map(includedSpsTradeLineItem)).isNull();
  }

  @Test
  void map_ReturnsNetWeight_WhenQuantityTypeIsKilograms() throws NotificationMapperException {
    when(quantityTypeMapper.map(includedSpsTradeLineItem)).thenReturn(buildKeyDataPair("type_quantity","Kilograms"));
    when(netWeightMeasureKeyDataMapper.map(includedSpsTradeLineItem)).thenReturn(buildKeyDataPair("netweight", "567"));
    assertThat(chedppNetWeightMeasureMapper.map(includedSpsTradeLineItem)).isEqualTo(buildKeyDataPair("netweight", "567"));
  }

  private ComplementParameterSetKeyDataPair buildKeyDataPair(String key, String data) {
    return ComplementParameterSetKeyDataPair.builder()
        .key(key)
        .data(data)
        .build();
  }
}
