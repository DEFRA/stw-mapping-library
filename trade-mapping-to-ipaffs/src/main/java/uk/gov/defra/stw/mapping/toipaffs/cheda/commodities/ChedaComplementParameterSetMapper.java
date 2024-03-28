package uk.gov.defra.stw.mapping.toipaffs.cheda.commodities;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.NumberOfPackagesKeyDataMapper;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;

@Component
public class ChedaComplementParameterSetMapper implements
    Mapper<SpsCertificate, List<ComplementParameterSet>> {

  private final NumberOfPackagesKeyDataMapper numberOfPackagesKeyDataMapper;
  private final NumberOfAnimalsKeyDataMapper numberOfAnimalsKeyDataMapper;
  private final IdentifiersMapper identifiersMapper;

  @Autowired
  public ChedaComplementParameterSetMapper(
      NumberOfPackagesKeyDataMapper numberOfPackagesKeyDataMapper,
      NumberOfAnimalsKeyDataMapper numberOfAnimalsKeyDataMapper,
      IdentifiersMapper identifiersMapper) {
    this.numberOfPackagesKeyDataMapper = numberOfPackagesKeyDataMapper;
    this.numberOfAnimalsKeyDataMapper = numberOfAnimalsKeyDataMapper;
    this.identifiersMapper = identifiersMapper;
  }

  @Override
  public List<ComplementParameterSet> map(SpsCertificate spsCertificate) {
    return spsCertificate.getSpsConsignment().getIncludedSpsConsignmentItem().stream()
        .flatMap(consignmentItem -> consignmentItem.getIncludedSpsTradeLineItem().stream())
        .map(this::createParameterSet)
        .toList();
  }

  private ComplementParameterSet createParameterSet(IncludedSpsTradeLineItem tradeLineItem) {
    return ComplementParameterSet.builder()
        .uniqueComplementID(UUID.randomUUID())
        .complementID(tradeLineItem.getSequenceNumeric().getValue())
        .keyDataPair(List.of(
            numberOfPackagesKeyDataMapper.map(tradeLineItem.getPhysicalSpsPackage().get(0)),
            numberOfAnimalsKeyDataMapper.map(tradeLineItem)
        ))
        .identifiers(identifiersMapper.map(tradeLineItem))
        .build();
  }
}
