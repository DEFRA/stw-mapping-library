package uk.gov.defra.stw.mapping.toipaffs.common.common;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.AffixedSpsSeal;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.SpsTransportEquipmentType;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.NotificationSealsContainers;
import uk.gov.defra.tracesx.notificationschema.representation.NotificationSealsContainers.NotificationSealsContainersBuilder;

@Component
public class SealsContainersMapper
    implements Mapper<List<SpsTransportEquipmentType>, List<NotificationSealsContainers>> {

  private static final String OFFICIAL_SEAL_NUMBER = "official_seal_number";
  private static final String SEAL_NUMBER = "seal_number";

  @Override
  public List<NotificationSealsContainers> map(
      List<SpsTransportEquipmentType> spsTransportEquipmentTypes) {
    List<NotificationSealsContainers> notificationSealsContainers = null;
    if (spsTransportEquipmentTypes != null && !spsTransportEquipmentTypes.isEmpty()) {
      notificationSealsContainers = new ArrayList<>();
      for (SpsTransportEquipmentType spsTransportEquipmentType : spsTransportEquipmentTypes) {
        NotificationSealsContainersBuilder notificationSealsContainersBuilder =
            NotificationSealsContainers.builder();
        notificationSealsContainersBuilder.containerNumber(
            mapContainerNumber(spsTransportEquipmentType));

        List<AffixedSpsSeal> affixedSpsSeals = spsTransportEquipmentType.getAffixedSpsSeal();
        if (affixedSpsSeals != null) {
          mapSealContainer(affixedSpsSeals.get(0), notificationSealsContainersBuilder);
        }
        notificationSealsContainers.add(notificationSealsContainersBuilder.build());
      }
    }
    return notificationSealsContainers;
  }

  private NotificationSealsContainersBuilder mapSealContainer(AffixedSpsSeal affixedSpsSeal,
      NotificationSealsContainersBuilder notificationSealsContainerBuilder) {
    if (hasSchemeId(affixedSpsSeal.getId())
        && (affixedSpsSeal.getId().getSchemeID().equals(OFFICIAL_SEAL_NUMBER)
        || affixedSpsSeal.getId().getSchemeID().equals(SEAL_NUMBER))) {
      notificationSealsContainerBuilder.officialSeal(mapOfficialSeal(affixedSpsSeal));
      notificationSealsContainerBuilder.sealNumber(mapSealNumber(affixedSpsSeal));
    }
    return notificationSealsContainerBuilder;
  }

  private String mapContainerNumber(SpsTransportEquipmentType spsTransportEquipmentType) {
    return hasValue(spsTransportEquipmentType.getId())
        ? spsTransportEquipmentType.getId().getValue().trim() : null;
  }

  private Boolean mapOfficialSeal(AffixedSpsSeal affixedSpsSeal) {
    return hasSchemeId(affixedSpsSeal.getId()) && affixedSpsSeal.getId().getSchemeID()
        .equals(OFFICIAL_SEAL_NUMBER);
  }

  private String mapSealNumber(AffixedSpsSeal affixedSpsSeal) {
    return hasValue(affixedSpsSeal.getId()) ? affixedSpsSeal.getId().getValue() : null;
  }

  private boolean hasValue(IDType idType) {
    return idType != null && idType.getValue() != null;
  }

  private boolean hasSchemeId(IDType idType) {
    return idType != null && idType.getSchemeID() != null;
  }
}
