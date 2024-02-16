package uk.gov.defra.stw.mapping.totrade.chedpp.spsexchangeddocument;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.DocumentCodeType;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.RelationshipTypeCode;
import uk.gov.defra.stw.mapping.dto.SpsReferencedDocumentType;
import uk.gov.defra.stw.mapping.totrade.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.SplitConsignment;

@Component
public class ChedppSplitConsignmentMapper
    implements Mapper<Notification, List<SpsReferencedDocumentType>> {

  private static final String PARTIALLY_VALIDATED_NAME =
      "Call-off delivery (Partially validated in >>)";
  private static final String PARTIALLY_REJECTED_NAME =
      "Call-off delivery (Partially rejected in >>)";
  private static final String RELATIONSHIP_TYPE_CODE_NAME =
      "Related document number (Related document number)";
  private static final String TYPE_CODE_VALUE = "76";

  @Override
  public List<SpsReferencedDocumentType> map(Notification notification) {
    List<SpsReferencedDocumentType> spsReferencedDocuments = new ArrayList<>();
    SplitConsignment splitConsignment = notification.getSplitConsignment();
    if (splitConsignment != null) {
      String validReferenceNumber = splitConsignment.getValidReferenceNumber();
      if (validReferenceNumber != null) {
        spsReferencedDocuments.add(
            createReferenceSpsReferencedDocument(PARTIALLY_VALIDATED_NAME, validReferenceNumber));
      }

      String rejectedReferenceNumber = splitConsignment.getRejectedReferenceNumber();
      if (rejectedReferenceNumber != null) {
        spsReferencedDocuments.add(
            createReferenceSpsReferencedDocument(PARTIALLY_REJECTED_NAME, rejectedReferenceNumber));
      }
    }
    return spsReferencedDocuments;
  }

  private SpsReferencedDocumentType createReferenceSpsReferencedDocument(
      String typeCodeName, String idValue) {
    return new SpsReferencedDocumentType()
        .withTypeCode(new DocumentCodeType()
            .withName(typeCodeName)
            .withValue(TYPE_CODE_VALUE))
        .withRelationshipTypeCode(new RelationshipTypeCode()
            .withName(RELATIONSHIP_TYPE_CODE_NAME)
            .withValue(RelationshipTypeCode.Value.ACE))
        .withId(new IDType().withValue(idValue));
  }
}
