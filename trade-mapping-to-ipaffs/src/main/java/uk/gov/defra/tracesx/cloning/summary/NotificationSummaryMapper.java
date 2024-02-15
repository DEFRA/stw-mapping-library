package uk.gov.defra.tracesx.cloning.summary;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;
import uk.gov.defra.tracesx.notificationschema.representation.ExternalReference;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.ExternalSystem;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;

@Component
public class NotificationSummaryMapper implements Mapper<Notification, NotificationSummary> {

  private final CommoditiesSummaryMapper commoditiesSummaryMapper;
  private final SealAndContainerSummaryMapper sealAndContainerSummaryMapper;

  @Autowired
  public NotificationSummaryMapper(
      CommoditiesSummaryMapper commoditiesSummaryMapper,
      SealAndContainerSummaryMapper sealAndContainerSummaryMapper) {
    this.commoditiesSummaryMapper = commoditiesSummaryMapper;
    this.sealAndContainerSummaryMapper = sealAndContainerSummaryMapper;
  }

  @Override
  public NotificationSummary map(Notification notification) {
    return NotificationSummary.builder()
        .referenceNumber(
            mapReferenceNumber(notification.getExternalReferences(),
                getExternalSystem(notification.getType())))
        .purpose(mapPurpose(notification.getPartOne().getPurpose()))
        .commodities(commoditiesSummaryMapper
            .map(notification.getPartOne().getCommodities()))
        .pointOfEntry(notification.getPartOne().getPointOfEntry())
        .sealsContainers(sealAndContainerSummaryMapper
            .map(notification.getPartOne().getSealsContainers()))
        .consolidatedCommodities(isConsolidatedCommodities(notification))
        .build();
  }

  private String mapPurpose(Purpose purpose) {
    return purpose.getInternalMarketPurpose() != null
        ? purpose.getInternalMarketPurpose().getValue() : purpose.getPurposeGroup().getValue();
  }

  private ExternalSystem getExternalSystem(NotificationTypeEnum notificationTypeEnum) {
    return notificationTypeEnum.equals(NotificationTypeEnum.CHEDPP)
        ? ExternalSystem.EPHYTO : ExternalSystem.ECERT;
  }

  private String mapReferenceNumber(List<ExternalReference> externalReferenceList,
      ExternalSystem externalSystem) {
    return externalReferenceList.stream()
        .filter(externalReference -> externalReference.getSystem().equals(externalSystem))
        .toList()
        .get(0).getReference();
  }

  private boolean isConsolidatedCommodities(Notification notification) {
    Commodities commodities = notification.getPartOne().getCommodities();
    return commodities.getCommodityComplement().size() != getDistinctByCommodityAndSpecies(
        commodities).size();
  }

  private List<CommodityComplement> getDistinctByCommodityAndSpecies(Commodities commodities) {
    List<CommodityComplement> commodityComplements = commodities.getCommodityComplement();
    return commodityComplements.stream()
        .filter(distinctByKeys(CommodityComplement::getCommodityID,
            CommodityComplement::getSpeciesName))
        .toList();
  }

  private static Predicate<CommodityComplement> distinctByKeys(
      Function<CommodityComplement, String>... keyExtractors) {
    final Map<List<String>, Boolean> seen = new HashMap<>();
    return commodityComplement -> {
      final List<String> keys = Arrays.stream(keyExtractors)
          .map(keyFunction -> keyFunction.apply(commodityComplement))
          .toList();
      return seen.putIfAbsent(keys, Boolean.TRUE) == null;
    };
  }
}
