package uk.gov.defra.tracesx.mapper.common.spsconsignment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.mapper.common.utils.Format;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;
import uk.gov.defra.tracesx.trade.dto.DateTime;
import uk.gov.defra.tracesx.trade.dto.DateTimeType;

@Component
public class AvailabilityDueDateTimeMapper implements Mapper<PartOne, DateTimeType> {

  @Override
  public DateTimeType map(PartOne data) {
    if (data.getArrivalDate() == null || data.getArrivalTime() == null) {
      return null;
    }
    return new DateTimeType()
        .withDateTime(createDateTime(data.getArrivalDate(),
            data.getArrivalTime()));
  }

  private DateTime createDateTime(LocalDate localDate, LocalTime localTime) {
    LocalDateTime localDateTime =
        LocalDateTime.of(localDate.getYear(),
            localDate.getMonth(),
            localDate.getDayOfMonth(),
            localTime.getHour(),
            localTime.getMinute(),
            localTime.getSecond());
    return new DateTime().withValue(Format.localDateTime.apply(localDateTime));
  }
}
