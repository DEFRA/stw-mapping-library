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

public class NotificationConsignorValidator implements TradeValidationRule {

  private static final String VALIDATION_MESSAGE = "Invalid consignor";

  private Set<EconomicOperatorType> consignorTypeEnumSet;
  private Pattern pattern;

  public NotificationConsignorValidator() {
    consignorTypeEnumSet = Set.of(EconomicOperatorType.EXPORTER);
    pattern = Pattern.compile(ISO_CODE_REGEX);
  }

  @Override
  public boolean validate(Notification notification) {
    EconomicOperator consignor = notification.getPartOne().getConsignor();

    if (consignor == null) {
      return false;
    }

    String consignorCompanyName = consignor.getCompanyName();
    if (consignorCompanyName == null || consignorCompanyName.length() > MAX_COMPANY_NAME_LENGTH) {
      return false;
    }

    EconomicOperatorType consignorType = consignor.getType();
    if (consignorType != null && !consignorTypeEnumSet.contains(consignor.getType())) {
      return false;
    }

    EconomicOperatorAddress consignorAddress = consignor.getAddress();
    if (consignorAddress != null) {
      String addressLine1 = consignorAddress.getAddressLine1();
      if (addressLine1 != null && addressLine1.length() > MAX_ADDRESS_LINE_LENGTH) {
        return false;
      }

      String city = consignorAddress.getCity();
      if (city != null && city.length() > MAX_CITY_LENGTH) {
        return false;
      }

      String zipCode = consignorAddress.getPostalZipCode();
      if (zipCode != null && zipCode.length() > MAX_ZIPCODE_LENGTH) {
        return false;
      }

      return pattern.matcher(consignorAddress.getCountryISOCode()).matches();
    }

    return true;
  }

  @Override
  public String getMessage() {
    return VALIDATION_MESSAGE;
  }
}
