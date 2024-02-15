package uk.gov.defra.tracesx.mapper.chedpp.spsexchangeddocument;

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
import uk.gov.defra.tracesx.trade.dto.SpsReferencedDocumentType;

class ChedppReferenceSpsReferencedDocumentPartTwoMapperTest {
  private Notification notification;
  private ObjectMapper objectMapper;
  private ChedppReferenceSpsReferencedDocumentPartTwoMapper partTwoMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);
    ChedppAccompanyingDocumentMapper accompanyingDocumentMapper = new ChedppAccompanyingDocumentMapper();
    partTwoMapper = new ChedppReferenceSpsReferencedDocumentPartTwoMapper(accompanyingDocumentMapper);
  }

  @Test
  void map_ReturnsReferencedDocumentTypeList_WhenComplete() throws JsonProcessingException {
    List<SpsReferencedDocumentType> referencedDocumentTypeList =
        partTwoMapper.map(notification);

    String actualSps = objectMapper.writeValueAsString(referencedDocumentTypeList);
    String expectedSps = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsexchangeddocument/referenceSpsReferencedDocumentPartTwo.json");
    assertThat(actualSps).isEqualTo(expectedSps);
  }

  @Test
  void map_ReturnsEmptyList_WhenNullPartTwo() {
    notification.setPartTwo(null);

    List<SpsReferencedDocumentType> referencedDocumentTypeList =
        partTwoMapper.map(notification);

    assertThat(referencedDocumentTypeList).isEmpty();
  }
}
