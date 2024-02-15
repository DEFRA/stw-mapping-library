package uk.gov.defra.tracesx.cloning.chedp.commodities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.tracesx.common.common.commodities.NetWeightMeasureKeyDataMapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.tracesx.trade.dto.MeasureType;

@ExtendWith(MockitoExtension.class)
class ChedpNetWeightMeasureMapperTest {

  @Mock
  private NetWeightMeasureKeyDataMapper keyDataMapper;

  @InjectMocks
  private ChedpNetWeightMeasureMapper netWeightMeasureMapper;

  @Test
  void map_ReturnsKeyDataPair_WhenNetWeightMeasureIsNotNull()
      throws NotificationMapperException {
    when(keyDataMapper.map(createTradeLineItem())).thenReturn(createKeyDataPair());

    ComplementParameterSetKeyDataPair keyDataPair = netWeightMeasureMapper.map(createTradeLineItem());

    assertThat(keyDataPair.getKey()).isEqualTo("netweight");
    assertThat(keyDataPair.getData()).isEqualTo("100");
  }

  @Test
  void map_ReturnsNull_WhenNetWeightMeasureIsNull()
      throws NotificationMapperException {
    ComplementParameterSetKeyDataPair keyDataPair = netWeightMeasureMapper.map(new IncludedSpsTradeLineItem());

    assertThat(keyDataPair).isNull();
  }

  private IncludedSpsTradeLineItem createTradeLineItem() {
    return new IncludedSpsTradeLineItem().withNetWeightMeasure(new MeasureType().withValue(100.0));
  }

  private ComplementParameterSetKeyDataPair createKeyDataPair() {
    return new ComplementParameterSetKeyDataPair("netweight", "100");
  }
}
