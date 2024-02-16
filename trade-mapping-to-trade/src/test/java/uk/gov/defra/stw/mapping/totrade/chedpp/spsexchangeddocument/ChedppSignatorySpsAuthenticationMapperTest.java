package uk.gov.defra.stw.mapping.totrade.chedpp.spsexchangeddocument;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.SpsAuthenticationType;
import uk.gov.defra.stw.mapping.totrade.chedpp.spsexchangeddocument.childmappers.ChedppSpsAuthenticationTypeForClearanceMapper;
import uk.gov.defra.stw.mapping.totrade.chedpp.spsexchangeddocument.childmappers.ChedppSpsAuthenticationTypeForInspectionMapper;
import uk.gov.defra.stw.mapping.totrade.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

@ExtendWith(MockitoExtension.class)
class ChedppSignatorySpsAuthenticationMapperTest {

  private static final String DIRECT_TRANSIT = "DIRECT_TRANSIT";
  private static final String DIRECT_TRANSIT_DESCRIPTION = "For transit to another country";

  private static final String CONTENT_TYPE_KEY = "#{contentType}";
  private static final String CONTENT_DESCRIPTION_KEY = "#{contentDescription}";

  @Mock
  private ChedppSpsAuthenticationTypeForInspectionMapper spsAuthenticationTypeForInspectionMapper;
  @Mock
  private ChedppSpsAuthenticationTypeForClearanceMapper spsAuthenticationTypeForClearanceMapper;

  private ObjectMapper objectMapper;
  private ChedppSignatorySpsAuthenticationMapper chedppSignatorySpsAuthenticationMapper;
  private Notification notification;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);

    String spsAuthenticationTypeForInspectionString = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsexchangeddocument/spsAuthenticationTypeForInspectionComplete.json")
        .replace(CONTENT_TYPE_KEY, DIRECT_TRANSIT)
        .replace(CONTENT_DESCRIPTION_KEY, DIRECT_TRANSIT_DESCRIPTION);
    SpsAuthenticationType authenticationTypeForInspection = objectMapper.readValue(spsAuthenticationTypeForInspectionString, SpsAuthenticationType.class);
    when(spsAuthenticationTypeForInspectionMapper.map(notification.getPartOne())).thenReturn(authenticationTypeForInspection);

    String spsAuthenticationTypeForClearanceCompleteString = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsexchangeddocument/spsAuthenticationTypeForClearanceComplete.json");
    SpsAuthenticationType authenticationTypeForClearance = objectMapper.readValue(spsAuthenticationTypeForClearanceCompleteString, SpsAuthenticationType.class);
    when(spsAuthenticationTypeForClearanceMapper.map(notification.getPartTwo())).thenReturn(authenticationTypeForClearance);

    chedppSignatorySpsAuthenticationMapper = new ChedppSignatorySpsAuthenticationMapper(
        spsAuthenticationTypeForInspectionMapper, spsAuthenticationTypeForClearanceMapper);
  }

  @Test
  void map_ReturnsSpsAuthenticationTypeList_WhenComplete() throws JsonProcessingException {
    List<SpsAuthenticationType> statusCode = chedppSignatorySpsAuthenticationMapper.map(notification);
    String actualSps = objectMapper.writeValueAsString(statusCode);

    String expectedSps = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsexchangeddocument/signatorySpsAuthenticationComplete.json");

    assertThat(actualSps).isEqualTo(expectedSps);
  }
}
