package uk.gov.defra.stw.mapping.validation.validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import uk.gov.defra.stw.mapping.validation.rules.TradeValidationRule;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

public class TradeValidator {

  private final List<TradeValidationRule> tradeValidationRuleList;

  public TradeValidator(
      List<TradeValidationRule> tradeValidationRuleList) {
    this.tradeValidationRuleList = tradeValidationRuleList;
  }

  public Set<TradeValidationRule> validate(Notification notification) {
    Set<TradeValidationRule> ruleExceptions = new HashSet<>();
    for (TradeValidationRule rule : tradeValidationRuleList) {
      if (!rule.validate(notification)) {
        ruleExceptions.add(rule);
      }
    }
    return ruleExceptions;
  }
}
