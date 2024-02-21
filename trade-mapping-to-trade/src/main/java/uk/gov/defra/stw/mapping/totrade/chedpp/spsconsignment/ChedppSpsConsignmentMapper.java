package uk.gov.defra.stw.mapping.totrade.chedpp.spsconsignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.dto.SpsPartyType;
import uk.gov.defra.stw.mapping.totrade.common.Mapper;
import uk.gov.defra.stw.mapping.totrade.common.spsconsignment.AvailabilityDueDateTimeMapper;
import uk.gov.defra.stw.mapping.totrade.common.spsconsignment.CustomsTransitAgentSpsPartyMapper;
import uk.gov.defra.stw.mapping.totrade.common.spsconsignment.ExportSpsCountryMapper;
import uk.gov.defra.stw.mapping.totrade.common.spsconsignment.MainCarriageSpsTransportMovementAfterBcpMapper;
import uk.gov.defra.stw.mapping.totrade.common.spsconsignment.MainCarriageSpsTransportMovementBeforeBcpMapper;
import uk.gov.defra.stw.mapping.totrade.common.spsconsignment.UnloadingBaseportSpsLocationMapper;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperator;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransport;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransportAfterBip;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransportBeforeBip;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;

@Component
public class ChedppSpsConsignmentMapper implements Mapper<Notification, SpsConsignment> {

  private final ChedppConsignorSpsPartyMapper consignorSpsPartyMapper;
  private final ChedppConsigneeSpsPartyMapper consigneeSpsPartyMapper;
  private final ChedppDeliverySpsPartyMapper deliverySpsPartyMapper;
  private final ChedppTransitSpsCountryMapper transitSpsCountryMapper;
  private final ChedppImportSpsCountryMapper importSpsCountryMapper;
  private final ChedppExaminationSpsEventMapper examinationSpsEventMapper;
  private final ChedppIncludedSpsConsignmentItemMapper includedSpsConsignmentItemMapper;
  private final ChedppPodSpsEventMapper podSpsEventMapper;
  private final CustomsTransitAgentSpsPartyMapper customsTransitAgentSpsPartyMapper;
  private final AvailabilityDueDateTimeMapper availabilityDueDateTimeMapper;
  private final MainCarriageSpsTransportMovementBeforeBcpMapper beforeBcpMapper;
  private final MainCarriageSpsTransportMovementAfterBcpMapper afterBcpMapper;
  private final ChedppUtilizedSpsTransportEquipmentMapper utilizedSpsTransportEquipmentMapper;
  private final UnloadingBaseportSpsLocationMapper unloadingBaseportSpsLocationMapper;
  private final ExportSpsCountryMapper exportSpsCountryMapper;

  @Autowired
  public ChedppSpsConsignmentMapper(
      ChedppConsignorSpsPartyMapper consignorSpsPartyMapper,
      ChedppConsigneeSpsPartyMapper consigneeSpsPartyMapper,
      ChedppDeliverySpsPartyMapper deliverySpsPartyMapper,
      ChedppTransitSpsCountryMapper transitSpsCountryMapper,
      ChedppImportSpsCountryMapper importSpsCountryMapper,
      ChedppExaminationSpsEventMapper examinationSpsEventMapper,
      ChedppIncludedSpsConsignmentItemMapper includedSpsConsignmentItemMapper,
      ChedppPodSpsEventMapper podSpsEventMapper,
      CustomsTransitAgentSpsPartyMapper customsTransitAgentSpsPartyMapper,
      AvailabilityDueDateTimeMapper availabilityDueDateTimeMapper,
      MainCarriageSpsTransportMovementBeforeBcpMapper beforeBcpMapper,
      MainCarriageSpsTransportMovementAfterBcpMapper afterBcpMapper,
      ChedppUtilizedSpsTransportEquipmentMapper utilizedSpsTransportEquipmentMapper,
      ExportSpsCountryMapper exportSpsCountryMapper,
      UnloadingBaseportSpsLocationMapper unloadingBaseportSpsLocationMapper) {
    this.consignorSpsPartyMapper = consignorSpsPartyMapper;
    this.consigneeSpsPartyMapper = consigneeSpsPartyMapper;
    this.deliverySpsPartyMapper = deliverySpsPartyMapper;
    this.transitSpsCountryMapper = transitSpsCountryMapper;
    this.importSpsCountryMapper = importSpsCountryMapper;
    this.examinationSpsEventMapper = examinationSpsEventMapper;
    this.includedSpsConsignmentItemMapper = includedSpsConsignmentItemMapper;
    this.podSpsEventMapper = podSpsEventMapper;
    this.customsTransitAgentSpsPartyMapper = customsTransitAgentSpsPartyMapper;
    this.availabilityDueDateTimeMapper = availabilityDueDateTimeMapper;
    this.beforeBcpMapper = beforeBcpMapper;
    this.afterBcpMapper = afterBcpMapper;
    this.utilizedSpsTransportEquipmentMapper = utilizedSpsTransportEquipmentMapper;
    this.unloadingBaseportSpsLocationMapper = unloadingBaseportSpsLocationMapper;
    this.exportSpsCountryMapper = exportSpsCountryMapper;
  }

  @Override
  public SpsConsignment map(Notification data) {
    PartOne partOne = data.getPartOne();

    return new SpsConsignment()
        .withCustomsTransitAgentSpsParty(
            customsTransitAgentSpsPartyMapper.map(partOne.getPersonResponsible()))
        .withConsignorSpsParty(consignorSpsPartyMapper.map(partOne.getConsignor()))
        .withConsigneeSpsParty(createCorrectConsigneeParty(partOne.getConsignee(),
            partOne.getImporter()))
        .withDeliverySpsParty(deliverySpsPartyMapper.map(partOne.getPlaceOfDestination()))
        .withTransitSpsCountry(transitSpsCountryMapper.map(partOne.getPurpose()))
        .withImportSpsCountry(importSpsCountryMapper.map(partOne.getPurpose()))
        .withExaminationSpsEvent(examinationSpsEventMapper.map(partOne))
        .withExportSpsCountry(exportSpsCountryMapper.map(data.getPartOne().getCommodities()))
        .withIncludedSpsConsignmentItem(Collections.singletonList(
            includedSpsConsignmentItemMapper.map(data)))
        .withStorageSpsEvent(podSpsEventMapper.map(partOne))
        .withAvailabilityDueDateTime(availabilityDueDateTimeMapper.map(partOne))
        .withMainCarriageSpsTransportMovement(
            createMainCarriageSpsList(partOne.getMeansOfTransportFromEntryPoint(),
                partOne.getMeansOfTransport()))
        .withUtilizedSpsTransportEquipment(
            utilizedSpsTransportEquipmentMapper.map(data))
        .withUnloadingBaseportSpsLocation(
            unloadingBaseportSpsLocationMapper.map(partOne.getPointOfEntry()));
  }

  private SpsPartyType createCorrectConsigneeParty(EconomicOperator consignee,
      EconomicOperator importer) {
    if (consignee != null) {
      return consigneeSpsPartyMapper.map(consignee);
    }
    return consigneeSpsPartyMapper.map(importer);
  }

  private boolean isNull(MeansOfTransport data) {
    if (data == null) {
      return true;
    } else {
      return data.getId() == null || data.getType() == null;
    }
  }

  private List<MainCarriageSpsTransportMovement> createMainCarriageSpsList(
      MeansOfTransportBeforeBip beforeBip, MeansOfTransportAfterBip afterBip) {
    List<MainCarriageSpsTransportMovement> movementList = new ArrayList<>();
    if (!isNull(beforeBip)) {
      movementList.add(beforeBcpMapper.map(beforeBip));
    }
    if (!isNull(afterBip)) {
      movementList.add(afterBcpMapper.map(afterBip));
    }
    return movementList;
  }
}
