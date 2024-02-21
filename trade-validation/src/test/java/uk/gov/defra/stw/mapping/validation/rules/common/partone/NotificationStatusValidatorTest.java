package uk.gov.defra.stw.mapping.validation.rules.common.partone;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;
import uk.gov.defra.stw.mapping.validation.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum;

class NotificationStatusValidatorTest {

  private NotificationStatusValidator validator;
  private Notification notification;

  @BeforeEach
  void setup() throws Exception {
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = new ObjectMapper().readValue(notificationString, Notification.class);
    validator = new NotificationStatusValidator();
  }

  @ParameterizedTest
  @EnumSource(value = StatusEnum.class, names = {
      "IN_PROGRESS", "MODIFY", "REJECTED", "VALIDATED", "CANCELLED", "DELETED", "AMEND",
      "PARTIALLY_REJECTED", "SPLIT_CONSIGNMENT", "SUBMITTED"
  })
  void validate_ReturnsTrue_WhenValidStatusEnum(StatusEnum status) {
    notification.setStatus(status);
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsFalse_WhenNullStatusEnum() {
    notification.setStatus(null);
    assertThat(validator.validate(notification)).isFalse();
  }

  @ParameterizedTest
  @EnumSource(value = StatusEnum.class, mode = Mode.EXCLUDE,names = {
      "IN_PROGRESS", "MODIFY", "REJECTED", "VALIDATED", "CANCELLED", "DELETED", "AMEND",
      "PARTIALLY_REJECTED", "SPLIT_CONSIGNMENT", "SUBMITTED"
  })
  void validate_ReturnsFalse_WhenInvalidStatusEnum(StatusEnum status) {
    notification.setStatus(status);
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void getMessage_ReturnsCorrectMessage() {
    assertThat(validator.getMessage()).isEqualTo("Invalid status");
  }
}
