package uk.gov.defra.stw.mapping.toipaffs.cloning.chedp;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.CONSIGNEE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.EXPORTER;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.IMPORTER;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.PRIVATE_TRANSPORTER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.EconomicOperatorMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.chedp.ChedpPartOneMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.MeansOfTransportFromEntryPointMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;

@Component
public class ChedpPartOneMapperImpl implements ChedpPartOneMapper {

  private final ChedpPurposeMapper chedpPurposeMapper;
  private final MeansOfTransportFromEntryPointMapper meansOfTransportFromEntryPointMapper;
  private final ChedpVeterinaryInformationMapper chedpVeterinaryInformationMapper;
  private final ChedpSealsContainersMapper chedpSealsContainersMapper;
  private final EconomicOperatorMapper economicOperatorMapper;
  private final ChedpCommoditiesMapper chedpCommoditiesMapper;
  private final ChedpPartOneTransportToBcpMapper chedpPartOneTransportToBcpMapper;

  @Autowired
  public ChedpPartOneMapperImpl(
      ChedpPurposeMapper chedpPurposeMapper,
      MeansOfTransportFromEntryPointMapper meansOfTransportFromEntryPointMapper,
      ChedpVeterinaryInformationMapper chedpVeterinaryInformationMapper,
      ChedpSealsContainersMapper chedpSealsContainersMapper,
      EconomicOperatorMapper economicOperatorMapper,
      ChedpCommoditiesMapper chedpCommoditiesMapper,
      ChedpPartOneTransportToBcpMapper chedpPartOneTransportToBcpMapper) {
    this.chedpPurposeMapper = chedpPurposeMapper;
    this.meansOfTransportFromEntryPointMapper = meansOfTransportFromEntryPointMapper;
    this.chedpVeterinaryInformationMapper = chedpVeterinaryInformationMapper;
    this.chedpSealsContainersMapper = chedpSealsContainersMapper;
    this.economicOperatorMapper = economicOperatorMapper;
    this.chedpCommoditiesMapper = chedpCommoditiesMapper;
    this.chedpPartOneTransportToBcpMapper = chedpPartOneTransportToBcpMapper;
  }

  @Override
  public PartOne map(SpsCertificate spsCertificate) throws NotificationMapperException {
    return PartOne.builder()
        .placeOfDestination(economicOperatorMapper.mapEconomicOperator(
            spsCertificate.getSpsConsignment().getDeliverySpsParty(), EXPORTER))
        .purpose(chedpPurposeMapper.map(spsCertificate))
        .pointOfEntry(chedpPartOneTransportToBcpMapper.mapPointOfEntry(spsCertificate))
        .arrivalDate(chedpPartOneTransportToBcpMapper.mapArrivalDate(spsCertificate))
        .arrivalTime(chedpPartOneTransportToBcpMapper.mapArrivalTime(spsCertificate))
        .meansOfTransportFromEntryPoint(meansOfTransportFromEntryPointMapper
            .map(spsCertificate.getSpsConsignment().getMainCarriageSpsTransportMovement()))
        .veterinaryInformation(chedpVeterinaryInformationMapper
            .map(spsCertificate))
        .sealsContainers(chedpSealsContainersMapper.map(spsCertificate.getSpsConsignment()))
        .commodities(chedpCommoditiesMapper.map(spsCertificate))
        .transporterDetailsRequired(hasCarrierSpsParty(spsCertificate))
        .consignor(economicOperatorMapper.mapEconomicOperator(
            spsCertificate.getSpsConsignment().getConsignorSpsParty(), EXPORTER))
        .consignee(economicOperatorMapper.mapEconomicOperator(
            spsCertificate.getSpsConsignment().getConsigneeSpsParty(), CONSIGNEE))
        .importer(economicOperatorMapper.mapEconomicOperator(
            spsCertificate.getSpsConsignment().getConsigneeSpsParty(), IMPORTER))
        .transporter(economicOperatorMapper.mapEconomicOperator(
            spsCertificate.getSpsConsignment().getCarrierSpsParty(), PRIVATE_TRANSPORTER))
        .build();
  }

  private boolean hasCarrierSpsParty(SpsCertificate spsCertificate) {
    return spsCertificate.getSpsConsignment().getCarrierSpsParty() != null;
  }
}
