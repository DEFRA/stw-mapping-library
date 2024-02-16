package uk.gov.defra.stw.mapping.validation.rules.chedpp.partone;

import static uk.gov.defra.stw.mapping.validation.utils.CommodityComplementFinder.getComplementParameterSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import uk.gov.defra.stw.mapping.validation.rules.TradeValidationRule;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

public class ChedppComplementParameterSetValidator implements TradeValidationRule {

  private static final String UNITS_QUANTITY_KEY = "units-quantity";
  private static final String UNITS_TYPE_KEY = "units-type";
  private static final String COUNTRY_OF_ORIGIN_KEY = "country-of-origin";

  private static final String NETWEIGHT_KEY = "netweight";
  private static final String NUMBER_PACKAGE_KEY = "number_package";
  private static final String TYPE_PACKAGE_KEY = "type_package";

  private static final String VALIDATION_MESSAGE
      = "Invalid commodity complement / complement parameter set";

  private final List<String> woodPackagingKeys;
  private final List<String> nonWoodPackagingKeys;

  public ChedppComplementParameterSetValidator() {
    woodPackagingKeys = Arrays.asList(UNITS_QUANTITY_KEY, UNITS_TYPE_KEY, COUNTRY_OF_ORIGIN_KEY);
    nonWoodPackagingKeys = Arrays.asList(NETWEIGHT_KEY,
        NUMBER_PACKAGE_KEY, TYPE_PACKAGE_KEY);
  }

  @Override
  public boolean validate(Notification notification) {
    Commodities commodities = notification.getPartOne().getCommodities();
    if (commodities == null) {
      return true;
    }

    List<CommodityComplement> commodityComplements = commodities.getCommodityComplement();

    if (commodityComplements == null) {
      return false;
    }

    for (CommodityComplement commodityComplement : commodityComplements) {
      List<ComplementParameterSet> complementParameterSet = getComplementParameterSet(
          commodityComplement.getComplementID(),
          commodityComplement.getSpeciesID(),
          commodities.getComplementParameterSet());

      if (complementParameterSet == null || complementParameterSet.isEmpty()) {
        return false;
      }

      for (ComplementParameterSet parameterSet : complementParameterSet) {
        List<String> complementKeys = getComplementParameterKeys(
            parameterSet.getKeyDataPair());

        if (Boolean.TRUE.equals(commodityComplement.getIsWoodPackaging())) {
          if (!complementKeys.containsAll(woodPackagingKeys)) {
            return false;
          }
        } else {
          if (!complementKeys.containsAll(nonWoodPackagingKeys)) {
            return false;
          }
        }
      }
    }

    return true;
  }

  @Override
  public String getMessage() {
    return VALIDATION_MESSAGE;
  }

  private List<String> getComplementParameterKeys(List<ComplementParameterSetKeyDataPair> keys) {
    List<String> complementKeys = new ArrayList<>();
    for (ComplementParameterSetKeyDataPair keyDataPair : keys) {
      complementKeys.add(keyDataPair.getKey());
    }
    return complementKeys;
  }
}
