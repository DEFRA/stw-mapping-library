package uk.gov.defra.tracesx.validation.trade.rules.chedpp.partone;

import java.util.List;
import org.springframework.util.StringUtils;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.validation.trade.rules.TradeValidationRule;
import uk.gov.defra.tracesx.validation.trade.rules.common.partone.NotificationCommoditiesValidator;

public class ChedppNotificationCommoditiesValidator implements TradeValidationRule {

  private NotificationCommoditiesValidator notificationCommoditiesValidator;

  public ChedppNotificationCommoditiesValidator(
      NotificationCommoditiesValidator notificationCommoditiesValidator) {
    this.notificationCommoditiesValidator = notificationCommoditiesValidator;
  }

  @Override
  public boolean validate(Notification notification) {
    List<CommodityComplement> commodityComplement = notification.getPartOne().getCommodities()
        .getCommodityComplement();
    return notificationCommoditiesValidator.validate(notification) && commodityComplement.stream()
        .allMatch(this::isValidCommodityCode);
  }

  private boolean isValidCommodityCode(CommodityComplement commodityComplement) {
    return StringUtils.hasLength(commodityComplement.getEppoCode())
        && StringUtils.hasLength(commodityComplement.getSpeciesName());
  }

  @Override
  public String getMessage() {
    return notificationCommoditiesValidator.getMessage();
  }
}
