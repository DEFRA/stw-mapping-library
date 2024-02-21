package uk.gov.defra.stw.mapping.toipaffs.cloning.common;

import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.PhysicalSpsPackage;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

@Component
public class PhysicalSpsPackageMapper {

  private static final String NO_PACKAGING_HIERARCHY = "4";

  public ComplementParameterSetKeyDataPair map(IncludedSpsTradeLineItem includedSpsTradeLineItem,
      Mapper<PhysicalSpsPackage, ComplementParameterSetKeyDataPair> keyDataPairMapper)
      throws NotificationMapperException {
    List<PhysicalSpsPackage> physicalSpsPackages = includedSpsTradeLineItem
        .getPhysicalSpsPackage();
    if (physicalSpsPackages != null && !physicalSpsPackages.isEmpty()) {
      PhysicalSpsPackage firstPhysicalSpsPackage = includedSpsTradeLineItem
          .getPhysicalSpsPackage().get(0);
      if (firstPhysicalSpsPackage.getLevelCode().getValue().equals(NO_PACKAGING_HIERARCHY)) {
        return keyDataPairMapper.map(firstPhysicalSpsPackage);
      } else {
        throw new NotificationMapperException("Unable to map due to level code");
      }
    }
    return null;
  }

}
