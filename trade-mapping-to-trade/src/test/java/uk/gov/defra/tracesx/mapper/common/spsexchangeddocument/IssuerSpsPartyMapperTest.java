package uk.gov.defra.tracesx.mapper.common.spsexchangeddocument;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.mapper.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.trade.dto.SpsPartyType;

class IssuerSpsPartyMapperTest {
  private Notification notification;
  private IssuerSpsPartyMapper issuerSpsPartyMapper;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);
    issuerSpsPartyMapper = new IssuerSpsPartyMapper();
  }

  @Test
  void map_ReturnsNull_WhenPartyIsNull() {
    notification.getPartOne().setPersonResponsible(null);
    SpsPartyType partyType = issuerSpsPartyMapper.map(notification.getPartOne().getPersonResponsible());

    assertThat(partyType).isNull();
  }

  @Test
  void map_ReturnsStatusCode_WhenValidParty() throws JsonProcessingException {
    SpsPartyType partyType = issuerSpsPartyMapper.map(notification.getPartOne().getPersonResponsible());
    String actualSps = objectMapper.writeValueAsString(partyType);

    String expectedSps = ResourceUtil.readFileToString(
        "classpath:mapping/common/spsexchangeddocument/issuerSpsPartyComplete.json");
    assertThat(actualSps).isEqualTo(expectedSps);
  }
}
