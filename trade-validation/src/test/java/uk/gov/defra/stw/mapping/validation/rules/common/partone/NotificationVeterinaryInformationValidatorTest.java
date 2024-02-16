package uk.gov.defra.stw.mapping.validation.rules.common.partone;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.validation.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.AccompanyingDocument;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType;

class NotificationVeterinaryInformationValidatorTest {
  private NotificationVeterinaryInformationValidator validator;
  private Notification notification;
  private Set<DocumentType> documentTypeSet;

  @BeforeEach
  void setup() throws Exception {
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = new ObjectMapper().readValue(notificationString, Notification.class);
    validator = new NotificationVeterinaryInformationValidator();
    this.documentTypeSet
        = Set.of(DocumentType.AIR_WAYBILL,
        DocumentType.BILL_OF_LADING,
        DocumentType.CATCH_CERTIFICATE,
        DocumentType.COMMERCIAL_INVOICE,
        DocumentType.CUSTOMS_DECLARATION,
        DocumentType.HEALTH_CERTIFICATE,
        DocumentType.IMPORT_PERMIT,
        DocumentType.LABORATORY_SAMPLING_RESULTS_FOR_AFLATOXIN,
        DocumentType.LETTER_OF_AUTHORITY,
        DocumentType.OTHER,
        DocumentType.RAILWAY_BILL,
        DocumentType.SEA_WAYBILL,
        DocumentType.VETERINARY_HEALTH_CERTIFICATE,
        DocumentType.CARGO_MANIFEST,
        DocumentType.INSPECTION_CERTIFICATE,
        DocumentType.PHYTOSANITARY_CERTIFICATE,
        DocumentType.ORIGIN_CERTIFICATE,
        DocumentType.HEAT_TREATMENT_CERTIFICATE,
        DocumentType.CONTAINER_MANIFEST,
        DocumentType.CONFORMITY_CERTIFICATE
    );
  }

  @Test
  void validate_ReturnsTrue_WhenVeterinaryInformationIsNull() {
    notification.getPartOne().setVeterinaryInformation(null);
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsTrue_WhenValidNullDate() {
    notification.getPartOne().getVeterinaryInformation().setVeterinaryDocumentIssueDate(null);
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsTrue_WhenValidNullAccompanyingDocument() {
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsTrue_WhenCompleteValidData() {
    notification.getPartOne().getVeterinaryInformation().setAccompanyingDocuments(createAccompanyingDocumentList());
    assertThat(validator.validate(notification)).isTrue();
  }

  @Test
  void validate_ReturnsFalse_WhenInvalidDate() {
    notification.getPartOne().getVeterinaryInformation().setVeterinaryDocumentIssueDate("12-05-2020");
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenDocumentReferenceIsNull() {
    AccompanyingDocument accompanyingDocument = new AccompanyingDocument();
    accompanyingDocument.setDocumentReference(null);
    accompanyingDocument.setDocumentType(DocumentType.AIR_WAYBILL);
    notification.getPartOne().getVeterinaryInformation().setAccompanyingDocuments(
        Collections.singletonList(accompanyingDocument));
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenDocumentReferenceIsEmpty() {
    AccompanyingDocument accompanyingDocument = new AccompanyingDocument();
    accompanyingDocument.setDocumentReference("");
    accompanyingDocument.setDocumentType(DocumentType.AIR_WAYBILL);
    notification.getPartOne().getVeterinaryInformation().setAccompanyingDocuments(
        Collections.singletonList(accompanyingDocument));
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void validate_ReturnsFalse_WhenInvalidDocumentType() {
    AccompanyingDocument accompanyingDocument = new AccompanyingDocument();
    accompanyingDocument.setDocumentReference("CHEDPP.GB.2020.123455");
    accompanyingDocument.setDocumentType(DocumentType.fromValue("Invalid"));
    notification.getPartOne().getVeterinaryInformation().setAccompanyingDocuments(
        Collections.singletonList(accompanyingDocument));
    assertThat(validator.validate(notification)).isFalse();
  }

  @Test
  void getMessage_ReturnsCorrectMessage() {
    assertThat(validator.getMessage()).isEqualTo("Invalid veterinary information");
  }

  private List<AccompanyingDocument> createAccompanyingDocumentList() {
    List<AccompanyingDocument> accompanyingDocuments = new ArrayList<>();
    for(DocumentType documentType : documentTypeSet) {
      AccompanyingDocument accompanyingDocument = new AccompanyingDocument();
      accompanyingDocument.setDocumentReference("CHEDPP.GB.2020.123455");
      accompanyingDocument.setDocumentType(documentType);
      accompanyingDocuments.add(accompanyingDocument);
    }
    return accompanyingDocuments;
  }
}
