package uk.gov.defra.stw.mapping.schama;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static uk.gov.defra.stw.mapping.schama.testutils.ResourceUtil.readFileToString;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.everit.json.schema.ValidationException;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.schema.UncefactValidator;

class UncefactValidatorTest {

  private final UncefactValidator uncefactJsonValidator = new UncefactValidator();

  @Test
  void validate_throwsValidationException_WhenInvalidUncefact() {
    String payload = readFileToString("/uncefact/invalidUncefact.json");

    assertThatThrownBy(() -> uncefactJsonValidator.validate(payload))
        .isInstanceOf(ValidationException.class)
        .asInstanceOf(InstanceOfAssertFactories.type(ValidationException.class))
        .extracting(ValidationException::getAllMessages)
        .asList()
        .containsExactlyInAnyOrder(
            "#/spsConsignment/consignorSpsParty/name: required key [value] not found",
            "#/spsConsignment/exportSpsCountry/id: required key [value] not found",
            "#/spsConsignment/importSpsCountry/id: required key [value] not found",
            "#/spsConsignment/consigneeSpsParty/name: required key [value] not found",
            "#/spsExchangedDocument: required key [signatorySpsAuthentication] not found",
            "#/spsExchangedDocument: required key [statusCode] not found",
            "#/spsExchangedDocument/id: required key [value] not found",
            "#/spsConsignment/examinationSpsEvent/occurrenceSpsLocation/name: expected minimum item count: 1, found: 0",
            "#/spsConsignment/exportSpsCountry/name: expected minimum item count: 1, found: 0",
            "#/spsConsignment/importSpsCountry/name: expected minimum item count: 1, found: 0",
            "#/spsExchangedDocument/name: expected minimum item count: 1, found: 0"
        );
  }

  @Test
  void validate_DoesNotThrow_WhenValidUncefact() {
    String payload = readFileToString("/uncefact/validUncefact.json");

    assertDoesNotThrow(() -> uncefactJsonValidator.validate(payload));
  }

  @Test
  void validate_DoesNotThrow_WhenMinimalValidUncefact() {
    String payload = readFileToString("/uncefact/validUncefactMinimal.json");

    assertDoesNotThrow(() -> uncefactJsonValidator.validate(payload));
  }
}
