package uk.gov.defra.stw.mapping.schama;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.everit.json.schema.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.schama.testutils.ResourceUtil;
import uk.gov.defra.stw.mapping.schema.UncefactValidator;

class UncefactValidatorTest {

  private static final String INVALID_UNCEFACT_PATH = "/uncefact/invalidPayload.json";
  private static final String VALID_UNCEFACT_PATH = "/uncefact/validUncefact.json";
  private static final String DELIMITER = ",";

  private UncefactValidator uncefactJsonValidator;

  private String invalidUncefact;
  private String validUncefact;

  @BeforeEach
  void setup() {
    uncefactJsonValidator = new UncefactValidator();

    invalidUncefact = ResourceUtil.readFileToString(INVALID_UNCEFACT_PATH);
    validUncefact = ResourceUtil.readFileToString(VALID_UNCEFACT_PATH);
  }

  @Test
  void validate_throwsValidationException_WhenInvalidUncefact() {
    try {
      uncefactJsonValidator.validate(invalidUncefact);
    } catch (ValidationException ve) {
      String validationErrors = "#/spsConsignment/consignorSpsParty/name: required key [value] not found," +
          "#/spsConsignment/exportSpsCountry/id: required key [value] not found," +
          "#/spsConsignment/importSpsCountry/id: required key [value] not found," +
          "#/spsConsignment/consigneeSpsParty/name: required key [value] not found," +
          "#/spsExchangedDocument: required key [signatorySpsAuthentication] not found," +
          "#/spsExchangedDocument: required key [statusCode] not found," +
          "#/spsExchangedDocument/id: required key [value] not found";

      assertThat(String.join(DELIMITER, ve.getAllMessages()))
          .isEqualTo(validationErrors);
    }
  }

  @Test
  void validate_throwsNoException_WhenValidUncefactJson() {
    assertDoesNotThrow(() -> uncefactJsonValidator.validate(validUncefact));
  }
}
