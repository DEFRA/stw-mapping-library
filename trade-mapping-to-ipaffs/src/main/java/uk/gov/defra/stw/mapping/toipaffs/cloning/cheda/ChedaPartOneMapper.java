package uk.gov.defra.stw.mapping.toipaffs.cloning.cheda;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.CONSIGNEE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.EXPORTER;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.IMPORTER;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType.PRIVATE_TRANSPORTER;

import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.EconomicOperatorMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.MeansOfTransportFromEntryPointMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.SealsContainersMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;

@Component
public class ChedaPartOneMapper implements Mapper<SpsCertificate, PartOne> {
  
  private final MeansOfTransportFromEntryPointMapper meansOfTransportFromEntryPointMapper;
  private final ChedaVeterinaryInformationMapper chedaVeterinaryInformationMapper;
  private final EconomicOperatorMapper economicOperatorMapper;
  private final SealsContainersMapper sealsContainersMapper;
  private final ChedaCommoditiesMapper commoditiesMapper;
  private final ChedaPartOneTransportToBcpMapper chedaPartOneTransportToBcpMapper;
  private final ChedaPurposeMapper chedaPurposeMapper;
  private final ChedaRouteMapper chedaRouteMapper;

  @Autowired
  public ChedaPartOneMapper(ChedaPartOneTransportToBcpMapper transportToBcpMapper,
      MeansOfTransportFromEntryPointMapper meansOfTransportFromEntryPointMapper,
      ChedaVeterinaryInformationMapper chedaVeterinaryInformationMapper,
      EconomicOperatorMapper economicOperatorMapper,
      SealsContainersMapper sealsContainersMapper,
      ChedaCommoditiesMapper commoditiesMapper,
      ChedaPurposeMapper chedaPurposeMapper,
      ChedaRouteMapper chedaRouteMapper) {
    this.meansOfTransportFromEntryPointMapper = meansOfTransportFromEntryPointMapper;
    this.chedaVeterinaryInformationMapper = chedaVeterinaryInformationMapper;
    this.economicOperatorMapper = economicOperatorMapper;
    this.sealsContainersMapper = sealsContainersMapper;
    this.commoditiesMapper = commoditiesMapper;
    this.chedaPartOneTransportToBcpMapper = transportToBcpMapper;
    this.chedaPurposeMapper = chedaPurposeMapper;
    this.chedaRouteMapper = chedaRouteMapper;
  }

  @Override
  public PartOne map(SpsCertificate spsCertificate) throws NotificationMapperException {
    return PartOne.builder()
        .purpose(chedaPurposeMapper.map(spsCertificate))
        .placeOfDestination(economicOperatorMapper.mapEconomicOperator(
            spsCertificate.getSpsConsignment().getDeliverySpsParty(), EXPORTER))
        .pointOfEntry(chedaPartOneTransportToBcpMapper.mapPointOfEntry(spsCertificate))
        .arrivalDate(chedaPartOneTransportToBcpMapper.mapArrivalDate(spsCertificate))
        .arrivalTime(LocalTime.parse("22:00:00"))
        .meansOfTransportFromEntryPoint(meansOfTransportFromEntryPointMapper
            .map(spsCertificate.getSpsConsignment().getMainCarriageSpsTransportMovement()))
        .veterinaryInformation(chedaVeterinaryInformationMapper
            .map(spsCertificate))
        .sealsContainers(sealsContainersMapper
            .map(spsCertificate.getSpsConsignment().getUtilizedSpsTransportEquipment()))
        .consignor(economicOperatorMapper.mapEconomicOperator(
            spsCertificate.getSpsConsignment().getConsignorSpsParty(), EXPORTER))
        .consignee(economicOperatorMapper.mapEconomicOperator(
            spsCertificate.getSpsConsignment().getConsigneeSpsParty(), CONSIGNEE))
        .importer(economicOperatorMapper.mapEconomicOperator(
            spsCertificate.getSpsConsignment().getConsigneeSpsParty(), IMPORTER))
        .transporter(economicOperatorMapper.mapEconomicOperator(
            spsCertificate.getSpsConsignment().getCarrierSpsParty(), PRIVATE_TRANSPORTER))
        .commodities(commoditiesMapper.map(spsCertificate))
        .route(chedaRouteMapper.map(spsCertificate))
        .build();
  }
}
