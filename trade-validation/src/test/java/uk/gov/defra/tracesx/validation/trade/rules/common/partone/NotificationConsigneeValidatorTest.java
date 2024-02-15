package uk.gov.defra.tracesx.validation.trade.rules.common.partone;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperator;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType;
import uk.gov.defra.tracesx.validation.trade.testutils.ResourceUtil;

class NotificationConsigneeValidatorTest {

  private NotificationConsigneeValidator validator;
  private Notification notification;
  private EconomicOperator consignee;

  @BeforeEach
  void setup() throws Exception {
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = new ObjectMapper().readValue(notificationString, Notification.class);
    consignee = notification.getPartOne().getConsignee();
    validator = new NotificationConsigneeValidator();
  }

  @Test
  void validate_ReturnsFalse_WhenNullConsigneeCompanyName() {
    consignee.setCompanyName(null);
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenConsigneeCompanyNameTooBig() {
    String longName = "a".repeat(256);
    consignee.setCompanyName(longName);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenInvalidConsigneeType() {
    consignee.setType(EconomicOperatorType.CHARITY);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenConsigneeAddressLineTooBig() {
    String longName = "a".repeat(256);
    consignee.getAddress().setAddressLine1(longName);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenConsigneeCityTooBig() {
    String longName = "a".repeat(59);
    consignee.getAddress().setCity(longName);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenConsigneePostalCodeTooBig() {
    String longName = "a".repeat(33);
    consignee.getAddress().setPostalZipCode(longName);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenInvalidCountryISOCode() {
    String invalidISOCode = "G6";
    consignee.getAddress().setCountryISOCode(invalidISOCode);

    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsTrue_WhenConsigneeIsConsignee() {
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsTrue_WhenConsigneeIsImporter() {
    notification.getPartOne().getConsignee().setType(EconomicOperatorType.IMPORTER);
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsTrue_WhenConsigneeIsDestination() {
    notification.getPartOne().getConsignee().setType(EconomicOperatorType.DESTINATION);
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void getMessage_ReturnsCorrectMessage() {
    assertThat(validator.getMessage()).isEqualTo("Invalid consignee");
  }
}
