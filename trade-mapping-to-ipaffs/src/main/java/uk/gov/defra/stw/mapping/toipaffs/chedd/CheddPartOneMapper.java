package uk.gov.defra.stw.mapping.toipaffs.chedd;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.CONSIGNEE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.DESTINATION;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.EXPORTER;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.IMPORTER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.chedp.ApprovedEstablishmentMapper;
import uk.gov.defra.stw.mapping.toipaffs.chedp.ChedpCommoditiesMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.ArrivalDateMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.ArrivalTimeMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.EconomicOperatorMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.MeansOfTransportFromEntryPointMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.PointOfEntryMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.SealsContainersMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.SubmissionDateMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.TransportToBcpQuestionMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.VeterinaryInformationMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;

@Component
public class CheddPartOneMapper implements Mapper<SpsCertificate, PartOne> {
  private final CheddCommoditiesMapper cheddCommoditiesMapper;
  private final PointOfEntryMapper pointOfEntryMapper;
  private final VeterinaryInformationMapper veterinaryInformationMapper;
  private final SealsContainersMapper sealsContainersMapper;
  private final EconomicOperatorMapper economicOperatorMapper;
  private final TransportToBcpQuestionMapper transportToBcpQuestionMapper;
  private final MeansOfTransportFromEntryPointMapper meansOfTransportFromEntryPointMapper;
  private final ArrivalDateMapper arrivalDateMapper;
  private final ArrivalTimeMapper arrivalTimeMapper;
  private final CheddPurposeMapper cheddPurposeMapper;
  private final SubmissionDateMapper submissionDateMapper;

  @Autowired
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
      ArrivalDateMapper arrivalDateMapper,
      ArrivalTimeMapper arrivalTimeMapper,
      CheddPurposeMapper cheddPurposeMapper,
      SubmissionDateMapper submissionDateMapper) {
    this.cheddCommoditiesMapper = cheddCommoditiesMapper;
    this.meansOfTransportFromEntryPointMapper = meansOfTransportFromEntryPointMapper;
    this.veterinaryInformationMapper = veterinaryInformationMapper;
    this.economicOperatorMapper = economicOperatorMapper;
    this.transportToBcpQuestionMapper = transportToBcpQuestionMapper;
    this.arrivalDateMapper = arrivalDateMapper;
    this.arrivalTimeMapper = arrivalTimeMapper;
    this.sealsContainersMapper = sealsContainersMapper;
    this.pointOfEntryMapper = pointOfEntryMapper;
    this.cheddPurposeMapper = cheddPurposeMapper;
    this.submissionDateMapper = submissionDateMapper;
  }

  @Override
  public PartOne map(SpsCertificate spsCertificate) throws NotificationMapperException {
    return PartOne.builder()
        .commodities(cheddCommoditiesMapper.map(spsCertificate))
        .meansOfTransportFromEntryPoint(meansOfTransportFromEntryPointMapper.map(spsCertificate))
        .importer(economicOperatorMapper.setEconomicOperatorType(
            economicOperatorMapper.map(spsCertificate.getSpsConsignment().getConsigneeSpsParty()),
            IMPORTER))
        .placeOfDestination(economicOperatorMapper.setEconomicOperatorType(
            economicOperatorMapper.map(spsCertificate.getSpsConsignment().getDeliverySpsParty()),
            DESTINATION))
        .consignor(economicOperatorMapper.setEconomicOperatorType(
            economicOperatorMapper.map(spsCertificate.getSpsConsignment().getConsignorSpsParty()),
            EXPORTER))
        .consignee(economicOperatorMapper.setEconomicOperatorType(
            economicOperatorMapper.map(spsCertificate.getSpsConsignment().getConsigneeSpsParty()),
            CONSIGNEE))
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
        .submissionDate(submissionDateMapper.map(spsCertificate))
        .purpose(cheddPurposeMapper.map(spsCertificate))
        .build();
  }
}
