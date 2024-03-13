package uk.gov.defra.stw.mapping.toipaffs.chedpp;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.EXPORTER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.EconomicOperatorMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.MeansOfTransportMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.PointOfEntryMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.SealsContainersMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.TransportToBcpQuestionMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;

@Component
public class ChedppPartOneMapper implements Mapper<SpsCertificate, PartOne> {

  private final ChedppPurposeMapper chedppPurposeMapper;
  private final EconomicOperatorMapper economicOperatorMapper;
  private final ChedppMeansOfTransportFromEntryPointMapper meansOfTransportFromEntryPointMapper;
  private final PointOfEntryMapper pointOfEntryMapper;
  private final ChedppVeterinaryInformationMapper chedppVeterinaryInformationMapper;
  private final SealsContainersMapper chedppSealsContainersMapper;
  private final MeansOfTransportMapper meansOfTransportMapper;
  private final ChedppCommoditiesMapper chedppCommoditiesMapper;
  private final TransportToBcpQuestionMapper transportToBcpQuestionMapper;

  @Autowired
  public ChedppPartOneMapper(ChedppPurposeMapper chedppPurposeMapper,
      EconomicOperatorMapper economicOperatorMapper,
      ChedppMeansOfTransportFromEntryPointMapper meansOfTransportFromEntryPointMapper,
      MeansOfTransportMapper meansOfTransportMapper,
      PointOfEntryMapper pointOfEntryMapper,
      ChedppVeterinaryInformationMapper chedppVeterinaryInformationMapper,
      SealsContainersMapper chedppSealsContainersMapper,
      ChedppCommoditiesMapper chedppCommoditiesMapper,
      TransportToBcpQuestionMapper transportToBcpQuestionMapper) {
    this.chedppPurposeMapper = chedppPurposeMapper;
    this.economicOperatorMapper = economicOperatorMapper;
    this.meansOfTransportFromEntryPointMapper = meansOfTransportFromEntryPointMapper;
    this.meansOfTransportMapper = meansOfTransportMapper;
    this.pointOfEntryMapper = pointOfEntryMapper;
    this.chedppVeterinaryInformationMapper = chedppVeterinaryInformationMapper;
    this.chedppSealsContainersMapper = chedppSealsContainersMapper;
    this.chedppCommoditiesMapper = chedppCommoditiesMapper;
    this.transportToBcpQuestionMapper = transportToBcpQuestionMapper;
  }

  @Override
  public PartOne map(SpsCertificate spsCertificate) throws NotificationMapperException {
    SpsConsignment spsConsignment = spsCertificate.getSpsConsignment();
    SpsExchangedDocument spsExchangedDocument = spsCertificate.getSpsExchangedDocument();

    return PartOne.builder()
        .purpose(chedppPurposeMapper.map(spsExchangedDocument.getSignatorySpsAuthentication()))
        .consignor(economicOperatorMapper.setEconomicOperatorType(
            economicOperatorMapper.map(spsConsignment.getConsignorSpsParty()), EXPORTER))
        .placeOfDestination(economicOperatorMapper.setEconomicOperatorType(
            economicOperatorMapper.map(spsConsignment.getDeliverySpsParty()), EXPORTER))
        .pointOfEntry(pointOfEntryMapper.map(spsConsignment.getUnloadingBaseportSpsLocation()))
        .meansOfTransportFromEntryPoint(
            meansOfTransportFromEntryPointMapper.map(
                spsConsignment.getMainCarriageSpsTransportMovement()))
        .meansOfTransport(
            meansOfTransportMapper.map(spsCertificate))
        .veterinaryInformation(
            chedppVeterinaryInformationMapper.map(
                spsExchangedDocument.getReferenceSpsReferencedDocument()))
        .sealsContainers(
            chedppSealsContainersMapper.map(spsConsignment.getUtilizedSpsTransportEquipment()))
        .commodities(chedppCommoditiesMapper.map(spsCertificate))
        .transporterDetailsRequired(transportToBcpQuestionMapper.map(spsConsignment))
        .build();
  }
}
