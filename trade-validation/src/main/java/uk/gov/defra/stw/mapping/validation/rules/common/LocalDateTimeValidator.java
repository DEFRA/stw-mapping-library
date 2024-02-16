package uk.gov.defra.stw.mapping.validation.rules.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeValidator {

  public boolean validate(LocalDateTime localDateTime) {
    if (localDateTime == null) {
      return false;
    }
    return isValid(localDateTime);
  }

  private boolean isValid(LocalDateTime localDateTime) {
    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    try {
      formatter.parse(localDateTime.toString());
    } catch (DateTimeParseException dateTimeParseException) {
      return false;
    }
    return true;
  }
}
