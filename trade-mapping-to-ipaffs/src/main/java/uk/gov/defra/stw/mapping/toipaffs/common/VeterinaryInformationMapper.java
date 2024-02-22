package uk.gov.defra.stw.mapping.toipaffs.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.SpsReferencedDocumentType;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.utils.VeterinaryInformationFieldMapper;
import uk.gov.defra.tracesx.notificationschema.representation.AccompanyingDocument;
import uk.gov.defra.tracesx.notificationschema.representation.VeterinaryInformation;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType;

@Component
public class VeterinaryInformationMapper implements
    Mapper<SpsExchangedDocument, VeterinaryInformation> {

  private final VeterinaryInformationFieldMapper veterinaryInformationFieldMapper;

  @Autowired
  public VeterinaryInformationMapper(
      VeterinaryInformationFieldMapper veterinaryInformationFieldMapper) {
    this.veterinaryInformationFieldMapper = veterinaryInformationFieldMapper;
  }

  @Override
  public VeterinaryInformation map(SpsExchangedDocument spsExchangedDocument) {
    return VeterinaryInformation.builder()
        .accompanyingDocuments(mapAccompanyingDocuments(spsExchangedDocument))
        .build();
  }

  private List<AccompanyingDocument> mapAccompanyingDocuments(
      SpsExchangedDocument spsExchangedDocument) {
    List<SpsReferencedDocumentType> spsReferencedDocumentTypes = spsExchangedDocument
        .getReferenceSpsReferencedDocument();

    if (spsReferencedDocumentTypes == null || spsReferencedDocumentTypes.isEmpty()) {
      return Collections.singletonList(
          createPlaceholderForVeterinaryHealthCertificate(spsExchangedDocument));
    }

    List<AccompanyingDocument> accompanyingDocuments = new ArrayList<>();
    if (!isECertTypeCode(spsReferencedDocumentTypes)) {
      accompanyingDocuments.add(
          createPlaceholderForVeterinaryHealthCertificate(spsExchangedDocument));
    }

    for (SpsReferencedDocumentType spsReferencedDocumentType : spsReferencedDocumentTypes) {
      accompanyingDocuments.add(AccompanyingDocument.builder()
          .documentIssueDate(
              veterinaryInformationFieldMapper.mapDocumentIssueDate(spsReferencedDocumentType))
          .documentType(
              veterinaryInformationFieldMapper.mapDocumentType(spsReferencedDocumentType))
          .documentReference(
              veterinaryInformationFieldMapper.mapDocumentReference(spsReferencedDocumentType))
          .build());
    }

    return accompanyingDocuments;
  }

  private AccompanyingDocument createPlaceholderForVeterinaryHealthCertificate(
      SpsExchangedDocument spsExchangedDocument) {
    return AccompanyingDocument.builder()
        .documentType(DocumentType.VETERINARY_HEALTH_CERTIFICATE)
        .documentReference(spsExchangedDocument.getId().getValue())
        .documentIssueDate(LocalDate.parse(
            spsExchangedDocument.getIssueDateTime().getDateTime().getValue(),
            DateTimeFormatter.ISO_OFFSET_DATE_TIME))
        .build();
  }

  @SuppressWarnings("checkstyle:AbbreviationAsWordInName")
  private boolean isECertTypeCode(List<SpsReferencedDocumentType> spsReferencedDocumentTypes) {
    return spsReferencedDocumentTypes.stream()
        .anyMatch(spsReferencedDocumentType ->
            veterinaryInformationFieldMapper.isTypeCodeNonNull(
                spsReferencedDocumentType.getTypeCode())
                && VeterinaryInformationFieldMapper.VETERINARY_CERTIFICATE_TYPE_CODES.contains(
                spsReferencedDocumentType.getTypeCode().getValue()));
  }
}
