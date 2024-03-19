package uk.gov.defra.stw.mapping.toipaffs.common;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;

@Component
public class DepartureTimeMapper implements Mapper<SpsCertificate, LocalTime> {

  @Override
  public LocalTime map(SpsCertificate spsCertificate) {
    return spsCertificate.getSpsExchangedDocument()
        .getIncludedSpsNote()
        .stream()
        .filter(includedSpsNote -> includedSpsNote.getSubjectCode()
            .getValue()
            .equals("DEPARTURE_FROM_BCP_DATETIME"))
        .findAny()
        .map(SpsNoteType::getContent)
        .map(content -> content.get(0))
        .map(TextType::getValue)
        .map(departureDateTime -> OffsetDateTime.parse(departureDateTime)
            .atZoneSameInstant(ZoneId.of("Europe/London"))
            .toLocalTime())
        .orElse(null);
  }
}
