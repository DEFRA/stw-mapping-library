package uk.gov.defra.tracesx.mapper.common.spsexchangeddocument;

import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.UserInformation;
import uk.gov.defra.tracesx.trade.dto.SubmittedBy;

@Component
public class SubmittedByMapper implements Mapper<UserInformation, SubmittedBy> {

  @Override
  public SubmittedBy map(UserInformation submittedBy) {
    return new SubmittedBy(submittedBy.getDisplayName(), submittedBy.getUserId());
  }
}
