package uk.gov.defra.stw.mapping.toipaffs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.chedp.ChedpNotificationMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;

@ExtendWith(MockitoExtension.class)
class NotificationMapperTest {

  @Mock
  private NotificationMapperFactory notificationMapperFactory;

  @Mock
  private ChedpNotificationMapper chedpNotificationMapper;

  private final ObjectMapper objectMapper = new ObjectMapper();

  private NotificationMapper notificationMapper;
  private SpsCertificate spsCertificate;
  private Notification ehcChedpNotification;

  @BeforeEach
  void setup() throws Exception {
    notificationMapper = new NotificationMapper(notificationMapperFactory);
    spsCertificate =
        JsonDeserializer.get(
            "chedp/chedp_ehc_complete.json", SpsCertificate.class);
    ehcChedpNotification =
        JsonDeserializer.get(
            "chedp/chedp_ipaffs_complete.json", Notification.class);
  }

  @Test
  void processCertificate_ReturnsChedpNotification_WhenChedpSpsCertificate()
      throws Exception {
    when(notificationMapperFactory.getMapper(NotificationTypeEnum.CVEDP))
        .thenReturn(chedpNotificationMapper);
    when(chedpNotificationMapper.map(spsCertificate)).thenReturn(ehcChedpNotification);

    Notification actualNotification =
        notificationMapper.processCertificate(spsCertificate, "CVEDP");
    assertThat(actualNotification).isEqualTo(ehcChedpNotification);
  }

  @Test
  void
      processCertificate_ThrowsNotificationMapperException_WhenInvalidNotificationTypeEnum() {
    assertThrows(
        NotificationMapperException.class,
        () -> notificationMapper.processCertificate(spsCertificate, "Invalid"));
  }
}
