package uk.gov.defra.stw.mapping.toipaffs.common;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test; 
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.TextType;

public class DepartureDateMapperTest {
  private DepartureDateMapper departureDateMapper;
  private SpsCertificate spsCertificate;

  @Test
  void map_ReturnsDepartureDate_WhenProvided() {
    departureDateMapper = new DepartureDateMapper();
    spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of(new SpsNoteType()
                .withSubjectCode(new CodeType()
                    .withValue("DEPARTURE_FROM_BCP_DATETIME"))
                .withContent(List.of(new TextType()
                    .withValue("2020-01-01T22:30:00Z"))))));
    
    LocalDate actualDate = departureDateMapper.map(spsCertificate);

    assertThat(actualDate).isEqualTo("2020-01-01");
  }

  @Test
  void map_ReturnsDepartureDate_WhenDaylightSavings() {
    departureDateMapper = new DepartureDateMapper();
    spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of(new SpsNoteType()
                .withSubjectCode(new CodeType()
                    .withValue("DEPARTURE_FROM_BCP_DATETIME"))
                .withContent(List.of(new TextType()
                    .withValue("2024-03-31T23:59:00Z"))))));
    
    LocalDate actualDate = departureDateMapper.map(spsCertificate);

    assertThat(actualDate).isEqualTo("2024-04-01");
  }
}
