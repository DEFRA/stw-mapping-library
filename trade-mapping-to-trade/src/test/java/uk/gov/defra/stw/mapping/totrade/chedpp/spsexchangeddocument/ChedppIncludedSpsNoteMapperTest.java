package uk.gov.defra.stw.mapping.totrade.chedpp.spsexchangeddocument;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.totrade.common.spsexchangeddocument.IncludedSpsNoteMapper;
import uk.gov.defra.stw.mapping.totrade.common.spsexchangeddocument.SpsNoteTypeCertificateTypeMapper;
import uk.gov.defra.stw.mapping.totrade.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

@ExtendWith(MockitoExtension.class)
class ChedppIncludedSpsNoteMapperTest {

  private static final String CONTENT_VALUE_KEY = "#{contentValue}";
  private static final String NO = "NO";

  private Notification notification;
  private ChedppIncludedSpsNoteMapper chedppIncludedSpsNoteMapper;
  private ObjectMapper objectMapper;

  @Mock
  private IncludedSpsNoteMapper includedSpsNoteMapper;
  @Mock
  private ChedppIncludedSpsNoteFromPartOneMapper includedSpsNoteFromPartOneMapper;
  @Mock
  private ChedppSpsNoteTypeChildNotificationMapper spsNoteTypeChildNotificationMapper;
  @Mock
  private ChedppSpsNoteTypeRiskAssessmentMapper mockChedppSpsNoteTypeRiskAssessmentMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedChedppWithRiskAssessment.json");
    notification = objectMapper.readValue(notificationString, Notification.class);
    SpsNoteTypeCertificateTypeMapper spsNoteTypeCertificateTypeMapper = new SpsNoteTypeCertificateTypeMapper();
    chedppIncludedSpsNoteMapper = new ChedppIncludedSpsNoteMapper(
        includedSpsNoteFromPartOneMapper,
        spsNoteTypeCertificateTypeMapper,
        includedSpsNoteMapper,
        spsNoteTypeChildNotificationMapper, mockChedppSpsNoteTypeRiskAssessmentMapper);
  }

  @Test
  void map_ReturnsSpsNoteTypeList_WhenComplete() throws Exception {
    String spsNoteTypeCompleteString = ResourceUtil.readFileToString(
        "classpath:mapping/common/spsexchangeddocument/spsNoteTypeComplete.json");
    String spsNoteTypesFromPartOneString = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsexchangeddocument/spsNoteTypeFromPartOneComplete.json");
    String spsNoteTypeChildNotificationString = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsexchangeddocument/spsNoteTypeChildNotification.json")
        .replace(CONTENT_VALUE_KEY, NO);
    String spsNoteTypesFromRiskAssessmentString = ResourceUtil.readFileToString(
            "classpath:mapping/chedpp/spsexchangeddocument/spsNoteTypeRiskAssessment.json");

    List<SpsNoteType> spsNoteTypeComplete = objectMapper.readValue(spsNoteTypeCompleteString, new TypeReference<>() {});
    when(includedSpsNoteMapper.map(notification)).thenReturn(spsNoteTypeComplete);

    List<SpsNoteType> spsNoteTypesFromPartOne = objectMapper.readValue(spsNoteTypesFromPartOneString, new TypeReference<>() {});
    when(includedSpsNoteFromPartOneMapper.map(notification.getPartOne())).thenReturn(spsNoteTypesFromPartOne);

    SpsNoteType spsNoteTypeChildNotification = objectMapper.readValue(spsNoteTypeChildNotificationString, SpsNoteType.class);
    when(spsNoteTypeChildNotificationMapper.map(notification)).thenReturn(spsNoteTypeChildNotification);

    SpsNoteType spsNoteTypeRiskAssessment = objectMapper.readValue(spsNoteTypesFromRiskAssessmentString, SpsNoteType.class);
    when(mockChedppSpsNoteTypeRiskAssessmentMapper.map(notification)).thenReturn(spsNoteTypeRiskAssessment);

    List<SpsNoteType> spsNoteTypes = chedppIncludedSpsNoteMapper.map(notification);
    String actualSps = objectMapper.writeValueAsString(spsNoteTypes);
    String expectedSps = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsexchangeddocument/spsNoteTypeComplete.json");

    assertThat(actualSps).isEqualTo(expectedSps);
  }
}
