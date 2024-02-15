package uk.gov.defra.tracesx.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;

class CertificateHelperTest {

  @Test
  void getCertificateType_ReturnsNotificationTypeEnum_WhenProvidedTypeIsValid()
      throws NotificationMapperException {
    NotificationTypeEnum result = CertificateHelper.getCertificateType("CVEDP");

    assertThat(result).isEqualTo(NotificationTypeEnum.CVEDP);
  }

  @Test
  void getCertificateType_ThrowsException_WhenProvidedTypeIsInvalid() {
    assertThatThrownBy(() -> CertificateHelper.getCertificateType("Invalid"))
        .isInstanceOf(NotificationMapperException.class);
  }
}
