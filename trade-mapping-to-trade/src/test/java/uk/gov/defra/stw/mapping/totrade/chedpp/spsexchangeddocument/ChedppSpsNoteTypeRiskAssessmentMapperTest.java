package uk.gov.defra.stw.mapping.totrade.chedpp.spsexchangeddocument;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.totrade.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

class ChedppSpsNoteTypeRiskAssessmentMapperTest {

  private static final String RISK_ASSESSMENT = "RISK_ASSESSMENT";

  private static final String EXPECTED_RISK_ASSESSMENT_JSON = "{\"commodityResults\":[{\"hmiDecision\":\"NOTREQUIRED\",\"phsiDecision\":\"REQUIRED\",\"phsiClassification\":\"Mandatory\",\"uniqueId\":\"f54bb9ff-1e89-4d66-9d1f-d8201f1f2382\",\"eppoCode\":\"ABICH\"},{\"hmiDecision\":\"NOTREQUIRED\",\"phsiDecision\":\"REQUIRED\",\"phsiClassification\":\"Mandatory\",\"uniqueId\":\"fd8cf75f-7ff3-43a1-9ec1-69013f11346f\",\"eppoCode\":\"2DTRE\"},{\"hmiDecision\":\"NOTREQUIRED\",\"phsiDecision\":\"REQUIRED\",\"phsiClassification\":\"Mandatory\",\"uniqueId\":\"2e58e413-d87f-46b1-b01c-754bcc104644\",\"eppoCode\":\"1NARG\"}],\"assessmentDateTime\":\"2022-05-25T11:09:30.572139\"}";

  private static final String EXPECTED_RISK_ASSESSMENT_ARTICLE_72_MIX = "{\"commodityResults\":[{\"hmiDecision\":\"NOTREQUIRED\",\"phsiDecision\":\"REQUIRED\",\"phsiClassification\":\"Mandatory\",\"uniqueId\":\"f54bb9ff-1e89-4d66-9d1f-d8201f1f2382\",\"eppoCode\":\"ABICH\"},{\"hmiDecision\":\"NOTREQUIRED\",\"phsiDecision\":\"REQUIRED\",\"phsiClassification\":\"Mandatory\",\"uniqueId\":\"fd8cf75f-7ff3-43a1-9ec1-69013f11346f\",\"eppoCode\":\"2DTRE\"}],\"assessmentDateTime\":\"2022-05-25T11:09:30.572139\"}";

  private ObjectMapper objectMapper;

  private ChedppSpsNoteTypeRiskAssessmentMapper spsNoteTypeRiskAssessmentMapper;

  public ChedppSpsNoteTypeRiskAssessmentMapperTest() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    spsNoteTypeRiskAssessmentMapper = new ChedppSpsNoteTypeRiskAssessmentMapper();
  }

  @Test
  void map_ReturnsSpsNoteType_WhenNotificationHasRiskAssessment()
      throws JsonProcessingException {
    String notificationString = ResourceUtil.readFileToString(
        "classpath:validatedChedppWithRiskAssessment.json");
    Notification notification = objectMapper.readValue(notificationString, Notification.class);

    SpsNoteType actualSpsNoteType = spsNoteTypeRiskAssessmentMapper.map(notification);
    assertThat(actualSpsNoteType.getSubjectCode().getValue()).isEqualTo(RISK_ASSESSMENT);
    assertThat(actualSpsNoteType.getContent().get(0).getValue()).isEqualTo(
        EXPECTED_RISK_ASSESSMENT_JSON);
  }

  @Test
  void map_ReturnsNoteWithoutArticle72CommodityResults_WhenNotificationContainsArticle72Commodities()
      throws JsonProcessingException {
    String notificationString = ResourceUtil.readFileToString(
        "classpath:validatedChedppWithRiskAssessmentAndArticle72Mix.json");
    Notification notification = objectMapper.readValue(notificationString, Notification.class);

    SpsNoteType actualSpsNoteType = spsNoteTypeRiskAssessmentMapper.map(notification);
    assertThat(actualSpsNoteType.getSubjectCode().getValue()).isEqualTo(RISK_ASSESSMENT);
    assertThat(actualSpsNoteType.getContent().get(0).getValue()).isEqualTo(
        EXPECTED_RISK_ASSESSMENT_ARTICLE_72_MIX);
  }

  @Test
  void map_ReturnsNoteWithArticle72CommodityResults_WhenNotificationContainsArticle72Commodities_AndNonEUCountry()
      throws JsonProcessingException {
    String notificationString = ResourceUtil.readFileToString(
        "classpath:validatedChedppWithRiskAssessmentArticle72CommoditiesNonEUCountry.json");
    Notification notification = objectMapper.readValue(notificationString, Notification.class);

    SpsNoteType actualSpsNoteType = spsNoteTypeRiskAssessmentMapper.map(notification);
    assertThat(actualSpsNoteType.getSubjectCode().getValue()).isEqualTo(RISK_ASSESSMENT);
    assertThat(actualSpsNoteType.getContent().get(0).getValue()).isEqualTo(
        EXPECTED_RISK_ASSESSMENT_JSON);
  }

  @Test
  void map_ReturnsRiskAssessment_WhenPartOneIsNull()
      throws JsonProcessingException {
    String notificationString = ResourceUtil.readFileToString(
        "classpath:validatedChedppWithRiskAssessment.json");
    Notification notification = objectMapper.readValue(notificationString, Notification.class);
    notification.setPartOne(null);

    SpsNoteType actualSpsNoteType = spsNoteTypeRiskAssessmentMapper.map(notification);
    assertThat(actualSpsNoteType.getSubjectCode().getValue()).isEqualTo(RISK_ASSESSMENT);
    assertThat(actualSpsNoteType.getContent().get(0).getValue()).isEqualTo(EXPECTED_RISK_ASSESSMENT_JSON);
  }

  @Test
  void map_ReturnsRiskAssessment_WhenCommoditiesIsNull()
      throws JsonProcessingException {
    String notificationString = ResourceUtil.readFileToString(
        "classpath:validatedChedppWithRiskAssessment.json");
    Notification notification = objectMapper.readValue(notificationString, Notification.class);
    notification.getPartOne().setCommodities(null);

    SpsNoteType actualSpsNoteType = spsNoteTypeRiskAssessmentMapper.map(notification);
    assertThat(actualSpsNoteType.getSubjectCode().getValue()).isEqualTo(RISK_ASSESSMENT);
    assertThat(actualSpsNoteType.getContent().get(0).getValue()).isEqualTo(EXPECTED_RISK_ASSESSMENT_JSON);
  }

  @Test
  void map_ReturnsNull_WhenArticle72AndComplementParameterSetsIsNull()
      throws JsonProcessingException {
    String notificationString = ResourceUtil.readFileToString(
        "classpath:validatedChedppWithRiskAssessmentAndArticle72Mix.json");
    Notification notification = objectMapper.readValue(notificationString, Notification.class);
    notification.getPartOne().getCommodities().setComplementParameterSet(null);

    SpsNoteType actualSpsNoteType = spsNoteTypeRiskAssessmentMapper.map(notification);
    assertThat(actualSpsNoteType.getSubjectCode().getValue()).isEqualTo(RISK_ASSESSMENT);
    assertThat(actualSpsNoteType.getContent().get(0).getValue()).isEqualTo("null");
  }

  @Test
  void map_ReturnsNull_WhenArticle72AndRiskAssessmentCommodityResultsIsNull()
      throws JsonProcessingException {
    String notificationString = ResourceUtil.readFileToString(
        "classpath:validatedChedppWithRiskAssessmentAndArticle72Mix.json");
    Notification notification = objectMapper.readValue(notificationString, Notification.class);
    notification.getRiskAssessment().setCommodityResults(null);

    SpsNoteType actualSpsNoteType = spsNoteTypeRiskAssessmentMapper.map(notification);
    assertThat(actualSpsNoteType.getSubjectCode().getValue()).isEqualTo(RISK_ASSESSMENT);
    assertThat(actualSpsNoteType.getContent().get(0).getValue()).isEqualTo("null");
  }
}
