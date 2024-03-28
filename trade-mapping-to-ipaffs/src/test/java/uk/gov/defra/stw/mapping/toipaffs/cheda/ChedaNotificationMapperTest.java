package uk.gov.defra.stw.mapping.toipaffs.cheda;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum.CVEDA;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.SUBMITTED;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;

@ExtendWith(MockitoExtension.class)
class ChedaNotificationMapperTest {

  @Mock
  private ChedaPartOneMapper chedaPartOneMapper;

  @InjectMocks
  private ChedaNotificationMapper mapper;

  @Test
  void map_ReturnsChedaNotification() throws NotificationMapperException {
    SpsCertificate spsCertificate = new SpsCertificate();
    PartOne partOne = new PartOne();
    when(chedaPartOneMapper.map(spsCertificate)).thenReturn(partOne);

    Notification actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo(Notification.builder()
        .partOne(partOne)
        .type(CVEDA)
        .status(SUBMITTED)
        .build());
  }
}
