package uk.gov.defra.tracesx.validation.trade.rules.common.partone;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperator;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType;
import uk.gov.defra.tracesx.validation.trade.testutils.ResourceUtil;

class NotificationConsignorValidatorTest {

  private NotificationConsignorValidator validator;
  private Notification notification;
  private EconomicOperator consignor;

  @BeforeEach
  void setup() throws Exception {
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = new ObjectMapper().readValue(notificationString, Notification.class);
    consignor = notification.getPartOne().getConsignor();
    validator = new NotificationConsignorValidator();
  }

  @Test
  void validate_ReturnsFalse_WhenNullConsignorCompanyName() {
    consignor.setCompanyName(null);
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenConsignorCompanyNameTooBig() {
    String longName = "a".repeat(256);
    consignor.setCompanyName(longName);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenInvalidConsignorType() {
    consignor.setType(EconomicOperatorType.CHARITY);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenConsignorAddressLineTooBig() {
    String longName = "a".repeat(256);
    consignor.getAddress().setAddressLine1(longName);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenConsignorCityTooBig() {
    String longName = "a".repeat(59);
    consignor.getAddress().setCity(longName);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenConsignorPostalCodeTooBig() {
    String longName = "a".repeat(33);
    consignor.getAddress().setPostalZipCode(longName);

    assertThat(validator.validate(notification)).isFalse();
  }

  @ParameterizedTest
  @MethodSource("invalidCodes")
  void validate_ReturnsFalse_WhenCountryIsoCodeIsInvalid(String invalidISOCode) {
    consignor.getAddress().setCountryISOCode(invalidISOCode);

    assertThat(validator.validate(notification)).isFalse();
  }

  private static Stream<Arguments> invalidCodes() {
    return Stream.of(
        arguments(named("6 character format", "GB-5LS")),
        arguments(named("unknown format", "GB-")),
        arguments(named("5 character format", "ES-C6"))
    );
  }


  @ParameterizedTest
  @MethodSource("validCodes")
  void validate_ReturnsTrue_WhenCountryIsoCodeIsValid(String validISOCode) {
    consignor.getAddress().setCountryISOCode(validISOCode);

    assertThat(validator.validate(notification)).isTrue();
  }

  private static Stream<Arguments> validCodes() {
    return Stream.of(
      arguments(named("6 character format", "GB-WLS")),
      arguments(named("2 character format", "AF")),
      arguments(named("5 character format", "ES-CN"))
    );
  }

  @Test
  void validate_ReturnsTrue_WhenValidConsignor() {
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void getMessage_ReturnsCorrectMessage() {
    assertThat(validator.getMessage()).isEqualTo("Invalid consignor");
  }
}
