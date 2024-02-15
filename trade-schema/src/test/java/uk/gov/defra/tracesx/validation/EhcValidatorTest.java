package uk.gov.defra.tracesx.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.everit.json.schema.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.validation.testutils.ResourceUtil;

class EhcValidatorTest {

  private static final String INVALID_UNCEFACT_PATH = "/uncefact/invalidPayload.json";
  private static final String INVALID_EHC_PATH = "/cloning/invalidPayload.json";
  private static final String VALID_EHC_ALL_FIELDS_PATH = "/cloning/validPayloadAllFields.json";
  private static final String VALID_EHC_MANDATORY_FIELDS_PATH = "/cloning/validPayloadMandatoryFields.json";
  private static final String DELIMITER = ",";

  private EhcValidator ehcJsonValidator;

  private String invalidUncefact;
  private String invalidEhc;
  private String validEhcAllFields;
  private String validEhcMandatoryFields;

  @BeforeEach
  void setup() {
    ehcJsonValidator = new EhcValidator();

    invalidUncefact = ResourceUtil.readFileToString(INVALID_UNCEFACT_PATH);
    invalidEhc = ResourceUtil.readFileToString(INVALID_EHC_PATH);
    validEhcAllFields = ResourceUtil.readFileToString(VALID_EHC_ALL_FIELDS_PATH);
    validEhcMandatoryFields = ResourceUtil.readFileToString(VALID_EHC_MANDATORY_FIELDS_PATH);
  }

  @Test
  void validate_ThrowsValidationException_WhenInvalidUncefact() {
    try {
      ehcJsonValidator.validate(invalidUncefact);
    } catch (ValidationException ve) {
      String validationErrors = "#/spsConsignment/consignorSpsParty/name: required key [value] not found," +
          "#/spsConsignment/exportSpsCountry/id: required key [value] not found," +
          "#/spsConsignment/importSpsCountry/id: required key [value] not found," +
          "#/spsConsignment/consigneeSpsParty/name: required key [value] not found," +
          "#/spsConsignment/availabilityDueDateTime: required key [dateTime] not found," +
          "#/spsConsignment/consigneeSpsParty: required key [id] not found," +
          "#/spsConsignment/consigneeSpsParty: required key [specifiedSpsAddress] not found," +
          "#/spsConsignment/consignorSpsParty: required key [id] not found," +
          "#/spsConsignment/consignorSpsParty: required key [specifiedSpsAddress] not found," +
          "#/spsExchangedDocument: required key [signatorySpsAuthentication] not found," +
          "#/spsExchangedDocument: required key [statusCode] not found," +
          "#/spsExchangedDocument/id: required key [value] not found";

      assertThat(String.join(DELIMITER, ve.getAllMessages()))
          .isEqualTo(validationErrors);
    }

  }

  @Test
  void validate_ThrowsValidationException_WhenInvalidEhc() {
    try {
      ehcJsonValidator.validate(invalidEhc);
    } catch (ValidationException ve) {
      String validationErrors = "#/spsConsignment/includedSpsConsignmentItem/0/includedSpsTradeLineItem/0/appliedSpsProcess/0: required key [typeCode] not found," +
          "#/spsConsignment/mainCarriageSpsTransportMovement/0/modeCode/value: 6 is not a valid enum value," +
          "#/spsConsignment/consignorSpsParty/specifiedSpsAddress/postcodeCode/value: expected maxLength: 32, actual: 40," +
          "#/spsConsignment/includedSpsConsignmentItem/0/includedSpsTradeLineItem/0/appliedSpsProcess/0: required key [typeCode] not found," +
          "#/spsConsignment/includedSpsConsignmentItem/0/includedSpsTradeLineItem/0/associatedSpsTransportEquipment/0/affixedSpsSeal/0/id/value: expected maxLength: 32, actual: 69," +
          "#/spsConsignment/includedSpsConsignmentItem/0/includedSpsTradeLineItem/0/physicalSpsPackage/0/levelCode/value: 0 is not a valid enum value," +
          "#/spsConsignment/includedSpsConsignmentItem/0/includedSpsTradeLineItem/0/physicalSpsPackage/0/typeCode/value: expected maxLength: 2, actual: 3," +
          "#/spsConsignment/includedSpsConsignmentItem/0/includedSpsTradeLineItem/0/applicableSpsClassification/0/systemID/value: AH is not a valid enum value," +
          "#/spsConsignment/includedSpsConsignmentItem/0/includedSpsTradeLineItem/0/applicableSpsClassification/0/systemName/0/value:  is not a valid enum value," +
          "#/spsExchangedDocument/id/value: expected maxLength: 90, actual: 100," +
          "#/spsExchangedDocument/typeCode/value:  is not a valid enum value," +
          "#/spsExchangedDocument/includedSpsNote/1/subjectCode/value: PROD_TEMPERATURE is not a valid enum value";

      assertThat(String.join(DELIMITER, ve.getAllMessages()))
          .isEqualTo(validationErrors);
    }
  }

  @Test
  void validate_ThrowsNoException_WhenValidEhcWithAllFieldsJson() {
    assertDoesNotThrow(() -> ehcJsonValidator.validate(validEhcAllFields));
  }

  @Test
  void validate_ThrowsNoException_WhenValidEhcWithMandatoryFieldsJson() {
    assertDoesNotThrow(() -> ehcJsonValidator.validate(validEhcMandatoryFields));
  }
}
