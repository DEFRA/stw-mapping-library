package uk.gov.defra.stw.mapping.toipaffs.cheda.purpose;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.TRANSHIPMENT_TO;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;

@Component
public class ChedaTranshipmentPurposeMapper implements Mapper<SpsCertificate, Purpose> {

  @Override
  public Purpose map(SpsCertificate spsCertificate) {
    return Purpose.builder()
        .purposeGroup(TRANSHIPMENT_TO)
        .thirdCountryTranshipment(ChedaPurposeUtils.getDestinationCountry(spsCertificate))
        .finalBIP(ChedaPurposeUtils.getExitBcp(spsCertificate))
        .build();
  }
}
