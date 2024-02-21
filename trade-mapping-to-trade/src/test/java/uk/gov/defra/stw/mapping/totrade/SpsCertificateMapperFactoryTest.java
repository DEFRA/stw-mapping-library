package uk.gov.defra.stw.mapping.totrade;

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
import uk.gov.defra.stw.mapping.totrade.chedpp.ChedppSpsCertificateMapper;
import uk.gov.defra.stw.mapping.totrade.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

@ExtendWith(MockitoExtension.class)
class SpsCertificateMapperFactoryTest {

  @Mock
  private ChedppSpsCertificateMapper chedppSpsCertificateMapper;

  private Notification notification;

  private SpsCertificateMapperFactory spsCertificateMapperFactory;

  @BeforeEach
  void setup() {
    spsCertificateMapperFactory = new SpsCertificateMapperFactory(chedppSpsCertificateMapper);

    notification = Notification.builder().build();
  }

  @Test
  void getMapper_ReturnNull_WhenCVEDANotificationType() {
    notification.setType(CVEDA);

    Mapper<Notification, SpsCertificate> actualSpsCertificateMapper = spsCertificateMapperFactory.getMapper(notification.getType());

    assertThat(actualSpsCertificateMapper).isNull();
  }

  @Test
  void getMapper_ReturnNull_WhenCVEDPNotificationType() {
    notification.setType(CVEDP);

    Mapper<Notification, SpsCertificate> actualSpsCertificateMapper = spsCertificateMapperFactory.getMapper(notification.getType());

    assertThat(actualSpsCertificateMapper).isNull();
  }

  @Test
  void getMapper_ReturnChedppSpsCertificateMapper_WhenCHEDPPNotificationType() {
    notification.setType(CHEDPP);

    Mapper<Notification, SpsCertificate> actualSpsCertificateMapper = spsCertificateMapperFactory.getMapper(notification.getType());

    assertThat(actualSpsCertificateMapper).isEqualTo(chedppSpsCertificateMapper);
  }

  @Test
  void getMapper_ReturnNull_WhenCEDNotificationType() {
    notification.setType(CED);

    Mapper<Notification, SpsCertificate> actualSpsCertificateMapper = spsCertificateMapperFactory.getMapper(notification.getType());

    assertThat(actualSpsCertificateMapper).isNull();
  }
}
