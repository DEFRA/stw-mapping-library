package uk.gov.defra.tracesx.cloning.chedpp.commodities.enums;

public enum ChedppCommodityComplementKeysEnum {
  COMMODITY_CODE("IPAFFS_CC");

  private final String value;

  ChedppCommodityComplementKeysEnum(final String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return this.value;
  }
}
