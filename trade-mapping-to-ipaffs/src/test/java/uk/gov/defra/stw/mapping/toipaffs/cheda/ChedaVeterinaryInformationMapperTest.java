package uk.gov.defra.stw.mapping.toipaffs.cheda;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.AIR_WAYBILL;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.LATEST_VETERINARY_HEALTH_CERTIFICATE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.VETERINARY_HEALTH_CERTIFICATE;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.AttachmentBinaryObject;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.DocumentCodeType;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.SpsReferencedDocumentType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.toipaffs.common.utils.VeterinaryInformationFieldMapper;
import uk.gov.defra.tracesx.notificationschema.representation.AccompanyingDocument;
import uk.gov.defra.tracesx.notificationschema.representation.NotificationIdentificationDetails;
import uk.gov.defra.tracesx.notificationschema.representation.VeterinaryInformation;

class ChedaVeterinaryInformationMapperTest {

  private static final String AIR_WAYBILL_TYPE = "740";
  private static final String VETERINARY_HEALTH_CERTIFICATE_TYPE = "853";

  private final VeterinaryInformationFieldMapper veterinaryInformationFieldMapper = new VeterinaryInformationFieldMapper();

  private final ChedaVeterinaryInformationMapper mapper = new ChedaVeterinaryInformationMapper(
      veterinaryInformationFieldMapper);

  @Test
  void map_ReturnsVeterinaryInformation_WhenComplete() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withReferenceSpsReferencedDocument(List.of(
                createDocument("Filename", "Mime type", "Document reference",
                    "2024-01-02T03:04:05Z", VETERINARY_HEALTH_CERTIFICATE_TYPE)
            ))
            .withIncludedSpsNote(List.of(new SpsNoteType()
                .withSubjectCode(new CodeType().withValue("IDENTIFICATION_DETAIL"))
                .withContentCode(List.of(new CodeType().withValue("Identification detail")))
                .withContent(List.of(new TextType().withValue("Identification description"))))));

    VeterinaryInformation actual = mapper.map(spsCertificate);

    assertThat(actual).usingRecursiveComparison().isEqualTo(VeterinaryInformation.builder()
        .accompanyingDocuments(List.of(AccompanyingDocument.builder()
            .documentIssueDate(LocalDate.of(2024, 1, 2))
            .documentType(LATEST_VETERINARY_HEALTH_CERTIFICATE)
            .documentReference("Document reference")
            .attachmentFilename("Filename")
            .attachmentContentType("Mime type")
            .build()))
        .identificationDetails(List.of(NotificationIdentificationDetails.builder()
            .identificationDetail("Identification detail")
            .identificationDescription("Identification description")
            .build()))
        .build());
  }

  @Test
  void map_SetsFirstVeterinaryHealthCertificateToLatest() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withReferenceSpsReferencedDocument(List.of(
                createDocument("Air waybill filename", "Air waybill mime type",
                    "Air waybill document reference", "2024-01-02T03:04:05Z", AIR_WAYBILL_TYPE),
                createDocument("First veterinary health certificate filename",
                    "First veterinary health certificate mime type",
                    "First veterinary health certificate document reference",
                    "2024-02-03T04:05:06Z", VETERINARY_HEALTH_CERTIFICATE_TYPE),
                createDocument("Second veterinary health certificate filename",
                    "Second veterinary health certificate mime type",
                    "Second veterinary health certificate document reference",
                    "2024-03-04T05:06:07Z", VETERINARY_HEALTH_CERTIFICATE_TYPE)
            )));

    VeterinaryInformation actual = mapper.map(spsCertificate);

    assertThat(actual).usingRecursiveComparison().isEqualTo(VeterinaryInformation.builder()
        .accompanyingDocuments(List.of(
            AccompanyingDocument.builder()
                .documentIssueDate(LocalDate.of(2024, 1, 2))
                .documentType(AIR_WAYBILL)
                .documentReference("Air waybill document reference")
                .attachmentFilename("Air waybill filename")
                .attachmentContentType("Air waybill mime type")
                .build(),
            AccompanyingDocument.builder()
                .documentIssueDate(LocalDate.of(2024, 2, 3))
                .documentType(LATEST_VETERINARY_HEALTH_CERTIFICATE)
                .documentReference("First veterinary health certificate document reference")
                .attachmentFilename("First veterinary health certificate filename")
                .attachmentContentType("First veterinary health certificate mime type")
                .build(),
        AccompanyingDocument.builder()
            .documentIssueDate(LocalDate.of(2024, 3, 4))
            .documentType(VETERINARY_HEALTH_CERTIFICATE)
            .documentReference("Second veterinary health certificate document reference")
            .attachmentFilename("Second veterinary health certificate filename")
            .attachmentContentType("Second veterinary health certificate mime type")
            .build())
        )
        .build());
  }

  @Test
  void map_ReturnsNullAccompanyingDocuments_WhenReferencedDocumentsNull() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withReferenceSpsReferencedDocument(null)
            .withIncludedSpsNote(List.of(new SpsNoteType()
                .withSubjectCode(new CodeType().withValue("IDENTIFICATION_DETAIL"))
                .withContentCode(List.of(new CodeType().withValue("Identification detail")))
                .withContent(List.of(new TextType().withValue("Identification description"))))));

    VeterinaryInformation actual = mapper.map(spsCertificate);

    assertThat(actual).usingRecursiveComparison().isEqualTo(VeterinaryInformation.builder()
        .identificationDetails(List.of(NotificationIdentificationDetails.builder()
            .identificationDetail("Identification detail")
            .identificationDescription("Identification description")
            .build()))
        .build());
  }

  @Test
  void map_ReturnsNullAccompanyingDocuments_WhenReferencedDocumentsEmpty() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withReferenceSpsReferencedDocument(List.of())
            .withIncludedSpsNote(List.of(new SpsNoteType()
                .withSubjectCode(new CodeType().withValue("IDENTIFICATION_DETAIL"))
                .withContentCode(List.of(new CodeType().withValue("Identification detail")))
                .withContent(List.of(new TextType().withValue("Identification description"))))));

    VeterinaryInformation actual = mapper.map(spsCertificate);

    assertThat(actual).usingRecursiveComparison().isEqualTo(VeterinaryInformation.builder()
        .identificationDetails(List.of(NotificationIdentificationDetails.builder()
            .identificationDetail("Identification detail")
            .identificationDescription("Identification description")
            .build()))
        .build());
  }

  @Test
  void map_ReturnsNullIdentificationDetails_WhenNoIdentificationDetailNotes() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withReferenceSpsReferencedDocument(List.of(
                createDocument("Filename", "Mime type", "Document reference",
                    "2024-01-02T03:04:05Z", VETERINARY_HEALTH_CERTIFICATE_TYPE)))
            .withIncludedSpsNote(List.of()));

    VeterinaryInformation actual = mapper.map(spsCertificate);

    assertThat(actual).usingRecursiveComparison().isEqualTo(VeterinaryInformation.builder()
        .accompanyingDocuments(List.of(AccompanyingDocument.builder()
            .documentIssueDate(LocalDate.of(2024, 1, 2))
            .documentType(LATEST_VETERINARY_HEALTH_CERTIFICATE)
            .documentReference("Document reference")
            .attachmentFilename("Filename")
            .attachmentContentType("Mime type")
            .build()))
        .build());
  }

  @Test
  void map_ReturnsNullIdentificationDetails_WhenNullNotes() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withReferenceSpsReferencedDocument(List.of(
                createDocument("Filename", "Mime type", "Document reference",
                    "2024-01-02T03:04:05Z", VETERINARY_HEALTH_CERTIFICATE_TYPE)
            ))
            .withIncludedSpsNote(null));

    VeterinaryInformation actual = mapper.map(spsCertificate);

    assertThat(actual).usingRecursiveComparison().isEqualTo(VeterinaryInformation.builder()
        .accompanyingDocuments(List.of(AccompanyingDocument.builder()
            .documentIssueDate(LocalDate.of(2024, 1, 2))
            .documentType(LATEST_VETERINARY_HEALTH_CERTIFICATE)
            .documentReference("Document reference")
            .attachmentFilename("Filename")
            .attachmentContentType("Mime type")
            .build()))
        .build());
  }

  @Test
  void map_ReturnsNull_WhenNoAccompanyingDocumentsOrIdentificationDetails() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument());

    VeterinaryInformation actual = mapper.map(spsCertificate);

    assertThat(actual).isNull();
  }

  private SpsReferencedDocumentType createDocument(String filename, String mimeCode,
      String reference, String issueDateType, String typeCode) {
    return new SpsReferencedDocumentType()
        .withAttachmentBinaryObject(List.of(new AttachmentBinaryObject()
            .withFilename(filename)
            .withMimeCode(mimeCode)
        ))
        .withId(new IDType().withValue(reference))
        .withIssueDateTime(issueDateType)
        .withTypeCode(new DocumentCodeType().withValue(typeCode));
  }
}
