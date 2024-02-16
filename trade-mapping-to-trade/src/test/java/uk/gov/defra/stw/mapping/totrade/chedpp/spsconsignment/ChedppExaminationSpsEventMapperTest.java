package uk.gov.defra.stw.mapping.totrade.chedpp.spsconsignment;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.SpsEventType;
import uk.gov.defra.stw.mapping.totrade.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

class ChedppExaminationSpsEventMapperTest {

  private Notification notification;
  private ChedppExaminationSpsEventMapper mapper;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);
    mapper = new ChedppExaminationSpsEventMapper();
  }

  @Test
  void map_ReturnsExaminationSpsEventType_WhenComplete() throws Exception {
    SpsEventType spsEventType = mapper.map(notification.getPartOne());
    String actualSps = objectMapper.writeValueAsString(spsEventType);
    String expectedSps = ResourceUtil.readFileToString("classpath:mapping/chedpp/spsconsignment/examinationSpsEventComplete.json");
    assertThat(actualSps).isEqualTo(expectedSps);
  }

  @Test
  void map_ReturnsEmptyExaminationSpsEventType_WhenPointOfEntryControlPointIsNull() {
    notification.getPartOne().setPointOfEntryControlPoint(null);
    SpsEventType spsEventType = mapper.map(notification.getPartOne());

    assertThat(spsEventType).isNull();
  }
}
