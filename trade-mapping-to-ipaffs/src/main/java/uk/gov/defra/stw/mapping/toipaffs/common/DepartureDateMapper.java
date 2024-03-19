package uk.gov.defra.stw.mapping.toipaffs.common;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;

@Component
public class DepartureDateMapper implements Mapper<SpsCertificate, LocalDate> {

  @Override
  public LocalDate map(SpsCertificate spsCertificate) {
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
            .toLocalDate())
        .orElse(null);
  }
}
