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
import uk.gov.defra.stw.mapping.toipaffs.common.ArrivalDateMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.ArrivalTimeMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.DepartureDateMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.DepartureTimeMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.EconomicOperatorMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.MeansOfTransportFromEntryPointMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.MeansOfTransportMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.PointOfEntryMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.SealsContainersMapper;
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
  private final PointOfEntryMapper pointOfEntryMapper;
  private final MeansOfTransportFromEntryPointMapper meansOfTransportFromEntryPointMapper;
  private final ArrivalDateMapper arrivalDateMapper;
  private final ArrivalTimeMapper arrivalTimeMapper;
  private final MeansOfTransportMapper meansOfTransportMapper;
  private final DepartureDateMapper departureDateMapper;
  private final DepartureTimeMapper departureTimeMapper;
  private final EstimatedJourneyTimeMapper estimatedJourneyTimeMapper;
  private final SealsContainersMapper sealsContainersMapper;

  @Autowired
  public ChedaPartOneMapper(
      EconomicOperatorMapper economicOperatorMapper,
      ChedaCommoditiesMapper chedaCommoditiesMapper,
      ChedaPurposeMapper chedaPurposeMapper,
      PortOfExitMapper portOfExitMapper,
      PointOfEntryMapper pointOfEntryMapper,
      MeansOfTransportFromEntryPointMapper meansOfTransportFromEntryPointMapper,
      ArrivalDateMapper arrivalDateMapper,
      ArrivalTimeMapper arrivalTimeMapper,
      MeansOfTransportMapper meansOfTransportMapper,
      DepartureDateMapper departureDateMapper,
      DepartureTimeMapper departureTimeMapper,
      EstimatedJourneyTimeMapper estimatedJourneyTimeMapper,
      SealsContainersMapper sealsContainersMapper) {
    this.economicOperatorMapper = economicOperatorMapper;
    this.chedaCommoditiesMapper = chedaCommoditiesMapper;
    this.chedaPurposeMapper = chedaPurposeMapper;
    this.portOfExitMapper = portOfExitMapper;
    this.pointOfEntryMapper = pointOfEntryMapper;
    this.meansOfTransportFromEntryPointMapper = meansOfTransportFromEntryPointMapper;
    this.arrivalDateMapper = arrivalDateMapper;
    this.arrivalTimeMapper = arrivalTimeMapper;
    this.meansOfTransportMapper = meansOfTransportMapper;
    this.departureDateMapper = departureDateMapper;
    this.departureTimeMapper = departureTimeMapper;
    this.estimatedJourneyTimeMapper = estimatedJourneyTimeMapper;
    this.sealsContainersMapper = sealsContainersMapper;
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
        .pointOfEntry(pointOfEntryMapper.map(spsConsignment.getUnloadingBaseportSpsLocation()))
        .transporter(economicOperator(spsConsignment.getConsignorSpsParty(), PRIVATE_TRANSPORTER))
        .meansOfTransportFromEntryPoint(meansOfTransportFromEntryPointMapper.map(spsCertificate))
        .arrivalDate(arrivalDateMapper.map(spsCertificate))
        .arrivalTime(arrivalTimeMapper.map(spsCertificate))
        .meansOfTransport(meansOfTransportMapper.map(spsCertificate))
        .departureDate(departureDateMapper.map(spsCertificate))
        .departureTime(departureTimeMapper.map(spsCertificate))
        .estimatedJourneyTimeInMinutes(estimatedJourneyTimeMapper.map(spsCertificate))
        .veterinaryInformation(null)
        .sealsContainers(
            sealsContainersMapper.map(spsConsignment.getUtilizedSpsTransportEquipment()))
        .build();
  }

  private EconomicOperator economicOperator(SpsPartyType spsParty, EconomicOperatorType type)
      throws NotificationMapperException {
    return economicOperatorMapper.setEconomicOperatorType(
        economicOperatorMapper.map(spsParty),
        type);
  }
}