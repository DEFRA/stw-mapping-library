package uk.gov.defra.tracesx.cloning.chedpp.commodities.enums;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ChedppCommodityComplementKeysEnumTest {

  @Test
  void getValue_ReturnsEnumValueAsString() {
    assertThat(ChedppCommodityComplementKeysEnum.COMMODITY_CODE.getValue()).isEqualTo("IPAFFS_CC");
  }

  @Test
  void toString_ReturnsEnumValueAsString() {
    assertThat(ChedppCommodityComplementKeysEnum.COMMODITY_CODE.toString()).isEqualTo("IPAFFS_CC");
  }
}
