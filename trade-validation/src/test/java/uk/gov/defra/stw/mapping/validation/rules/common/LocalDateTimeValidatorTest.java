package uk.gov.defra.stw.mapping.validation.rules.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocalDateTimeValidatorTest {
  private LocalDateTimeValidator validator;

  @BeforeEach
  void setup() {
    validator = new LocalDateTimeValidator();
  }

  @Test
  void validate_ReturnsTrue_WhenValidLocalDateTime() {
    assertThat(validator.validate(LocalDateTime.now())).isTrue();
  }

  @Test
  void validate_ReturnsFalse_WhenNull() {
    assertThat(validator.validate(null)).isFalse();
  }
}
