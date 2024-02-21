package uk.gov.defra.stw.mapping.totrade.chedpp.spsconsignment;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.SpsPartyType;
import uk.gov.defra.stw.mapping.totrade.common.spsconsignment.SpsPartyMapper;
import uk.gov.defra.stw.mapping.totrade.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

class ChedppConsignorSpsPartyMapperTest {

  private Notification notification;
  private ChedppConsignorSpsPartyMapper mapper;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);
    mapper = new ChedppConsignorSpsPartyMapper(new SpsPartyMapper());
  }

  @Test
  void map_ReturnsConsignorSpsPartyType_WhenAllFieldsComplete() throws Exception {
    SpsPartyType spsPartyType = mapper.map(notification.getPartOne().getConsignor());
    String actualSps = objectMapper.writeValueAsString(spsPartyType);
    String expectedSps = ResourceUtil.readFileToString("classpath:mapping/chedpp/spsconsignment/consignorSpsPartyComplete.json");
    assertThat(actualSps).isEqualTo(expectedSps);
  }
}
