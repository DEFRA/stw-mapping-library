package uk.gov.defra.stw.mapping.toipaffs.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SubjectCode {
  CONFORMS_TO_EU("CONFORMS_TO_EU"),
  NON_CONFORMING_GOODS_DESTINATION_REGISTERED_NUMBER(
      "NON_CONFORMING_GOODS_DESTINATION_REGISTERED_NUMBER"),
  NON_CONFORMING_GOODS_DESTINATION_SHIP_NAME("NON_CONFORMING_GOODS_DESTINATION_SHIP_NAME"),
  NON_CONFORMING_GOODS_DESTINATION_SHIP_PORT("NON_CONFORMING_GOODS_DESTINATION_SHIP_PORT"),
  NON_CONFORMING_GOODS_DESTINATION_TYPE("NON_CONFORMING_GOODS_DESTINATION_TYPE");

  private final String value;
}
