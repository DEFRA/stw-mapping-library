package uk.gov.defra.stw.mapping.toipaffs.common;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;

@Component
public class ArrivalTimeMapper implements Mapper<SpsCertificate, LocalTime> {

  @Override
  public LocalTime map(SpsCertificate spsCertificate) {
    String availabilityDueDateTime = spsCertificate.getSpsConsignment()
        .getAvailabilityDueDateTime()
        .getDateTime()
        .getValue();
    return OffsetDateTime.parse(availabilityDueDateTime)
        .atZoneSameInstant(ZoneId.of("Europe/London"))
        .toLocalTime();
  }

}