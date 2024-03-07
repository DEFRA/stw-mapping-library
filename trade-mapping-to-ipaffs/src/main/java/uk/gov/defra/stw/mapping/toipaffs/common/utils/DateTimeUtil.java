package uk.gov.defra.stw.mapping.toipaffs.common.utils;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.stereotype.Component;

@Component
public class DateTimeUtil {
  public ZonedDateTime toZonedDateTime(String data) {
    OffsetDateTime offsetDateTime = OffsetDateTime.parse(data);
    ZonedDateTime zonedDateTime = offsetDateTime.atZoneSameInstant(ZoneId.of("Europe/London"));
    return zonedDateTime;
  }
}
