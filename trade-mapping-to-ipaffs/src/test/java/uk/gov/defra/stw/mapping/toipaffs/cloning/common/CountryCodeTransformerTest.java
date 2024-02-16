package uk.gov.defra.stw.mapping.toipaffs.cloning.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CountryCodeTransformerTest {

  private CountryCodeTransformer countryCodeTransformer;

  @BeforeEach
  void setup() {
    countryCodeTransformer = new CountryCodeTransformer();
  }

  @Test
  void convertCountryCode_ReturnsUnalteredCountryCode_WhenNotAnXICountryCode() {
    String countryCode = countryCodeTransformer.convertCountryCode("NL");
    assertThat(countryCode).isEqualTo("NL");
  }

  @Test
  void convertCountryCode_ReturnsGBNIR_WhenAnXICountryCode() {
    String countryCode = countryCodeTransformer.convertCountryCode("XI");
    assertThat(countryCode).isEqualTo("GB-NIR");
  }

  @Test
  void convertCountryCode_ReturnsNull_WhenNullCountryCode() {
    String countryCode = countryCodeTransformer.convertCountryCode(null);
    assertThat(countryCode).isNull();
  }
}
