package uk.gov.defra.stw.mapping.totrade.chedpp.spsexchangeddocument;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.SpsPartyType;
import uk.gov.defra.stw.mapping.totrade.common.spsexchangeddocument.IssuerSpsPartyMapper;
import uk.gov.defra.stw.mapping.totrade.common.utils.SpsTypeConverter;
import uk.gov.defra.stw.mapping.totrade.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.Party;

class ChedppIssuerSpsPartyMapperTest {

  private ChedppIssuerSpsPartyMapper issuerSpsPartyMapper;
  private ObjectMapper objectMapper;
  private Party personResponsible;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    Notification notification = objectMapper.readValue(notificationString, Notification.class);
    issuerSpsPartyMapper = new ChedppIssuerSpsPartyMapper(new IssuerSpsPartyMapper());
    personResponsible = notification.getPartOne().getPersonResponsible();
  }

  @Test
  void map_ReturnsSpsPartyType_WhenComplete() throws JsonProcessingException {
    SpsPartyType actualSps = issuerSpsPartyMapper.map(personResponsible);

    String expectedSpsString = ResourceUtil.readFileToString(
        "classpath:mapping/common/spsexchangeddocument/issuerSpsPartyComplete.json");
    SpsPartyType expectedSps = objectMapper.readValue(expectedSpsString, SpsPartyType.class);
    expectedSps.setName(SpsTypeConverter.getTextType(personResponsible.getCompanyId()));

    assertThat(actualSps).isEqualTo(expectedSps);
  }

  @Test
  void map_ReturnsSpsPartyTypeWithNullAsName_WhenIndividualAccount() {
    personResponsible.setCompanyId(null);
    SpsPartyType partyType = issuerSpsPartyMapper.map(personResponsible);
    assertThat(partyType.getName()).isNull();
  }

  @Test
  void map_ReturnsSpsPartyTypeWithCompanyIDAsName_WhenBusinessAccount() {
    SpsPartyType partyType = issuerSpsPartyMapper.map(personResponsible);
    assertThat(partyType.getName().getValue()).isEqualTo(personResponsible.getCompanyId());
  }
}
