package uk.gov.defra.stw.mapping.toipaffs.utils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.util.ObjectUtils;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.TextType;

public class SpsNoteTypeHelper {

  private SpsNoteTypeHelper() {
  }

  public static Optional<SpsNoteType> findNoteBySubjectCode(SpsCertificate spsCertificate,
      String subjectCode) {
    return findNoteBySubjectCode(spsCertificate.getSpsExchangedDocument().getIncludedSpsNote(),
        subjectCode);
  }

  public static Optional<SpsNoteType> findNoteBySubjectCode(List<SpsNoteType> notes,
      String subjectCode) {
    return notes.stream()
        .filter(note -> !ObjectUtils.isEmpty(note.getSubjectCode()))
        .filter(note -> Objects.equals(note.getSubjectCode().getValue(), subjectCode))
        .filter(note -> !ObjectUtils.isEmpty(note.getContent()))
        .findFirst();
  }

  public static Optional<String> getNoteContentBySubjectCode(SpsCertificate spsCertificate,
      String subjectCode) {
    return getNoteContentBySubjectCode(
        spsCertificate.getSpsExchangedDocument().getIncludedSpsNote(), subjectCode);
  }

  public static Optional<String> getNoteContentBySubjectCode(List<SpsNoteType> notes,
      String subjectCode) {
    return findNoteBySubjectCode(notes, subjectCode)
        .map(SpsNoteType::getContent)
        .map(contents -> contents.get(0))
        .map(TextType::getValue);
  }
}
