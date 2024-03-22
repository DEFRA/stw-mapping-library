package uk.gov.defra.stw.mapping.toipaffs.cheda;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.LATEST_VETERINARY_HEALTH_CERTIFICATE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.VETERINARY_HEALTH_CERTIFICATE;

import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.SpsReferencedDocumentType;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.utils.VeterinaryInformationFieldMapper;
import uk.gov.defra.tracesx.notificationschema.representation.AccompanyingDocument;
import uk.gov.defra.tracesx.notificationschema.representation.NotificationIdentificationDetails;
import uk.gov.defra.tracesx.notificationschema.representation.VeterinaryInformation;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType;

@Component
public class ChedaVeterinaryInformationMapper implements
    Mapper<SpsCertificate, VeterinaryInformation> {

  private final VeterinaryInformationFieldMapper veterinaryInformationFieldMapper;

  @Autowired
  public ChedaVeterinaryInformationMapper(
      VeterinaryInformationFieldMapper veterinaryInformationFieldMapper) {
    this.veterinaryInformationFieldMapper = veterinaryInformationFieldMapper;
  }

  @Override
  public VeterinaryInformation map(SpsCertificate spsCertificate) {
    List<AccompanyingDocument> accompanyingDocuments = mapAccompanyingDocuments(spsCertificate);
    List<NotificationIdentificationDetails> identificationDetails = mapIdentificationDetails(
        spsCertificate);
    if (accompanyingDocuments != null || identificationDetails != null) {
      return VeterinaryInformation.builder()
          .accompanyingDocuments(accompanyingDocuments)
          .identificationDetails(identificationDetails)
          .build();
    } else {
      return null;
    }
  }

  private List<AccompanyingDocument> mapAccompanyingDocuments(SpsCertificate spsCertificate) {
    List<SpsReferencedDocumentType> referencedDocuments = spsCertificate.getSpsExchangedDocument()
        .getReferenceSpsReferencedDocument();
    if (referencedDocuments != null && !referencedDocuments.isEmpty()) {
      List<AccompanyingDocument> documents = referencedDocuments.stream()
          .map(document -> AccompanyingDocument.builder()
              .documentIssueDate(veterinaryInformationFieldMapper.mapDocumentIssueDate(document))
              .documentType(veterinaryInformationFieldMapper.mapDocumentType(document))
              .documentReference(veterinaryInformationFieldMapper.mapDocumentReference(document))
              .attachmentFilename(document.getAttachmentBinaryObject().get(0).getFilename())
              .attachmentContentType(document.getAttachmentBinaryObject().get(0).getMimeCode())
              .build())
          .toList();
      documents.stream()
          .filter(document -> document.getDocumentType().equals(VETERINARY_HEALTH_CERTIFICATE))
          .findFirst()
          .ifPresent(document -> document.setDocumentType(LATEST_VETERINARY_HEALTH_CERTIFICATE));
      return documents;
    } else {
      return null;
    }
  }

  private List<NotificationIdentificationDetails> mapIdentificationDetails(
      SpsCertificate spsCertificate) {
    List<SpsNoteType> notes = spsCertificate.getSpsExchangedDocument().getIncludedSpsNote();
    if (notes != null) {
      List<NotificationIdentificationDetails> identificationDetails = notes.stream()
          .filter(note -> note.getSubjectCode() != null
              && Objects.equals(note.getSubjectCode().getValue(), "IDENTIFICATION_DETAIL"))
          .map(note -> NotificationIdentificationDetails.builder()
              .identificationDetail(note.getContentCode().get(0).getValue())
              .identificationDescription(note.getContent().get(0).getValue())
              .build())
          .toList();
      return identificationDetails.isEmpty() ? null : identificationDetails;
    } else {
      return null;
    }
  }
}
