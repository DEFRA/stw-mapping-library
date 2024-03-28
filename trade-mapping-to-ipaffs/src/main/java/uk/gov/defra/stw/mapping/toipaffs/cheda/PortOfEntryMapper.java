package uk.gov.defra.stw.mapping.toipaffs.cheda;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;

@Component
public class PortOfEntryMapper implements Mapper<SpsCertificate, String> {

  @Override
  public String map(SpsCertificate spsCertificate) {
    IDType unloadingBaseportLocationId = spsCertificate.getSpsConsignment()
        .getUnloadingBaseportSpsLocation().getId();
    if (unloadingBaseportLocationId.getSchemeID().equals("IPAFFS_POE")) {
      return unloadingBaseportLocationId.getValue();
    } else {
      return null;
    }
  }
}
