package uk.gov.defra.stw.mapping.validation.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum.CHEDPP;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;

@ExtendWith(MockitoExtension.class)
class TradeValidatorFactoryTest {

  @Mock
  private TradeValidator chedppTradeValidator;

  private TradeValidatorFactory validatorFactory;

  @BeforeEach
  void setup() {
    validatorFactory = new TradeValidatorFactory(chedppTradeValidator);
  }

  @ParameterizedTest
  @EnumSource(value = NotificationTypeEnum.class, mode = Mode.EXCLUDE, names = "CHEDPP")
  void getValidator_ReturnNull_WhenNotChedpp(NotificationTypeEnum type) {
    TradeValidator actualTradeValidator = validatorFactory.getValidator(type);

    assertThat(actualTradeValidator).isNull();
  }

  @Test
  void getValidator_ReturnChedppTradeValidator_WhenCHEDPPNotificationType() {
    TradeValidator actualTradeValidator = validatorFactory.getValidator(CHEDPP);

    assertThat(actualTradeValidator).isEqualTo(chedppTradeValidator);
  }
}
