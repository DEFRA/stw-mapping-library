package uk.gov.defra.tracesx.mapper.chedpp.spsexchangeddocument;

import java.util.Collections;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.trade.dto.CodeType;
import uk.gov.defra.tracesx.trade.dto.SpsNoteType;
import uk.gov.defra.tracesx.trade.dto.TextType;

@Component
public class ChedppSpsNoteTypeChildNotificationMapper implements Mapper<Notification, SpsNoteType> {

  private static final String YES = "YES";
  private static final String NO = "NO";
  private static final String CHILD_NOTIFICATION = "CHILD_NOTIFICATION";

  @Override
  public SpsNoteType map(Notification notification) {
    return new SpsNoteType()
        .withContent(Collections.singletonList(new TextType()
            .withValue(convertBooleanToStringRepresentation(notification.getChildNotification()))))
        .withSubjectCode(new CodeType()
            .withValue(CHILD_NOTIFICATION));
  }

  private String convertBooleanToStringRepresentation(Boolean bool) {
    if (bool == null) {
      return NO;
    }

    return bool.equals(Boolean.TRUE) ? YES : NO;
  }
}
