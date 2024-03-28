package uk.gov.defra.stw.mapping.toipaffs.cheda;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;

class EstimatedJourneyTimeMapperTest {

  private final EstimatedJourneyTimeMapper mapper = new EstimatedJourneyTimeMapper();

  @Test
  void map_ReturnsEstimatedJourneyTime_WhenPresent() throws NotificationMapperException {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of(new SpsNoteType()
                .withSubjectCode(new CodeType().withValue("ESTIMATED_JOURNEY_TIME_IN_MINUTES"))
                .withContent(List.of(new TextType().withValue("1"))))));

    Integer actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo(1);
  }

  @Test
  void map_ReturnsNull_WhenNotPresent() throws NotificationMapperException {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of()));

    Integer actual = mapper.map(spsCertificate);

    assertThat(actual).isNull();
  }

  @Test
  void map_ThrowsException_WhenContentIsNotANumber() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of(new SpsNoteType()
                .withSubjectCode(new CodeType().withValue("ESTIMATED_JOURNEY_TIME_IN_MINUTES"))
                .withContent(List.of(new TextType().withValue("Not a number"))))));

    assertThatThrownBy(() -> mapper.map(spsCertificate))
        .isInstanceOf(NotificationMapperException.class)
        .hasMessage("Error mapping estimated journey time")
        .hasRootCauseInstanceOf(NumberFormatException.class)
        .hasRootCauseMessage("For input string: \"Not a number\"");
  }
}
