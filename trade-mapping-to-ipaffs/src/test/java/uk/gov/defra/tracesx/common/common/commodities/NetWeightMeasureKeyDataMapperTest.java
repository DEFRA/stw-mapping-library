package uk.gov.defra.tracesx.common.common.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.tracesx.trade.dto.MeasureType;

class NetWeightMeasureKeyDataMapperTest {

  @Test
  void map_ReturnsComplementParameterSetKeyDataPair_WhenComplete()
      throws NotificationMapperException {
    IncludedSpsTradeLineItem includedSpsTradeLineItem = new IncludedSpsTradeLineItem()
        .withNetWeightMeasure(new MeasureType()
            .withValue(12345678911234.12));

    ComplementParameterSetKeyDataPair expectedComplementParameterSetKeyDataPair =
        ComplementParameterSetKeyDataPair.builder()
            .key("netweight")
            .data("12345678911234.12")
            .build();

    assertThat(new NetWeightMeasureKeyDataMapper().map(includedSpsTradeLineItem))
        .isEqualTo(expectedComplementParameterSetKeyDataPair);
  }

  @Test
  void map_ReturnsNull_WhenMissingNetWeightMeasure()
      throws NotificationMapperException {
    assertThat(new NetWeightMeasureKeyDataMapper().map(new IncludedSpsTradeLineItem())).isNull();
  }
}
