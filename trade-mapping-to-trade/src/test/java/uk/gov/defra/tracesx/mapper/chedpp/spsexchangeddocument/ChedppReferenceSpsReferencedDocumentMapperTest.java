package uk.gov.defra.tracesx.mapper.chedpp.spsexchangeddocument;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.mapper.common.spsexchangeddocument.ReferenceSpsReferencedDocumentPartOneMapper;
import uk.gov.defra.tracesx.mapper.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.trade.dto.SpsReferencedDocumentType;

class ChedppReferenceSpsReferencedDocumentMapperTest {

  private Notification notification;
  private ObjectMapper objectMapper;
  private ChedppReferenceSpsReferencedDocumentMapper spsReferencedDocumentMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);
    ChedppAccompanyingDocumentMapper accompanyingDocumentMapper = new ChedppAccompanyingDocumentMapper();
    ReferenceSpsReferencedDocumentPartOneMapper referenceSpsReferencedDocumentPartOneMapper = new ReferenceSpsReferencedDocumentPartOneMapper();
    ChedppReferenceSpsReferencedDocumentPartOneMapper partOneMapper = new ChedppReferenceSpsReferencedDocumentPartOneMapper(
        accompanyingDocumentMapper, referenceSpsReferencedDocumentPartOneMapper);
    ChedppReferenceSpsReferencedDocumentPartTwoMapper partTwoMapper = new ChedppReferenceSpsReferencedDocumentPartTwoMapper(
        accompanyingDocumentMapper);
    ChedppSplitConsignmentMapper splitConsignmentMapper = new ChedppSplitConsignmentMapper();
    spsReferencedDocumentMapper = new ChedppReferenceSpsReferencedDocumentMapper(partOneMapper,
        partTwoMapper, splitConsignmentMapper);
  }

  @Test
  void map_ReturnsReferencedDocumentType_WhenComplete() throws JsonProcessingException {
    List<SpsReferencedDocumentType> spsReferencedDocumentTypes = spsReferencedDocumentMapper
        .map(notification);
    String actualSps = objectMapper.writeValueAsString(spsReferencedDocumentTypes);

    String expectedSps = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsexchangeddocument/referenceSpsReferencedDocumentComplete.json");

    assertThat(actualSps).isEqualTo(expectedSps);
  }
}
