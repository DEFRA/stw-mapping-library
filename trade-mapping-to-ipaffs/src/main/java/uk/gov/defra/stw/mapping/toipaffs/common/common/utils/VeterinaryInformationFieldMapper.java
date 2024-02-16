package uk.gov.defra.stw.mapping.toipaffs.common.common.utils;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.AIR_WAYBILL;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.BILL_OF_LADING;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.CARGO_MANIFEST;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.COMMERCIAL_INVOICE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.CONFORMITY_CERTIFICATE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.CONTAINER_MANIFEST;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.CUSTOMS_DECLARATION;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.HEAT_TREATMENT_CERTIFICATE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.IMPORT_PERMIT;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.INSPECTION_CERTIFICATE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.ORIGIN_CERTIFICATE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.OTHER;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.PHYTOSANITARY_CERTIFICATE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.SEA_WAYBILL;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.VETERINARY_HEALTH_CERTIFICATE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.DocumentCodeType;
import uk.gov.defra.stw.mapping.dto.SpsReferencedDocumentType;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType;

@Component
public class VeterinaryInformationFieldMapper {

  private static final List<String> AIR_WAYBILL_TYPE_CODES = List.of("740", "741", "743");
  private static final List<String> COMMERCIAL_INVOICE_TYPE_CODES = List.of("331", "380", "384",
      "385", "395");
  private static final List<String> CARGO_MANIFEST_TYPE_CODES = List.of("785", "786");
  private static final List<String> INSPECTION_CERTIFICATE_TYPE_CODES = List.of("856");
  private static final List<String> IMPORT_PERMIT_TYPE_CODES = List.of("911");
  private static final List<String> ORIGIN_CERTIFICATE_TYPE_CODES = List.of("17", "268", "861",
      "864", "865", "954");
  private static final List<String> HEAT_TREATMENT_CERTIFICATE_TYPE_CODES = List.of("625");
  private static final List<String> CONTAINER_MANIFEST_TYPE_CODES = List.of("788");
  private static final List<String> SEA_WAYBILL_TYPE_CODES = List.of("710");
  private static final List<String> CUSTOMS_DECLARATION_TYPE_CODES = List.of("333", "334", "337",
      "355", "578", "579", "581", "582", "583", "587", "913", "914", "915", "936", "960");
  private static final List<String> BILL_OF_LADING_TYPE_CODES = List.of("704", "705", "706", "707",
      "708", "709", "711", "714", "716", "761", "766");
  private static final List<String> CONFORMITY_CERTIFICATE_TYPE_CODES = List.of("2");
  public static final List<String> PHYTOSANITARY_CERTIFICATE_TYPE_CODES = List.of("657", "851");
  public static final List<String> VETERINARY_CERTIFICATE_TYPE_CODES = List.of("853", "629");

  private static final Map<String, DocumentType> DOCUMENT_TYPE_MAP = initialise();

  private static Map<String, DocumentType> initialise() {
    Map<String, DocumentType> mutable = new HashMap<>();
    AIR_WAYBILL_TYPE_CODES.forEach(code -> mutable.put(code, AIR_WAYBILL));
    COMMERCIAL_INVOICE_TYPE_CODES.forEach(code -> mutable.put(code, COMMERCIAL_INVOICE));
    CARGO_MANIFEST_TYPE_CODES.forEach(code -> mutable.put(code, CARGO_MANIFEST));
    INSPECTION_CERTIFICATE_TYPE_CODES.forEach(code -> mutable.put(code, INSPECTION_CERTIFICATE));
    IMPORT_PERMIT_TYPE_CODES.forEach(code -> mutable.put(code, IMPORT_PERMIT));
    ORIGIN_CERTIFICATE_TYPE_CODES.forEach(code -> mutable.put(code, ORIGIN_CERTIFICATE));
    HEAT_TREATMENT_CERTIFICATE_TYPE_CODES.forEach(
        code -> mutable.put(code, HEAT_TREATMENT_CERTIFICATE));
    CONTAINER_MANIFEST_TYPE_CODES.forEach(code -> mutable.put(code, CONTAINER_MANIFEST));
    SEA_WAYBILL_TYPE_CODES.forEach(code -> mutable.put(code, SEA_WAYBILL));
    CUSTOMS_DECLARATION_TYPE_CODES.forEach(code -> mutable.put(code, CUSTOMS_DECLARATION));
    BILL_OF_LADING_TYPE_CODES.forEach(code -> mutable.put(code, BILL_OF_LADING));
    CONFORMITY_CERTIFICATE_TYPE_CODES.forEach(code -> mutable.put(code, CONFORMITY_CERTIFICATE));
    PHYTOSANITARY_CERTIFICATE_TYPE_CODES.forEach(
        code -> mutable.put(code, PHYTOSANITARY_CERTIFICATE));
    VETERINARY_CERTIFICATE_TYPE_CODES.forEach(
        code -> mutable.put(code, VETERINARY_HEALTH_CERTIFICATE));
    return Collections.unmodifiableMap(mutable);
  }

  public DocumentType mapDocumentType(SpsReferencedDocumentType spsReferencedDocumentType) {
    if (!isTypeCodeNonNull(spsReferencedDocumentType.getTypeCode())) {
      return OTHER;
    }
    DocumentType documentType = DOCUMENT_TYPE_MAP.get(
        spsReferencedDocumentType.getTypeCode().getValue());
    return documentType != null ? documentType : OTHER;
  }

  public String mapDocumentReference(SpsReferencedDocumentType spsReferencedDocumentType) {
    return spsReferencedDocumentType.getId().getValue();
  }

  public LocalDate mapDocumentIssueDate(SpsReferencedDocumentType spsReferencedDocumentType) {
    return spsReferencedDocumentType.getIssueDateTime() == null
        ? null
        : getLocalDateTimeFromString(spsReferencedDocumentType.getIssueDateTime()).toLocalDate();
  }

  public boolean isTypeCodeNonNull(DocumentCodeType documentCodeType) {
    return documentCodeType != null && documentCodeType.getValue() != null;
  }

  private LocalDateTime getLocalDateTimeFromString(String localDateTime) {
    return LocalDateTime.parse(localDateTime, ISO_OFFSET_DATE_TIME);
  }
}
