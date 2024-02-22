package uk.gov.defra.stw.mapping.toipaffs.chedpp;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsReferencedDocumentType;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.utils.VeterinaryInformationFieldMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.AccompanyingDocument;
import uk.gov.defra.tracesx.notificationschema.representation.VeterinaryInformation;

@Component
public class ChedppVeterinaryInformationMapper implements
    Mapper<List<SpsReferencedDocumentType>, VeterinaryInformation> {

  private final VeterinaryInformationFieldMapper veterinaryInformationFieldMapper;

  @Autowired
  public ChedppVeterinaryInformationMapper(
      VeterinaryInformationFieldMapper veterinaryInformationFieldMapper) {
    this.veterinaryInformationFieldMapper = veterinaryInformationFieldMapper;
  }

  @Override
  public VeterinaryInformation map(List<SpsReferencedDocumentType> spsReferencedDocumentTypes)
      throws NotificationMapperException {
    return VeterinaryInformation.builder()
        .accompanyingDocuments(mapAccompanyingDocuments(spsReferencedDocumentTypes))
        .build();
  }

  private List<AccompanyingDocument> mapAccompanyingDocuments(
      List<SpsReferencedDocumentType> spsReferencedDocumentTypes)
      throws NotificationMapperException {
    List<AccompanyingDocument> accompanyingDocuments = null;
    if (spsReferencedDocumentTypes != null && !spsReferencedDocumentTypes.isEmpty()) {
      if (!hasEphytoWithAttachment(spsReferencedDocumentTypes)) {
        throw new NotificationMapperException("Unable to find an ephyto with attached document");
      }
      accompanyingDocuments = new ArrayList<>();
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
    }
    return accompanyingDocuments;
  }

  private boolean hasEphytoWithAttachment(List<SpsReferencedDocumentType>
      spsReferencedDocumentTypes) {
    return spsReferencedDocumentTypes.stream()
        .filter(spsReferencedDocumentType ->
            veterinaryInformationFieldMapper.isTypeCodeNonNull(
                spsReferencedDocumentType.getTypeCode())
                && VeterinaryInformationFieldMapper.PHYTOSANITARY_CERTIFICATE_TYPE_CODES.contains(
                spsReferencedDocumentType.getTypeCode().getValue()))
        .anyMatch(spsReferencedDocumentType ->
            spsReferencedDocumentType.getAttachmentBinaryObject() != null
                && !spsReferencedDocumentType.getAttachmentBinaryObject().isEmpty());
  }
}
