package uk.gov.defra.tracesx.mapper.common.spsexchangeddocument;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;
import uk.gov.defra.tracesx.trade.dto.CodeType;
import uk.gov.defra.tracesx.trade.dto.SpsNoteType;

class SpsNoteTypeCertificateTypeMapperTest {

  private static final String CHED_TYPE_SUBJECT_VALUE = "CHED_TYPE";

  private SpsNoteTypeCertificateTypeMapper certificateTypeMapper;
  private Notification notification;

  @BeforeEach
  void setup() {
    certificateTypeMapper = new SpsNoteTypeCertificateTypeMapper();
    notification = Notification.builder().build();
  }

  @Test
  void map_ReturnsCompleteSpsNoteType_WhenChedppNotificationType() {
    notification.setType(NotificationTypeEnum.CHEDPP);
    SpsNoteType spsNoteType = certificateTypeMapper.map(notification);
    assertCodeTypes(spsNoteType, "PP");
  }

  private void assertCodeTypes(SpsNoteType spsNoteType, String expectedChedValue) {
    List<CodeType> contentCodes = spsNoteType.getContentCode();
    assertThat(contentCodes.size()).isEqualTo(1);
    assertThat(contentCodes.get(0).getValue()).isEqualTo(expectedChedValue);
    CodeType subjectCode = spsNoteType.getSubjectCode();
    assertThat(subjectCode.getValue()).isEqualTo(CHED_TYPE_SUBJECT_VALUE);
  }
}
