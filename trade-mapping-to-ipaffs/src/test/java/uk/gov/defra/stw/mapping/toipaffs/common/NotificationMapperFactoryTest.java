package uk.gov.defra.stw.mapping.toipaffs.common;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum.CED;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum.CHEDPP;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum.CVEDA;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum.CVEDP;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.cloning.cheda.ChedaNotificationMapperImpl;
import uk.gov.defra.stw.mapping.toipaffs.cloning.chedp.ChedpNotificationMapperImpl;
import uk.gov.defra.stw.mapping.toipaffs.common.chedpp.ChedppNotificationMapper;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

@ExtendWith(MockitoExtension.class)
class NotificationMapperFactoryTest {

  @Mock
  private ChedaNotificationMapperImpl chedaNotificationMapper;

  @Mock
  private ChedpNotificationMapperImpl chedpNotificationMapper;

  @Mock
  private ChedppNotificationMapper chedppNotificationMapper;

  private NotificationMapperFactory notificationMapperFactory;

  @BeforeEach
  void setup() {
    notificationMapperFactory =
        new NotificationMapperFactory(chedaNotificationMapper, chedpNotificationMapper, chedppNotificationMapper);
  }

  @Test
  void getMapper_ReturnsCorrectMapper_WhenTypeIsChedpp() {
    Mapper<SpsCertificate, Notification> result = notificationMapperFactory.getMapper(CHEDPP);

    assertThat(result).isEqualTo(chedppNotificationMapper);
  }

  @Test
  void getMapper_ReturnsCorrectMapper_WhenTypeIsCvedp() {
    Mapper<SpsCertificate, Notification> result = notificationMapperFactory.getMapper(CVEDP);

    assertThat(result).isEqualTo(chedpNotificationMapper);
  }

  @Test
  void getMapper_ReturnsCorrectMapper_WhenTypeIsCveda() {
    Mapper<SpsCertificate, Notification> result = notificationMapperFactory.getMapper(CVEDA);

    assertThat(result).isEqualTo(chedaNotificationMapper);
  }

  @Test
  void getMapper_ReturnsNull_WhenMapperDoesNotExist() {
    Mapper<SpsCertificate, Notification> result = notificationMapperFactory.getMapper(CED);

    assertThat(result).isNull();
  }
}
