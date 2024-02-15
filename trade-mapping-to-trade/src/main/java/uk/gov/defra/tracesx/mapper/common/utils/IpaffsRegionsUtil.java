package uk.gov.defra.tracesx.mapper.common.utils;

import java.util.Map;

public class IpaffsRegionsUtil {
  public static final String GB = "GB";

  private static final Map<String, String> ukRegionsMap =
      Map.of(
          "EN", "GB-ENG",
          "SCO", "GB-SCT",
          "WA", "GB-WLS",
          "NIR", "GB-NIR");

  private IpaffsRegionsUtil() {}

  public static boolean isUkRegion(String countryCode) {
    return ukRegionsMap.containsKey(countryCode);
  }

  public static String getIsoRegionFromIpaffsRegion(String countryCode) {
    return ukRegionsMap.get(countryCode);
  }
}
