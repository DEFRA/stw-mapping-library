package uk.gov.defra.tracesx.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet.QUANTITY;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.trade.dto.CodeType;
import uk.gov.defra.tracesx.trade.dto.SpsNoteType;
import uk.gov.defra.tracesx.trade.dto.TextType;

class SpsNoteTypeHelperTest {

  @Test
  void findSpsByNoteType_ReturnsOptionalContainingSpsNote_WhenSpsNoteSearchedForExists() {
    List<SpsNoteType> noteTypeList = Collections.singletonList(new SpsNoteType()
        .withSubjectCode(new CodeType().withValue(QUANTITY))
        .withContent(Collections.singletonList(new TextType().withValue("TEST"))));

    Optional<SpsNoteType> result = SpsNoteTypeHelper.findSpsNoteByType(noteTypeList, QUANTITY);
    assertThat(result).isPresent();
  }

  @Test
  void findSpsByNoteType_ReturnsEmptyOptional_WhenSpsNoteSearchedForDoesNotExist() {
    List<SpsNoteType> noteTypeList = Collections.emptyList();

    Optional<SpsNoteType> result = SpsNoteTypeHelper.findSpsNoteByType(noteTypeList, QUANTITY);
    assertThat(result).isEmpty();
  }

  @Test
  void findSpsByNoteType_ReturnsEmptyOptional_WhenSpsNoteDoesNotHaveContent() {
    List<SpsNoteType> noteTypeList = Collections.singletonList(new SpsNoteType()
        .withSubjectCode(new CodeType().withValue(QUANTITY)));

    Optional<SpsNoteType> result = SpsNoteTypeHelper.findSpsNoteByType(noteTypeList, QUANTITY);
    assertThat(result).isEmpty();
  }

  @Test
  void findSpsByNoteType_ReturnsEmptyOptional_WhenSpsNoteDoesNotHaveSubjectCode(){
    List<SpsNoteType> noteTypeList = Collections.singletonList(new SpsNoteType()
        .withContent(Collections.singletonList(new TextType().withValue("TEST"))));

    Optional<SpsNoteType> result = SpsNoteTypeHelper.findSpsNoteByType(noteTypeList, QUANTITY);
    assertThat(result).isEmpty();
  }
}
