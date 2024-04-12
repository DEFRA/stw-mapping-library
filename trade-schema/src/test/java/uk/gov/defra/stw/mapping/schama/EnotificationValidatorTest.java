package uk.gov.defra.stw.mapping.schama;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static uk.gov.defra.stw.mapping.schama.testutils.ResourceUtil.readFileToString;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.everit.json.schema.ValidationException;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.schema.EnotificationValidator;

class EnotificationValidatorTest {

  private final EnotificationValidator enotificationValidator = new EnotificationValidator();

  @Test
  void validate_ThrowsValidationException_WhenInvalidUncefact() {
    String payload = readFileToString("/uncefact/invalidUncefact.json");

    assertThatThrownBy(() -> enotificationValidator.validate(payload))
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
            "#/spsExchangedDocument: required key [signatorySpsAuthentication] not found",
            "#/spsExchangedDocument: required key [statusCode] not found",
            "#/spsExchangedDocument/id: required key [value] not found",
            "#/spsConsignment/examinationSpsEvent/occurrenceSpsLocation/name: expected minimum item count: 1, found: 0",
            "#/spsConsignment/exportSpsCountry/name: expected minimum item count: 1, found: 0",
            "#/spsConsignment/importSpsCountry/name: expected minimum item count: 1, found: 0",
            "#/spsExchangedDocument/name: expected minimum item count: 1, found: 0",
            "#/spsConsignment: required key [deliverySpsParty] not found",
            "#/spsConsignment: required key [mainCarriageSpsTransportMovement] not found",
            "#/spsConsignment/consigneeSpsParty: required key [id] not found",
            "#/spsConsignment/consignorSpsParty: required key [id] not found"
        );
  }

  @Test
  void validate_ThrowsValidationException_WhenInvalidEnotification() {
    String payload = readFileToString("/enotification/invalidEnotification.json");

    assertThatThrownBy(() -> enotificationValidator.validate(payload))
        .isInstanceOf(ValidationException.class)
        .asInstanceOf(InstanceOfAssertFactories.type(ValidationException.class))
        .extracting(ValidationException::getAllMessages)
        .asList()
        .containsExactlyInAnyOrder(
            "#/spsConsignment/mainCarriageSpsTransportMovement/0/modeCode/value: 6 is not a valid enum value",
            "#/spsConsignment/includedSpsConsignmentItem/0/includedSpsTradeLineItem/0/physicalSpsPackage/0/levelCode/value: 0 is not a valid enum value",
            "#/spsConsignment/includedSpsConsignmentItem/0/includedSpsTradeLineItem/0/physicalSpsPackage/0/typeCode/value: LR1 is not a valid enum value",
            "#/spsConsignment/includedSpsConsignmentItem/0/includedSpsTradeLineItem/0/applicableSpsClassification/0/systemID/value: AH is not a valid enum value",
            "#/spsConsignment/includedSpsConsignmentItem/0/includedSpsTradeLineItem/0/applicableSpsClassification/0/systemName/0/value:  is not a valid enum value",
            "#/spsExchangedDocument/id/value: expected maxLength: 90, actual: 100",
            "#/spsExchangedDocument/typeCode/value:  is not a valid enum value",
            "#/spsExchangedDocument/includedSpsNote/0/subjectCode/value: PROD_TEMPERATURE is not a valid enum value",
            "#/spsConsignment: required key [deliverySpsParty] not found"
        );
  }

  @Test
  void validate_DoesNotThrow_WhenValidEnotificationPayloadWithAllFields() {
    String payload = readFileToString("/enotification/validEnotificationAllFields.json");

    assertDoesNotThrow(() -> enotificationValidator.validate(payload));
  }

  @Test
  void validate_DoesNotThrow_WhenMinimalValidEnotification() {
    String payload = readFileToString("/enotification/validEnotificationMinimal.json");

    assertDoesNotThrow(() -> enotificationValidator.validate(payload));
  }
}
