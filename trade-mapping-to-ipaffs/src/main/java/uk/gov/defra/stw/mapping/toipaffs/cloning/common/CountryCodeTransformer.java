package uk.gov.defra.stw.mapping.toipaffs.cloning.common;

import org.springframework.stereotype.Component;

@Component
public class CountryCodeTransformer {

  public String convertCountryCode(String country) {
    return "XI".equals(country) ? "GB-NIR" : country;
  }
}
