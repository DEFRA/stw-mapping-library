package uk.gov.defra.stw.mapping.totrade.common.spsexchangeddocument;

import static uk.gov.defra.stw.mapping.totrade.common.utils.Format.localDateTime;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.totrade.common.Mapper;
import uk.gov.defra.stw.mapping.totrade.common.utils.SpsTypeConverter;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;

@Component
public class IncludedSpsNoteFromPartOneMapper implements Mapper<PartOne, List<SpsNoteType>> {

  private static final String DATE_OF_DEPARTURE_SUBJECT_VALUE = "DEPARTURE_FROM_BCP_DATETIME";

  @Override
  public List<SpsNoteType> map(PartOne partOne) {
    List<SpsNoteType> notes = new ArrayList<>();

    if (partOne.getDepartureTime() != null && partOne.getDepartureDate() != null) {
      notes.add(mapForDepartureDateTime(partOne));
    }

    return notes;
  }

  private String mapDateTime(PartOne partOne) {
    return localDateTime.apply(LocalDateTime.of(
        partOne.getDepartureDate(),
        partOne.getDepartureTime()));
  }

  private SpsNoteType mapForDepartureDateTime(PartOne partOne) {
    return new SpsNoteType()
        .withContent(Collections
            .singletonList(SpsTypeConverter.getTextType(mapDateTime(partOne))))
        .withSubjectCode(SpsTypeConverter.getCodeType(DATE_OF_DEPARTURE_SUBJECT_VALUE));
  }
}
