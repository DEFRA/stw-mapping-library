package uk.gov.defra.stw.mapping.toipaffs.chedpp.commodities;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.PackageTypeCodeType;
import uk.gov.defra.stw.mapping.dto.PhysicalSpsPackage;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType;

class ChedppPackageTypeMapperTest {

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
      Map.entry("DR", OTHER),
      Map.entry("JR", OTHER),
      Map.entry("QQ", OTHER),
      Map.entry("QP", OTHER),
      Map.entry("PK", PACKAGE),
      Map.entry("PL", OTHER),
      Map.entry("PX", PALLET),
      Map.entry("LE", OTHER),
      Map.entry("PJ", OTHER),
      Map.entry("RT", OTHER),
      Map.entry("TK", OTHER),
      Map.entry("TT", OTHER),
      Map.entry("PU", TRAY),
      Map.entry("TU", TUBE),
      Map.entry("VI", VIAL),
      Map.entry("BE", WOOD_BUNDLE),
      Map.entry("8B", WOOD_CRATE),
      Map.entry("IA", OTHER),
      Map.entry("BA", WOOD_BARREL),
      Map.entry("QJ", OTHER),
      Map.entry("EE", WOOD_CASE_WITH_PALLET_BASE));

  private ChedppPackageTypeMapper mapper;

  @BeforeEach
  void setup() {
    mapper = new ChedppPackageTypeMapper();
  }

  @Test
  void map_ReturnsComplementParameterSetKeyDataPair_WhenComplete() throws NotificationMapperException {
    for (String item : packageMap.keySet()) {
      PhysicalSpsPackage physicalSpsPackage = createPhysicalPackage(item);
      assertThat(mapper.map(physicalSpsPackage))
          .isEqualTo(createComplementParameterSetKeyDataPair(packageMap.get(item).getValue()));
    }
  }

  @Test
  void map_ReturnsComplementParameterSetWithOtherPackageType_WhenUnknownPackageType() throws NotificationMapperException {
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
