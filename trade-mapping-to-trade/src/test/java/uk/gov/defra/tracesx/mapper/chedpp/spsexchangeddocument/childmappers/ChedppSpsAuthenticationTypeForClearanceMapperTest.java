package uk.gov.defra.tracesx.mapper.chedpp.spsexchangeddocument.childmappers;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.mapper.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.trade.dto.SpsAuthenticationType;

class ChedppSpsAuthenticationTypeForClearanceMapperTest {

  private Notification notification;
  private ChedppSpsAuthenticationTypeForClearanceMapper mapper;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);

    mapper = new ChedppSpsAuthenticationTypeForClearanceMapper();
  }

  @Test
  void map_ReturnsSpsAuthenticationType_WhenComplete() throws JsonProcessingException {
    SpsAuthenticationType spsAuthenticationType = mapper.map(notification.getPartTwo());
    String actualSps = objectMapper.writeValueAsString(spsAuthenticationType);

    String expectedSps = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsexchangeddocument/spsAuthenticationTypeForClearanceComplete.json");

    assertThat(actualSps).isEqualTo(expectedSps);
  }

  @Test
  void map_ReturnsNull_WhenNullControlAuthority() {
    notification.getPartTwo().setControlAuthority(null);

    SpsAuthenticationType spsAuthenticationType = mapper.map(notification.getPartTwo());

    assertThat(spsAuthenticationType).isNull();
  }

  @Test
  void map_ReturnsNull_WhenNullOfficialVeterinarian() {
    notification.getPartTwo().getControlAuthority().setOfficialVeterinarian(null);

    SpsAuthenticationType spsAuthenticationType = mapper.map(notification.getPartTwo());

    assertThat(spsAuthenticationType).isNull();
  }
}
