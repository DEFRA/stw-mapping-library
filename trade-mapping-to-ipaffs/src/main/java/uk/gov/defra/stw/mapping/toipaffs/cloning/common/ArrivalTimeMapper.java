package uk.gov.defra.stw.mapping.toipaffs.cloning.common;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.DateTimeType;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;

@Component
public class ArrivalTimeMapper implements Mapper<DateTimeType, LocalTime> {
  
  private static final ZoneId TIMEZONE = ZoneId.of("Europe/London");
  
  public LocalTime map(DateTimeType dateTime) throws NotificationMapperException {
    if (dateTime != null) {
      Instant utcDatetime = Instant.parse(dateTime.getDateTime().getValue());
      return LocalTime.ofInstant(utcDatetime, TIMEZONE);
    }
    return null;
  }
}
