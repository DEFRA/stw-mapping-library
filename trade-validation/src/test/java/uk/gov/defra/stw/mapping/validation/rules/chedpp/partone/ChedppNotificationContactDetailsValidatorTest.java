package uk.gov.defra.stw.mapping.validation.rules.chedpp.partone;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.validation.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.ContactDetails;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

class ChedppNotificationContactDetailsValidatorTest {
  private ChedppNotificationContactDetailsValidator validator;
  private Notification notification;

  @BeforeEach
  void setup() throws Exception {
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = new ObjectMapper().readValue(notificationString, Notification.class);
    validator = new ChedppNotificationContactDetailsValidator();
  }

  @Test
  void validate_ReturnsTrue_WhenValidContactDetails() {
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsTrue_WhenNullContactDetails() {
    notification.getPartOne().setContactDetails(null);
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsTrue_WhenNonNullContactDetailsAndNullFields() {
    notification.getPartOne().setContactDetails(new ContactDetails());
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsFalse_WhenContactNameTooLarge() {
    String longName = "a".repeat(256);
    notification.getPartOne().getContactDetails().setName(longName);
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenContactAgentTooLarge() {
    String longName = "a".repeat(256);
    notification.getPartOne().getContactDetails().setAgent(longName);
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenContactEmailTooLarge() {
    String longName = "a".repeat(256);
    notification.getPartOne().getContactDetails().setEmail(longName);
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenContactTelephoneTooLarge() {
    String longName = "a".repeat(256);
    notification.getPartOne().getContactDetails().setTelephone(longName);
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void getMessage_ReturnsCorrectMessage() {
    assertThat(validator.getMessage()).isEqualTo("Invalid contact details");
  }
}
