package uk.gov.defra.stw.mapping.toipaffs.cloning.cheda.commodities;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.cloning.cheda.ChedaIdentifiersMapper;
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.CommoditySetKeyDataPairMapper;
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.NumberOfPackagesMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;

@Component
public class ChedaComplementParameterSetMapper implements
    Mapper<SpsCertificate, List<ComplementParameterSet>> {

  private final CommoditySetKeyDataPairMapper keyDataPairMapper;
  private final NumberOfPackagesMapper numberOfPackagesMapper;
  private final ChedaNumberOfAnimalsMapper chedaNumberOfAnimalsMapper;
  private final ChedaIdentifiersMapper chedaIdentifiersMapper;

  @Autowired
  public ChedaComplementParameterSetMapper(
      CommoditySetKeyDataPairMapper keyDataPairMapper,
      NumberOfPackagesMapper numberOfPackagesMapper,
      ChedaNumberOfAnimalsMapper chedaNumberOfAnimalsMapper,
      ChedaIdentifiersMapper chedaIdentifiersMapper) {
    this.keyDataPairMapper = keyDataPairMapper;
    this.numberOfPackagesMapper = numberOfPackagesMapper;
    this.chedaNumberOfAnimalsMapper = chedaNumberOfAnimalsMapper;
    this.chedaIdentifiersMapper = chedaIdentifiersMapper;
  }

  @Override
  public List<ComplementParameterSet> map(SpsCertificate data) throws NotificationMapperException {
    return data.getSpsConsignment().getIncludedSpsConsignmentItem().stream()
        .flatMap(item -> item.getIncludedSpsTradeLineItem().stream())
        .map(item ->
            ComplementParameterSet.builder()
                .uniqueComplementID(UUID.randomUUID())
                .complementID(item.getSequenceNumeric().getValue())
                .keyDataPair(
                    keyDataPairMapper.map(item, numberOfPackagesMapper, chedaNumberOfAnimalsMapper))
                .identifiers(chedaIdentifiersMapper.map(item))
                .build())
        .collect(Collectors.toList());
  }
}
