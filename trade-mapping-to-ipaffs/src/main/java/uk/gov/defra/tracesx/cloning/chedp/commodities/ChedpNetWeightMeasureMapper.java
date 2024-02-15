package uk.gov.defra.tracesx.cloning.chedp.commodities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.common.common.commodities.NetWeightMeasureKeyDataMapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;

@Component
public class ChedpNetWeightMeasureMapper implements
    Mapper<IncludedSpsTradeLineItem, ComplementParameterSetKeyDataPair> {

  private final NetWeightMeasureKeyDataMapper keyDataMapper;

  @Autowired
  public ChedpNetWeightMeasureMapper(
      NetWeightMeasureKeyDataMapper keyDataMapper) {
    this.keyDataMapper = keyDataMapper;
  }

  @Override
  public ComplementParameterSetKeyDataPair map(IncludedSpsTradeLineItem includedSpsTradeLineItem)
      throws NotificationMapperException {
    return includedSpsTradeLineItem.getNetWeightMeasure() != null ? keyDataMapper.map(
        includedSpsTradeLineItem) : null;
  }
}
