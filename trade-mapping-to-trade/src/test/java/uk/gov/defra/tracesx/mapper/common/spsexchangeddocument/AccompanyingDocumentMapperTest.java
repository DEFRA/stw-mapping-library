package uk.gov.defra.tracesx.mapper.common.spsexchangeddocument;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.mapper.common.spsexchangeddocument.AccompanyingDocumentMapper.AccompanyingDocumentInformation;
import uk.gov.defra.tracesx.mapper.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.AccompanyingDocument;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType;
import uk.gov.defra.tracesx.trade.dto.SpsReferencedDocumentType;

class AccompanyingDocumentMapperTest {

  private static final String TYPE_CODE_NAME_KEY = "#{typeCodeName}";
  private static final String TYPE_CODE_VALUE_KEY = "#{typeCodeValue}";

  public static class ConcreteAccompanyingDocumentMapper extends AccompanyingDocumentMapper { }

  private Notification notification;
  private ConcreteAccompanyingDocumentMapper accompanyingDocumentMapper;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);
    accompanyingDocumentMapper = new ConcreteAccompanyingDocumentMapper();
  }

  @Test
  void map_ReturnsReferencedDocumentType_WhenAirWaybillDocumentType() throws JsonProcessingException {
    assertCorrectReferenceDocumentType(DocumentType.AIR_WAYBILL,"Air waybill","740");
  }

  @Test
  void map_ReturnsReferencedDocumentType_WhenBillOfLadingDocumentType() throws JsonProcessingException {
    assertCorrectReferenceDocumentType(DocumentType.BILL_OF_LADING, "Bill of lading", "705");
  }

  @Test
  void map_ReturnsReferencedDocumentType_WhenCommercialInvoiceDocumentType() throws JsonProcessingException {
    assertCorrectReferenceDocumentType(DocumentType.COMMERCIAL_INVOICE, "Commercial invoice", "380");
  }

  @Test
  void map_ReturnsReferencedDocumentType_WhenImportPermitDocumentType() throws JsonProcessingException {
    assertCorrectReferenceDocumentType(DocumentType.IMPORT_PERMIT,
        "Special requirements permit related to the transport of cargo", "521");
  }

  @Test
  void map_ReturnsReferencedDocumentType_WhenLetterOfAuthorityDocumentType() throws JsonProcessingException {
    assertCorrectReferenceDocumentType(DocumentType.LETTER_OF_AUTHORITY, "Dangerous goods declaration", "890");
  }

  @Test
  void map_ReturnsReferencedDocumentType_WhenOtherDocumentType() throws JsonProcessingException {
    assertCorrectReferenceDocumentType(DocumentType.OTHER, "Related document", "916");
  }

  @Test
  void map_ReturnsReferencedDocumentType_WhenRailwayBillDocumentType() throws JsonProcessingException {
    assertCorrectReferenceDocumentType(DocumentType.RAILWAY_BILL, "Rail consignment note", "720");
  }

  @Test
  void map_ReturnsReferencedDocumentType_WhenSeawayBillDocumentType() throws JsonProcessingException {
    assertCorrectReferenceDocumentType(DocumentType.SEA_WAYBILL, "Sea waybill", "710");
  }

  @Test
  void map_ReturnsReferencedDocumentType_WhenCustomsDeclarationDocumentType() throws JsonProcessingException {
    assertCorrectReferenceDocumentType(DocumentType.CUSTOMS_DECLARATION, "Single administrative document", "960");
  }

  private void assertCorrectReferenceDocumentType(DocumentType documentType,
      String typeCodeName, String typeCodeValue) throws JsonProcessingException {
    List<AccompanyingDocument> accompanyingDocuments = notification.getPartOne().getVeterinaryInformation().getAccompanyingDocuments();
    accompanyingDocuments.get(0).setDocumentType(documentType);

    List<SpsReferencedDocumentType> referencedDocumentTypes = accompanyingDocumentMapper
        .map(new AccompanyingDocumentInformation(accompanyingDocuments, notification.getPartOne().getCommodities().getCountryOfOrigin()));
    String actualSps = objectMapper.writeValueAsString(referencedDocumentTypes);

    String expectedSps = ResourceUtil.readFileToString(
        "classpath:mapping/common/spsexchangeddocument/veterinaryInformationComplete.json");
    expectedSps = expectedSps.replace(TYPE_CODE_NAME_KEY, typeCodeName);
    expectedSps = expectedSps.replace(TYPE_CODE_VALUE_KEY, typeCodeValue);

    assertThat(actualSps).isEqualTo(expectedSps);
  }
}
