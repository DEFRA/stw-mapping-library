package uk.gov.defra.stw.mapping.validation.rules;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.validation.rules.common.LocalDateTimeValidator;
import uk.gov.defra.stw.mapping.validation.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

@ExtendWith(MockitoExtension.class)
class NotificationLastUpdatedValidatorTest {
  @Mock
  private LocalDateTimeValidator validator;
  private NotificationLastUpdatedValidator lastUpdatedValidator;
  private Notification notification;

  @BeforeEach
  void setup() throws Exception {
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = new ObjectMapper().readValue(notificationString, Notification.class);
    lastUpdatedValidator = new NotificationLastUpdatedValidator(validator);
  }

  @Test
  void validate_CallsCorrectFunction() {
    lastUpdatedValidator.validate(notification);
    verify(validator).validate(notification.getLastUpdated());
  }

  @Test
  void getMessage_ReturnsCorrectMessage() {
    assertThat(lastUpdatedValidator.getMessage()).isEqualTo("Invalid last updated dateTime");
  }
}
