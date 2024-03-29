package uk.gov.defra.stw.mapping.toipaffs.chedpp.commodities;

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
import uk.gov.defra.stw.mapping.toipaffs.exceptions.CommoditiesMapperException;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

@Component
public class ChedppComplementParameterSetMapper
    implements Mapper<SpsCertificate, List<ComplementParameterSet>> {

  private final ComplementParameterSetMapper complementParameterSetMapper;
  private final ChedppQuantityTypeMapper quantityTypeMapper;
  private final ChedppQuantityMapper quantityMapper;
  private final NetWeightMeasureKeyDataMapper netWeightMeasureKeyDataMapper;
  private final NumberOfPackagesKeyDataMapper numberOfPackagesKeyDataMapper;
  private final ChedppPackageTypeMapper chedppPackageTypeMapper;
  private final ChedppControlledAtmosphereContainerMapper containerMapper;
  private final FinishedOrPropagatedMapper finishedOrPropagatedMapper;
  private final VarietyMapper varietyMapper;
  private final ClassMapper classMapper;
  private final ChedppSequenceNumericMapper sequenceNumericMapper;

  @Autowired
  public ChedppComplementParameterSetMapper(
      ComplementParameterSetMapper complementParameterSetMapper,
      ChedppQuantityTypeMapper quantityTypeMapper,
      ChedppQuantityMapper quantityMapper,
      NetWeightMeasureKeyDataMapper netWeightMeasureKeyDataMapper,
      NumberOfPackagesKeyDataMapper numberOfPackagesKeyDataMapper,
      ChedppPackageTypeMapper chedppPackageTypeMapper,
      ChedppControlledAtmosphereContainerMapper containerMapper,
      FinishedOrPropagatedMapper finishedOrPropagatedMapper,
      VarietyMapper varietyMapper,
      ClassMapper classMapper,
      ChedppSequenceNumericMapper sequenceNumericMapper) {
    this.complementParameterSetMapper = complementParameterSetMapper;
    this.quantityTypeMapper = quantityTypeMapper;
    this.quantityMapper = quantityMapper;
    this.netWeightMeasureKeyDataMapper = netWeightMeasureKeyDataMapper;
    this.numberOfPackagesKeyDataMapper = numberOfPackagesKeyDataMapper;
    this.chedppPackageTypeMapper = chedppPackageTypeMapper;
    this.containerMapper = containerMapper;
    this.finishedOrPropagatedMapper = finishedOrPropagatedMapper;
    this.varietyMapper = varietyMapper;
    this.classMapper = classMapper;
    this.sequenceNumericMapper = sequenceNumericMapper;
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
            netWeightMeasureKeyDataMapper, numberOfPackagesKeyDataMapper, chedppPackageTypeMapper,
            item);

    addToParameterSetListIfNotNull(quantityMapper, item, complementParameterSetKeyDataPairs);
    addToParameterSetListIfNotNull(quantityTypeMapper, item, complementParameterSetKeyDataPairs);
    addToParameterSetListIfNotNull(containerMapper, item, complementParameterSetKeyDataPairs);
    addToParameterSetListIfNotNull(
        finishedOrPropagatedMapper, item, complementParameterSetKeyDataPairs);
    addToParameterSetListIfNotNull(varietyMapper, item, complementParameterSetKeyDataPairs);
    addToParameterSetListIfNotNull(classMapper, item, complementParameterSetKeyDataPairs);
    addToParameterSetListIfNotNull(sequenceNumericMapper, item, complementParameterSetKeyDataPairs);

    return ComplementParameterSet.builder()
        .uniqueComplementID(UUID.randomUUID())
        .complementID(item.getSequenceNumeric().getValue())
        .keyDataPair(complementParameterSetKeyDataPairs)
        .build();
  }

  private void addToParameterSetListIfNotNull(
      Mapper<IncludedSpsTradeLineItem, ComplementParameterSetKeyDataPair> mapper,
      IncludedSpsTradeLineItem item,
      List<ComplementParameterSetKeyDataPair> complementParameterSetKeyDataPairs) {
    try {
      ComplementParameterSetKeyDataPair complementParameterSetKeyDataPair = mapper.map(item);
      if (complementParameterSetKeyDataPair != null) {
        complementParameterSetKeyDataPairs.add(complementParameterSetKeyDataPair);
      }
    } catch (NotificationMapperException exception) {
      throw new CommoditiesMapperException(exception);
    }
  }
}
