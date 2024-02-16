package uk.gov.defra.stw.mapping.totrade.common.spsexchangeddocument;

import java.util.Collections;
import java.util.Map;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.totrade.common.Mapper;
import uk.gov.defra.stw.mapping.totrade.common.utils.SpsTypeConverter;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;

@Component
public class SpsNoteTypeCertificateTypeMapper implements Mapper<Notification, SpsNoteType> {

  private static final String CHEDPP_TYPE_VALUE = "PP";
  private static final String CHED_TYPE_SUBJECT_VALUE = "CHED_TYPE";

  private final Map<NotificationTypeEnum, String> typeMap;

  public SpsNoteTypeCertificateTypeMapper() {
    typeMap = Map.of(
        NotificationTypeEnum.CHEDPP, CHEDPP_TYPE_VALUE);
  }

  @Override
  public SpsNoteType map(Notification data) {
    return new SpsNoteType()
        .withContentCode(Collections
            .singletonList(SpsTypeConverter.getCodeType(typeMap.get(data.getType()))))
        .withSubjectCode(SpsTypeConverter.getCodeType(CHED_TYPE_SUBJECT_VALUE));
  }
}
