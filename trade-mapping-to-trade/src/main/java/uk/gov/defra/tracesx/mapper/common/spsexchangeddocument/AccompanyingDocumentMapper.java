package uk.gov.defra.tracesx.mapper.common.spsexchangeddocument;

import static uk.gov.defra.tracesx.mapper.common.utils.Format.localDate;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.AIR_WAYBILL;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.BILL_OF_LADING;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.COMMERCIAL_INVOICE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.CUSTOMS_DECLARATION;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.IMPORT_PERMIT;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.LETTER_OF_AUTHORITY;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.OTHER;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.RAILWAY_BILL;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.SEA_WAYBILL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.mapper.common.spsexchangeddocument.AccompanyingDocumentMapper.AccompanyingDocumentInformation;
import uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter;
import uk.gov.defra.tracesx.notificationschema.representation.AccompanyingDocument;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType;
import uk.gov.defra.tracesx.trade.dto.AttachmentBinaryObject;
import uk.gov.defra.tracesx.trade.dto.DocumentCodeType;
import uk.gov.defra.tracesx.trade.dto.RelationshipTypeCode;
import uk.gov.defra.tracesx.trade.dto.RelationshipTypeCode.Value;
import uk.gov.defra.tracesx.trade.dto.SpsReferencedDocumentType;

public abstract class AccompanyingDocumentMapper implements
    Mapper<AccompanyingDocumentInformation, List<SpsReferencedDocumentType>> {

  private static final String AIR_WAYBILL_NAME_VALUE = "Air waybill";
  private static final String AIR_WAYBILL_TYPE_CODE = "740";
  private static final String SEA_WAYBILL_NAME_VALUE = "Sea waybill";
  private static final String SEA_WAYBILL_TYPE_CODE = "710";
  private static final String RAILWAY_BILL_NAME_VALUE = "Rail consignment note";
  private static final String RAILWAY_BILL_TYPE_CODE = "720";
  private static final String OTHER_NAME_VALUE = "Related document";
  private static final String OTHER_TYPE_CODE = "916";
  private static final String LETTER_OF_AUTHORITY_NAME_VALUE = "Dangerous goods declaration";
  private static final String LETTER_OF_AUTHORITY_TYPE_CODE = "890";
  private static final String IMPORT_PERMIT_NAME_VALUE =
      "Special requirements permit related to the transport of cargo";
  private static final String IMPORT_PERMIT_TYPE_CODE = "521";
  private static final String COMMERCIAL_INVOICE_NAME_VALUE = "Commercial invoice";
  private static final String COMMERCIAL_INVOICE_TYPE_CODE = "380";
  private static final String BILL_OF_LADING_NAME_VALUE = "Bill of lading";
  private static final String BILL_OF_LADING_TYPE_CODE = "705";
  private static final String REFERENCED_DOCUMENT_RELATIONSHIP_TYPE_CODE_NAME = "Mutually defined "
      + "reference number (Supporting document)";
  private static final String REFERENCED_DOCUMENT_RELATIONSHIP_TYPE_CODE_VALUE = "ZZZ";
  private static final String CUSTOMS_DECLARATION_NAME_VALUE = "Single administrative document";
  private static final String CUSTOMS_DECLARATION_TYPE_CODE = "960";

  protected final Map<DocumentType, DocumentTypeCode> documentReferenceMap;

  protected AccompanyingDocumentMapper() {
    documentReferenceMap = new EnumMap<>(DocumentType.class);
    documentReferenceMap.put(AIR_WAYBILL,
        new DocumentTypeCode(AIR_WAYBILL_NAME_VALUE, AIR_WAYBILL_TYPE_CODE));
    documentReferenceMap.put(BILL_OF_LADING,
        new DocumentTypeCode(BILL_OF_LADING_NAME_VALUE, BILL_OF_LADING_TYPE_CODE));
    documentReferenceMap.put(COMMERCIAL_INVOICE,
        new DocumentTypeCode(COMMERCIAL_INVOICE_NAME_VALUE, COMMERCIAL_INVOICE_TYPE_CODE));
    documentReferenceMap.put(IMPORT_PERMIT,
        new DocumentTypeCode(IMPORT_PERMIT_NAME_VALUE, IMPORT_PERMIT_TYPE_CODE));
    documentReferenceMap.put(LETTER_OF_AUTHORITY,
        new DocumentTypeCode(LETTER_OF_AUTHORITY_NAME_VALUE, LETTER_OF_AUTHORITY_TYPE_CODE));
    documentReferenceMap.put(OTHER, new DocumentTypeCode(OTHER_NAME_VALUE, OTHER_TYPE_CODE));
    documentReferenceMap.put(RAILWAY_BILL,
        new DocumentTypeCode(RAILWAY_BILL_NAME_VALUE, RAILWAY_BILL_TYPE_CODE));
    documentReferenceMap.put(SEA_WAYBILL,
        new DocumentTypeCode(SEA_WAYBILL_NAME_VALUE, SEA_WAYBILL_TYPE_CODE));
    documentReferenceMap.put(CUSTOMS_DECLARATION,
        new DocumentTypeCode(CUSTOMS_DECLARATION_NAME_VALUE, CUSTOMS_DECLARATION_TYPE_CODE));
  }

  @Override
  public List<SpsReferencedDocumentType> map(AccompanyingDocumentInformation data) {
    List<AccompanyingDocument> accompanyingDocuments = data.getAccompanyingDocument();

    if (accompanyingDocuments == null || accompanyingDocuments.isEmpty()) {
      return Collections.emptyList();
    }

    String countryOfOrigin = data.getCountryOfOrigin();
    String schemeId = data.getSchemeId();

    List<SpsReferencedDocumentType> spsReferencedDocumentTypes = new ArrayList<>();

    for (AccompanyingDocument accompanyingDocument : accompanyingDocuments) {
      DocumentTypeCode documentType =
          documentReferenceMap.get(accompanyingDocument.getDocumentType());
      spsReferencedDocumentTypes.add(
          mapSpsReferencedDocumentType(accompanyingDocument,
              documentType,
              countryOfOrigin,
              schemeId));
    }

    return spsReferencedDocumentTypes;
  }

  private SpsReferencedDocumentType mapSpsReferencedDocumentType(
      AccompanyingDocument accompanyingDocument,
      DocumentTypeCode documentType,
      String countryOfOrigin,
      String schemeId) {
    return new SpsReferencedDocumentType()
        .withIssueDateTime(localDate.apply(accompanyingDocument.getDocumentIssueDate()))
        .withTypeCode(new DocumentCodeType()
            .withName(documentType.getTypeCodeName())
            .withValue(documentType.getTypeCodeValue()))
        .withRelationshipTypeCode(new RelationshipTypeCode()
            .withName(REFERENCED_DOCUMENT_RELATIONSHIP_TYPE_CODE_NAME)
            .withValue(Value.valueOf(REFERENCED_DOCUMENT_RELATIONSHIP_TYPE_CODE_VALUE)))
        .withId(SpsTypeConverter.getIdType(accompanyingDocument.getDocumentReference())
            .withSchemeAgencyID(countryOfOrigin)
            .withSchemeID(schemeId))
        .withAttachmentBinaryObject(createAttachmentBinaryObject(accompanyingDocument));
  }

  private List<AttachmentBinaryObject> createAttachmentBinaryObject(
      AccompanyingDocument accompanyingDocument) {
    if (accompanyingDocument.getAttachmentContentType() == null
        || accompanyingDocument.getAttachmentId() == null) {
      return Collections.emptyList();
    }

    return Collections.singletonList(new AttachmentBinaryObject()
        .withMimeCode(accompanyingDocument.getAttachmentContentType())
        .withFilename(uuidToString(accompanyingDocument.getAttachmentId())));
  }

  @AllArgsConstructor
  @Getter
  protected static class DocumentTypeCode {

    private final String typeCodeName;
    private final String typeCodeValue;
  }

  @AllArgsConstructor
  @Getter
  @EqualsAndHashCode
  public static class AccompanyingDocumentInformation {

    private final List<AccompanyingDocument> accompanyingDocument;
    private final String countryOfOrigin;
    private final String schemeId;

    public AccompanyingDocumentInformation(
        List<AccompanyingDocument> accompanyingDocument, String countryOfOrigin) {
      this.accompanyingDocument = accompanyingDocument;
      this.countryOfOrigin = countryOfOrigin;
      schemeId = null;
    }
  }

  private String uuidToString(UUID uuid) {
    return uuid != null ? uuid.toString() : "";
  }
}
