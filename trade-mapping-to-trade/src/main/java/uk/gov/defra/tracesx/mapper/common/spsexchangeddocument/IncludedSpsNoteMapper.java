package uk.gov.defra.tracesx.mapper.common.spsexchangeddocument;

import static uk.gov.defra.tracesx.mapper.common.utils.Format.localDateTime;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.trade.dto.SpsNoteType;

@Component
public class IncludedSpsNoteMapper implements Mapper<Notification, List<SpsNoteType>> {

  @Override
  public List<SpsNoteType> map(Notification notification) {
    return Arrays.asList(mapVersion(notification),
        mapLastUpdated(notification),
        mapLastUpdatedBy(notification));
  }

  private SpsNoteType mapVersion(Notification notification) {
    return createSpsNoteType(String.valueOf(notification.getVersion()), "VERSION");
  }

  private SpsNoteType mapLastUpdated(Notification notification) {
    return createSpsNoteType(localDateTime.apply(notification.getLastUpdated()),
        "LAST_UPDATE_DATETIME");
  }

  private SpsNoteType mapLastUpdatedBy(Notification notification) {
    return createSpsNoteType(notification.getLastUpdatedBy().getDisplayName(), "LAST_UPDATE_BY");
  }

  private SpsNoteType createSpsNoteType(String textType, String codeType) {
    return new SpsNoteType()
        .withContent(Collections
            .singletonList(SpsTypeConverter.getTextType(textType)))
        .withSubjectCode(SpsTypeConverter.getCodeType(codeType));
  }
}
