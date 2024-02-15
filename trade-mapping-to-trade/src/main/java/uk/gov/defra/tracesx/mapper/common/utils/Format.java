package uk.gov.defra.tracesx.mapper.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class Format {

  private Format() { }

  public static final Function<LocalDateTime, String> localDateTime = x -> x
      .atZone(ZoneId.of("UTC"))
      .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));

  public static final Function<LocalDate, String> localDate = x -> x
      .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
}
