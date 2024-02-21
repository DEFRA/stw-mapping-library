package uk.gov.defra.stw.mapping.validation.rules.chedpp.partone;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.validation.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

class ChedppComplementParameterSetValidatorTest {

  private Notification notification;
  private ChedppComplementParameterSetValidator validator;

  @BeforeEach
  void setup() throws Exception {
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = new ObjectMapper().readValue(notificationString, Notification.class);
    validator = new ChedppComplementParameterSetValidator();
  }

  @Test
  void validate_ReturnsTrue_WhenValidWoodPackagingCommodity() {
    setCommodityComplement(Boolean.TRUE);
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsTrue_WhenValidNonWoodPackagingCommodity() {
    setCommodityComplement(Boolean.FALSE);
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsTrue_WhenNullCommodities() {
    notification.getPartOne().setCommodities(null);
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsFalse_WhenNullCommodityComplements() {
    notification.getPartOne().getCommodities().setCommodityComplement(null);
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenNullComplementParameterSet() {
    notification.getPartOne().getCommodities().setComplementParameterSet(null);
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenNoMatchingComplementParameterSet() {
    for (ComplementParameterSet set : notification.getPartOne().getCommodities().getComplementParameterSet()) {
      set.setComplementID(0);
    }
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenMissingKey() {
    for (ComplementParameterSet set : notification.getPartOne().getCommodities().getComplementParameterSet()) {
      set.setKeyDataPair(Collections.singletonList(new ComplementParameterSetKeyDataPair("invalid", "invalid")));
    }
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void getMessage_ReturnsCorrectMessage() {
    assertThat(validator.getMessage())
        .isEqualTo("Invalid commodity complement / complement parameter set");
  }

  private void setCommodityComplement(Boolean isWoodPackaging) {
    CommodityComplement commodityComplement = notification.getPartOne().getCommodities()
        .getCommodityComplement().stream()
        .filter(complement -> isWoodPackaging.equals(complement.getIsWoodPackaging()))
        .findFirst()
        .orElse(null);

    notification.getPartOne().getCommodities()
        .setCommodityComplement(Collections.singletonList(commodityComplement));
  }
}
