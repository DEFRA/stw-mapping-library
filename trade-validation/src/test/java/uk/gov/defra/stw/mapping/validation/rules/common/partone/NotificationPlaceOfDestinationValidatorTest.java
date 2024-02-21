package uk.gov.defra.stw.mapping.validation.rules.common.partone;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.validation.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperator;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType;

class NotificationPlaceOfDestinationValidatorTest {

  private NotificationPlaceOfDestinationValidator validator;
  private Notification notification;
  private EconomicOperator placeOfDestination;

  @BeforeEach
  void setup() throws Exception {
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = new ObjectMapper().readValue(notificationString, Notification.class);
    placeOfDestination = notification.getPartOne().getPlaceOfDestination();
    validator = new NotificationPlaceOfDestinationValidator();
  }

  @Test
  void validate_ReturnsFalse_WhenNullPlaceOfDestinationCompanyName() {
    placeOfDestination.setCompanyName(null);
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenPlaceOfDestinationCompanyNameTooBig() {
    String longName = "a".repeat(256);
    placeOfDestination.setCompanyName(longName);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenInvalidPlaceOfDestinationType() {
    placeOfDestination.setType(EconomicOperatorType.CHARITY);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenPlaceOfDestinationAddressLineTooBig() {
    String longName = "a".repeat(256);
    placeOfDestination.getAddress().setAddressLine1(longName);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenPlaceOfDestinationCityTooBig() {
    String longName = "a".repeat(59);
    placeOfDestination.getAddress().setCity(longName);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenPlaceOfDestinationPostalCodeTooBig() {
    String longName = "a".repeat(33);
    placeOfDestination.getAddress().setPostalZipCode(longName);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenInvalidCountryISOCode() {
    String invalidISOCode = "G6";
    placeOfDestination.getAddress().setCountryISOCode(invalidISOCode);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsTrue_WhenConsigneePlaceOfDestination() {
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsTrue_WhenDestinationPlaceOfDestination() {
    notification.getPartOne().getPlaceOfDestination().setType(EconomicOperatorType.DESTINATION);
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsTrue_WhenImporterPlaceOfDestination() {
    notification.getPartOne().getPlaceOfDestination().setType(EconomicOperatorType.IMPORTER);
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void getMessage_ReturnsCorrectMessage() {
    assertThat(validator.getMessage()).isEqualTo("Invalid place of destination");
  }
}
