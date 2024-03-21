package uk.gov.defra.stw.mapping.toipaffs.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.TextType;

class SpsNoteTypeHelperTest {

  @Test
  void findNoteBySubjectCode_ReturnsOptionalContainingSpsNote_WhenSpsNoteSearchedForExists() {
    SpsNoteType note = new SpsNoteType()
        .withSubjectCode(new CodeType().withValue("SUBJECT_CODE"))
        .withContent(Collections.singletonList(new TextType().withValue("CONTENT")));
    List<SpsNoteType> noteTypeList = Collections.singletonList(note);

    Optional<SpsNoteType> result = SpsNoteTypeHelper.findNoteBySubjectCode(noteTypeList,
        "SUBJECT_CODE");

    assertThat(result).hasValue(note);
  }

  @Test
  void findNoteBySubjectCode_ReturnsOptionalContainingSpsNote_WhenCallingWithSpsCertificate() {
    SpsNoteType note = new SpsNoteType()
        .withSubjectCode(new CodeType().withValue("SUBJECT_CODE"))
        .withContent(Collections.singletonList(new TextType().withValue("CONTENT")));
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of(note)));

    Optional<SpsNoteType> result = SpsNoteTypeHelper.findNoteBySubjectCode(spsCertificate,
        "SUBJECT_CODE");

    assertThat(result).hasValue(note);
  }

  @Test
  void findNoteBySubjectCode_ReturnsEmptyOptional_WhenSpsNoteSearchedForDoesNotExist() {
    List<SpsNoteType> noteTypeList = Collections.emptyList();

    Optional<SpsNoteType> result = SpsNoteTypeHelper.findNoteBySubjectCode(noteTypeList,
        "SUBJECT_CODE");

    assertThat(result).isEmpty();
  }

  @Test
  void findNoteBySubjectCode_ReturnsEmptyOptional_WhenSpsNoteDoesNotHaveContent() {
    List<SpsNoteType> noteTypeList = Collections.singletonList(new SpsNoteType()
        .withSubjectCode(new CodeType().withValue("SUBJECT_CODE")));

    Optional<SpsNoteType> result = SpsNoteTypeHelper.findNoteBySubjectCode(noteTypeList,
        "SUBJECT_CODE");

    assertThat(result).isEmpty();
  }

  @Test
  void findNoteBySubjectCode_ReturnsEmptyOptional_WhenSpsNoteDoesNotHaveSubjectCode() {
    List<SpsNoteType> noteTypeList = Collections.singletonList(new SpsNoteType()
        .withContent(Collections.singletonList(new TextType().withValue("CONTENT"))));

    Optional<SpsNoteType> result = SpsNoteTypeHelper.findNoteBySubjectCode(noteTypeList,
        "SUBJECT_CODE");

    assertThat(result).isEmpty();
  }

  @Test
  void getNoteContentBySubjectCode_ReturnsOptionalContainingContent() {
    List<SpsNoteType> notes = List.of(new SpsNoteType()
        .withSubjectCode(new CodeType().withValue("SUBJECT_CODE"))
        .withContent(Collections.singletonList(new TextType().withValue("CONTENT"))));

    Optional<String> result = SpsNoteTypeHelper.getNoteContentBySubjectCode(notes, "SUBJECT_CODE");

    assertThat(result).hasValue("CONTENT");
  }

  @Test
  void getNoteContentBySubjectCode_ReturnsOptionalContainingContent_WhenCallingWithSpsCertificate() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of(new SpsNoteType()
                .withSubjectCode(new CodeType().withValue("SUBJECT_CODE"))
                .withContent(Collections.singletonList(new TextType().withValue("CONTENT"))))));

    Optional<String> result = SpsNoteTypeHelper.getNoteContentBySubjectCode(spsCertificate,
        "SUBJECT_CODE");

    assertThat(result).hasValue("CONTENT");
  }
}
