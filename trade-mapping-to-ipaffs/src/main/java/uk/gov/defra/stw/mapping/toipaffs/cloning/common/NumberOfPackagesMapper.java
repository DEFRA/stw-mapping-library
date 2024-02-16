package uk.gov.defra.stw.mapping.toipaffs.cloning.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.commodities.NumberOfPackagesKeyDataMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

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
