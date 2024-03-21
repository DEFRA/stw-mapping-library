package uk.gov.defra.stw.mapping.toipaffs.cheda.purpose;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;

@Component
public class PortOfExitMapper implements Mapper<SpsCertificate, String> {

  @Override
  public String map(SpsCertificate spsCertificate) {
    return ChedaPurposeUtils.getPortOfExit(spsCertificate).orElse(null);
  }
}
