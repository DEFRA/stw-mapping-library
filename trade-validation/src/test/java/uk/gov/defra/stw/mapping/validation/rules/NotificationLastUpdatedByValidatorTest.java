package uk.gov.defra.stw.mapping.validation.rules;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.validation.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

class NotificationLastUpdatedByValidatorTest {
  private NotificationLastUpdatedByValidator validator;
  private Notification notification;

  @BeforeEach
  void setup() throws Exception {
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = new ObjectMapper().readValue(notificationString, Notification.class);
    validator = new NotificationLastUpdatedByValidator();
  }

  @Test
  void validate_ReturnsTrue_WhenValidLastUpdatedDisplayName() {
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsFalse_WhenNullLastUpdated() {
    notification.setLastUpdatedBy(null);
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenNullDisplayName() {
    notification.getLastUpdatedBy().setDisplayName(null);
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void getMessage_ReturnsCorrectMessage() {
    assertThat(validator.getMessage()).isEqualTo("Invalid last updated by");
  }
}
