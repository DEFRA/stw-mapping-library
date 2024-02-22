package uk.gov.defra.stw.mapping.toipaffs.chedp.commodities;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.PackageTypeCodeType;
import uk.gov.defra.stw.mapping.dto.PhysicalSpsPackage;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType;

class ChedpPackageTypeKeyDataMapperTest {

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

  private ChedpPackageTypeKeyDataMapper mapper;

  @BeforeEach
  void setup() {
    mapper = new ChedpPackageTypeKeyDataMapper();
  }

  @Test
  void map_ReturnsComplementParameterSetKeyDataPair_WhenComplete() {
    for (String item : packageMap.keySet()) {
      PhysicalSpsPackage physicalSpsPackage = createPhysicalPackage(item);
      assertThat(mapper.map(physicalSpsPackage))
          .isEqualTo(createComplementParameterSetKeyDataPair(packageMap.get(item).getValue()));
    }
  }

  @Test
  void map_ReturnsComplementParameterSetWithOtherPackageType_WhenUnknownPackageType() {
    PhysicalSpsPackage physicalSpsPackage = createPhysicalPackage("XX");

    assertThat(mapper.map(physicalSpsPackage)).isEqualTo(createComplementParameterSetKeyDataPair("Other"));
  }

  private PhysicalSpsPackage createPhysicalPackage(String typeCode) {
    return new PhysicalSpsPackage()
        .withLevelCode(new CodeType()
            .withValue("4"))
        .withTypeCode(new PackageTypeCodeType()
            .withValue(typeCode));
  }

  private ComplementParameterSetKeyDataPair createComplementParameterSetKeyDataPair(String data) {
    return ComplementParameterSetKeyDataPair.builder()
        .key("type_package")
        .data(data)
        .build();
  }
}
