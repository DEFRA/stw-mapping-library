package uk.gov.defra.stw.mapping.toipaffs.chedpp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum;

@ExtendWith(MockitoExtension.class)
class ChedppNotificationMapperTest {

  @Mock
  private ChedppPartOneMapper chedppPartOneMapper;

  @InjectMocks
  private ChedppNotificationMapper chedppNotificationMapper;

  @Test
  void map_ReturnsChedppNotification() throws NotificationMapperException {
    SpsCertificate spsCertificate = new SpsCertificate();
    PartOne partOne = new PartOne();
    when(chedppPartOneMapper.map(spsCertificate)).thenReturn(partOne);

    Notification actual = chedppNotificationMapper.map(spsCertificate);

    assertThat(actual).isEqualTo(Notification.builder()
        .partOne(partOne)
        .type(NotificationTypeEnum.CHEDPP)
        .status(StatusEnum.SUBMITTED)
        .build());
  }
}
