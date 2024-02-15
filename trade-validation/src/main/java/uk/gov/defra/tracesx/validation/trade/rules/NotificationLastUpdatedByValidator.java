package uk.gov.defra.tracesx.validation.trade.rules;

import uk.gov.defra.tracesx.notificationschema.representation.Notification;

public class NotificationLastUpdatedByValidator implements
    TradeValidationRule {

  private static final String VALIDATION_MESSAGE = "Invalid last updated by";

  @Override
  public boolean validate(Notification notification) {
    return notification.getLastUpdatedBy() != null
        && notification.getLastUpdatedBy().getDisplayName() != null;
  }

  @Override
  public String getMessage() {
    return VALIDATION_MESSAGE;
  }
}