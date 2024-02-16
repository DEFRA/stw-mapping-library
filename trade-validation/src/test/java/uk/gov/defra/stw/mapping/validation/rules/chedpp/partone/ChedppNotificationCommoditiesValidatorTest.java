package uk.gov.defra.stw.mapping.validation.rules.chedpp.partone;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.validation.rules.common.partone.NotificationCommoditiesValidator;
import uk.gov.defra.stw.mapping.validation.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

@ExtendWith(MockitoExtension.class)
class ChedppNotificationCommoditiesValidatorTest {

  @Mock
  private NotificationCommoditiesValidator validator;
  private ChedppNotificationCommoditiesValidator commoditiesValidator;
  private Notification notification;

  @BeforeEach
  void setup() throws Exception {
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = new ObjectMapper().readValue(notificationString, Notification.class);

    commoditiesValidator = new ChedppNotificationCommoditiesValidator(validator);
  }

  @Test
  void validate_CallsCorrectFunctions() {
    when(validator.validate(notification)).thenReturn(true);
    commoditiesValidator.validate(notification);
    verify(validator).validate(notification);
  }

  @Test
  void validate_ReturnsFalse_WhenNullEppoCode() {
    when(validator.validate(notification)).thenReturn(true);
    notification.getPartOne().getCommodities().getCommodityComplement().get(0).setEppoCode(null);
    assertThat(commoditiesValidator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenNullSpeciesName() {
    when(validator.validate(notification)).thenReturn(true);
    notification.getPartOne().getCommodities().getCommodityComplement().get(0).setSpeciesName(null);
    assertThat(commoditiesValidator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenInvalidCommodity() {
    when(validator.validate(notification)).thenReturn(true);
    when(validator.validate(notification)).thenReturn(false);
    assertThat(commoditiesValidator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsTrue_WhenValidNotification() {
    when(validator.validate(notification)).thenReturn(true);
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void getMessage_ReturnsCorrectMessage() {
    commoditiesValidator.getMessage();
    verify(validator).getMessage();
  }
}
