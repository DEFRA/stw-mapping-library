package uk.gov.defra.stw.mapping.validation.rules.common.partone;

import uk.gov.defra.stw.mapping.validation.rules.TradeValidationRule;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

public class NotificationImporterLocalReferenceNumberValidator implements TradeValidationRule {

  private static final String VALIDATION_MESSAGE
      = "Invalid notification part one local reference number";

  @Override
  public boolean validate(Notification notification) {
    String referenceNumber = notification.getPartOne().getImporterLocalReferenceNumber();

    if (referenceNumber == null) {
      return true;
    }

    return referenceNumber.length() > 0 && referenceNumber.length() <= 90;
  }

  @Override
  public String getMessage() {
    return VALIDATION_MESSAGE;
  }
}
