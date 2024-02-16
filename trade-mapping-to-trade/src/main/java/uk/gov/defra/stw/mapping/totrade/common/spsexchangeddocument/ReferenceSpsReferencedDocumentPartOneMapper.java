package uk.gov.defra.stw.mapping.totrade.common.spsexchangeddocument;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.DocumentCodeType;
import uk.gov.defra.stw.mapping.dto.RelationshipTypeCode;
import uk.gov.defra.stw.mapping.dto.RelationshipTypeCode.Value;
import uk.gov.defra.stw.mapping.dto.SpsReferencedDocumentType;
import uk.gov.defra.stw.mapping.totrade.common.Mapper;
import uk.gov.defra.stw.mapping.totrade.common.utils.SpsTypeConverter;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;

@Component
public class ReferenceSpsReferencedDocumentPartOneMapper implements
    Mapper<Notification, List<SpsReferencedDocumentType>> {

  private static final String DOCUMENT_CODE_NAME = "Related document";
  private static final String DOCUMENT_CODE_VALUE = "916";
  private static final String DOCUMENT_RELATIONSHIP_TYPE_CODE_NAME
      = "Local Reference Number (Local reference)";
  private static final String DOCUMENT_RELATIONSHIP_TYPE_CODE_VALUE = "AVZ";

  @Override
  public List<SpsReferencedDocumentType> map(Notification data) {
    List<SpsReferencedDocumentType> documents = new ArrayList<>();
    PartOne partOne = data.getPartOne();
    if (partOne.getImporterLocalReferenceNumber() != null) {
      documents.add(mapImporterLocalReferenceNumber(partOne.getImporterLocalReferenceNumber()));
    }
    return documents;
  }

  private SpsReferencedDocumentType mapImporterLocalReferenceNumber(
      String importerLocalReferenceNumber) {
    return new SpsReferencedDocumentType()
        .withTypeCode(new DocumentCodeType()
            .withName(DOCUMENT_CODE_NAME).withValue(DOCUMENT_CODE_VALUE))
        .withRelationshipTypeCode(new RelationshipTypeCode()
            .withName(DOCUMENT_RELATIONSHIP_TYPE_CODE_NAME)
            .withValue(Value.fromValue(DOCUMENT_RELATIONSHIP_TYPE_CODE_VALUE)))
        .withId(SpsTypeConverter.getIdType(importerLocalReferenceNumber));
  }
}
