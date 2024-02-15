package uk.gov.defra.tracesx.validation.trade;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;
import uk.gov.defra.tracesx.validation.trade.validator.TradeValidator;
import uk.gov.defra.tracesx.validation.trade.validator.TradeValidatorFactory;

@ExtendWith(SpringExtension.class)
@ComponentScan(
    basePackages = {
        "uk.gov.defra.tracesx.validation.trade"
    })
@EnableAutoConfiguration
class TradeValidatorFactoryIntegrationTest {

  @Autowired
  private TradeValidatorFactory validatorFactory;

  @Test
  void getValidator_ReturnsCorrectlyWiredTradeValidatorFactory() {
    TradeValidator chedppValidator = validatorFactory.getValidator(NotificationTypeEnum.CHEDPP);
    assertThat(chedppValidator).isNotNull();
  }
}
