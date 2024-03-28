package uk.gov.defra.stw.mapping.toipaffs.chedd;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.PRIVATE_TRANSPORTER;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.chedp.ApprovedEstablishmentMapper;
import uk.gov.defra.stw.mapping.toipaffs.chedp.ChedpCommoditiesMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.ArrivalDateMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.ArrivalTimeMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.DepartureDateMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.DepartureTimeMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.EconomicOperatorMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.MeansOfTransportFromEntryPointMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.MeansOfTransportMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.PointOfEntryMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.SealsContainersMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.TransportToBcpQuestionMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.VeterinaryInformationMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;

@Component
public class CheddPartOneMapper implements Mapper<SpsCertificate, PartOne> {
  private CheddCommoditiesMapper cheddCommoditiesMapper;
  private PointOfEntryMapper pointOfEntryMapper;
  private VeterinaryInformationMapper veterinaryInformationMapper;
  private SealsContainersMapper sealsContainersMapper;
  private EconomicOperatorMapper economicOperatorMapper;
  private TransportToBcpQuestionMapper transportToBcpQuestionMapper;
  private MeansOfTransportMapper meansOfTransportMapper;
  private MeansOfTransportFromEntryPointMapper meansOfTransportFromEntryPointMapper;
  private DepartureDateMapper departureDateMapper;
  private DepartureTimeMapper departureTimeMapper;
  private ArrivalDateMapper arrivalDateMapper;
  private ArrivalTimeMapper arrivalTimeMapper;
  private CheddPurposeMapper cheddPurposeMapper;

  public CheddPartOneMapper(
      CheddCommoditiesMapper cheddCommoditiesMapper,
      MeansOfTransportFromEntryPointMapper meansOfTransportFromEntryPointMapper,
      PointOfEntryMapper pointOfEntryMapper,
      VeterinaryInformationMapper veterinaryInformationMapper,
      SealsContainersMapper sealsContainersMapper,
      ChedpCommoditiesMapper commoditiesMapper,
      ApprovedEstablishmentMapper approvedEstablishmentMapper,
      EconomicOperatorMapper economicOperatorMapper,
      TransportToBcpQuestionMapper transportToBcpQuestionMapper,
      MeansOfTransportMapper meansOfTransportMapper,
      DepartureDateMapper departureDateMapper,
      DepartureTimeMapper departureTimeMapper,
      ArrivalDateMapper arrivalDateMapper,
      ArrivalTimeMapper arrivalTimeMapper,
      CheddPurposeMapper cheddPurposeMapper) {
    this.cheddCommoditiesMapper = cheddCommoditiesMapper;
    this.meansOfTransportFromEntryPointMapper = meansOfTransportFromEntryPointMapper;
    this.veterinaryInformationMapper = veterinaryInformationMapper;
    this.economicOperatorMapper = economicOperatorMapper;
    this.transportToBcpQuestionMapper = transportToBcpQuestionMapper;
    this.meansOfTransportMapper = meansOfTransportMapper;
    this.departureDateMapper = departureDateMapper;
    this.departureTimeMapper = departureTimeMapper;
    this.arrivalDateMapper = arrivalDateMapper;
    this.arrivalTimeMapper = arrivalTimeMapper;
    this.sealsContainersMapper = sealsContainersMapper;
    this.meansOfTransportMapper = meansOfTransportMapper;
    this.pointOfEntryMapper = pointOfEntryMapper;
    this.cheddPurposeMapper = cheddPurposeMapper;
  }

  @Override
  public PartOne map(SpsCertificate spsCertificate) throws NotificationMapperException {
    return PartOne.builder()
        .commodities(cheddCommoditiesMapper.map(spsCertificate))
        .meansOfTransport(meansOfTransportMapper.map(spsCertificate))
        .meansOfTransportFromEntryPoint(meansOfTransportFromEntryPointMapper.map(spsCertificate))
        .transporter(economicOperatorMapper.setEconomicOperatorType(
            economicOperatorMapper.map(spsCertificate.getSpsConsignment().getCarrierSpsParty()),
            PRIVATE_TRANSPORTER))
        .transporterDetailsRequired(
            transportToBcpQuestionMapper.map(spsCertificate.getSpsConsignment()))
        .veterinaryInformation(
            veterinaryInformationMapper.map(spsCertificate.getSpsExchangedDocument()))
        .pointOfEntry(
            pointOfEntryMapper.map(
                spsCertificate.getSpsConsignment().getUnloadingBaseportSpsLocation()))
        .sealsContainers(sealsContainersMapper.map(
            spsCertificate.getSpsConsignment().getUtilizedSpsTransportEquipment()))
        .arrivalDate(arrivalDateMapper.map(spsCertificate))
        .arrivalTime(arrivalTimeMapper.map(spsCertificate))
        .departureDate(departureDateMapper.map(spsCertificate))
        .departureTime(departureTimeMapper.map(spsCertificate))
        .purpose(cheddPurposeMapper.map(spsCertificate))
        .build();
  }
}
