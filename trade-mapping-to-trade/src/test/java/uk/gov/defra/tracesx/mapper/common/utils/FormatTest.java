package uk.gov.defra.tracesx.mapper.common.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class FormatTest {

  @Test
  void fromLocalDateTime_SuccessfullyReturnsFormattedString() {
    String actual = Format.localDateTime.apply(LocalDateTime.of(2020, 5, 29, 16, 41, 51));

    assertThat(actual).isEqualTo("2020-05-29T16:41:51Z");
  }

  @Test
  void fromLocalDate_SuccessfullyReturnsFormattedString() {
    String actual = Format.localDate.apply(LocalDate.of(2020, 6, 17));

    assertThat(actual).isEqualTo("2020-06-17");
  }

}
