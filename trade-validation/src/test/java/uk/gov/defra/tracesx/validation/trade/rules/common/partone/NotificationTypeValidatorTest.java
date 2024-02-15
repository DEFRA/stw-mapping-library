package uk.gov.defra.tracesx.validation.trade.rules.common.partone;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;
import uk.gov.defra.tracesx.validation.trade.testutils.ResourceUtil;

class NotificationTypeValidatorTest {

  private NotificationTypeValidator validator;
  private Notification notification;

  @BeforeEach
  void setup() throws Exception {
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = new ObjectMapper().readValue(notificationString, Notification.class);
    validator = new NotificationTypeValidator();
  }

  @Test
  void validate_ReturnsTrue_WhenValidTypeEnum() {
    notification.setType(NotificationTypeEnum.CHEDPP);
    assertThat(validator.validate(notification)).isTrue();
  }

  @ParameterizedTest
  @EnumSource(value = NotificationTypeEnum.class, mode = Mode.EXCLUDE, names = "CHEDPP")
  void validate_ReturnsFalse_WhenInValidTypeEnum(NotificationTypeEnum type) {
    notification.setType(type);
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenNullNotificationType() {
    notification.setType(null);
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void getMessage_ReturnsCorrectMessage() {
    assertThat(validator.getMessage()).isEqualTo("Invalid type");
  }
}
