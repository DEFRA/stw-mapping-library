package uk.gov.defra.stw.mapping.toipaffs.common;

import static uk.gov.defra.stw.mapping.toipaffs.utils.SpsNoteTypeHelper.getNoteContentBySubjectCode;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;

@Component
public class DepartureTimeMapper implements Mapper<SpsCertificate, LocalTime> {

  @Override
  public LocalTime map(SpsCertificate spsCertificate) {
    return getNoteContentBySubjectCode(spsCertificate, "DEPARTURE_FROM_BCP_DATETIME")
        .map(departureDateTime -> OffsetDateTime.parse(departureDateTime)
            .atZoneSameInstant(ZoneId.of("Europe/London"))
            .toLocalTime())
        .orElse(null);
  }
}
