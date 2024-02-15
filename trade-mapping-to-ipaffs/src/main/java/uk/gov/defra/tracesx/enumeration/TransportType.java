package uk.gov.defra.tracesx.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TransportType {
  SHIP("1"),
  RAILWAY_WAGON("2"),
  ROAD_VEHICLE("3"),
  AEROPLANE("4");

  private String value;

  TransportType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
