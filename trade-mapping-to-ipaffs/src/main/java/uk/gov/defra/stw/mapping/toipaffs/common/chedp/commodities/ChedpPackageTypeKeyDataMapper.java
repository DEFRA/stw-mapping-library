package uk.gov.defra.stw.mapping.toipaffs.common.chedp.commodities;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.BAG;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.BALE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.BALLOON_PROTECTED;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.BLOCK;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.BOX;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.CAN;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.CARTON;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.CASE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.CASK;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.COFFER;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.CONTAINER_NOT_SPECIFIED;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.CRATE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.DRUM;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.JAR;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.OTHER;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.PACKAGE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.PAIL;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.PALLET;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.PALLET_BOX;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.TANK;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.TOTE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.TRAY;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.TUBE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType.VIAL;

import java.util.Map;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.PhysicalSpsPackage;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType;

@Component
public class ChedpPackageTypeKeyDataMapper implements
    Mapper<PhysicalSpsPackage, ComplementParameterSetKeyDataPair> {

  private static final Map<String, PackageType> packageMap = Map.ofEntries(
      Map.entry("BG", BAG),
      Map.entry("BL", BALE),
      Map.entry("BP", BALLOON_PROTECTED),
      Map.entry("OK", BLOCK),
      Map.entry("BX", BOX),
      Map.entry("CA", CAN),
      Map.entry("CT", CARTON),
      Map.entry("CS", CASE),
      Map.entry("CK", CASK),
      Map.entry("CF", COFFER),
      Map.entry("CN", CONTAINER_NOT_SPECIFIED),
      Map.entry("CR", CRATE),
      Map.entry("DR", DRUM),
      Map.entry("JR", JAR),
      Map.entry("PK", PACKAGE),
      Map.entry("PL", PAIL),
      Map.entry("PX", PALLET),
      Map.entry("PB", PALLET_BOX),
      Map.entry("TK", TANK),
      Map.entry("TT", TOTE),
      Map.entry("DT", TRAY),
      Map.entry("TU", TUBE),
      Map.entry("VI", VIAL));

  @Override
  public ComplementParameterSetKeyDataPair map(PhysicalSpsPackage data) {
    PackageType packageType = packageMap.get(data.getTypeCode().getValue());
    if (packageType == null) {
      packageType = OTHER;
    }
    return ComplementParameterSetKeyDataPair.builder()
        .key("type_package")
        .data(packageType.getValue())
        .build();
  }
}
