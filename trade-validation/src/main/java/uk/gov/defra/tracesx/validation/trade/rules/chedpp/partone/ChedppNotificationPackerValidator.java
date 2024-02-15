package uk.gov.defra.tracesx.validation.trade.rules.chedpp.partone;

import static uk.gov.defra.tracesx.validation.trade.rules.ValidationConstraintsConstants.ISO_CODE_REGEX;
import static uk.gov.defra.tracesx.validation.trade.rules.ValidationConstraintsConstants.MAX_ADDRESS_LINE_LENGTH;
import static uk.gov.defra.tracesx.validation.trade.rules.ValidationConstraintsConstants.MAX_CITY_LENGTH;
import static uk.gov.defra.tracesx.validation.trade.rules.ValidationConstraintsConstants.MAX_COMPANY_NAME_LENGTH;
import static uk.gov.defra.tracesx.validation.trade.rules.ValidationConstraintsConstants.MAX_ZIPCODE_LENGTH;

import java.util.Set;
import java.util.regex.Pattern;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperator;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperatorAddress;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType;
import uk.gov.defra.tracesx.validation.trade.rules.TradeValidationRule;

public class ChedppNotificationPackerValidator implements TradeValidationRule {

  private static final String VALIDATION_MESSAGE = "Invalid packer";

  private Set<EconomicOperatorType> packerTypeEnumSet;
  private Pattern pattern;

  public ChedppNotificationPackerValidator() {
    packerTypeEnumSet = Set.of(EconomicOperatorType.PACKER);
    pattern = Pattern.compile(ISO_CODE_REGEX);
  }

  @Override
  public boolean validate(Notification notification) {
    EconomicOperator packer = notification.getPartOne().getPacker();

    if (packer == null) {
      return true;
    }

    String packerCompanyName = packer.getCompanyName();
    if (packerCompanyName == null || packerCompanyName.length() > MAX_COMPANY_NAME_LENGTH) {
      return false;
    }

    EconomicOperatorType packerType = packer.getType();
    if (packerType != null && !packerTypeEnumSet.contains(packer.getType())) {
      return false;
    }

    return validateAddress(packer.getAddress());
  }

  private boolean validateAddress(EconomicOperatorAddress packerAddress) {
    if (packerAddress != null) {
      if (!validString(packerAddress.getAddressLine1(), MAX_ADDRESS_LINE_LENGTH)) {
        return false;
      }

      if (!validString(packerAddress.getAddressLine2(), MAX_ADDRESS_LINE_LENGTH)) {
        return false;
      }

      if (!validString(packerAddress.getAddressLine3(), MAX_ADDRESS_LINE_LENGTH)) {
        return false;
      }

      if (!validString(packerAddress.getCity(), MAX_CITY_LENGTH)) {
        return false;
      }

      if (!validString(packerAddress.getPostalZipCode(), MAX_ZIPCODE_LENGTH)) {
        return false;
      }

      return pattern.matcher(packerAddress.getCountryISOCode()).matches();
    }

    return true;
  }

  private boolean validString(String addressField, int maxSize) {
    return addressField == null || addressField.length() <= maxSize;
  }

  @Override
  public String getMessage() {
    return VALIDATION_MESSAGE;
  }
}
