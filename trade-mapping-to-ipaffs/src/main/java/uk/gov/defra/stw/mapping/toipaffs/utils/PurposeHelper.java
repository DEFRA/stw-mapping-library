package uk.gov.defra.stw.mapping.toipaffs.utils;

import static uk.gov.defra.stw.mapping.toipaffs.enumeration.SubjectCode.NON_CONFORMING_GOODS_DESTINATION_REGISTERED_NUMBER;

import java.util.List;
import java.util.Optional;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;

public class PurposeHelper {

  private PurposeHelper() {
  }

  public static Optional<String> getRegistrationNumber(List<SpsNoteType> spsNoteTypeList) {
    return SpsNoteTypeHelper.getNoteContentBySubjectCode(spsNoteTypeList,
        NON_CONFORMING_GOODS_DESTINATION_REGISTERED_NUMBER.getValue());
  }
}
