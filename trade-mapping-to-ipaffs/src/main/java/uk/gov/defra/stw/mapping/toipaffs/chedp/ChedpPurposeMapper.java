package uk.gov.defra.stw.mapping.toipaffs.chedp;

import static uk.gov.defra.stw.mapping.toipaffs.enumeration.SubjectCode.CONFORMS_TO_EU;
import static uk.gov.defra.stw.mapping.toipaffs.utils.SpsNoteTypeHelper.getNoteContentBySubjectCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;

@Component
public class ChedpPurposeMapper implements Mapper<SpsCertificate, Purpose> {

  private final PurposeMapperFactory purposeMapperFactory;

  @Autowired
  public ChedpPurposeMapper(PurposeMapperFactory purposeMapperFactory) {
    this.purposeMapperFactory = purposeMapperFactory;
  }

  @Override
  public Purpose map(SpsCertificate spsCertificate) throws NotificationMapperException {
    Purpose purpose =
        purposeMapperFactory
            .get(spsCertificate.getSpsExchangedDocument())
            .map(mapper -> mapper.map(spsCertificate))
            .orElseThrow(() -> new NotificationMapperException("Unable to map to the Purpose"));

    purpose.setConformsToEU(mapToEuConformity(spsCertificate));
    return purpose;
  }

  private Boolean mapToEuConformity(SpsCertificate spsCertificate) {
    return getNoteContentBySubjectCode(spsCertificate, CONFORMS_TO_EU.getValue())
        .map(Boolean::parseBoolean)
        .orElse(Boolean.FALSE);
  }
}
