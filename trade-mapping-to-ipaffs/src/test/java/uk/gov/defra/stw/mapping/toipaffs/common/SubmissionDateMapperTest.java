package uk.gov.defra.stw.mapping.toipaffs.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.DateTime;
import uk.gov.defra.stw.mapping.dto.DateTimeType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;

class SubmissionDateMapperTest {

  private final SubmissionDateMapper mapper = new SubmissionDateMapper();

  @Test
  void map_ReturnsSubmissionDate_WhenOutsideDaylightSavings() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIssueDateTime(new DateTimeType()
                .withDateTime(new DateTime()
                    .withValue("2024-01-01T09:30:15Z"))));

    LocalDateTime actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo(LocalDateTime.of(2024, 1, 1, 9, 30, 15));
  }

  @Test
  void map_ReturnsSubmissionDate_WhenInsideDaylightSavings() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIssueDateTime(new DateTimeType()
                .withDateTime(new DateTime()
                    .withValue("2024-06-01T09:30:15Z"))));

    LocalDateTime actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo(LocalDateTime.of(2024, 6, 1, 10, 30, 15));
  }
}
