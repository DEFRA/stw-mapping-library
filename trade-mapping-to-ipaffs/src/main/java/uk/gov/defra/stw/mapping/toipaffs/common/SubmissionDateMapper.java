package uk.gov.defra.stw.mapping.toipaffs.common;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;

@Component
public class SubmissionDateMapper implements Mapper<SpsCertificate, LocalDateTime> {

  @Override
  public LocalDateTime map(SpsCertificate spsCertificate) {
    String dateString = spsCertificate.getSpsExchangedDocument().getIssueDateTime().getDateTime()
        .getValue();
    return OffsetDateTime.parse(dateString)
        .atZoneSameInstant(ZoneId.of("Europe/London"))
        .toLocalDateTime();
  }
}
