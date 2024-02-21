package uk.gov.defra.stw.mapping.schama;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.everit.json.schema.ValidationException;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.schama.testutils.ResourceUtil;
import uk.gov.defra.stw.mapping.schema.EnotificationValidator;

class EnotificationValidatorTest {

  private static final String INVALID_UNCEFACT_PATH = "/uncefact/invalidPayload.json";
  private static final String INVALID_ENOTIFICATION_PATH = "/enotification/invalidPayload.json";
  private static final String VALID_ENOTIFICATION_ALL_FIELDS_PATH = "/enotification/validPayloadAllFields.json";
  private static final String VALID_ENOTIFICATION_MANDATORY_FIELDS_PATH = "/enotification/validPayloadMandatoryFields.json";

  private final EnotificationValidator enotificationValidator = new EnotificationValidator();

  private final String invalidUncefact = ResourceUtil.readFileToString(INVALID_UNCEFACT_PATH);
  private final String invalidEnotification = ResourceUtil.readFileToString(INVALID_ENOTIFICATION_PATH);
  private final String validEnotificationAllFields = ResourceUtil.readFileToString(VALID_ENOTIFICATION_ALL_FIELDS_PATH);
  private final String validEnotificationMandatoryFields = ResourceUtil.readFileToString(VALID_ENOTIFICATION_MANDATORY_FIELDS_PATH);

  @Test
  void validate_ThrowsValidationException_WhenInvalidUncefact() {
    assertThatThrownBy(() -> enotificationValidator.validate(invalidUncefact))
        .isInstanceOf(ValidationException.class)
        .asInstanceOf(InstanceOfAssertFactories.type(ValidationException.class))
        .extracting(ValidationException::getAllMessages)
        .asList()
        .containsExactlyInAnyOrder(
            "#/spsConsignment/consignorSpsParty/name: required key [value] not found",
            "#/spsConsignment/exportSpsCountry/id: required key [value] not found",
            "#/spsConsignment/importSpsCountry/id: required key [value] not found",
            "#/spsConsignment/consigneeSpsParty/name: required key [value] not found",
            "#/spsConsignment: required key [unloadingBaseportSpsLocation] not found",
            "#/spsConsignment/availabilityDueDateTime: required key [dateTime] not found",
            "#/spsConsignment/consigneeSpsParty/name: required key [value] not found",
            "#/spsConsignment/consignorSpsParty/name: required key [value] not found",
            "#/spsExchangedDocument: required key [signatorySpsAuthentication] not found",
            "#/spsExchangedDocument: required key [statusCode] not found",
            "#/spsExchangedDocument/id: required key [value] not found");
  }

  @Test
  void validate_ThrowsValidationException_WhenInvalidEnotificationPayload() {
    List<String> expectedErrors = List.of(
        "#/spsConsignment/mainCarriageSpsTransportMovement/0/modeCode/value: 6 is not a valid enum value",
        "#/spsConsignment/includedSpsConsignmentItem/0/includedSpsTradeLineItem/0/physicalSpsPackage/0/levelCode/value: 0 is not a valid enum value",
        "#/spsConsignment/includedSpsConsignmentItem/0/includedSpsTradeLineItem/0/physicalSpsPackage/0/typeCode/value: expected maxLength: 2, actual: 3",
        "#/spsConsignment/includedSpsConsignmentItem/0/includedSpsTradeLineItem/0/applicableSpsClassification/0/systemID/value: AH is not a valid enum value",
        "#/spsConsignment/includedSpsConsignmentItem/0/includedSpsTradeLineItem/0/applicableSpsClassification/0/systemName/0/value:  is not a valid enum value",
        "#/spsExchangedDocument/id/value: expected maxLength: 90, actual: 100",
        "#/spsExchangedDocument/typeCode/value:  is not a valid enum value",
        "#/spsExchangedDocument/includedSpsNote/1/subjectCode/value: PROD_TEMPERATURE is not a valid enum value"
    );

    assertThatThrownBy(() -> enotificationValidator.validate(invalidEnotification))
        .isInstanceOf(ValidationException.class)
        .asInstanceOf(InstanceOfAssertFactories.type(ValidationException.class))
        .extracting(ValidationException::getAllMessages)
        .isEqualTo(expectedErrors);
  }

  @Test
  void validate_ThrowsNoException_WhenValidEnotificationPayloadWithAllFieldsJson() {
    assertDoesNotThrow(() -> enotificationValidator.validate(validEnotificationAllFields));
  }

  @Test
  void validate_ThrowsNoException_WhenValidEnotificationPayloadWithMandatoryFieldsJson() {
    assertDoesNotThrow(() -> enotificationValidator.validate(validEnotificationMandatoryFields));
  }
}
