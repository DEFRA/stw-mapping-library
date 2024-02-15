package uk.gov.defra.tracesx.validation.trade.rules.common.partone;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.AMEND;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.CANCELLED;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.DELETED;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.IN_PROGRESS;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.MODIFY;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.PARTIALLY_REJECTED;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.REJECTED;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.SPLIT_CONSIGNMENT;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.SUBMITTED;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.VALIDATED;

import java.util.Set;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum;
import uk.gov.defra.tracesx.validation.trade.rules.TradeValidationRule;

public class NotificationStatusValidator implements TradeValidationRule {

  private static final String VALIDATION_MESSAGE = "Invalid status";

  private Set<StatusEnum> statusEnumSet;

  public NotificationStatusValidator() {
    statusEnumSet = Set.of(SUBMITTED,
        IN_PROGRESS,
        MODIFY,
        REJECTED,
        VALIDATED,
        CANCELLED,
        DELETED,
        AMEND,
        PARTIALLY_REJECTED,
        SPLIT_CONSIGNMENT);
  }

  @Override
  public boolean validate(Notification notification) {
    if (notification.getStatus() == null) {
      return false;
    }
    return statusEnumSet.contains(notification.getStatus());
  }

  @Override
  public String getMessage() {
    return VALIDATION_MESSAGE;
  }
}
