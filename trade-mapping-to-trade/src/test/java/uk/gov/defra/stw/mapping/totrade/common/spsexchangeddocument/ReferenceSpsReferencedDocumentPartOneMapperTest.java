package uk.gov.defra.stw.mapping.totrade.common.spsexchangeddocument;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.SpsReferencedDocumentType;
import uk.gov.defra.stw.mapping.totrade.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

class ReferenceSpsReferencedDocumentPartOneMapperTest {

  private Notification notification;
  private ReferenceSpsReferencedDocumentPartOneMapper referenceSpsReferencedDocumentPartOneMapper;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);

    referenceSpsReferencedDocumentPartOneMapper = new ReferenceSpsReferencedDocumentPartOneMapper();
  }

  @Test
  void map_ReturnsReferencedDocumentTypeList_WhenComplete()
      throws JsonProcessingException {
    List<SpsReferencedDocumentType> referencedDocumentTypeList =
        referenceSpsReferencedDocumentPartOneMapper.map(notification);

    String actualSps = objectMapper.writeValueAsString(referencedDocumentTypeList);
    String expectedSps = ResourceUtil.readFileToString(
        "classpath:mapping/common/spsexchangeddocument/referenceSpsReferencedDocumentPartOne.json");
    assertThat(actualSps).isEqualTo(expectedSps);
  }

  @Test
  void map_ReturnsEmptyList_WhenNullImporterLocalReferenceNumber()
      throws JsonProcessingException {
    notification.getPartOne().setImporterLocalReferenceNumber(null);

    List<SpsReferencedDocumentType> referencedDocumentTypeList =
        referenceSpsReferencedDocumentPartOneMapper.map(notification);

    String actualSps = objectMapper.writeValueAsString(referencedDocumentTypeList);
    assertThat(actualSps).isEqualTo("[ ]");
  }

}
