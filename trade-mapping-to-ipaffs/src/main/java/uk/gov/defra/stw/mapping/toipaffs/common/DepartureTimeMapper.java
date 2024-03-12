package uk.gov.defra.stw.mapping.toipaffs.common;

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
    String departureDateTime = spsCertificate.getSpsExchangedDocument()
        .getIncludedSpsNote()
        .stream()
        .filter(includedSpsNote -> includedSpsNote.getSubjectCode()
            .getValue()
            .equals("DEPARTURE_FROM_BCP_DATETIME"))
        .findAny()
        .orElseThrow()
        .getContent()
        .get(0)
        .getValue();
    return OffsetDateTime.parse(departureDateTime)
      .atZoneSameInstant(ZoneId.of("Europe/London"))
      .toLocalTime();
  }

}