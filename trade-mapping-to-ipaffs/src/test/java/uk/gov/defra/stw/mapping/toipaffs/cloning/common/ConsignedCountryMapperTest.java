package uk.gov.defra.stw.mapping.toipaffs.cloning.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.common.common.CountryOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;

@ExtendWith(MockitoExtension.class)
class ConsignedCountryMapperTest {
  @Mock
  private CountryCodeTransformer countryCodeTransformer;
  @Mock
  private CountryOfOriginMapper countryOfOriginMapper;

  private ConsignedCountryMapper consignedCountryMapper;
  private SpsCertificate spsCertificate;

  @BeforeEach
  void setup() throws JsonProcessingException {
    consignedCountryMapper = new ConsignedCountryMapper(countryOfOriginMapper, countryCodeTransformer);

    ObjectMapper objectMapper = TestUtils.initObjectMapper();

    spsCertificate = JsonDeserializer
        .get(SpsCertificate.class, "cloning/chedpp/chedpp_ehc_complete.json", objectMapper);
  }

  @Test
  void map_ReturnsConsignedCountry_WhenExportSpsCountryIsNotNull()
      throws NotificationMapperException {
    when(countryCodeTransformer.convertCountryCode("NL")).thenReturn("NL");

    String consignedCountry = consignedCountryMapper.map(spsCertificate);

    assertThat(consignedCountry).isEqualTo("NL");
  }

  @Test
  void map_ReturnsCountryOfOrigin_WhenExportSpsCountryIsNull()
      throws NotificationMapperException {
    spsCertificate.getSpsConsignment().getExportSpsCountry().getId().setValue(null);
    when(countryOfOriginMapper.map(spsCertificate)).thenReturn("NL");

    String consignedCountry = consignedCountryMapper.map(spsCertificate);

    assertThat(consignedCountry).isEqualTo("NL");
  }

  @Test
  void map_ReturnsCountryOfOrigin_WhenExportSpsCountryIsEmpty()
      throws NotificationMapperException {
    spsCertificate.getSpsConsignment().getExportSpsCountry().getId().setValue("");
    when(countryOfOriginMapper.map(spsCertificate)).thenReturn("NL");

    String consignedCountry = consignedCountryMapper.map(spsCertificate);

    assertThat(consignedCountry).isEqualTo("NL");
  }
}
