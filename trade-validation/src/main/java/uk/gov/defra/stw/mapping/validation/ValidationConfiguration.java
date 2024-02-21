package uk.gov.defra.stw.mapping.validation;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.gov.defra.stw.mapping.validation.rules.NotificationLastUpdatedByValidator;
import uk.gov.defra.stw.mapping.validation.rules.NotificationLastUpdatedValidator;
import uk.gov.defra.stw.mapping.validation.rules.TradeValidationRule;
import uk.gov.defra.stw.mapping.validation.rules.chedpp.partone.ChedppComplementParameterSetValidator;
import uk.gov.defra.stw.mapping.validation.rules.chedpp.partone.ChedppNotificationCommoditiesValidator;
import uk.gov.defra.stw.mapping.validation.rules.chedpp.partone.ChedppNotificationContactDetailsValidator;
import uk.gov.defra.stw.mapping.validation.rules.chedpp.partone.ChedppNotificationPackerValidator;
import uk.gov.defra.stw.mapping.validation.rules.chedpp.parttwo.CommodityChecksValidator;
import uk.gov.defra.stw.mapping.validation.rules.common.LocalDateTimeValidator;
import uk.gov.defra.stw.mapping.validation.rules.common.partone.NotificationCommoditiesValidator;
import uk.gov.defra.stw.mapping.validation.rules.common.partone.NotificationConsigneeValidator;
import uk.gov.defra.stw.mapping.validation.rules.common.partone.NotificationConsignorValidator;
import uk.gov.defra.stw.mapping.validation.rules.common.partone.NotificationImporterLocalReferenceNumberValidator;
import uk.gov.defra.stw.mapping.validation.rules.common.partone.NotificationPlaceOfDestinationValidator;
import uk.gov.defra.stw.mapping.validation.rules.common.partone.NotificationStatusValidator;
import uk.gov.defra.stw.mapping.validation.rules.common.partone.NotificationTransporterValidator;
import uk.gov.defra.stw.mapping.validation.rules.common.partone.NotificationTypeValidator;
import uk.gov.defra.stw.mapping.validation.rules.common.partone.NotificationVeterinaryInformationValidator;
import uk.gov.defra.stw.mapping.validation.validator.TradeValidator;

@Configuration
public class ValidationConfiguration {

  @Bean(name = "chedppTradeValidator")
  public TradeValidator chedppTradeValidator() {
    return new TradeValidator(chedppTradeValidationRuleList());
  }

  private List<TradeValidationRule> chedppTradeValidationRuleList() {
    List<TradeValidationRule> rules = new ArrayList<>();
    rules.add(new NotificationLastUpdatedValidator(
        new LocalDateTimeValidator()
    ));
    rules.add(new NotificationLastUpdatedByValidator());
    addChedppPartOneValidators(rules);
    addChedppPartTwoValidators(rules);
    return rules;
  }

  private void addChedppPartOneValidators(List<TradeValidationRule> rules) {
    rules.add(new NotificationConsigneeValidator());
    rules.add(new NotificationConsignorValidator());
    rules.add(new NotificationPlaceOfDestinationValidator());
    rules.add(new NotificationTransporterValidator());
    rules.add(new ChedppNotificationCommoditiesValidator(
        new NotificationCommoditiesValidator()
    ));
    rules.add(new ChedppComplementParameterSetValidator());
    rules.add(new NotificationTypeValidator());
    rules.add(new NotificationVeterinaryInformationValidator());
    rules.add(new NotificationImporterLocalReferenceNumberValidator());
    rules.add(new NotificationStatusValidator());
    rules.add(new ChedppNotificationPackerValidator());
    rules.add(new ChedppNotificationContactDetailsValidator());
  }

  private void addChedppPartTwoValidators(List<TradeValidationRule> rules) {
    rules.add(new CommodityChecksValidator());
  }

}
