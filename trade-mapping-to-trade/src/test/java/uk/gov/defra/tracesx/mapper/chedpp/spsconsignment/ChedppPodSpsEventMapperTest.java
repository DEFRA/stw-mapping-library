package uk.gov.defra.tracesx.mapper.chedpp.spsconsignment;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.mapper.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.trade.dto.SpsEventType;

class ChedppPodSpsEventMapperTest {

  private Notification notification;
  private ChedppPodSpsEventMapper mapper;
  private ObjectMapper objectMapper;

  private static final String NOTIFICATION_FIXTURE_PATH = "classpath:validatedCHEDPP.json";
  private static final String EVENT_FIXTURE_PATH = "classpath:mapping/chedpp/spsconsignment/podSpsEventComplete.json";

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString(NOTIFICATION_FIXTURE_PATH);
    notification = objectMapper.readValue(notificationString, Notification.class);
    mapper = new ChedppPodSpsEventMapper();
  }

  @Test
  void mapperReturnsPodSpsEventTypeListWhenPodIsNotNull() throws Exception {
    List<SpsEventType> spsEventTypeList = mapper.map(notification.getPartOne());
    String actualSpsEventType = objectMapper.writeValueAsString(spsEventTypeList);
    String expectedSpsEventType = ResourceUtil.readFileToString(EVENT_FIXTURE_PATH);
    assertThat(actualSpsEventType).isEqualTo(expectedSpsEventType);
  }

  @Test
  void mapperReturnsEmptyPodSpsEventTypeListWhenPodIsNull() {
    notification.getPartOne().setPod(null);
    List<SpsEventType> spsEventTypeList = mapper.map(notification.getPartOne());
    assertThat(spsEventTypeList).isEmpty();
  }
}
