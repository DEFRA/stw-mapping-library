package uk.gov.defra.tracesx.cloning.chedp.commodities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.cloning.common.PhysicalSpsPackageMapper;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.common.chedp.commodities.ChedpPackageTypeKeyDataMapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;

@Component
public class ChedpPackageTypeMapper implements
    Mapper<IncludedSpsTradeLineItem, ComplementParameterSetKeyDataPair> {

  private final PhysicalSpsPackageMapper physicalSpsPackageMapper;
  private final ChedpPackageTypeKeyDataMapper keyDataMapper;

  @Autowired
  public ChedpPackageTypeMapper(
      PhysicalSpsPackageMapper physicalSpsPackageMapper,
      ChedpPackageTypeKeyDataMapper keyDataMapper) {
    this.physicalSpsPackageMapper = physicalSpsPackageMapper;
    this.keyDataMapper = keyDataMapper;
  }

  @Override
  public ComplementParameterSetKeyDataPair map(IncludedSpsTradeLineItem includedSpsTradeLineItem)
      throws NotificationMapperException {
    return physicalSpsPackageMapper.map(includedSpsTradeLineItem, keyDataMapper);
  }
}
