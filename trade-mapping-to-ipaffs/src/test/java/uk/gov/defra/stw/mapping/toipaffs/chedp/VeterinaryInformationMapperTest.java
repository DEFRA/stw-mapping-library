package uk.gov.defra.stw.mapping.toipaffs.chedp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.OTHER;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.DocumentCodeType;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.SpsReferencedDocumentType;
import uk.gov.defra.stw.mapping.toipaffs.common.VeterinaryInformationMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.utils.VeterinaryInformationFieldMapper;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.tracesx.notificationschema.representation.VeterinaryInformation;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType;

@ExtendWith(MockitoExtension.class)
class VeterinaryInformationMapperTest {

  @Mock
  private VeterinaryInformationFieldMapper veterinaryInformationFieldMapper;
  private VeterinaryInformationMapper mapper;
  private SpsExchangedDocument spsExchangedDocument;

  @BeforeEach
  void setup() throws Exception {
    mapper = new VeterinaryInformationMapper(veterinaryInformationFieldMapper);

    spsExchangedDocument = setUpSpsExchangedDocument();
  }

  @Test
  void map_ReturnsVeterinaryInformation_WhenCompleteEhcExchangedDocument() throws Exception {
    when(veterinaryInformationFieldMapper.isTypeCodeNonNull(any(DocumentCodeType.class)))
        .thenReturn(true);

    when(veterinaryInformationFieldMapper.mapDocumentIssueDate(any(SpsReferencedDocumentType.class)))
        .thenReturn(LocalDate.parse("2020-07-01"));
    when(veterinaryInformationFieldMapper.mapDocumentType(any(SpsReferencedDocumentType.class)))
        .thenReturn(DocumentType.VETERINARY_HEALTH_CERTIFICATE);
    when(veterinaryInformationFieldMapper.mapDocumentReference(any(SpsReferencedDocumentType.class)))
        .thenReturn("9022780365");

    VeterinaryInformation expectedVeterinaryInformation = setUpExpectedVeterinaryInformation();

    VeterinaryInformation actualVeterinaryInformation = mapper.map(spsExchangedDocument);

    assertThat(actualVeterinaryInformation).isEqualTo(expectedVeterinaryInformation);
  }


  @Test
  void map_ReturnsVeterinaryInformationWithPlaceholder_WhenTypeCodeIsNull()
      throws Exception {
    when(veterinaryInformationFieldMapper.isTypeCodeNonNull(any(DocumentCodeType.class)))
        .thenReturn(false);

    when(veterinaryInformationFieldMapper.mapDocumentIssueDate(any(SpsReferencedDocumentType.class)))
        .thenReturn(LocalDate.parse("2020-07-01"));
    when(veterinaryInformationFieldMapper.mapDocumentType(any(SpsReferencedDocumentType.class)))
        .thenReturn(OTHER);
    when(veterinaryInformationFieldMapper.mapDocumentReference(any(SpsReferencedDocumentType.class)))
        .thenReturn("9022780365");


    VeterinaryInformation expectedVeterinaryInformation = setUpExpectedVeterinaryInformationWithPlaceholder();

    VeterinaryInformation actualVeterinaryInformation = mapper.map(spsExchangedDocument);

    assertThat(actualVeterinaryInformation).isEqualTo(expectedVeterinaryInformation);
  }

  @Test
  void map_ReturnsVeterinaryInformation_WhenTypeCodeIsNotVeterinaryHealthCertificate()
      throws Exception {
    spsExchangedDocument.getReferenceSpsReferencedDocument().get(0).getTypeCode().setValue("other");
    when(veterinaryInformationFieldMapper.isTypeCodeNonNull(any(DocumentCodeType.class)))
        .thenReturn(true);

    when(veterinaryInformationFieldMapper.mapDocumentIssueDate(any(SpsReferencedDocumentType.class)))
        .thenReturn(LocalDate.parse("2020-07-01"));
    when(veterinaryInformationFieldMapper.mapDocumentType(any(SpsReferencedDocumentType.class)))
        .thenReturn(OTHER);
    when(veterinaryInformationFieldMapper.mapDocumentReference(any(SpsReferencedDocumentType.class)))
        .thenReturn("9022780365");


    VeterinaryInformation expectedVeterinaryInformation = setUpExpectedVeterinaryInformationWithPlaceholder();

    VeterinaryInformation actualVeterinaryInformation = mapper.map(spsExchangedDocument);

    assertThat(actualVeterinaryInformation).isEqualTo(expectedVeterinaryInformation);
  }

  @Test
  void map_ReturnsVeterinaryInformation_WhenNullSpsReferencedDocumentTypeList()
      throws Exception {
    spsExchangedDocument.setReferenceSpsReferencedDocument(null);

    VeterinaryInformation expectedVeterinaryInformation = setUpExpectedVeterinaryInformationForPlaceholder();

    VeterinaryInformation actualVeterinaryInformation = mapper.map(spsExchangedDocument);

    assertThat(actualVeterinaryInformation).isEqualTo(expectedVeterinaryInformation);
  }

  @Test
  void map_ReturnsVeterinaryInformation_WhenSpsReferenceDocumentTypeListEmpty()
      throws Exception {
    spsExchangedDocument.setReferenceSpsReferencedDocument(List.of());

    VeterinaryInformation expectedVeterinaryInformation = setUpExpectedVeterinaryInformationForPlaceholder();

    VeterinaryInformation actualVeterinaryInformation = mapper.map(spsExchangedDocument);

    assertThat(actualVeterinaryInformation).isEqualTo(expectedVeterinaryInformation);
  }

  private SpsExchangedDocument setUpSpsExchangedDocument() throws Exception{
    return JsonDeserializer.get(
        "common/veterinaryinformation/common_ehc_veterinaryinformation_complete.json",
        SpsExchangedDocument.class
    );
  }

  private VeterinaryInformation setUpExpectedVeterinaryInformation() throws Exception {
    return JsonDeserializer.get(
        "common/veterinaryinformation/common_ipaffs_veterinaryinformation_complete.json",
        VeterinaryInformation.class
    );
  }

  private VeterinaryInformation setUpExpectedVeterinaryInformationWithPlaceholder() throws Exception {
    return JsonDeserializer.get(
        "common/veterinaryinformation/common_ipaffs_veterinaryinformation_complete_with_placeholder.json",
        VeterinaryInformation.class
    );
  }

  private VeterinaryInformation setUpExpectedVeterinaryInformationForPlaceholder() throws Exception {
    return JsonDeserializer.get(
        "common/veterinaryinformation/common_ipaffs_veterinaryinformation_for_placeholder.json",
        VeterinaryInformation.class
    );
  }
}
