package uk.gov.defra.stw.mapping.validation.rules;

import uk.gov.defra.tracesx.notificationschema.representation.Notification;

public interface TradeValidationRule {
  boolean validate(Notification notification);

  String getMessage();
}
