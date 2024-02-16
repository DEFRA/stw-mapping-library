package uk.gov.defra.stw.mapping.totrade.common.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MapperVersionTest {

  @Test
  void getVersion_ReturnsVersionString() {
    assertThat(MapperVersion.getVersion()).isEqualTo("IPAFFS-17A-1.0.0-alpha");
  }

  @Test
  void getDescription_ReturnsDescriptionString() {
    assertThat(MapperVersion.getDescription()).isEqualTo("TradePlatform UN/CEFACT Mapping Version");
  }
}
