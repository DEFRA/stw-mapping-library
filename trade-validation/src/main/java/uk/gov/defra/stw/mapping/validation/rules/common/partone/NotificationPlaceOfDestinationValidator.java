package uk.gov.defra.stw.mapping.validation.rules.common.partone;

import static uk.gov.defra.stw.mapping.validation.rules.ValidationConstraintsConstants.ISO_CODE_REGEX;
import static uk.gov.defra.stw.mapping.validation.rules.ValidationConstraintsConstants.MAX_ADDRESS_LINE_LENGTH;
import static uk.gov.defra.stw.mapping.validation.rules.ValidationConstraintsConstants.MAX_CITY_LENGTH;
import static uk.gov.defra.stw.mapping.validation.rules.ValidationConstraintsConstants.MAX_COMPANY_NAME_LENGTH;
import static uk.gov.defra.stw.mapping.validation.rules.ValidationConstraintsConstants.MAX_ZIPCODE_LENGTH;

import java.util.Set;
import java.util.regex.Pattern;
import uk.gov.defra.stw.mapping.validation.rules.TradeValidationRule;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperator;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperatorAddress;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType;

public class NotificationPlaceOfDestinationValidator implements TradeValidationRule {

  private static final String VALIDATION_MESSAGE = "Invalid place of destination";

  private Set<EconomicOperatorType> placeOfDestinationTypeEnumSet;
  private Pattern pattern;

  public NotificationPlaceOfDestinationValidator() {
    placeOfDestinationTypeEnumSet = Set.of(EconomicOperatorType.IMPORTER,
        EconomicOperatorType.DESTINATION,
        EconomicOperatorType.CONSIGNEE);
    pattern = Pattern.compile(ISO_CODE_REGEX);
  }

  @Override
  public boolean validate(Notification notification) {
    EconomicOperator placeOfDestination = notification.getPartOne().getPlaceOfDestination();

    if (placeOfDestination == null) {
      return true;
    }

    String placeOfDestinationCompanyName = placeOfDestination.getCompanyName();
    if (placeOfDestinationCompanyName == null
        || placeOfDestinationCompanyName.length() > MAX_COMPANY_NAME_LENGTH) {
      return false;
    }

    EconomicOperatorType placeOfDestinationType = placeOfDestination.getType();
    if (placeOfDestinationType != null
        && !placeOfDestinationTypeEnumSet.contains(placeOfDestination.getType())) {
      return false;
    }

    EconomicOperatorAddress placeOfDestinationAddress = placeOfDestination.getAddress();
    if (placeOfDestinationAddress != null) {
      String addressLine1 = placeOfDestinationAddress.getAddressLine1();
      if (addressLine1 != null && addressLine1.length() > MAX_ADDRESS_LINE_LENGTH) {
        return false;
      }

      String city = placeOfDestinationAddress.getCity();
      if (city != null && city.length() > MAX_CITY_LENGTH) {
        return false;
      }

      String zipCode = placeOfDestinationAddress.getPostalZipCode();
      if (zipCode != null && zipCode.length() > MAX_ZIPCODE_LENGTH) {
        return false;
      }

      return pattern.matcher(placeOfDestinationAddress.getCountryISOCode()).matches();
    }

    return true;
  }

  @Override
  public String getMessage() {
    return VALIDATION_MESSAGE;
  }
}
