package uk.gov.defra.tracesx.mapper.chedpp.spsexchangeddocument;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.CARGO_MANIFEST;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.CONFORMITY_CERTIFICATE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.CONTAINER_MANIFEST;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.HEAT_TREATMENT_CERTIFICATE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.INSPECTION_CERTIFICATE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.ORIGIN_CERTIFICATE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.PHYTOSANITARY_CERTIFICATE;

import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.mapper.common.spsexchangeddocument.AccompanyingDocumentMapper;

@Component
public class ChedppAccompanyingDocumentMapper extends AccompanyingDocumentMapper {

  private static final String CARGO_MANIFEST_NAME_VALUE = "Cargo manifest";
  private static final String CARGO_MANIFEST_TYPE_CODE = "785";
  private static final String INSPECTION_CERTIFICATE_NAME_VALUE = "Inspection certificate";
  private static final String INSPECTION_CERTIFICATE_TYPE_CODE = "856";
  private static final String PHYTOSANITARY_CERTIFICATE_NAME_VALUE = "Phytosanitary certificate";
  private static final String PHYTOSANITARY_CERTIFICATE_TYPE_CODE = "851";
  private static final String ORIGIN_CERTIFICATE_NAME_VALUE = "Certificate of origin";
  private static final String ORIGIN_CERTIFICATE_TYPE_CODE = "861";
  private static final String HEAT_TREATMENT_CERTIFICATE_NAME_VALUE = "Heat treatment certificate";
  private static final String HEAT_TREATMENT_CERTIFICATE_TYPE_CODE = "625";
  private static final String CONTAINER_MANIFEST_NAME_VALUE =
      "Container manifest (unit packing list)";
  private static final String CONTAINER_MANIFEST_TYPE_CODE = "788";
  private static final String CONFORMITY_CERTIFICATE_NAME_VALUE = "Certificate of conformity";
  private static final String CONFORMITY_CERTIFICATE_TYPE_CODE = "2";

  public ChedppAccompanyingDocumentMapper() {
    documentReferenceMap.put(CARGO_MANIFEST, new DocumentTypeCode(CARGO_MANIFEST_NAME_VALUE,
        CARGO_MANIFEST_TYPE_CODE));
    documentReferenceMap.put(INSPECTION_CERTIFICATE,
        new DocumentTypeCode(INSPECTION_CERTIFICATE_NAME_VALUE, INSPECTION_CERTIFICATE_TYPE_CODE));
    documentReferenceMap.put(PHYTOSANITARY_CERTIFICATE,
        new DocumentTypeCode(PHYTOSANITARY_CERTIFICATE_NAME_VALUE,
            PHYTOSANITARY_CERTIFICATE_TYPE_CODE));
    documentReferenceMap.put(ORIGIN_CERTIFICATE,
        new DocumentTypeCode(ORIGIN_CERTIFICATE_NAME_VALUE, ORIGIN_CERTIFICATE_TYPE_CODE));
    documentReferenceMap.put(HEAT_TREATMENT_CERTIFICATE,
        new DocumentTypeCode(HEAT_TREATMENT_CERTIFICATE_NAME_VALUE,
            HEAT_TREATMENT_CERTIFICATE_TYPE_CODE));
    documentReferenceMap.put(CONTAINER_MANIFEST,
        new DocumentTypeCode(CONTAINER_MANIFEST_NAME_VALUE, CONTAINER_MANIFEST_TYPE_CODE));
    documentReferenceMap.put(CONFORMITY_CERTIFICATE,
        new DocumentTypeCode(CONFORMITY_CERTIFICATE_NAME_VALUE, CONFORMITY_CERTIFICATE_TYPE_CODE));
  }
}