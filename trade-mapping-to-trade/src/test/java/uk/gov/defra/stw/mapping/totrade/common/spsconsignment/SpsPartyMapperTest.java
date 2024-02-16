package uk.gov.defra.stw.mapping.totrade.common.spsconsignment;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.SpsPartyType;
import uk.gov.defra.stw.mapping.totrade.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

class SpsPartyMapperTest {

  private Notification notification;
  private SpsPartyMapper mapper;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);
    mapper = new SpsPartyMapper();
  }

  @Test
  void map_ReturnsSpsPartyType_WhenAllFieldsComplete() throws Exception {
    SpsPartyType spsPartyType = mapper.map(notification.getPartOne().getConsignor());
    String actualSps = objectMapper.writeValueAsString(spsPartyType);
    String expectedSps = ResourceUtil.readFileToString("classpath:mapping/common/spsconsignment/spsPartyComplete.json");
    assertThat(actualSps).isEqualTo(expectedSps);
  }

  @Test
  void map_ReturnsNull_WhenEconomicOperatorIsNull() {
    notification.getPartOne().setConsignor(null);
    SpsPartyType spsPartyType = mapper.map(notification.getPartOne().getConsignor());
    assertThat(spsPartyType).isNull();
  }

  @Test
  void map_ReturnsNullSpsSpecifiedAddress_WhenNullEconomicOperatorAddress() throws Exception {
    notification.getPartOne().getConsignor().setAddress(null);
    SpsPartyType spsPartyType = mapper.map(notification.getPartOne().getConsignor());
    String actualSps = objectMapper.writeValueAsString(spsPartyType);
    String expectedSps = ResourceUtil.readFileToString("classpath:mapping/common/spsconsignment/spsPartyNullAddress.json");
    assertThat(actualSps).isEqualTo(expectedSps);
  }

  @Test
  void map_ReturnsSpsPartyTypeWithMissingPostCode_WhenNullEconomicOperatorAddressPostCode() throws Exception {
    notification.getPartOne().getConsignor().getAddress().setPostalZipCode(null);
    SpsPartyType spsPartyType = mapper.map(notification.getPartOne().getConsignor());
    String actualSps = objectMapper.writeValueAsString(spsPartyType);
    String expectedSps = ResourceUtil.readFileToString("classpath:mapping/common/spsconsignment/spsPartyNullPostCode.json");
    assertThat(actualSps).isEqualTo(expectedSps);
  }

  @Test
  void map_ReturnsSpsPartyTypeWithMissingAddressLine1_WhenNullEconomicOperatorAddressLine1() throws Exception {
    notification.getPartOne().getConsignor().getAddress().setAddressLine1(null);
    SpsPartyType spsPartyType = mapper.map(notification.getPartOne().getConsignor());
    String actualSps = objectMapper.writeValueAsString(spsPartyType);
    String expectedSps = ResourceUtil.readFileToString("classpath:mapping/common/spsconsignment/spsPartyNullAddressLineOne.json");
    assertThat(actualSps).isEqualTo(expectedSps);
  }

  @Test
  void map_ReturnsSpsPartyTypeWithMissingAddressLine1_WhenNullEconomicOperatorAddressLine2() throws Exception {
    notification.getPartOne().getConsignor().getAddress().setAddressLine2(null);
    SpsPartyType spsPartyType = mapper.map(notification.getPartOne().getConsignor());
    String actualSps = objectMapper.writeValueAsString(spsPartyType);
    String expectedSps = ResourceUtil.readFileToString("classpath:mapping/common/spsconsignment/spsPartyNullAddressLineTwo.json");
    assertThat(actualSps).isEqualTo(expectedSps);
  }

  @Test
  void map_ReturnsSpsPartyTypeWithMissingAddressLine1_WhenNullEconomicOperatorAddressLine3() throws Exception {
    notification.getPartOne().getConsignor().getAddress().setAddressLine3(null);
    SpsPartyType spsPartyType = mapper.map(notification.getPartOne().getConsignor());
    String actualSps = objectMapper.writeValueAsString(spsPartyType);
    String expectedSps = ResourceUtil.readFileToString("classpath:mapping/common/spsconsignment/spsPartyNullAddressLineThree.json");
    assertThat(actualSps).isEqualTo(expectedSps);
  }

  @Test
  void map_ReturnsSpsPartyTypeWithMissingCity_WhenNullEconomicOperatorCity() throws Exception {
    notification.getPartOne().getConsignor().getAddress().setCity(null);
    SpsPartyType spsPartyType = mapper.map(notification.getPartOne().getConsignor());
    String actualSps = objectMapper.writeValueAsString(spsPartyType);
    String expectedSps = ResourceUtil.readFileToString("classpath:mapping/common/spsconsignment/spsPartyNullAddressCity.json");
    assertThat(actualSps).isEqualTo(expectedSps);
  }

  @Test
  void map_ReturnsSpsPartyTypeWithMissingCountryCode_WhenNullEconomicOperatorCountryISOCode() throws Exception {
    notification.getPartOne().getConsignor().getAddress().setCountryISOCode(null);
    SpsPartyType spsPartyType = mapper.map(notification.getPartOne().getConsignor());
    String actualSps = objectMapper.writeValueAsString(spsPartyType);
    String expectedSps = ResourceUtil.readFileToString("classpath:mapping/common/spsconsignment/spsPartyNullAddressCountryCode.json");
    assertThat(actualSps).isEqualTo(expectedSps);
  }
}
