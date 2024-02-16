package uk.gov.defra.stw.mapping.toipaffs.enotification.chedp;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.RE_IMPORT;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;

@Component
public class ReEntryPurposeMapper implements PurposeMapper {

  @Override
  public Purpose map(SpsCertificate spsCertificate) {
    return Purpose.builder()
        .purposeGroup(RE_IMPORT)
        .build();
  }
}
