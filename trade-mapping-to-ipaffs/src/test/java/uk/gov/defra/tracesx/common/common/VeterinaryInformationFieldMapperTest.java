package uk.gov.defra.tracesx.common.common;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.PHYTOSANITARY_CERTIFICATE;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.common.common.utils.VeterinaryInformationFieldMapper;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType;
import uk.gov.defra.tracesx.testutils.JsonDeserializer;
import uk.gov.defra.tracesx.testutils.TestUtils;
import uk.gov.defra.tracesx.trade.dto.DocumentCodeType;
import uk.gov.defra.tracesx.trade.dto.SpsExchangedDocument;
import uk.gov.defra.tracesx.trade.dto.SpsReferencedDocumentType;

class VeterinaryInformationFieldMapperTest {

  private static final List<String> AIR_WAYBILL_TYPE_CODES = Arrays.asList("740", "741", "743");
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
      "355","578", "579", "581", "582", "583", "587", "913", "914", "915", "936", "960");
  private static final List<String> BILL_OF_LADING_TYPE_CODES = List.of("704", "705", "706", "707",
      "708", "709", "711", "714", "716", "761", "766");
  private static final List<String> CONFORMITY_CERTIFICATE_TYPE_CODES = List.of("2");
  private static final List<String> VETERINARY_CERTIFICATE_TYPE_CODES = List.of("853", "629");
  private static final List<String> PHYTOSANITARY_CERTIFICATE_TYPE_CODES = List.of("657", "851");

  private final VeterinaryInformationFieldMapper mapper;
  private final ObjectMapper objectMapper;
  private final SpsReferencedDocumentType spsReferencedDocumentType;

  public VeterinaryInformationFieldMapperTest() throws Exception {
    mapper = new VeterinaryInformationFieldMapper();
    objectMapper = TestUtils.initObjectMapper();

    spsReferencedDocumentType = getSpsReferencedDocumentType();
  }

  @Test
  void mapDocumentType_ReturnsDocumentType_WhenAirWaybill() {
    for (String typeCode : AIR_WAYBILL_TYPE_CODES) {
      spsReferencedDocumentType.getTypeCode().setValue(typeCode);

      DocumentType type = mapper.mapDocumentType(spsReferencedDocumentType);

      assertThat(type).isEqualTo(DocumentType.AIR_WAYBILL);
    }
  }

  @Test
  void mapDocumentType_ReturnsDocumentType_WhenCommercialInvoice() {
    for (String typeCode : COMMERCIAL_INVOICE_TYPE_CODES) {
      spsReferencedDocumentType.getTypeCode().setValue(typeCode);

      DocumentType type = mapper.mapDocumentType(spsReferencedDocumentType);

      assertThat(type).isEqualTo(DocumentType.COMMERCIAL_INVOICE);
    }
  }

  @Test
  void mapDocumentType_ReturnsDocumentType_WhenCargoManifest() {
    for (String typeCode : CARGO_MANIFEST_TYPE_CODES) {
      spsReferencedDocumentType.getTypeCode().setValue(typeCode);

      DocumentType type = mapper.mapDocumentType(spsReferencedDocumentType);

      assertThat(type).isEqualTo(DocumentType.CARGO_MANIFEST);
    }
  }

  @Test
  void mapDocumentType_ReturnsDocumentType_WhenInspectionCertificate() {
    for (String typeCode : INSPECTION_CERTIFICATE_TYPE_CODES) {
      spsReferencedDocumentType.getTypeCode().setValue(typeCode);

      DocumentType type = mapper.mapDocumentType(spsReferencedDocumentType);

      assertThat(type).isEqualTo(DocumentType.INSPECTION_CERTIFICATE);
    }
  }

  @Test
  void mapDocumentType_ReturnsDocumentType_WhenImportPermit() {
    for (String typeCode : IMPORT_PERMIT_TYPE_CODES) {
      spsReferencedDocumentType.getTypeCode().setValue(typeCode);

      DocumentType type = mapper.mapDocumentType(spsReferencedDocumentType);

      assertThat(type).isEqualTo(DocumentType.IMPORT_PERMIT);
    }
  }

  @Test
  void mapDocumentType_ReturnsDocumentType_WhenOriginCertificate() {
    for (String typeCode : ORIGIN_CERTIFICATE_TYPE_CODES) {
      spsReferencedDocumentType.getTypeCode().setValue(typeCode);

      DocumentType type = mapper.mapDocumentType(spsReferencedDocumentType);

      assertThat(type).isEqualTo(DocumentType.ORIGIN_CERTIFICATE);
    }
  }

  @Test
  void mapDocumentType_ReturnsDocumentType_WhenHeatTreatmentCertificate() {
    for (String typeCode : HEAT_TREATMENT_CERTIFICATE_TYPE_CODES) {
      spsReferencedDocumentType.getTypeCode().setValue(typeCode);

      DocumentType type = mapper.mapDocumentType(spsReferencedDocumentType);

      assertThat(type).isEqualTo(DocumentType.HEAT_TREATMENT_CERTIFICATE);
    }
  }

  @Test
  void mapDocumentType_ReturnsDocumentType_WhenContainerManifest() {
    for (String typeCode : CONTAINER_MANIFEST_TYPE_CODES) {
      spsReferencedDocumentType.getTypeCode().setValue(typeCode);

      DocumentType type = mapper.mapDocumentType(spsReferencedDocumentType);

      assertThat(type).isEqualTo(DocumentType.CONTAINER_MANIFEST);
    }
  }

  @Test
  void mapDocumentType_ReturnsDocumentType_WhenSeaWaybill() {
    for (String typeCode : SEA_WAYBILL_TYPE_CODES) {
      spsReferencedDocumentType.getTypeCode().setValue(typeCode);

      DocumentType type = mapper.mapDocumentType(spsReferencedDocumentType);

      assertThat(type).isEqualTo(DocumentType.SEA_WAYBILL);
    }
  }

  @Test
  void mapDocumentType_ReturnsDocumentType_WhenCustomsDeclaration() {
    for (String typeCode : CUSTOMS_DECLARATION_TYPE_CODES) {
      spsReferencedDocumentType.getTypeCode().setValue(typeCode);

      DocumentType type = mapper.mapDocumentType(spsReferencedDocumentType);

      assertThat(type).isEqualTo(DocumentType.CUSTOMS_DECLARATION);
    }
  }

  @Test
  void mapDocumentType_ReturnsDocumentType_WhenBillOfLading() {
    for (String typeCode : BILL_OF_LADING_TYPE_CODES) {
      spsReferencedDocumentType.getTypeCode().setValue(typeCode);

      DocumentType type = mapper.mapDocumentType(spsReferencedDocumentType);

      assertThat(type).isEqualTo(DocumentType.BILL_OF_LADING);
    }
  }

  @Test
  void mapDocumentType_ReturnsDocumentType_WhenConformityCertificate() {
    for (String typeCode : CONFORMITY_CERTIFICATE_TYPE_CODES) {
      spsReferencedDocumentType.getTypeCode().setValue(typeCode);

      DocumentType type = mapper.mapDocumentType(spsReferencedDocumentType);

      assertThat(type).isEqualTo(DocumentType.CONFORMITY_CERTIFICATE);
    }
  }

  @Test
  void mapDocumentType_ReturnsDocumentType_WhenVeterinaryHealthCertificate() {
    for (String typeCode : VETERINARY_CERTIFICATE_TYPE_CODES) {
      spsReferencedDocumentType.getTypeCode().setValue(typeCode);

      DocumentType type = mapper.mapDocumentType(spsReferencedDocumentType);

      assertThat(type).isEqualTo(DocumentType.VETERINARY_HEALTH_CERTIFICATE);
    }
  }

  @Test
  void mapDocumentType_ReturnsDocumentType_WhenPhytoSanitaryCertificate() {
    for (String typeCode : PHYTOSANITARY_CERTIFICATE_TYPE_CODES) {
      spsReferencedDocumentType.getTypeCode().setValue(typeCode);

      DocumentType type = mapper.mapDocumentType(spsReferencedDocumentType);

      assertThat(type).isEqualTo(PHYTOSANITARY_CERTIFICATE);
    }
  }

  @Test
  void mapDocumentType_ReturnsDocumentType_WhenUnrecognisedSpsReferencedDocumentType() {
    spsReferencedDocumentType.getTypeCode().setValue("unrecognisedTypeCode");

    DocumentType type = mapper.mapDocumentType(spsReferencedDocumentType);

    assertThat(type).isEqualTo(DocumentType.OTHER);
  }

  @Test
  void mapDocumentType_ReturnsDocumentType_WhenSpsReferenceDocumentTypeNotSupplied() {
    spsReferencedDocumentType.getTypeCode().setValue(null);

    DocumentType type = mapper.mapDocumentType(spsReferencedDocumentType);

    assertThat(type).isEqualTo(DocumentType.OTHER);
  }

  @Test
  void mapDocumentType_ReturnsDocumentType_WhenSpsReferenceDocumentTypeCodeIsNull() {
    spsReferencedDocumentType.setTypeCode(null);

    DocumentType type = mapper.mapDocumentType(spsReferencedDocumentType);

    assertThat(type).isEqualTo(DocumentType.OTHER);
  }

  @Test
  void mapDocumentReference_ReturnsDocumentReferenceValue() {
    String value = mapper.mapDocumentReference(spsReferencedDocumentType);

    assertThat(value).isEqualTo("9022780365");
  }

  @Test
  void mapDocumentIssueDate_ReturnsLocalDate_WhenIssueDateTimeSet() {
    LocalDate localDate = mapper.mapDocumentIssueDate(spsReferencedDocumentType);

    assertThat(localDate).isEqualTo(LocalDate.parse("2020-07-01"));
  }

  @Test
  void mapDocumentIssueDate_ReturnsNull_WhenNullIssueDateTimeSet() {
    spsReferencedDocumentType.setIssueDateTime(null);

    LocalDate localDate = mapper.mapDocumentIssueDate(spsReferencedDocumentType);

    assertThat(localDate).isNull();
  }

  @Test
  void isTypeCodeNonNull_ReturnsTrue_WhenDocumentCodeTypeWithValue() {
    boolean isTypeCodeNonNull = mapper.isTypeCodeNonNull(new DocumentCodeType().withValue("Some value"));

    assertThat(isTypeCodeNonNull).isTrue();
  }

  @Test
  void isTypeCodeNonNull_ReturnsFalse_WhenDocumentCodeTypeValueIsNull() {
    boolean isTypeCodeNonNull = mapper.isTypeCodeNonNull(new DocumentCodeType().withValue(null));

    assertThat(isTypeCodeNonNull).isFalse();
  }

  @Test
  void isTypeCodeNonNull_ReturnsFalse_WhenDocumentCodeTypeIsNull() {
    boolean isTypeCodeNonNull = mapper.isTypeCodeNonNull(null);

    assertThat(isTypeCodeNonNull).isFalse();
  }

  private SpsReferencedDocumentType getSpsReferencedDocumentType() throws Exception{
    SpsExchangedDocument spsExchangedDocument = JsonDeserializer.get(SpsExchangedDocument.class,
        "common/common/veterinaryinformation/common_ehc_veterinaryinformation_complete.json",
        objectMapper);
    return spsExchangedDocument.getReferenceSpsReferencedDocument().get(0);
  }
}
