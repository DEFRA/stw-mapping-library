package uk.gov.defra.tracesx.validation.trade.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.validation.trade.rules.TradeValidationRule;

@ExtendWith(MockitoExtension.class)
class TradeValidatorTest {

  private Notification notification;
  private TradeValidator tradeValidator;

  @Mock
  private TradeValidationRule tradeValidationRule;

  @BeforeEach
  void setup() {
    notification = Notification.builder().build();
    tradeValidator = new TradeValidator(Collections.singletonList(tradeValidationRule));
  }

  @Test
  void validate_ReturnsEmptySet_WhenNoValidationExceptions() {
    when(tradeValidationRule.validate(notification)).thenReturn(true);

    Set<TradeValidationRule> tradeValidationRules = tradeValidator.validate(notification);

    assertThat(tradeValidationRules.isEmpty()).isTrue();
  }

  @Test
  void validate_ReturnsTradeRuleValidation_WhenValidationException() {
    when(tradeValidationRule.validate(notification)).thenReturn(false);

    Set<TradeValidationRule> tradeValidationRules = tradeValidator.validate(notification);

    assertThat(tradeValidationRules.size()).isEqualTo(1);
  }
}
