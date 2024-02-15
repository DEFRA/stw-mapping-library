package uk.gov.defra.tracesx.validation.trade.rules;

import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.validation.trade.rules.common.LocalDateTimeValidator;

public class NotificationLastUpdatedValidator implements
    TradeValidationRule {

  private static final String VALIDATION_MESSAGE = "Invalid last updated dateTime";

  private LocalDateTimeValidator localDateTimeValidator;

  public NotificationLastUpdatedValidator(LocalDateTimeValidator localDateTimeValidator) {
    this.localDateTimeValidator = localDateTimeValidator;
  }

  @Override
  public boolean validate(Notification notification) {
    return localDateTimeValidator.validate(notification.getLastUpdated());
  }

  @Override
  public String getMessage() {
    return VALIDATION_MESSAGE;
  }
}