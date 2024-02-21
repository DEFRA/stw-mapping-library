package uk.gov.defra.stw.mapping.validation.rules.common.partone;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;
import uk.gov.defra.stw.mapping.validation.rules.TradeValidationRule;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

public class NotificationCommoditiesValidator implements TradeValidationRule {

  private static final String VALIDATION_MESSAGE = "Invalid commodities";
  private static final String COUNTRY_CODE_REGEX = "^([A-Z]{2,3}||[A-Z]{2}-[A-Z]{2,3})$";

  private Pattern pattern;

  public NotificationCommoditiesValidator() {
    pattern = Pattern.compile(COUNTRY_CODE_REGEX);
  }

  @Override
  public boolean validate(Notification notification) {
    Commodities commodities = notification.getPartOne().getCommodities();
    if (commodities == null) {
      return true;
    }

    if (isWeightInvalid(commodities.getTotalGrossWeight())
        || isWeightInvalid(commodities.getTotalNetWeight())) {
      return false;
    }

    if (!isOptionalWeightValid(commodities.getTotalGrossVolume())) {
      return false;
    }

    if (commodities.getNumberOfPackages() != null && commodities.getNumberOfPackages() < 0) {
      return false;
    }

    List<CommodityComplement> commodityComplements = commodities.getCommodityComplement();
    if (commodityComplements != null) {
      for (CommodityComplement commodityComplement : commodityComplements) {
        String speciesName = commodityComplement.getSpeciesName();

        if (speciesName != null && (speciesName.length() < 1 || speciesName.length() > 100)) {
          return false;
        }

        String commodityId = commodityComplement.getCommodityID();
        if (commodityId != null && commodityId.length() < 1) {
          return false;
        }
      }
    }

    String countryOfOrigin = commodities.getCountryOfOrigin();
    if (countryOfOrigin != null
        && !pattern.matcher(countryOfOrigin).matches()) {
      return false;
    }

    String regionOfOrigin = commodities.getRegionOfOrigin();
    if (regionOfOrigin != null && (regionOfOrigin.length() < 1 || regionOfOrigin.length() > 10)) {
      return false;
    }

    String consignedCountry = commodities.getConsignedCountry();
    return consignedCountry == null
        || pattern.matcher(consignedCountry).matches();
  }

  private boolean isWeightInvalid(BigDecimal weight) {
    return weight == null || weight.compareTo(BigDecimal.ZERO) < 0;
  }

  private boolean isOptionalWeightValid(BigDecimal weight) {
    if (weight == null) {
      return true;
    }
    return weight.compareTo(BigDecimal.ZERO) >= 0;
  }

  @Override
  public String getMessage() {
    return VALIDATION_MESSAGE;
  }
}
