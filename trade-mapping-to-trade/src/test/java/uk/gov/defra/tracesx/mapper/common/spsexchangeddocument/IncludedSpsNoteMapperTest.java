package uk.gov.defra.tracesx.mapper.common.spsexchangeddocument;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.mapper.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.trade.dto.SpsNoteType;

class IncludedSpsNoteMapperTest {

  private IncludedSpsNoteMapper includedSpsNoteMapper;
  private ObjectMapper objectMapper;
  private Notification notification;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);

    includedSpsNoteMapper = new IncludedSpsNoteMapper();
  }

  @Test
  void map_ReturnsSpsNoteTypeList_WhenComplete() throws JsonProcessingException {
    List<SpsNoteType> spsNoteTypes = includedSpsNoteMapper.map(notification);

    String actualSps = objectMapper.writeValueAsString(spsNoteTypes);
    String expectedSps = ResourceUtil.readFileToString("classpath:mapping/common/spsexchangeddocument/spsNoteTypeComplete.json");

    assertThat(actualSps).isEqualTo(expectedSps);
  }
}
