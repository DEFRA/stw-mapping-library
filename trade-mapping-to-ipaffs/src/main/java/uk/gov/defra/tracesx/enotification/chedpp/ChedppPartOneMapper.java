package uk.gov.defra.tracesx.enotification.chedpp;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.EXPORTER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.common.chedpp.ChedppMeansOfTransportFromEntryPointMapper;
import uk.gov.defra.tracesx.common.chedpp.ChedppMeansOfTransportMapper;
import uk.gov.defra.tracesx.common.chedpp.ChedppPurposeMapper;
import uk.gov.defra.tracesx.common.chedpp.ChedppVeterinaryInformationMapper;
import uk.gov.defra.tracesx.common.common.PointOfEntryMapper;
import uk.gov.defra.tracesx.common.common.SealsContainersMapper;
import uk.gov.defra.tracesx.common.common.TransportToBcpQuestionMapper;
import uk.gov.defra.tracesx.enotification.common.EconomicOperatorMapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;
import uk.gov.defra.tracesx.trade.dto.SpsConsignment;
import uk.gov.defra.tracesx.trade.dto.SpsExchangedDocument;

@Component
public class ChedppPartOneMapper implements Mapper<SpsCertificate, PartOne> {

  private final ChedppPurposeMapper chedppPurposeMapper;
  private final EconomicOperatorMapper economicOperatorMapper;
  private final ChedppMeansOfTransportFromEntryPointMapper meansOfTransportFromEntryPointMapper;
  private final PointOfEntryMapper pointOfEntryMapper;
  private final ChedppVeterinaryInformationMapper chedppVeterinaryInformationMapper;
  private final SealsContainersMapper chedppSealsContainersMapper;
  private final ChedppMeansOfTransportMapper chedppMeansOfTransportMapper;
  private final ChedppCommoditiesMapper chedppCommoditiesMapper;
  private final TransportToBcpQuestionMapper transportToBcpQuestionMapper;

  @Autowired
  public ChedppPartOneMapper(ChedppPurposeMapper chedppPurposeMapper,
      EconomicOperatorMapper economicOperatorMapper,
      ChedppMeansOfTransportFromEntryPointMapper meansOfTransportFromEntryPointMapper,
      ChedppMeansOfTransportMapper chedppMeansOfTransportMapper,
      PointOfEntryMapper pointOfEntryMapper,
      ChedppVeterinaryInformationMapper chedppVeterinaryInformationMapper,
      SealsContainersMapper chedppSealsContainersMapper,
      ChedppCommoditiesMapper chedppCommoditiesMapper,
      TransportToBcpQuestionMapper transportToBcpQuestionMapper) {
    this.chedppPurposeMapper = chedppPurposeMapper;
    this.economicOperatorMapper = economicOperatorMapper;
    this.meansOfTransportFromEntryPointMapper = meansOfTransportFromEntryPointMapper;
    this.chedppMeansOfTransportMapper = chedppMeansOfTransportMapper;
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
            chedppMeansOfTransportMapper.map(spsConsignment.getMainCarriageSpsTransportMovement()))
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