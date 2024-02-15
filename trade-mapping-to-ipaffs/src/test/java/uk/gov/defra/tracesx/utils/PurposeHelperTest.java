package uk.gov.defra.tracesx.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.tracesx.testutils.TestConstants.INVALID;
import static uk.gov.defra.tracesx.testutils.TestConstants.NON_CONFORMING_GOODS_DESTINATION_REGISTERED_NUMBER;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.testutils.TestHelper;
import uk.gov.defra.tracesx.trade.dto.SpsNoteType;

class PurposeHelperTest {

  private static final String REGISTRATION_NUMBER = "1234";

  @Test
  void getRegistrationNumber_ReturnsRegistrationNumber_WhenFindBySpsNoteTypeCalled() {

    SpsNoteType spsNoteType = TestHelper.buildSpsNoteType(
        REGISTRATION_NUMBER, NON_CONFORMING_GOODS_DESTINATION_REGISTERED_NUMBER);

    List<SpsNoteType> noteTypeList = Collections.singletonList(spsNoteType);

    Optional<String> actual = PurposeHelper.getRegistrationNumber(noteTypeList);
    assertThat(actual).containsSame(REGISTRATION_NUMBER);
  }

  @Test
  void getRegistrationNumber_ReturnsNull_WhenFindBySpsNoteTypeCalledWithDifferentSubjectCode() {

    SpsNoteType spsNoteType = TestHelper.buildSpsNoteType(
        REGISTRATION_NUMBER, INVALID);

    List<SpsNoteType> noteTypeList = Collections.singletonList(spsNoteType);

    Optional<String> actual = PurposeHelper.getRegistrationNumber(noteTypeList);
    assertThat(actual).isEmpty();
  }
}
