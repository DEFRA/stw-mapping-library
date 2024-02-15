package uk.gov.defra.tracesx.mapper.common.spsconsignment;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.mapper.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.trade.dto.DateTimeType;

class AvailabilityDueDateTimeMapperTest {
  private Notification notification;
  private AvailabilityDueDateTimeMapper mapper;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);
    mapper = new AvailabilityDueDateTimeMapper();
  }

  @Test
  void map_ReturnsDateTimeType_WhenAllFieldsComplete() throws Exception {
    DateTimeType dateTimeType = mapper.map(notification.getPartOne());
    String actualDateTimeType = objectMapper.writeValueAsString(dateTimeType);
    String expectedDateTimeType = ResourceUtil.readFileToString("classpath:mapping/common/spsconsignment/availabilityDueDateTimeComplete.json");
    assertThat(actualDateTimeType).isEqualTo(expectedDateTimeType);
  }

  @Test
  void map_ReturnsNull_WhenNullArrivalDate() {
    notification.getPartOne().setArrivalDate(null);
    DateTimeType dateTimeType = mapper.map(notification.getPartOne());
    assertThat(dateTimeType).isNull();
  }

  @Test
  void map_ReturnsNull_WhenNullArrivalTime() {
    notification.getPartOne().setArrivalTime(null);
    DateTimeType dateTimeType = mapper.map(notification.getPartOne());
    assertThat(dateTimeType).isNull();
  }
}
