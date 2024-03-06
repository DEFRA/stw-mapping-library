package uk.gov.defra.stw.mapping.toipaffs.chedpp;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.CONSIGNEE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.EXPORTER;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.IMPORTER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.EconomicOperatorMapper;
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
  private final ChedppMeansOfTransportMapper chedppMeansOfTransportMapper;
  private final ChedppCommoditiesMapper chedppCommoditiesMapper;
  private final TransportToBcpQuestionMapper transportToBcpQuestionMapper;
  private final ChedppPointOfEntryControlPointMapper chedppPointOfEntryControlPointMapper;

  @Autowired
  public ChedppPartOneMapper(ChedppPurposeMapper chedppPurposeMapper,
      EconomicOperatorMapper economicOperatorMapper,
      ChedppMeansOfTransportFromEntryPointMapper meansOfTransportFromEntryPointMapper,
      ChedppMeansOfTransportMapper chedppMeansOfTransportMapper,
      PointOfEntryMapper pointOfEntryMapper,
      ChedppVeterinaryInformationMapper chedppVeterinaryInformationMapper,
      SealsContainersMapper chedppSealsContainersMapper,
      ChedppCommoditiesMapper chedppCommoditiesMapper,
      TransportToBcpQuestionMapper transportToBcpQuestionMapper,
      ChedppPointOfEntryControlPointMapper chedppPointOfEntryControlPointMapper) {
    this.chedppPurposeMapper = chedppPurposeMapper;
    this.economicOperatorMapper = economicOperatorMapper;
    this.meansOfTransportFromEntryPointMapper = meansOfTransportFromEntryPointMapper;
    this.chedppMeansOfTransportMapper = chedppMeansOfTransportMapper;
    this.pointOfEntryMapper = pointOfEntryMapper;
    this.chedppVeterinaryInformationMapper = chedppVeterinaryInformationMapper;
    this.chedppSealsContainersMapper = chedppSealsContainersMapper;
    this.chedppCommoditiesMapper = chedppCommoditiesMapper;
    this.transportToBcpQuestionMapper = transportToBcpQuestionMapper;
    this.chedppPointOfEntryControlPointMapper = chedppPointOfEntryControlPointMapper;
  }

  @Override
  public PartOne map(SpsCertificate spsCertificate) throws NotificationMapperException {
    SpsConsignment spsConsignment = spsCertificate.getSpsConsignment();
    SpsExchangedDocument spsExchangedDocument = spsCertificate.getSpsExchangedDocument();

    return PartOne.builder()
        .purpose(chedppPurposeMapper.map(spsExchangedDocument.getSignatorySpsAuthentication()))
        .consignor(economicOperatorMapper.setEconomicOperatorType(
            economicOperatorMapper.map(spsConsignment.getConsignorSpsParty()), EXPORTER))
        .consignee(economicOperatorMapper.setEconomicOperatorType(
            economicOperatorMapper.map(spsConsignment.getConsigneeSpsParty()), CONSIGNEE))
        .importer(economicOperatorMapper.setEconomicOperatorType(
            economicOperatorMapper.map(spsConsignment.getConsigneeSpsParty()), IMPORTER))
        .placeOfDestination(economicOperatorMapper.setEconomicOperatorType(
            economicOperatorMapper.map(spsConsignment.getDeliverySpsParty()), EXPORTER))
        .pointOfEntry(pointOfEntryMapper.map(spsConsignment.getUnloadingBaseportSpsLocation()))
        .pointOfEntryControlPoint(chedppPointOfEntryControlPointMapper.map(
            spsConsignment.getUnloadingBaseportSpsLocation()))
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
        // TODO: arrivalDate, arrivalTime, submissionDate, submittedBy, departureDate, departureTime
        .build();
  }
}
