package uk.gov.defra.stw.mapping.toipaffs.common;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;

@Component
public class ConsignedCountryMapper implements Mapper<SpsCertificate, String> {

  @Override
  public String map(SpsCertificate spsCertificate) {
    return spsCertificate.getSpsConsignment()
        .getExportSpsCountry()
        .getId()
        .getValue();
  }
}
