package uk.gov.defra.tracesx.mapper.chedpp.spsexchangeddocument;

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

class ChedppAccompanyingDocumentMapperTest {

  private static final String TYPE_CODE_NAME_KEY = "#{typeCodeName}";
  private static final String TYPE_CODE_VALUE_KEY = "#{typeCodeValue}";

  private Notification notification;
  private ChedppAccompanyingDocumentMapper accompanyingDocumentMapper;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);
    accompanyingDocumentMapper = new ChedppAccompanyingDocumentMapper();
  }

  @Test
  void map_ReturnsReferencedDocumentType_WhenCargoManifestDocumentType() throws JsonProcessingException {
    assertCorrectReferenceDocumentType(DocumentType.CARGO_MANIFEST, "Cargo manifest", "785");
  }

  @Test
  void map_ReturnsReferencedDocumentType_WhenInspectionCertificateDocumentType() throws JsonProcessingException {
    assertCorrectReferenceDocumentType(DocumentType.INSPECTION_CERTIFICATE, "Inspection certificate", "856");
  }

  @Test
  void map_ReturnsReferencedDocumentType_WhenPhytosanitaryDocumentType() throws JsonProcessingException {
    assertCorrectReferenceDocumentType(DocumentType.PHYTOSANITARY_CERTIFICATE, "Phytosanitary certificate", "851");
  }

  @Test
  void map_ReturnsReferencedDocumentType_WhenOriginCertificateDocumentType() throws JsonProcessingException {
    assertCorrectReferenceDocumentType(DocumentType.ORIGIN_CERTIFICATE, "Certificate of origin", "861");
  }

  @Test
  void map_ReturnsReferencedDocumentType_WhenHeatTreatmentCertificateDocumentType() throws JsonProcessingException {
    assertCorrectReferenceDocumentType(DocumentType.HEAT_TREATMENT_CERTIFICATE, "Heat treatment certificate", "625");
  }

  @Test
  void map_ReturnsReferencedDocumentType_WhenContainerManifestDocumentType() throws JsonProcessingException {
    assertCorrectReferenceDocumentType(DocumentType.CONTAINER_MANIFEST, "Container manifest (unit packing list)", "788");
  }

  @Test
  void map_ReturnsReferencedDocumentType_WhenConformityCertificateDocumentType() throws JsonProcessingException {
    assertCorrectReferenceDocumentType(DocumentType.CONFORMITY_CERTIFICATE, "Certificate of conformity", "2");
  }

  private void assertCorrectReferenceDocumentType(DocumentType documentType,
      String typeCodeName, String typeCodeValue) throws JsonProcessingException {
    List<AccompanyingDocument> accompanyingDocuments = notification.getPartOne().getVeterinaryInformation().getAccompanyingDocuments();
    accompanyingDocuments.get(0).setDocumentType(documentType);

    List<SpsReferencedDocumentType> referencedDocumentTypes = accompanyingDocumentMapper
        .map(new AccompanyingDocumentInformation(accompanyingDocuments, notification.getPartOne().getCommodities().getCountryOfOrigin()));
    String actualSps = objectMapper.writeValueAsString(referencedDocumentTypes);

    String expectedSps = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsexchangeddocument/veterinaryInformationComplete.json");
    expectedSps = expectedSps.replace(TYPE_CODE_NAME_KEY, typeCodeName);
    expectedSps = expectedSps.replace(TYPE_CODE_VALUE_KEY, typeCodeValue);

    assertThat(actualSps).isEqualTo(expectedSps);
  }
}
