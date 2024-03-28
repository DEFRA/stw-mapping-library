package uk.gov.defra.stw.mapping.toipaffs.cheda;

import static uk.gov.defra.stw.mapping.toipaffs.cheda.purpose.ChedaPurposeUtils.APHA_BCP_ID;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;

@Component
public class ChedaPointOfEntryMapper implements Mapper<SpsCertificate, String> {

  @Override
  public String map(SpsCertificate spsCertificate) {
    IDType unloadingBaseportLocationId = spsCertificate.getSpsConsignment()
        .getUnloadingBaseportSpsLocation().getId();
    if (unloadingBaseportLocationId.getSchemeID().equals("un_locode")) {
      return unloadingBaseportLocationId.getValue();
    } else {
      return APHA_BCP_ID;
    }
  }
}
