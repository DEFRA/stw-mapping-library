package uk.gov.defra.stw.mapping.toipaffs.cloning.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.CountryOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;

@Component
public class ConsignedCountryMapper implements Mapper<SpsCertificate, String> {

  private final CountryOfOriginMapper countryOfOriginMapper;
  private final CountryCodeTransformer countryCodeTransformer;

  @Autowired
  public ConsignedCountryMapper(
      CountryOfOriginMapper countryOfOriginMapper,
      CountryCodeTransformer countryCodeTransformer) {
    this.countryOfOriginMapper = countryOfOriginMapper;
    this.countryCodeTransformer = countryCodeTransformer;
  }

  @Override
  public String map(SpsCertificate spsCertificate) throws NotificationMapperException {
    String consignedCountry = spsCertificate
        .getSpsConsignment().getExportSpsCountry().getId().getValue();

    return StringUtils.isEmpty(consignedCountry) ? countryOfOriginMapper.map(spsCertificate)
        : countryCodeTransformer.convertCountryCode(consignedCountry);
  }
}
