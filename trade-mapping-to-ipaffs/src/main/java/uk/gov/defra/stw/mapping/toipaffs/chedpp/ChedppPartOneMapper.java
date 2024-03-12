package uk.gov.defra.stw.mapping.toipaffs.chedpp;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.CONSIGNEE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.EXPORTER;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.IMPORTER;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.PACKER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
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
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;

@Component
public class ChedppPartOneMapper implements Mapper<SpsCertificate, PartOne> {

  private final ChedppPurposeMapper chedppPurposeMapper;
  private final EconomicOperatorMapper economicOperatorMapper;
  private final MeansOfTransportFromEntryPointMapper meansOfTransportFromEntryPointMapper;
  private final PointOfEntryMapper pointOfEntryMapper;
  private final ChedppVeterinaryInformationMapper chedppVeterinaryInformationMapper;
  private final SealsContainersMapper chedppSealsContainersMapper;
  private final MeansOfTransportMapper meansOfTransportMapper;
  private final ChedppCommoditiesMapper chedppCommoditiesMapper;
  private final TransportToBcpQuestionMapper transportToBcpQuestionMapper;
  private final ChedppPointOfEntryControlPointMapper chedppPointOfEntryControlPointMapper;
  private final ContactDetailsMapper contactDetailsMapper;
  private final NominatedContactsMapper nominatedContactsMapper;
  private final DepartureDateMapper departureDateMapper;
  private final DepartureTimeMapper departureTimeMapper;
  private final ArrivalDateMapper arrivalDateMapper;
  private final ArrivalTimeMapper arrivalTimeMapper;

  @Autowired
  public ChedppPartOneMapper(ChedppPurposeMapper chedppPurposeMapper,
      EconomicOperatorMapper economicOperatorMapper,
      MeansOfTransportFromEntryPointMapper meansOfTransportFromEntryPointMapper,
      MeansOfTransportMapper meansOfTransportMapper,
      PointOfEntryMapper pointOfEntryMapper,
      ChedppVeterinaryInformationMapper chedppVeterinaryInformationMapper,
      SealsContainersMapper chedppSealsContainersMapper,
      ChedppCommoditiesMapper chedppCommoditiesMapper,
      TransportToBcpQuestionMapper transportToBcpQuestionMapper,
      ChedppPointOfEntryControlPointMapper chedppPointOfEntryControlPointMapper,
      ContactDetailsMapper contactDetailsMapper,
      NominatedContactsMapper nominatedContactsMapper,
      DepartureDateMapper departureDateMapper,
      DepartureTimeMapper departureTimeMapper,
      ArrivalDateMapper arrivalDateMapper,
      ArrivalTimeMapper arrivalTimeMapper) {
    this.chedppPurposeMapper = chedppPurposeMapper;
    this.economicOperatorMapper = economicOperatorMapper;
    this.meansOfTransportFromEntryPointMapper = meansOfTransportFromEntryPointMapper;
    this.meansOfTransportMapper = meansOfTransportMapper;
    this.pointOfEntryMapper = pointOfEntryMapper;
    this.chedppVeterinaryInformationMapper = chedppVeterinaryInformationMapper;
    this.chedppSealsContainersMapper = chedppSealsContainersMapper;
    this.chedppCommoditiesMapper = chedppCommoditiesMapper;
    this.transportToBcpQuestionMapper = transportToBcpQuestionMapper;
    this.chedppPointOfEntryControlPointMapper = chedppPointOfEntryControlPointMapper;
    this.contactDetailsMapper = contactDetailsMapper;
    this.nominatedContactsMapper = nominatedContactsMapper;
    this.departureDateMapper = departureDateMapper;
    this.departureTimeMapper = departureTimeMapper;
    this.arrivalDateMapper = arrivalDateMapper;
    this.arrivalTimeMapper = arrivalTimeMapper;
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
        .packer(economicOperatorMapper.setEconomicOperatorType(
            economicOperatorMapper.map(spsConsignment.getDespatchSpsParty()), PACKER))
        .pointOfEntry(pointOfEntryMapper.map(spsConsignment.getUnloadingBaseportSpsLocation()))
        .pointOfEntryControlPoint(chedppPointOfEntryControlPointMapper.map(
            spsConsignment.getUnloadingBaseportSpsLocation()))
        .meansOfTransportFromEntryPoint(meansOfTransportFromEntryPointMapper.map(spsCertificate))
        .meansOfTransport(meansOfTransportMapper.map(spsCertificate))
        .veterinaryInformation(
            chedppVeterinaryInformationMapper.map(
                spsExchangedDocument.getReferenceSpsReferencedDocument()))
        .sealsContainers(
            chedppSealsContainersMapper.map(spsConsignment.getUtilizedSpsTransportEquipment()))
        .commodities(chedppCommoditiesMapper.map(spsCertificate))
        .transporterDetailsRequired(transportToBcpQuestionMapper.map(spsConsignment))
        .contactDetails(contactDetailsMapper.map(spsCertificate))
        .nominatedContacts(nominatedContactsMapper.map(spsCertificate))
        .arrivalDate(arrivalDateMapper.map(spsCertificate))
        .arrivalTime(arrivalTimeMapper.map(spsCertificate))
        .departureDate(departureDateMapper.map(spsCertificate))
        .departureTime(departureTimeMapper.map(spsCertificate))
        // TODO: submissionDate, submittedBy
        .build();
  }
}
