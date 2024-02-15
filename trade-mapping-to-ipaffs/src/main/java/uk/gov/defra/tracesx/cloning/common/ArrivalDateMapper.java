package uk.gov.defra.tracesx.cloning.common;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.trade.dto.DateTimeType;

@Component
public class ArrivalDateMapper implements Mapper<DateTimeType, LocalDate> {
  
  private static final ZoneId TIMEZONE = ZoneId.of("Europe/London");
  
  public LocalDate map(DateTimeType dateTime) throws NotificationMapperException {
    if (dateTime != null) {
      Instant utcDatetime = Instant.parse(dateTime.getDateTime().getValue());
      return LocalDate.ofInstant(utcDatetime, TIMEZONE);
    }
    return null;
  }
}
