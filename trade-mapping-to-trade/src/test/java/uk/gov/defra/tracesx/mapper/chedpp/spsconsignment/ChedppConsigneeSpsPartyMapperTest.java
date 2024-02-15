package uk.gov.defra.tracesx.mapper.chedpp.spsconsignment;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.mapper.common.spsconsignment.SpsPartyMapper;
import uk.gov.defra.tracesx.mapper.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.trade.dto.SpsPartyType;

class ChedppConsigneeSpsPartyMapperTest {

  private Notification notification;
  private ChedppConsigneeSpsPartyMapper mapper;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);
    mapper = new ChedppConsigneeSpsPartyMapper(new SpsPartyMapper());
  }

  @Test
  void map_ReturnsConsigneeSpsPartyType_WhenAllFieldsComplete() throws Exception {
    SpsPartyType spsPartyType = mapper.map(notification.getPartOne().getConsignee());
    String actualSps = objectMapper.writeValueAsString(spsPartyType);
    String expectedSps = ResourceUtil.readFileToString("classpath:mapping/chedpp/spsconsignment/consigneeSpsPartyComplete.json");
    assertThat(actualSps).isEqualTo(expectedSps);
  }
}
