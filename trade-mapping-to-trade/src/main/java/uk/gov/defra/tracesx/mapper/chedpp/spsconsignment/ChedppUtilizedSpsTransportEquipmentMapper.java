package uk.gov.defra.tracesx.mapper.chedpp.spsconsignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.NotificationSealsContainers;
import uk.gov.defra.tracesx.trade.dto.AffixedSpsSeal;
import uk.gov.defra.tracesx.trade.dto.SpsTransportEquipmentType;

@Component
public class ChedppUtilizedSpsTransportEquipmentMapper
    implements Mapper<Notification, List<SpsTransportEquipmentType>> {

  private static final String NA = "NA";

  @Override
  public List<SpsTransportEquipmentType> map(Notification data) {
    if (data == null) {
      return Collections.emptyList();
    }
    List<SpsTransportEquipmentType> transportEquipmentTypes = new ArrayList<>();

    List<NotificationSealsContainers> sealsContainers = data.getPartOne().getSealsContainers();
    if (sealsContainers != null) {
      for (NotificationSealsContainers sealsContainer : sealsContainers) {
        List<AffixedSpsSeal> affixedSpsSeals = new ArrayList<>();

        affixedSpsSeals.add(new AffixedSpsSeal(SpsTypeConverter
            .getIdTypeWithSchemeId(getSchemeId(sealsContainer),
                replaceNullWithEmpty(sealsContainer.getSealNumber()))));
        transportEquipmentTypes.add(new SpsTransportEquipmentType()
            .withId(SpsTypeConverter.getIdTypeWithSchemeId("container_number",
                sealsContainer.getContainerNumber()))
            .withAffixedSpsSeal(affixedSpsSeals));
      }
    }
    return transportEquipmentTypes;
  }

  private String getSchemeId(NotificationSealsContainers container) {
    if (container.isOfficialSeal()) {
      return "official_seal_number";
    }
    return "seal_number";
  }

  private String replaceNullWithEmpty(String sealNumber) {
    return StringUtils.isEmpty(sealNumber) ? NA : sealNumber;
  }
}
