package uk.gov.defra.tracesx.validation.trade.rules.chedpp.partone;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperator;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType;
import uk.gov.defra.tracesx.validation.trade.testutils.ResourceUtil;

class ChedppNotificationPackerValidatorTest {
  private ChedppNotificationPackerValidator validator;
  private Notification notification;
  private EconomicOperator packer;

  @BeforeEach
  void setup() throws Exception {
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = new ObjectMapper().readValue(notificationString, Notification.class);
    packer = notification.getPartOne().getPacker();
    validator = new ChedppNotificationPackerValidator();
  }

  @Test
  void validate_ReturnsFalse_WhenNullPackerCompanyName() {
    packer.setCompanyName(null);
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenPackerCompanyNameTooBig() {
    String longName = "a".repeat(256);
    packer.setCompanyName(longName);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenInvalidPackerType() {
    packer.setType(EconomicOperatorType.CHARITY);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenPackerAddressLine1TooBig() {
    String longName = "a".repeat(256);
    packer.getAddress().setAddressLine1(longName);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenPackerAddressLine2TooBig() {
    String longName = "a".repeat(256);
    packer.getAddress().setAddressLine2(longName);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenPackerAddressLine3TooBig() {
    String longName = "a".repeat(256);
    packer.getAddress().setAddressLine3(longName);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenPackerCityTooBig() {
    String longName = "a".repeat(59);
    packer.getAddress().setCity(longName);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenPackerPostalCodeTooBig() {
    String longName = "a".repeat(33);
    packer.getAddress().setPostalZipCode(longName);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenInvalidCountryISOCode() {
    String invalidISOCode = "G6";
    packer.getAddress().setCountryISOCode(invalidISOCode);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsTrue_WhenValidPacker() {
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsTrue_WhenNullPacker() {
    notification.getPartOne().setPacker(null);
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void getMessage_ReturnsCorrectMessage() {
    assertThat(validator.getMessage()).isEqualTo("Invalid packer");
  }
}
