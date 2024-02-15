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
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;
import uk.gov.defra.tracesx.trade.dto.SpsNoteType;

class IncludedSpsNoteFromPartOneMapperTest {

  private PartOne partOne;
  private IncludedSpsNoteFromPartOneMapper includedSpsNoteFromPartOneMapper;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    partOne = objectMapper.readValue(notificationString, Notification.class).getPartOne();

    includedSpsNoteFromPartOneMapper = new IncludedSpsNoteFromPartOneMapper();
  }

  @Test
  void map_ReturnsSpsNoteTypeList_WhenComplete() throws JsonProcessingException {
    List<SpsNoteType> spsNoteTypes = includedSpsNoteFromPartOneMapper.map(partOne);
    String actualSps = objectMapper.writeValueAsString(spsNoteTypes);
    String expectedSps = ResourceUtil.readFileToString(
        "classpath:mapping/common/spsexchangeddocument/spsNoteTypeFromPartOneComplete.json");

    assertThat(actualSps).isEqualTo(expectedSps);
  }

  @Test
  void map_ReturnsEmptySpsNoteTypeList_WhenNoDepartureDate() {
    partOne.setDepartureDate(null);
    List<SpsNoteType> spsNoteTypes = includedSpsNoteFromPartOneMapper.map(partOne);

    assertThat(spsNoteTypes).isEmpty();
  }

  @Test
  void map_ReturnsEmptySpsNoteTypeList_WhenNoDepartureTime() {
    partOne.setDepartureTime(null);
    List<SpsNoteType> spsNoteTypes = includedSpsNoteFromPartOneMapper.map(partOne);

    assertThat(spsNoteTypes).isEmpty();
  }
}
