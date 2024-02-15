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

public class NotificationTransporterValidator implements TradeValidationRule {

  private static final String VALIDATION_MESSAGE = "Invalid transporter";
  private Set<EconomicOperatorType> ptransporterTypeEnumSet;
  private Pattern pattern;

  public NotificationTransporterValidator() {
    ptransporterTypeEnumSet = Set.of(EconomicOperatorType.COMMERCIAL_TRANSPORTER,
        EconomicOperatorType.PRIVATE_TRANSPORTER);
    pattern = Pattern.compile(ISO_CODE_REGEX);
  }

  @Override
  public boolean validate(Notification notification) {
    EconomicOperator transporter = notification.getPartOne().getTransporter();

    if (transporter == null) {
      return true;
    }

    String transporterCompanyName = transporter.getCompanyName();
    if (transporterCompanyName == null
        || transporterCompanyName.length() > MAX_COMPANY_NAME_LENGTH) {
      return false;
    }

    EconomicOperatorType transporterType = transporter.getType();
    if (transporterType != null
        && !ptransporterTypeEnumSet.contains(transporter.getType())) {
      return false;
    }

    EconomicOperatorAddress transporterAddress = transporter.getAddress();
    if (transporterAddress != null) {
      String addressLine1 = transporterAddress.getAddressLine1();
      if (addressLine1 != null && addressLine1.length() > MAX_ADDRESS_LINE_LENGTH) {
        return false;
      }

      String city = transporterAddress.getCity();
      if (city != null && city.length() > MAX_CITY_LENGTH) {
        return false;
      }

      String zipCode = transporterAddress.getPostalZipCode();
      if (zipCode != null && zipCode.length() > MAX_ZIPCODE_LENGTH) {
        return false;
      }

      return pattern.matcher(transporterAddress.getCountryISOCode()).matches();
    }

    return true;
  }

  @Override
  public String getMessage() {
    return VALIDATION_MESSAGE;
  }
}
