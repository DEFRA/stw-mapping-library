package uk.gov.defra.stw.mapping.toipaffs.common;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;

import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;

public class DepartureTimeMapperTest {
  private DepartureTimeMapper departureTimeMapper;
  private SpsCertificate spsCertificate;

  @Test
  void map_ReturnsDepartureTime_WhenProvided() throws NotificationMapperException {
    spsCertificate = new SpsCertificate();
    departureTimeMapper = new DepartureTimeMapper();

    spsCertificate = new SpsCertificate()
    .withSpsExchangedDocument(new SpsExchangedDocument()
        .withIncludedSpsNote(List.of(new SpsNoteType()
            .withSubjectCode(new CodeType()
                .withValue("DEPARTURE_FROM_BCP_DATETIME"))
            .withContent(List.of(new TextType()
                .withValue("2020-01-01T22:30:00Z"))))));

    LocalTime actualTime = departureTimeMapper.map(spsCertificate);

    assertThat(actualTime).isEqualTo(LocalTime.parse("22:30:00"));
  }
}
