package uk.gov.defra.stw.mapping.toipaffs.utils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.util.ObjectUtils;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;

public class SpsNoteTypeHelper {

  private SpsNoteTypeHelper() {
  }

  public static Optional<SpsNoteType> findSpsNoteByType(List<SpsNoteType> spsNoteTypeList,
      String spsNoteType) {
    return spsNoteTypeList.stream()
        .filter(note -> !ObjectUtils.isEmpty(note.getSubjectCode()))
        .filter(note -> Objects.equals(note.getSubjectCode().getValue(), spsNoteType))
        .filter(note -> !ObjectUtils.isEmpty(note.getContent()))
        .findFirst();
  }
}
