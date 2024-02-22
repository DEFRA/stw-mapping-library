package uk.gov.defra.stw.mapping.toipaffs.common.commodities;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

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
