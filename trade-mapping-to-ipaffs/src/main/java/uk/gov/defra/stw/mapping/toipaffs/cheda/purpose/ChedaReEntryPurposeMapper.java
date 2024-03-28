package uk.gov.defra.stw.mapping.toipaffs.cheda.purpose;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.ForImportOrAdmissionEnum.HORSES_RE_ENTRY;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.RE_IMPORT;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;

@Component
public class ChedaReEntryPurposeMapper implements Mapper<SpsCertificate, Purpose> {

  @Override
  public Purpose map(SpsCertificate spsCertificate) {
    return Purpose.builder()
        .purposeGroup(RE_IMPORT)
        .forImportOrAdmission(HORSES_RE_ENTRY)
        .build();
  }
}
