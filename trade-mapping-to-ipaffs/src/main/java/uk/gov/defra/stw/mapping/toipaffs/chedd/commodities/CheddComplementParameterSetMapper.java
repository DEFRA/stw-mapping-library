package uk.gov.defra.stw.mapping.toipaffs.chedd.commodities;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.ComplementParameterSetMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.NetWeightMeasureKeyDataMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.NumberOfPackagesKeyDataMapper;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

@Component
public class CheddComplementParameterSetMapper
    implements Mapper<SpsCertificate, List<ComplementParameterSet>> {
  private final ComplementParameterSetMapper complementParameterSetMapper;
  private final NetWeightMeasureKeyDataMapper netWeightMeasureKeyDataMapper;
  private final NumberOfPackagesKeyDataMapper numberOfPackagesKeyDataMapper;
  private final CheddPackageTypeKeyDataMapper cheddPackageTypeKeyDataMapper;
  
  @Autowired
  public CheddComplementParameterSetMapper(
      ComplementParameterSetMapper complementParameterSetMapper,
      NetWeightMeasureKeyDataMapper netWeightMeasureKeyDataMapper,
      NumberOfPackagesKeyDataMapper numberOfPackagesKeyDataMapper,
      CheddPackageTypeKeyDataMapper cheddPackageTypeKeyDataMapper) {
    this.complementParameterSetMapper = complementParameterSetMapper;
    this.netWeightMeasureKeyDataMapper = netWeightMeasureKeyDataMapper;
    this.numberOfPackagesKeyDataMapper = numberOfPackagesKeyDataMapper;
    this.cheddPackageTypeKeyDataMapper = cheddPackageTypeKeyDataMapper;
  }

  @Override
  public List<ComplementParameterSet> map(SpsCertificate data) {
    return data.getSpsConsignment().getIncludedSpsConsignmentItem().stream()
        .flatMap(item -> item.getIncludedSpsTradeLineItem().stream())
        .map(this::createComplementParameterSet)
        .collect(Collectors.toList());
  }

  private ComplementParameterSet createComplementParameterSet(IncludedSpsTradeLineItem item) {
    List<ComplementParameterSetKeyDataPair> complementParameterSetKeyDataPairs = 
        complementParameterSetMapper.create(
            netWeightMeasureKeyDataMapper,
            numberOfPackagesKeyDataMapper,
            cheddPackageTypeKeyDataMapper,
            item);

    return ComplementParameterSet.builder()
        .uniqueComplementID(UUID.randomUUID())
        .complementID(item.getSequenceNumeric().getValue())
        .keyDataPair(complementParameterSetKeyDataPairs)
        .build();
  }
}
