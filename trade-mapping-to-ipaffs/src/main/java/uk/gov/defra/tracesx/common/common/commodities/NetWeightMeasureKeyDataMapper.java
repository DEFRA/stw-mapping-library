package uk.gov.defra.tracesx.common.common.commodities;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;

@Component
public class NetWeightMeasureKeyDataMapper implements
    Mapper<IncludedSpsTradeLineItem, ComplementParameterSetKeyDataPair> {

  @Override
  public ComplementParameterSetKeyDataPair map(IncludedSpsTradeLineItem data)
      throws NotificationMapperException {
    if (data.getNetWeightMeasure() == null) {
      return null;
    }

    return ComplementParameterSetKeyDataPair.builder()
        .key("netweight")
        .data(BigDecimal.valueOf(data.getNetWeightMeasure().getValue()).toPlainString())
        .build();
  }
}
