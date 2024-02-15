package uk.gov.defra.tracesx.validation.trade.rules.common.partone;

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

public class NotificationConsigneeValidator implements TradeValidationRule {

  private static final String VALIDATION_MESSAGE = "Invalid consignee";

  private Set<EconomicOperatorType> consigneeTypeEnumSet;
  private Pattern pattern;

  public NotificationConsigneeValidator() {
    consigneeTypeEnumSet = Set.of(EconomicOperatorType.IMPORTER,
        EconomicOperatorType.CONSIGNEE, EconomicOperatorType.DESTINATION);
    pattern = Pattern.compile(ISO_CODE_REGEX);
  }

  @Override
  public boolean validate(Notification notification) {
    EconomicOperator consignee = notification.getPartOne().getConsignee();
    EconomicOperator importer = notification.getPartOne().getImporter();

    if (consignee == null && importer == null) {
      return false;
    }

    if (consignee == null) {
      return true;
    }

    String consigneeCompanyName = consignee.getCompanyName();
    if (consigneeCompanyName == null || consigneeCompanyName.length() > MAX_COMPANY_NAME_LENGTH) {
      return false;
    }

    EconomicOperatorType consigneeType = consignee.getType();
    if (consigneeType != null && !consigneeTypeEnumSet.contains(consignee.getType())) {
      return false;
    }

    EconomicOperatorAddress consigneeAddress = consignee.getAddress();
    if (consigneeAddress != null) {
      String addressLine1 = consigneeAddress.getAddressLine1();
      if (addressLine1 != null && addressLine1.length() > MAX_ADDRESS_LINE_LENGTH) {
        return false;
      }

      String city = consigneeAddress.getCity();
      if (city != null && city.length() > MAX_CITY_LENGTH) {
        return false;
      }

      String zipCode = consigneeAddress.getPostalZipCode();
      if (zipCode != null && zipCode.length() > MAX_ZIPCODE_LENGTH) {
        return false;
      }

      return pattern.matcher(consigneeAddress.getCountryISOCode()).matches();
    }

    return true;
  }

  @Override
  public String getMessage() {
    return VALIDATION_MESSAGE;
  }
}
