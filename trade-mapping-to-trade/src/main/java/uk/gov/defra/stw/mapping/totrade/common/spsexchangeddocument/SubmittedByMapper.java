package uk.gov.defra.stw.mapping.totrade.common.spsexchangeddocument;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SubmittedBy;
import uk.gov.defra.stw.mapping.totrade.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.UserInformation;

@Component
public class SubmittedByMapper implements Mapper<UserInformation, SubmittedBy> {

  @Override
  public SubmittedBy map(UserInformation submittedBy) {
    return new SubmittedBy(submittedBy.getDisplayName(), submittedBy.getUserId());
  }
}
