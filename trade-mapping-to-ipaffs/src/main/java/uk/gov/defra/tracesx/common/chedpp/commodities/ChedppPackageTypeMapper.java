package uk.gov.defra.tracesx.common.chedpp.commodities;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.BAG;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.BALE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.BOX;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.BULK;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.CAN;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.CARTON;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.CASE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.CASK;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.COFFER;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.CONTAINER;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.CRATE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.OTHER;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.PACKAGE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.PALLET;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.TRAY;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.TUBE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.VIAL;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.WOOD_BARREL;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.WOOD_BUNDLE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.WOOD_CASE_WITH_PALLET_BASE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.WOOD_CRATE;

import java.util.Map;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType;
import uk.gov.defra.tracesx.trade.dto.PhysicalSpsPackage;

@Component
public class ChedppPackageTypeMapper implements
    Mapper<PhysicalSpsPackage, ComplementParameterSetKeyDataPair> {

  private static final Map<String, PackageType> packageMap = Map.ofEntries(
      Map.entry("BG", BAG),
      Map.entry("BL", BALE),
      Map.entry("BX", BOX),
      Map.entry("VR", BULK),
      Map.entry("CA", CAN),
      Map.entry("CT", CARTON),
      Map.entry("CS", CASE),
      Map.entry("CK", CASK),
      Map.entry("CF", COFFER),
      Map.entry("CN", CONTAINER),
      Map.entry("CR", CRATE),
      Map.entry("PK", PACKAGE),
      Map.entry("PX", PALLET),
      Map.entry("PU", TRAY),
      Map.entry("TU", TUBE),
      Map.entry("VI", VIAL),
      Map.entry("BE", WOOD_BUNDLE),
      Map.entry("8B", WOOD_CRATE),
      Map.entry("BA", WOOD_BARREL),
      Map.entry("EE", WOOD_CASE_WITH_PALLET_BASE));

  @Override
  public ComplementParameterSetKeyDataPair map(PhysicalSpsPackage physicalSpsPackage)
      throws NotificationMapperException {
    PackageType packageType = packageMap.get(physicalSpsPackage.getTypeCode().getValue());
    if (packageType == null) {
      packageType = OTHER;
    }
    return ComplementParameterSetKeyDataPair.builder()
        .key("type_package")
        .data(packageType.getValue())
        .build();
  }
}
