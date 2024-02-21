package uk.gov.defra.stw.mapping.validation.rules.chedpp.parttwo;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.validation.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.InspectionCheck;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.CheckStatus;

class CommodityChecksValidatorTest {

  private Notification notification;
  private CommodityChecksValidator validator;

  @BeforeEach
  void setup() throws Exception {
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = new ObjectMapper().readValue(notificationString, Notification.class);
    validator = new CommodityChecksValidator();
  }

  @Test
  void validate_ReturnsTrue_WhenCompliantStatusAndAllInspectionChecksAreValid() {
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsTrue_WhenCompliantStatusAndNoReason() {
    InspectionCheck check = getInspectionCheck(CheckStatus.COMPLIANT);
    check.setStatus(CheckStatus.COMPLIANT);
    check.setReason(null);
    check.setOtherReason(null);
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsTrue_WhenAutoClearedStatusAndNoReason() {
    InspectionCheck check = getInspectionCheck(CheckStatus.COMPLIANT);
    check.setStatus(CheckStatus.AUTO_CLEARED);
    check.setReason(null);
    check.setOtherReason(null);
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsTrue_WhenToBeInspectedStatusAndNoReason() {
    InspectionCheck check = getInspectionCheck(CheckStatus.COMPLIANT);
    check.setStatus(CheckStatus.TO_BE_INSPECTED);
    check.setReason(null);
    check.setOtherReason(null);
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsTrue_WhenPartTwoNull() {
    notification.setPartTwo(null);
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsTrue_WhenNullCommodities() {
    notification.getPartOne().setCommodities(null);
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsTrue_WhenNullCommodityChecks() {
    notification.getPartTwo().setCommodityChecks(null);
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsFalse_WhenNullCommodityComplements() {
    notification.getPartOne().getCommodities().setCommodityComplement(null);
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void getMessage_ReturnsCorrectMessage() {
    assertThat(validator.getMessage())
        .isEqualTo("Invalid commodity inspection checks");
  }

  private InspectionCheck getInspectionCheck(CheckStatus checkStatus) {
    return notification.getPartTwo().getCommodityChecks().stream()
        .flatMap(commodityCheck -> commodityCheck.getChecks().stream())
        .filter(inspectionCheck -> inspectionCheck.getStatus().equals(checkStatus))
        .findFirst().orElse(null);
  }
}
