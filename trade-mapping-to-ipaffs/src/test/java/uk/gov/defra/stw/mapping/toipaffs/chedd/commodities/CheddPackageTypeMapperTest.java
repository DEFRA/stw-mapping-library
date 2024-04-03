package uk.gov.defra.stw.mapping.toipaffs.chedd.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.PackageTypeCodeType;
import uk.gov.defra.stw.mapping.dto.PhysicalSpsPackage;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType;

class CheddPackageTypeMapperTest {

  private CheddPackageTypeKeyDataMapper cheddPackageTypeKeyDataMapper;

  @BeforeEach
  void setup() {
    cheddPackageTypeKeyDataMapper = new CheddPackageTypeKeyDataMapper();
  }

  @ParameterizedTest
  @CsvSource({
      "BG, BAG",
      "BL, BALE",
      "OK, BLOCK",
      "BX, BOX",
      "CA, CAN",
      "CT, CARTON",
      "CS, CASE",
      "CK, CASK",
      "CF, COFFER",
      "CN, CONTAINER_NOT_SPECIFIED",
      "CR, CRATE",
      "DR, DRUM",
      "JR, JAR",
      "PK, PACKAGE",
      "PL, PAIL",
      "PX, PALLET",
      "PB, PALLET_BOX",
      "TK, TANK",
      "TT, TOTE",
      "DT, TRAY",
      "TU, TUBE",
      "VI, VIAL"
  })

  void map_ReturnsComplementParameterSetKeyDataPair_WhenComplete(String value, PackageType expected){
      PhysicalSpsPackage physicalSpsPackage = createPhysicalPackage(value);
      assertThat(cheddPackageTypeKeyDataMapper.map(physicalSpsPackage))
          .isEqualTo(createComplementParameterSetKeyDataPair(expected.getValue()));
    
  }

  @Test
  void map_ReturnsComplementParameterSetWithOtherPackageType_WhenUnknownPackageType() {
    PhysicalSpsPackage physicalSpsPackage = createPhysicalPackage("XX");

    assertThat(cheddPackageTypeKeyDataMapper.map(physicalSpsPackage)).isEqualTo(createComplementParameterSetKeyDataPair("Other"));
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
