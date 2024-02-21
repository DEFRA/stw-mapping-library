package uk.gov.defra.stw.mapping.toipaffs.common.chedp.commodities;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsConsignmentItem;
import uk.gov.defra.stw.mapping.toipaffs.common.common.commodities.ComplementParameterSetMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.commodities.NetWeightMeasureKeyDataMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.commodities.NumberOfPackagesKeyDataMapper;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;

@Component
public class ChedpComplementParameterSetHelper {

  private final ComplementParameterSetMapper complementParameterSetMapper;
  private final NetWeightMeasureKeyDataMapper netWeightMeasureKeyDataMapper;
  private final NumberOfPackagesKeyDataMapper numberOfPackagesKeyDataMapper;
  private final ChedpPackageTypeKeyDataMapper chedpPackageTypeKeyDataMapper;

  public ChedpComplementParameterSetHelper(
      ComplementParameterSetMapper complementParameterSetMapper,
      NetWeightMeasureKeyDataMapper netWeightMeasureKeyDataMapper,
      NumberOfPackagesKeyDataMapper numberOfPackagesKeyDataMapper,
      ChedpPackageTypeKeyDataMapper chedpPackageTypeKeyDataMapper) {
    this.complementParameterSetMapper = complementParameterSetMapper;
    this.netWeightMeasureKeyDataMapper = netWeightMeasureKeyDataMapper;
    this.numberOfPackagesKeyDataMapper = numberOfPackagesKeyDataMapper;
    this.chedpPackageTypeKeyDataMapper = chedpPackageTypeKeyDataMapper;
  }

  public List<ComplementParameterSet> map(List<IncludedSpsConsignmentItem> consignmentItems) {
    return consignmentItems.stream()
        .flatMap(item -> item.getIncludedSpsTradeLineItem().stream())
        .map(item ->
            ComplementParameterSet.builder()
                .uniqueComplementID(UUID.randomUUID())
                .complementID(item.getSequenceNumeric().getValue())
                .keyDataPair(complementParameterSetMapper.create(netWeightMeasureKeyDataMapper,
                    numberOfPackagesKeyDataMapper, chedpPackageTypeKeyDataMapper, item))
                .build())
        .collect(Collectors.toList());
  }
}
