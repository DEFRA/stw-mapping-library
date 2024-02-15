package uk.gov.defra.tracesx.cloning.chedp.commodities;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.cloning.common.CommoditySetKeyDataPairMapper;
import uk.gov.defra.tracesx.cloning.common.NumberOfPackagesMapper;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsConsignmentItem;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

@Component
public class ChedpComplementParameterSetMapperImpl implements
    Mapper<SpsCertificate, List<ComplementParameterSet>> {

  private final CommoditySetKeyDataPairMapper keyDataPairMapper;
  private final ChedpNetWeightMeasureMapper chedpNetWeightMeasureMapper;
  private final NumberOfPackagesMapper numberOfPackagesMapper;
  private final ChedpPackageTypeMapper chedpPackageTypeMapper;

  @Autowired
  public ChedpComplementParameterSetMapperImpl(
      CommoditySetKeyDataPairMapper keyDataPairMapper,
      ChedpNetWeightMeasureMapper chedpNetWeightMeasureMapper,
      NumberOfPackagesMapper numberOfPackagesMapper,
      ChedpPackageTypeMapper chedpPackageTypeMapper
  ) {
    this.keyDataPairMapper = keyDataPairMapper;
    this.chedpNetWeightMeasureMapper = chedpNetWeightMeasureMapper;
    this.numberOfPackagesMapper = numberOfPackagesMapper;
    this.chedpPackageTypeMapper = chedpPackageTypeMapper;
  }

  @Override
  public List<ComplementParameterSet> map(SpsCertificate data) {
    List<IncludedSpsConsignmentItem> consignmentItems = data.getSpsConsignment()
        .getIncludedSpsConsignmentItem();

    return consignmentItems.stream()
        .flatMap(item -> item.getIncludedSpsTradeLineItem().stream())
        .map(item ->
            ComplementParameterSet.builder()
                .uniqueComplementID(UUID.randomUUID())
                .complementID(item.getSequenceNumeric().getValue())
                .keyDataPair(
                    keyDataPairMapper.map(item, chedpNetWeightMeasureMapper, numberOfPackagesMapper,
                        chedpPackageTypeMapper))
                .build())
        .collect(Collectors.toList());
  }
}
