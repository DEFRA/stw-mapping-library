package uk.gov.defra.stw.mapping.validation.rules.chedpp.parttwo;

import java.util.List;
import uk.gov.defra.stw.mapping.validation.rules.TradeValidationRule;
import uk.gov.defra.stw.mapping.validation.utils.NotificationUtils;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

public class CommodityChecksValidator implements TradeValidationRule {

  private static final String VALIDATION_MESSAGE
      = "Invalid commodity inspection checks";

  @Override
  public boolean validate(Notification notification) {
    if (isCommodityNull(notification)) {
      return true;
    }

    Commodities commodities = notification.getPartOne().getCommodities();
    List<CommodityComplement> commodityComplements = commodities.getCommodityComplement();

    return commodityComplements != null;
  }

  @Override
  public String getMessage() {
    return VALIDATION_MESSAGE;
  }

  private boolean isCommodityNull(Notification notification) {
    return NotificationUtils.isPartTwoEmpty(notification.getPartTwo())
        || notification.getPartTwo().getCommodityChecks() == null
        || notification.getPartOne().getCommodities() == null;
  }
}
