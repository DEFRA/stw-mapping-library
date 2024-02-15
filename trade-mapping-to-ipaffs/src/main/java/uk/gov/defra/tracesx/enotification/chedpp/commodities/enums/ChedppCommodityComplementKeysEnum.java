package uk.gov.defra.tracesx.enotification.chedpp.commodities.enums;

import java.util.Arrays;

public enum ChedppCommodityComplementKeysEnum {
  EPPO("EPPO"),
  COMBINED_NOMENCLATURE("CN Code (Combined Nomenclature)");

  private final String value;

  ChedppCommodityComplementKeysEnum(final String value) {
    this.value = value;
  }

  public static ChedppCommodityComplementKeysEnum fromValue(String text) {
    return Arrays.stream(values())
        .filter(entry -> entry.value.equals(text))
        .findFirst()
        .orElse(null);
  }

  public String getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return this.value;
  }
}
