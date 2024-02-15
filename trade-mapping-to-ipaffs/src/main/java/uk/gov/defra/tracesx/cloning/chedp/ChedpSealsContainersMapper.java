package uk.gov.defra.tracesx.cloning.chedp;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.common.common.SealsContainersMapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.NotificationSealsContainers;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.tracesx.trade.dto.SpsConsignment;
import uk.gov.defra.tracesx.trade.dto.SpsTransportEquipmentType;

@Component
public class ChedpSealsContainersMapper
    implements Mapper<SpsConsignment, List<NotificationSealsContainers>> {

  private final SealsContainersMapper sealsContainersMapper;

  @Autowired
  public ChedpSealsContainersMapper(SealsContainersMapper sealsContainersMapper) {
    this.sealsContainersMapper = sealsContainersMapper;
  }

  @Override
  public List<NotificationSealsContainers> map(SpsConsignment spsConsignment)
      throws NotificationMapperException {
    List<NotificationSealsContainers> consignmentSealsContainers = sealsContainersMapper
        .map(spsConsignment.getUtilizedSpsTransportEquipment());
    List<NotificationSealsContainers> commoditySealsContainers = sealsContainersMapper
        .map(getTradeLineItemsTransportEquipment(spsConsignment));
    List<NotificationSealsContainers> combinedList =
        Stream.concat(getStream(consignmentSealsContainers), getStream(commoditySealsContainers))
            .distinct()
            .toList();
    return combinedList.isEmpty() ? null : combinedList;
  }

  private List<SpsTransportEquipmentType> getTradeLineItemsTransportEquipment(SpsConsignment
      spsConsignment) {
    return spsConsignment.getIncludedSpsConsignmentItem().stream()
        .flatMap(consignment -> consignment.getIncludedSpsTradeLineItem().stream())
        .map(IncludedSpsTradeLineItem::getAssociatedSpsTransportEquipment)
        .filter(Objects::nonNull)
        .flatMap(Collection::stream)
        .toList();
  }

  private <T> Stream<T> getStream(List<T> list) {
    return list == null ? Stream.empty() : list.stream();
  }
}
