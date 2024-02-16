package uk.gov.defra.stw.mapping.toipaffs.common;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;

class CountryOfOriginMapperTest {

  private CountryOfOriginMapper countryOfOriginMapper;
  private SpsCertificate spsCertificate;

  @BeforeEach
  void setup() throws JsonProcessingException {
    countryOfOriginMapper = new CountryOfOriginMapper();
    ObjectMapper objectMapper = TestUtils.initObjectMapper();

    spsCertificate = JsonDeserializer
        .get(SpsCertificate.class, "chedpp/chedpp_ehc_complete.json", objectMapper);
  }

  @Test
  void map_ReturnsCountryOfOrigin_WhenCompleteSpsCertificate()
      throws NotificationMapperException {
    String country = countryOfOriginMapper.map(spsCertificate);
    assertThat(country).isEqualTo("NL");
  }
}
