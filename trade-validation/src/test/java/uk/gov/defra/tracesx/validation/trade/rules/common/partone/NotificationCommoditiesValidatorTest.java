package uk.gov.defra.tracesx.validation.trade.rules.common.partone;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.validation.trade.testutils.ResourceUtil;

class NotificationCommoditiesValidatorTest {

  private static final double NEGATIVE_DOUBLE = -0.5;
  private static final int NEGATIVE_INT = -1;
  private static final String INVALID_COUNTRY = "GBPC";
  private static final String VALID_REGION = "ES-CN";
  private static final String INVALID_REGION = "ABC-DEF";
  private static final String VALID_OLD_CODE = "NIR";
  private static final double POSITIVE_DOUBLE = 0.5;

  private NotificationCommoditiesValidator validator;
  private Notification notification;

  @BeforeEach
  void setup() throws Exception {
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = new ObjectMapper().readValue(notificationString, Notification.class);
    validator = new NotificationCommoditiesValidator();
  }

  @Test
  void validate_ReturnsTrue_WhenNullCommodities() {
    notification.getPartOne().setCommodities(null);
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsFalse_WhenNullTotalGrossWeight() {
    notification.getPartOne().getCommodities().setTotalGrossWeight(null);
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenNegativeTotalGrossWeight() {
    notification.getPartOne().getCommodities().setTotalGrossWeight(new BigDecimal(NEGATIVE_DOUBLE));
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenNullTotalNetWeight() {
    notification.getPartOne().getCommodities().setTotalNetWeight(null);
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenNegativeTotalNetWeight() {
    notification.getPartOne().getCommodities().setTotalNetWeight(new BigDecimal(NEGATIVE_DOUBLE));
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsTrue_WhenNullTotalGrossVolume() {
    notification.getPartOne().getCommodities().setTotalGrossVolume(null);
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsFalse_WhenNegativeTotalGrossVolume() {
    notification.getPartOne().getCommodities().setTotalGrossVolume(new BigDecimal(NEGATIVE_DOUBLE));
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenNegativeNumberOfPackages() {
    notification.getPartOne().getCommodities().setNumberOfPackages(NEGATIVE_INT);
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenSmallCommodityComplementSpeciesName() {
    notification.getPartOne().getCommodities().getCommodityComplement().get(0).setSpeciesName("");
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenLongCommodityComplementSpeciesName() {
    String longName = "a".repeat(101);
    notification.getPartOne().getCommodities().getCommodityComplement().get(0).setSpeciesName(longName);
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenSmallCommodityComplementId() {
    notification.getPartOne().getCommodities().getCommodityComplement().get(0).setCommodityID("");
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenInvalidCountryOfOrigin() {
    notification.getPartOne().getCommodities().setCountryOfOrigin(INVALID_COUNTRY);
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsTrue_WhenValidRegionOfOrigin() {
    notification.getPartOne().getCommodities().setCountryOfOrigin(VALID_REGION);
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsFalse_WhenInvalidRegionOfOrigin() {
    notification.getPartOne().getCommodities().setCountryOfOrigin(INVALID_REGION);
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsTrue_WhenValidOldCodeCountryOfOrigin() {
    notification.getPartOne().getCommodities().setCountryOfOrigin(VALID_OLD_CODE);
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsFalse_WhenSmallRegionOfOriginString() {
    notification.getPartOne().getCommodities().setRegionOfOrigin("");
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenLongRegionOfOriginString() {
    String longRegionOfOrigin = "a".repeat(11);
    notification.getPartOne().getCommodities().setRegionOfOrigin(longRegionOfOrigin);
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenInvalidConsignedCountry() {
    notification.getPartOne().getCommodities().setCountryOfOrigin(INVALID_COUNTRY);
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsTrue_WhenValidNotification() {
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void getMessage_ReturnsCorrectMessage() {
    assertThat(validator.getMessage()).isEqualTo("Invalid commodities");
  }

  @Test
  void validate_ReturnsTrue_WhenTotalGrossVolumeIsZero() {
    notification.getPartOne().getCommodities().setTotalGrossVolume(BigDecimal.ZERO);
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsTrue_WhenTotalGrossVolumeIsGreaterThanZero() {
    notification.getPartOne().getCommodities().setTotalGrossVolume(new BigDecimal(POSITIVE_DOUBLE));
    assertThat(validator.validate(notification)).isTrue();
  }
}
