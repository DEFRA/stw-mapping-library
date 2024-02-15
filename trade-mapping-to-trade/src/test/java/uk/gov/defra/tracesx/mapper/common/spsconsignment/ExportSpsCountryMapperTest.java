package uk.gov.defra.tracesx.mapper.common.spsconsignment;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.mapper.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.trade.dto.SpsCountryType;

class ExportSpsCountryMapperTest {

  private Notification notification;
  private ExportSpsCountryMapper mapper;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);
    mapper = new ExportSpsCountryMapper();
  }

  @Test
  void map_ReturnsSpsCountryType_WhenAllFieldsComplete() throws Exception {
    SpsCountryType spsCountryType = mapper.map(notification.getPartOne().getCommodities());
    String actualSpsCountryType = objectMapper.writeValueAsString(spsCountryType);
    String expectedSpsCountryType = ResourceUtil.readFileToString(
        "classpath:mapping/common/spsconsignment/exportSpsCountryTypeComplete.json");
    assertThat(actualSpsCountryType).isEqualTo(expectedSpsCountryType);
  }

  @Test
  void map_ReturnsSpsCountryType_WhenUkRegion() throws Exception {
    notification.getPartOne().getCommodities().setConsignedCountry("WA");
    SpsCountryType spsCountryType = mapper.map(notification.getPartOne().getCommodities());
    String actualSpsCountryType = objectMapper.writeValueAsString(spsCountryType);
    String expectedSpsCountryType = ResourceUtil.readFileToString(
        "classpath:mapping/common/spsconsignment/exportSpsCountryTypeUkRegion.json");
    assertThat(actualSpsCountryType).isEqualTo(expectedSpsCountryType);
  }
}
