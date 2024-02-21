package uk.gov.defra.stw.mapping.validation.validator;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum.CHEDPP;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;

@Component
public class TradeValidatorFactory {

  private final Map<NotificationTypeEnum, TradeValidator> validatorMap;

  @Autowired
  public TradeValidatorFactory(
      TradeValidator chedppTradeValidator) {
    validatorMap = Map.of(
        CHEDPP, chedppTradeValidator);
  }

  public TradeValidator getValidator(NotificationTypeEnum type) {
    return validatorMap.get(type);
  }
}
