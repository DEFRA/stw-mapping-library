package uk.gov.defra.stw.mapping.toipaffs.cloning.chedp.commodities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.commodities.NetWeightMeasureKeyDataMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

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
