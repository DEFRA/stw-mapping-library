package uk.gov.defra.stw.mapping.validation.rules.common.partone;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.validation.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

class NotificationImporterLocalReferenceNumberValidatorTest {
  private Notification notification;
  private NotificationImporterLocalReferenceNumberValidator validator;


  @BeforeEach
  void setup() throws Exception {
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = new ObjectMapper().readValue(notificationString, Notification.class);
    validator = new NotificationImporterLocalReferenceNumberValidator();
  }

  @Test
  void validate_ReturnsTrue_WhenValidReferenceNumber() {
    notification.getPartOne().setImporterLocalReferenceNumber(notification.getReferenceNumber());
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsTrue_WhenNullReferenceNumber() {
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsFalse_WhenEmptyReferenceNumber() {
    notification.getPartOne().setImporterLocalReferenceNumber("");
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenReferenceNumberIsTooLong() {
    String hundredCharacterString = "oixgs5PUARvzySS2ub8xoixgs5PUARvzySS2ub8xoixgs5PUARvzySS2ub8xoixgs5PUARvzySS2ub8xoixgs5PUARvzySS2ub8x";
    assertThat(hundredCharacterString).hasSize(100);
    notification.getPartOne().setImporterLocalReferenceNumber(hundredCharacterString);
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void getMessage_ReturnsCorrectMessage() {
    assertThat(validator.getMessage()).isEqualTo("Invalid notification part one local reference number");
  }
}
