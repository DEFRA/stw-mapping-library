package uk.gov.defra.stw.mapping.toipaffs.chedpp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.AIR_WAYBILL;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType.PHYTOSANITARY_CERTIFICATE;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.AttachmentBinaryObject;
import uk.gov.defra.stw.mapping.dto.DocumentCodeType;
import uk.gov.defra.stw.mapping.dto.SpsReferencedDocumentType;
import uk.gov.defra.stw.mapping.toipaffs.common.utils.VeterinaryInformationFieldMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.tracesx.notificationschema.representation.VeterinaryInformation;

@ExtendWith(MockitoExtension.class)
class ChedppVeterinaryInformationMapperTest {

  @Mock
  private VeterinaryInformationFieldMapper veterinaryInformationFieldMapper;
  private ChedppVeterinaryInformationMapper mapper;
  private ObjectMapper objectMapper;
  private List<SpsReferencedDocumentType> spsReferencedDocumentTypes;

  @BeforeEach
  void setup() {
    mapper = new ChedppVeterinaryInformationMapper(veterinaryInformationFieldMapper);
    objectMapper = new ObjectMapper();
  }

  @Test
  void map_ReturnsVeterinaryInformation_WhenComplete() throws Exception {
    spsReferencedDocumentTypes = setUpSpsReferenceDocumentTypes();

    when(veterinaryInformationFieldMapper.isTypeCodeNonNull(any(DocumentCodeType.class)))
        .thenReturn(true);

    when(veterinaryInformationFieldMapper.mapDocumentIssueDate(any(SpsReferencedDocumentType.class)))
        .thenReturn(LocalDate.parse("2020-07-01"))
        .thenReturn(LocalDate.parse("2020-07-02"));
    when(veterinaryInformationFieldMapper.mapDocumentType(any(SpsReferencedDocumentType.class)))
        .thenReturn(AIR_WAYBILL)
        .thenReturn(PHYTOSANITARY_CERTIFICATE);
    when(veterinaryInformationFieldMapper.mapDocumentReference(any(SpsReferencedDocumentType.class)))
        .thenReturn("9022780365")
        .thenReturn("9022780366");

    VeterinaryInformation expectedVeterinaryInformation = setUpExpectedVeterinaryInformation();

    VeterinaryInformation actualVeterinaryInformation = mapper.map(spsReferencedDocumentTypes);

    assertThat(actualVeterinaryInformation).isEqualTo(expectedVeterinaryInformation);
  }

  @Test
  void map_ReturnsVeterinaryInformation_WhenNullSpsReferencedDocumentTypeList()
      throws NotificationMapperException {
    VeterinaryInformation expectedVeterinaryInformation = VeterinaryInformation.builder().build();
    VeterinaryInformation actualVeterinaryInformation = mapper.map(null);

    assertThat(actualVeterinaryInformation).isEqualTo(expectedVeterinaryInformation);
  }

  @Test
  void map_ReturnsVeterinaryInformation_WhenSpsReferenceDocumentTypeListEmpty()
      throws NotificationMapperException{
    VeterinaryInformation expectedVeterinaryInformation = VeterinaryInformation.builder().build();
    List<SpsReferencedDocumentType> emptyDocumentTypeList = List.of();
    VeterinaryInformation actualVeterinaryInformation = mapper.map(emptyDocumentTypeList);

    assertThat(actualVeterinaryInformation).isEqualTo(expectedVeterinaryInformation);
  }

  @Test
  void map_ThrowsNotificationMapperException_WhenEPhytoDocumentTypeIsNull()
      throws Exception {
    spsReferencedDocumentTypes = setUpSpsReferenceDocumentTypes();
    spsReferencedDocumentTypes.get(1).setTypeCode(null);

    assertThrows(NotificationMapperException.class, () -> mapper.map(spsReferencedDocumentTypes));
  }

  @Test
  void map_ThrowsNotificationMapperException_WhenEPhytoDocumentTypeValueIsNull()
      throws Exception {
    spsReferencedDocumentTypes = setUpSpsReferenceDocumentTypes();
    spsReferencedDocumentTypes.get(1).setTypeCode(new DocumentCodeType());

    assertThrows(NotificationMapperException.class, () -> mapper.map(spsReferencedDocumentTypes));
  }

  @Test
  void map_ThrowsNotificationMapperException_WhenEphytoWithNoDocumentAttachment()
      throws Exception {
    spsReferencedDocumentTypes = setUpSpsReferenceDocumentTypes();
    spsReferencedDocumentTypes.get(1).setAttachmentBinaryObject(null);

    assertThrows(NotificationMapperException.class, () -> mapper.map(spsReferencedDocumentTypes));
  }

  @Test
  void map_ThrowsNotificationMapperException_WhenEphytoWithEmptyDocumentAttachment()
      throws Exception {
    spsReferencedDocumentTypes = setUpSpsReferenceDocumentTypes();
    List<AttachmentBinaryObject> emptyAttachmentBinaryObject = List.of();
    spsReferencedDocumentTypes.get(1).setAttachmentBinaryObject(emptyAttachmentBinaryObject);

    assertThrows(NotificationMapperException.class, () -> mapper.map(spsReferencedDocumentTypes));
  }

  private VeterinaryInformation setUpExpectedVeterinaryInformation() throws Exception {
    return JsonDeserializer.get(
        "chedpp/partone/veterinaryinformation/chedpp_ipaffs_veterinaryinformation_complete.json",
        VeterinaryInformation.class
    );
  }

  private List<SpsReferencedDocumentType> setUpSpsReferenceDocumentTypes() throws Exception{
    return JsonDeserializer.get(
        "chedpp/partone/veterinaryinformation/chedpp_ehc_veterinaryinformation_complete.json",
        objectMapper.getTypeFactory()
            .constructCollectionType(List.class, SpsReferencedDocumentType.class)
    );
  }
}
