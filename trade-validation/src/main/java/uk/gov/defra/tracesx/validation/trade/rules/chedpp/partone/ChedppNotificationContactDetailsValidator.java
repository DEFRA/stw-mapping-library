package uk.gov.defra.tracesx.validation.trade.rules.chedpp.partone;

import static uk.gov.defra.tracesx.validation.trade.rules.ValidationConstraintsConstants.MAX_CONTACT_AGENT_LENGTH;
import static uk.gov.defra.tracesx.validation.trade.rules.ValidationConstraintsConstants.MAX_CONTACT_EMAIL_LENGTH;
import static uk.gov.defra.tracesx.validation.trade.rules.ValidationConstraintsConstants.MAX_CONTACT_NAME_LENGTH;
import static uk.gov.defra.tracesx.validation.trade.rules.ValidationConstraintsConstants.MAX_CONTACT_TELEPHONE_LENGTH;

import uk.gov.defra.tracesx.notificationschema.representation.ContactDetails;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.validation.trade.rules.TradeValidationRule;

public class ChedppNotificationContactDetailsValidator implements TradeValidationRule {

  private static final String VALIDATION_MESSAGE = "Invalid contact details";

  @Override
  public boolean validate(Notification notification) {
    ContactDetails contactDetails = notification.getPartOne().getContactDetails();

    if (contactDetails == null) {
      return true;
    }

    if (!validString(contactDetails.getName(), MAX_CONTACT_NAME_LENGTH)) {
      return false;
    }

    if (!validString(contactDetails.getAgent(), MAX_CONTACT_AGENT_LENGTH)) {
      return false;
    }

    if (!validString(contactDetails.getEmail(), MAX_CONTACT_EMAIL_LENGTH)) {
      return false;
    }

    return validString(contactDetails.getTelephone(), MAX_CONTACT_TELEPHONE_LENGTH);
  }

  @Override
  public String getMessage() {
    return VALIDATION_MESSAGE;
  }

  private boolean validString(String contactDetailField, int maxSize) {
    return contactDetailField == null || contactDetailField.length() <= maxSize;
  }
}