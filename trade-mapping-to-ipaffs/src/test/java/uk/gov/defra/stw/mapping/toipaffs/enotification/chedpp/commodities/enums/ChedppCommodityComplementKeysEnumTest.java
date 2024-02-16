package uk.gov.defra.stw.mapping.toipaffs.enotification.chedpp.commodities.enums;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ChedppCommodityComplementKeysEnumTest {

  @Test
  void getValue_ReturnsEnumValueAsString() {
    // Given // When // Then
    assertThat(ChedppCommodityComplementKeysEnum.EPPO.getValue()).isEqualTo("EPPO");
  }

  @Test
  void toString_ReturnsEnumValueAsString() {
    // Given // When // Then
    assertThat(ChedppCommodityComplementKeysEnum.COMBINED_NOMENCLATURE.toString()).isEqualTo("CN Code (Combined Nomenclature)");
  }

  @Test
  void fromValue_ReturnsEnumForProvidedString() {
    // Given // When // Then
    assertThat(ChedppCommodityComplementKeysEnum.fromValue("EPPO")).isEqualTo(ChedppCommodityComplementKeysEnum.EPPO);
  }

  @Test
  void fromValue_ReturnsNoEnumIfInvalidValueProvided() {
    // Given // When // Then
    assertThat(ChedppCommodityComplementKeysEnum.fromValue("WIBBLE")).isNull();
  }
}
