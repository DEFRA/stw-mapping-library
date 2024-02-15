package uk.gov.defra.tracesx.validation.trade.rules.common.partone;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperator;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType;
import uk.gov.defra.tracesx.validation.trade.testutils.ResourceUtil;

class NotificationTransporterValidatorTest {
  private NotificationTransporterValidator validator;
  private Notification notification;
  private EconomicOperator transporter;

  @BeforeEach
  void setup() throws Exception {
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = new ObjectMapper().readValue(notificationString, Notification.class);
    transporter = notification.getPartOne().getTransporter();
    validator = new NotificationTransporterValidator();
  }

  @Test
  void validate_ReturnsFalse_WhenNullTransporterCompanyName() {
    transporter.setCompanyName(null);
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenTransporterCompanyNameTooBig() {
    String longName = "a".repeat(256);
    transporter.setCompanyName(longName);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenInvalidTransporterType() {
    transporter.setType(EconomicOperatorType.CHARITY);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenTransporterAddressLineTooBig() {
    String longName = "a".repeat(256);
    transporter.getAddress().setAddressLine1(longName);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenTransporterCityTooBig() {
    String longName = "a".repeat(59);
    transporter.getAddress().setCity(longName);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenTransporterPostalCodeTooBig() {
    String longName = "a".repeat(33);
    transporter.getAddress().setPostalZipCode(longName);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenInvalidCountryISOCode() {
    String invalidISOCode = "G6";
    transporter.getAddress().setCountryISOCode(invalidISOCode);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsTrue_WhenCommercialTransporterType() {
    notification.getPartOne().getTransporter().setType(EconomicOperatorType.COMMERCIAL_TRANSPORTER);
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsTrue_WhenPrivateTransporterType() {
    notification.getPartOne().getTransporter().setType(EconomicOperatorType.PRIVATE_TRANSPORTER);
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void getMessage_ReturnsCorrectMessage() {
    assertThat(validator.getMessage()).isEqualTo("Invalid transporter");
  }
}
