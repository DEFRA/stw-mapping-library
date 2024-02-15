package uk.gov.defra.tracesx.common.common;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.testutils.JsonDeserializer;
import uk.gov.defra.tracesx.testutils.TestUtils;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

class CountryOfOriginMapperTest {

  private CountryOfOriginMapper countryOfOriginMapper;
  private SpsCertificate spsCertificate;

  @BeforeEach
  void setup() throws JsonProcessingException {
    countryOfOriginMapper = new CountryOfOriginMapper();
    ObjectMapper objectMapper = TestUtils.initObjectMapper();

    spsCertificate = JsonDeserializer
        .get(SpsCertificate.class, "cloning/chedpp/chedpp_ehc_complete.json", objectMapper);
  }

  @Test
  void map_ReturnsCountryOfOrigin_WhenCompleteSpsCertificate()
      throws NotificationMapperException {
    String country = countryOfOriginMapper.map(spsCertificate);
    assertThat(country).isEqualTo("NL");
  }
}
