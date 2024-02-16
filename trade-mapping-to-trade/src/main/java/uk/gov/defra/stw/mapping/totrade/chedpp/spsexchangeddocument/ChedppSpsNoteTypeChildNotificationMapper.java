package uk.gov.defra.stw.mapping.totrade.chedpp.spsexchangeddocument;

import java.util.Collections;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.totrade.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

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
