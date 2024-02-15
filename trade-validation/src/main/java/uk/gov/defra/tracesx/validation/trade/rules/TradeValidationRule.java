package uk.gov.defra.tracesx.validation.trade.rules;

import uk.gov.defra.tracesx.notificationschema.representation.Notification;

public interface TradeValidationRule {
  boolean validate(Notification notification);

  String getMessage();
}