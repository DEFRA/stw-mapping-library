package uk.gov.defra.tracesx.utils;

import static uk.gov.defra.tracesx.enumeration.SubjectCode.NON_CONFORMING_GOODS_DESTINATION_REGISTERED_NUMBER;

import java.util.List;
import java.util.Optional;
import uk.gov.defra.tracesx.trade.dto.SpsNoteType;

public class PurposeHelper {

  private PurposeHelper() {
  }

  public static Optional<String> getRegistrationNumber(List<SpsNoteType> spsNoteTypeList) {
    return SpsNoteTypeHelper.findSpsNoteByType(
            spsNoteTypeList, NON_CONFORMING_GOODS_DESTINATION_REGISTERED_NUMBER.getValue())
        .map(spsNoteType -> spsNoteType.getContent().get(0).getValue());
  }
}
