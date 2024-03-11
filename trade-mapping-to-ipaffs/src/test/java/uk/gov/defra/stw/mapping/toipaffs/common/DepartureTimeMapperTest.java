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

public class DepartureTimeMapperTest {
  private DepartureTimeMapper departureTimeMapper;
  private SpsCertificate spsCertificate;

  @Test
  void map_ReturnsDepartureTime_WhenProvided() {
    spsCertificate = new SpsCertificate();
    departureTimeMapper = new DepartureTimeMapper();

    spsCertificate = new SpsCertificate()
    .withSpsExchangedDocument(new SpsExchangedDocument()
        .withIncludedSpsNote(List.of(new SpsNoteType()
            .withSubjectCode(new CodeType()
                .withValue("DEPARTURE_FROM_BCP_DATETIME"))
            .withContent(List.of(new TextType()
                .withValue("2024-02-01T00:00:00Z"))))));

    LocalTime actualTime = departureTimeMapper.map(spsCertificate);

    assertThat(actualTime).isEqualTo("00:00:00");
  }

  @Test
  void map_ReturnsDepartureTime_WhenDaylightSavings() {
    spsCertificate = new SpsCertificate();
    departureTimeMapper = new DepartureTimeMapper();

    spsCertificate = new SpsCertificate()
    .withSpsExchangedDocument(new SpsExchangedDocument()
        .withIncludedSpsNote(List.of(new SpsNoteType()
            .withSubjectCode(new CodeType()
                .withValue("DEPARTURE_FROM_BCP_DATETIME"))
            .withContent(List.of(new TextType()
                .withValue("2024-04-01T00:00:00Z"))))));

    LocalTime actualTime = departureTimeMapper.map(spsCertificate);

    assertThat(actualTime).isEqualTo("01:00:00");
  }
}
