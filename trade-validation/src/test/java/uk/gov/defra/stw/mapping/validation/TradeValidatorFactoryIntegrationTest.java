package uk.gov.defra.stw.mapping.validation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.defra.stw.mapping.validation.validator.TradeValidator;
import uk.gov.defra.stw.mapping.validation.validator.TradeValidatorFactory;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;

@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = {"uk.gov.defra.stw.mapping.validation"})
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
