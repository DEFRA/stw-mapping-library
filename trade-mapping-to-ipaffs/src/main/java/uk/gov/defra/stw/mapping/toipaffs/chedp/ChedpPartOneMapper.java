package uk.gov.defra.stw.mapping.toipaffs.chedp;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.CONSIGNEE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.DESTINATION;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.EXPORTER;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.IMPORTER;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.PRIVATE_TRANSPORTER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
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
public class ChedpPartOneMapper implements Mapper<SpsCertificate, PartOne> {

  private final ChedpPurposeMapper chedpPurposeMapper;
  private final MeansOfTransportFromEntryPointMapper meansOfTransportFromEntryPointMapper;
  private final PointOfEntryMapper chedpPointOfEntryMapper;
  private final VeterinaryInformationMapper veterinaryInformationMapper;
  private final SealsContainersMapper chedpSealsContainersMapper;
  private final ChedpCommoditiesMapper chedpCommoditiesMapper;
  private final ApprovedEstablishmentMapper approvedEstablishmentMapper;
  private final EconomicOperatorMapper economicOperatorMapper;
  private final TransportToBcpQuestionMapper transportToBcpQuestionMapper;
  private final MeansOfTransportMapper meansOfTransportMapper;
  private final DepartureDateMapper departureDateMapper;
  private final DepartureTimeMapper departureTimeMapper;
  private final ArrivalDateMapper arrivalDateMapper;
  private final ArrivalTimeMapper arrivalTimeMapper;

  @Autowired
  public ChedpPartOneMapper(
      ChedpPurposeMapper chedpPurposeMapper,
      MeansOfTransportFromEntryPointMapper meansOfTransportFromEntryPointMapper,
      PointOfEntryMapper chedpPointOfEntryMapper,
      VeterinaryInformationMapper veterinaryInformationMapper,
      SealsContainersMapper chedpSealsContainersMapper,
      ChedpCommoditiesMapper chedpCommoditiesMapper,
      ApprovedEstablishmentMapper approvedEstablishmentMapper,
      EconomicOperatorMapper economicOperatorMapper,
      TransportToBcpQuestionMapper transportToBcpQuestionMapper,
      MeansOfTransportMapper meansOfTransportMapper,
      DepartureDateMapper departureDateMapper,
      DepartureTimeMapper departureTimeMapper,
      ArrivalDateMapper arrivalDateMapper,
      ArrivalTimeMapper arrivalTimeMapper) {
    this.chedpPurposeMapper = chedpPurposeMapper;
    this.meansOfTransportFromEntryPointMapper = meansOfTransportFromEntryPointMapper;
    this.chedpPointOfEntryMapper = chedpPointOfEntryMapper;
    this.veterinaryInformationMapper = veterinaryInformationMapper;
    this.chedpSealsContainersMapper = chedpSealsContainersMapper;
    this.chedpCommoditiesMapper = chedpCommoditiesMapper;
    this.approvedEstablishmentMapper = approvedEstablishmentMapper;
    this.economicOperatorMapper = economicOperatorMapper;
    this.transportToBcpQuestionMapper = transportToBcpQuestionMapper;
    this.meansOfTransportMapper = meansOfTransportMapper;
    this.departureDateMapper = departureDateMapper;
    this.departureTimeMapper = departureTimeMapper;
    this.arrivalDateMapper = arrivalDateMapper;
    this.arrivalTimeMapper = arrivalTimeMapper;
  }

  @Override
  public PartOne map(SpsCertificate spsCertificate) throws NotificationMapperException {
    PartOne partOne = PartOne.builder()
        .purpose(chedpPurposeMapper.map(spsCertificate))
        .pointOfEntry(chedpPointOfEntryMapper
            .map(spsCertificate.getSpsConsignment().getUnloadingBaseportSpsLocation()))
        .meansOfTransportFromEntryPoint(meansOfTransportFromEntryPointMapper.map(spsCertificate))
        .veterinaryInformation(veterinaryInformationMapper
            .map(spsCertificate.getSpsExchangedDocument()))
        .sealsContainers(chedpSealsContainersMapper
            .map(spsCertificate.getSpsConsignment().getUtilizedSpsTransportEquipment()))
        .commodities(chedpCommoditiesMapper.map(spsCertificate))
        .consignor(economicOperatorMapper.setEconomicOperatorType(
            economicOperatorMapper.map(spsCertificate.getSpsConsignment().getConsignorSpsParty()),
            EXPORTER))
        .consignee(economicOperatorMapper.setEconomicOperatorType(
            economicOperatorMapper.map(spsCertificate.getSpsConsignment().getConsigneeSpsParty()),
            CONSIGNEE))
        .importer(economicOperatorMapper.setEconomicOperatorType(
            economicOperatorMapper.map(spsCertificate.getSpsConsignment().getConsigneeSpsParty()),
            IMPORTER))
        .placeOfDestination(economicOperatorMapper.setEconomicOperatorType(
            economicOperatorMapper.map(spsCertificate.getSpsConsignment().getDeliverySpsParty()),
            DESTINATION))
        .transporter(economicOperatorMapper.setEconomicOperatorType(
            economicOperatorMapper.map(spsCertificate.getSpsConsignment().getCarrierSpsParty()),
            PRIVATE_TRANSPORTER))
        .transporterDetailsRequired(
            transportToBcpQuestionMapper.map(spsCertificate.getSpsConsignment()))
        .meansOfTransport(meansOfTransportMapper.map(spsCertificate))
        .arrivalDate(arrivalDateMapper.map(spsCertificate))
        .arrivalTime(arrivalTimeMapper.map(spsCertificate))
        .departureDate(departureDateMapper.map(spsCertificate))
        .departureTime(departureTimeMapper.map(spsCertificate))
        .build();

    if (partOne.getVeterinaryInformation() != null) {
      partOne
          .getVeterinaryInformation().setEstablishmentsOfOrigin(
              approvedEstablishmentMapper.map(
                  spsCertificate.getSpsConsignment().getIncludedSpsConsignmentItem()));
    }

    return partOne;
  }
}
