package uk.gov.defra.tracesx.validation.trade.rules.common.partone;

import java.util.Set;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;
import uk.gov.defra.tracesx.validation.trade.rules.TradeValidationRule;

public class NotificationTypeValidator implements TradeValidationRule {

  private static final String VALIDATION_MESSAGE = "Invalid type";

  private final Set<NotificationTypeEnum> notificationTypeEnumSet;

  public NotificationTypeValidator() {
    notificationTypeEnumSet =
        Set.of(NotificationTypeEnum.CHEDPP);
  }

  @Override
  public boolean validate(Notification notification) {
    if (notification.getType() == null) {
      return false;
    }
    return notificationTypeEnumSet.contains(notification.getType());
  }

  @Override
  public String getMessage() {
    return VALIDATION_MESSAGE;
  }
}
