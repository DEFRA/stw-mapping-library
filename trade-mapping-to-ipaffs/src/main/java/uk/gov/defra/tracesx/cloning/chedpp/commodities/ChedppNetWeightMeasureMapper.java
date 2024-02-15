package uk.gov.defra.tracesx.cloning.chedpp.commodities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.common.common.commodities.NetWeightMeasureKeyDataMapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;

@Component
public class ChedppNetWeightMeasureMapper implements
    Mapper<IncludedSpsTradeLineItem, ComplementParameterSetKeyDataPair> {

  private static final String PIECES = "Pieces";

  private final NetWeightMeasureKeyDataMapper netWeightMeasureKeyDataMapper;
  private final ChedppQuantityTypeMapper quantityTypeMapper;

  @Autowired
  public ChedppNetWeightMeasureMapper(
      NetWeightMeasureKeyDataMapper netWeightMeasureKeyDataMapper,
      ChedppQuantityTypeMapper quantityTypeMapper) {
    this.netWeightMeasureKeyDataMapper = netWeightMeasureKeyDataMapper;
    this.quantityTypeMapper = quantityTypeMapper;
  }

  @Override
  public ComplementParameterSetKeyDataPair map(IncludedSpsTradeLineItem data)
      throws NotificationMapperException {
    ComplementParameterSetKeyDataPair keyDataPair = quantityTypeMapper.map(data);
    String quantityType = keyDataPair != null ? keyDataPair.getData() : null;
    return PIECES.equals(quantityType) ? null : netWeightMeasureKeyDataMapper.map(data);
  }
}
