package uk.gov.defra.stw.mapping.toipaffs.cloning.chedpp.commodities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.commodities.NetWeightMeasureKeyDataMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

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
