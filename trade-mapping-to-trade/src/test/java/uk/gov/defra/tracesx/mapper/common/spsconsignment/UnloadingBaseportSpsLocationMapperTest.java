package uk.gov.defra.tracesx.mapper.common.spsconsignment;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.mapper.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.trade.dto.SpsLocationType;

class UnloadingBaseportSpsLocationMapperTest {
  private Notification notification;
  private UnloadingBaseportSpsLocationMapper mapper;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);
    mapper = new UnloadingBaseportSpsLocationMapper();
  }

  @Test
  void map_ReturnsSpsLocationType_WhenAllFieldsComplete() throws Exception {
    SpsLocationType spsLocationType = mapper.map(notification.getPartOne().getPointOfEntry());
    String actualSpsLocationType = objectMapper.writeValueAsString(spsLocationType);
    String expectedSpsLocationType = ResourceUtil.readFileToString("classpath:mapping/common/spsconsignment/UnloadingBaseportSpsLocationComplete.json");
    assertThat(actualSpsLocationType).isEqualTo(expectedSpsLocationType);
  }

  @Test
  void map_ReturnsNull_WhenPointOfEntryNull() {
    notification.getPartOne().setPointOfEntry(null);
    SpsLocationType spsLocationType = mapper.map(notification.getPartOne().getPointOfEntry());
    assertThat(spsLocationType).isNull();
  }

  @Test
  void map_ReturnsNull_WhenPointOfEntryIsEmpty() {
    notification.getPartOne().setPointOfEntry("");
    SpsLocationType spsLocationType = mapper.map(notification.getPartOne().getPointOfEntry());
    assertThat(spsLocationType).isNull();
  }
}
