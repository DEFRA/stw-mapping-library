package uk.gov.defra.tracesx.mapper.common.utils;

public class MapperVersion {

  private static final String VERSION = "IPAFFS-17A-1.0.0-alpha";
  private static final String DESCRIPTION = "TradePlatform UN/CEFACT Mapping Version";

  private MapperVersion() {}

  public static String getVersion() {
    return VERSION;
  }

  public static String getDescription() {
    return DESCRIPTION;
  }
}