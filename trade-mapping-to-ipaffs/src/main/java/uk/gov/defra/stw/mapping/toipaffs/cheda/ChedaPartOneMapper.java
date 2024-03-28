package uk.gov.defra.stw.mapping.toipaffs.cheda;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.CONSIGNEE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.DESTINATION;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.EXPORTER;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.IMPORTER;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.PRIVATE_TRANSPORTER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.dto.SpsPartyType;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.cheda.purpose.PortOfExitMapper;
import uk.gov.defra.stw.mapping.toipaffs.chedd.CheddCommoditiesMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.ArrivalDateMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.ArrivalTimeMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.DepartureDateMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.DepartureTimeMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.EconomicOperatorMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.MeansOfTransportFromEntryPointMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.MeansOfTransportMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.PointOfEntryMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.SealsContainersMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.SubmissionDateMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.TransportToBcpQuestionMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.VeterinaryInformationMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperator;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType;

@Component
public class ChedaPartOneMapper implements Mapper<SpsCertificate, PartOne> {

  private final EconomicOperatorMapper economicOperatorMapper;
  private final ChedaCommoditiesMapper chedaCommoditiesMapper;
  private final ChedaPurposeMapper chedaPurposeMapper;
  private final PortOfExitMapper portOfExitMapper;
  private final ChedaPointOfEntryMapper chedaPointOfEntryMapper;
  private final PortOfEntryMapper portOfEntryMapper;
  private final MeansOfTransportFromEntryPointMapper meansOfTransportFromEntryPointMapper;
  private final ArrivalDateMapper arrivalDateMapper;
  private final ArrivalTimeMapper arrivalTimeMapper;
  private final MeansOfTransportMapper meansOfTransportMapper;
  private final DepartureDateMapper departureDateMapper;
  private final DepartureTimeMapper departureTimeMapper;
  private final EstimatedJourneyTimeMapper estimatedJourneyTimeMapper;
  private final ChedaVeterinaryInformationMapper chedaVeterinaryInformationMapper;
  private final SealsContainersMapper sealsContainersMapper;
  private final IsGvmsRouteMapper isGvmsRouteMapper;
  private final SubmissionDateMapper submissionDateMapper;

  @Autowired
  public ChedaPartOneMapper(
      EconomicOperatorMapper economicOperatorMapper,
      ChedaCommoditiesMapper chedaCommoditiesMapper,
      ChedaPurposeMapper chedaPurposeMapper,
      PortOfExitMapper portOfExitMapper,
      ChedaPointOfEntryMapper chedaPointOfEntryMapper,
      PortOfEntryMapper portOfEntryMapper,
      MeansOfTransportFromEntryPointMapper meansOfTransportFromEntryPointMapper,
      ArrivalDateMapper arrivalDateMapper,
      ArrivalTimeMapper arrivalTimeMapper,
      MeansOfTransportMapper meansOfTransportMapper,
      DepartureDateMapper departureDateMapper,
      DepartureTimeMapper departureTimeMapper,
      EstimatedJourneyTimeMapper estimatedJourneyTimeMapper,
      ChedaVeterinaryInformationMapper chedaVeterinaryInformationMapper,
      SealsContainersMapper sealsContainersMapper,
      IsGvmsRouteMapper isGvmsRouteMapper,
      SubmissionDateMapper submissionDateMapper) {
    this.economicOperatorMapper = economicOperatorMapper;
    this.chedaCommoditiesMapper = chedaCommoditiesMapper;
    this.chedaPurposeMapper = chedaPurposeMapper;
    this.portOfExitMapper = portOfExitMapper;
    this.chedaPointOfEntryMapper = chedaPointOfEntryMapper;
    this.portOfEntryMapper = portOfEntryMapper;
    this.meansOfTransportFromEntryPointMapper = meansOfTransportFromEntryPointMapper;
    this.arrivalDateMapper = arrivalDateMapper;
    this.arrivalTimeMapper = arrivalTimeMapper;
    this.meansOfTransportMapper = meansOfTransportMapper;
    this.departureDateMapper = departureDateMapper;
    this.departureTimeMapper = departureTimeMapper;
    this.estimatedJourneyTimeMapper = estimatedJourneyTimeMapper;
    this.chedaVeterinaryInformationMapper = chedaVeterinaryInformationMapper;
    this.sealsContainersMapper = sealsContainersMapper;
    this.isGvmsRouteMapper = isGvmsRouteMapper;
    this.submissionDateMapper = submissionDateMapper;
  }

  @Override
  public PartOne map(SpsCertificate spsCertificate) throws NotificationMapperException {
    SpsConsignment spsConsignment = spsCertificate.getSpsConsignment();
    return PartOne.builder()
        .consignor(economicOperator(spsConsignment.getConsignorSpsParty(), EXPORTER))
        .consignee(economicOperator(spsConsignment.getConsigneeSpsParty(), CONSIGNEE))
        .importer(economicOperator(spsConsignment.getConsigneeSpsParty(), IMPORTER))
        .placeOfDestination(economicOperator(spsConsignment.getDeliverySpsParty(), DESTINATION))
        .commodities(chedaCommoditiesMapper.map(spsCertificate))
        .purpose(chedaPurposeMapper.map(spsCertificate))
        .portOfExit(portOfExitMapper.map(spsCertificate))
        .pointOfEntry(chedaPointOfEntryMapper.map(spsCertificate))
        .portOfEntry(portOfEntryMapper.map(spsCertificate))
        .transporter(economicOperator(spsConsignment.getCarrierSpsParty(), PRIVATE_TRANSPORTER))
        .meansOfTransportFromEntryPoint(meansOfTransportFromEntryPointMapper.map(spsCertificate))
        .arrivalDate(arrivalDateMapper.map(spsCertificate))
        .arrivalTime(arrivalTimeMapper.map(spsCertificate))
        .meansOfTransport(meansOfTransportMapper.map(spsCertificate))
        .departureDate(departureDateMapper.map(spsCertificate))
        .departureTime(departureTimeMapper.map(spsCertificate))
        .estimatedJourneyTimeInMinutes(estimatedJourneyTimeMapper.map(spsCertificate))
        .veterinaryInformation(chedaVeterinaryInformationMapper.map(spsCertificate))
        .sealsContainers(
            sealsContainersMapper.map(spsConsignment.getUtilizedSpsTransportEquipment()))
        .isGVMSRoute(isGvmsRouteMapper.map(spsCertificate))
        .submissionDate(submissionDateMapper.map(spsCertificate))
        .build();
  }

  private EconomicOperator economicOperator(SpsPartyType spsParty, EconomicOperatorType type)
      throws NotificationMapperException {
    return economicOperatorMapper.setEconomicOperatorType(
        economicOperatorMapper.map(spsParty),
        type);
  }
}
