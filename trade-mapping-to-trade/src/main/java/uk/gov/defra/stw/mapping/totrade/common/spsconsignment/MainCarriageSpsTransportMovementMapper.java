package uk.gov.defra.stw.mapping.totrade.common.spsconsignment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransport;

public class MainCarriageSpsTransportMovementMapper {

  protected static final String SCHEME_AGENCY_ID = "GB";
  protected static final String SCHEME_AGENCY_NAME = "United Kingdom";

  protected boolean isNull(MeansOfTransport data) {
    if (data == null) {
      return true;
    } else {
      return data.getId() == null || data.getType() == null;
    }
  }

  @Getter
  @AllArgsConstructor
  public static class TypeMap {

    private final String id;
    private final String schemeId;
  }
}
