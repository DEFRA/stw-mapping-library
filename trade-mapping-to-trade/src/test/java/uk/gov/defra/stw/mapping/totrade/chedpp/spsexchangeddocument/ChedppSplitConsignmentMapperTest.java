package uk.gov.defra.stw.mapping.totrade.chedpp.spsexchangeddocument;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.List;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.SpsReferencedDocumentType;
import uk.gov.defra.stw.mapping.totrade.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.SplitConsignment;

class ChedppSplitConsignmentMapperTest {

  private static final String REJECTED_REFERENCE_NUMBER = "CHEDPP.GB.2020.1029356R";
  private static final String VALID_REFERENCE_NUMBER = "CHEDPP.GB.2020.1029356V";

  private Notification notification;
  private ChedppSplitConsignmentMapper splitConsignmentMapper;
  private ObjectMapper objectMapper;

  public ChedppSplitConsignmentMapperTest() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);

    splitConsignmentMapper = new ChedppSplitConsignmentMapper();
  }

  @Test
  void map_ReturnEmptySpsReferencedDocumentList_WhenNotificationIsNotSplit() {
    notification.setSplitConsignment(null);
    List<SpsReferencedDocumentType> spsReferencedDocumentList = splitConsignmentMapper.map(notification);

    assertThat(spsReferencedDocumentList).isEmpty();
  }

  @Test
  void map_ReturnSpsReferencedDocumentList_WhenNotificationIsSplit() throws JsonProcessingException {
    notification.setSplitConsignment(SplitConsignment.builder()
        .rejectedReferenceNumber(REJECTED_REFERENCE_NUMBER)
        .validReferenceNumber(VALID_REFERENCE_NUMBER)
        .build());
    List<SpsReferencedDocumentType> spsReferencedDocumentList = splitConsignmentMapper.map(notification);
    String actualSps = objectMapper.writeValueAsString(spsReferencedDocumentList);

    String expectedSps = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsexchangeddocument/referenceSpsReferencedDocumentSplitConsignment.json");

    assertThat(actualSps).isEqualTo(expectedSps);
  }

  @Test
  void map_ReturnSpsReferencedDocumentList_WhenSplitConsignmentWithValidOnly() throws JsonProcessingException {
    notification.setSplitConsignment(SplitConsignment.builder()
        .validReferenceNumber(VALID_REFERENCE_NUMBER)
        .build());
    List<SpsReferencedDocumentType> spsReferencedDocumentList = splitConsignmentMapper.map(notification);
    String actualSps = objectMapper.writeValueAsString(spsReferencedDocumentList);

    String expectedSps = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsexchangeddocument/referenceSpsReferencedDocumentSplitConsignmentValid.json");

    assertThat(actualSps).isEqualTo(expectedSps);
  }

  @Test
  void map_ReturnSpsReferencedDocumentList_WhenSplitConsignmentWithRejectedOnly() throws JsonProcessingException {
    notification.setSplitConsignment(SplitConsignment.builder()
        .rejectedReferenceNumber(REJECTED_REFERENCE_NUMBER)
        .build());
    List<SpsReferencedDocumentType> spsReferencedDocumentList = splitConsignmentMapper.map(notification);
    String actualSps = objectMapper.writeValueAsString(spsReferencedDocumentList);

    String expectedSps = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsexchangeddocument/referenceSpsReferencedDocumentSplitConsignmentRejected.json");

    assertThat(actualSps).isEqualTo(expectedSps);
  }
}
