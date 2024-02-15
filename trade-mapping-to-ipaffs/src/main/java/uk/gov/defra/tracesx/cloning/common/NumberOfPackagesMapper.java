package uk.gov.defra.tracesx.cloning.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.common.common.commodities.NumberOfPackagesKeyDataMapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;

@Component
public class NumberOfPackagesMapper implements
    Mapper<IncludedSpsTradeLineItem, ComplementParameterSetKeyDataPair> {

  private final PhysicalSpsPackageMapper physicalSpsPackageMapper;
  private final NumberOfPackagesKeyDataMapper keyDataMapper;

  @Autowired
  public NumberOfPackagesMapper(
      PhysicalSpsPackageMapper physicalSpsPackageMapper,
      NumberOfPackagesKeyDataMapper keyDataMapper) {
    this.physicalSpsPackageMapper = physicalSpsPackageMapper;
    this.keyDataMapper = keyDataMapper;
  }

  @Override
  public ComplementParameterSetKeyDataPair map(IncludedSpsTradeLineItem includedSpsTradeLineItem)
      throws NotificationMapperException {
    return physicalSpsPackageMapper.map(includedSpsTradeLineItem, keyDataMapper);
  }
}
