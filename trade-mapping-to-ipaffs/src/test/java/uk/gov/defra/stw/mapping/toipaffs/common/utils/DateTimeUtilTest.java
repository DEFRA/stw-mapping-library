package uk.gov.defra.stw.mapping.toipaffs.common.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

public class DateTimeUtilTest {
  private DateTimeUtil dateTimeUtil;
  private static final String dateTimeString = "2020-01-01T22:30:00Z";

  @Test
  void maps_DateString_To_ZonedTimeZone() {
    dateTimeUtil = new DateTimeUtil();
    ZonedDateTime actualZonedDateTime = dateTimeUtil.toZonedDateTime(dateTimeString);
    assertThat(actualZonedDateTime.toLocalDate()).isEqualTo("2020-01-01");
    assertThat(actualZonedDateTime.toLocalTime()).isEqualTo("22:30:00");
  }
}
