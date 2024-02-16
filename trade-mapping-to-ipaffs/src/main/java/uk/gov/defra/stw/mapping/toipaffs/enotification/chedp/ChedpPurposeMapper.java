package uk.gov.defra.stw.mapping.toipaffs.enotification.chedp;

import static uk.gov.defra.stw.mapping.toipaffs.enumeration.SubjectCode.CONFORMS_TO_EU;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
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
    return spsCertificate.getSpsExchangedDocument().getIncludedSpsNote().stream()
        .filter(this::hasConformToEu)
        .map(SpsNoteType::getContent)
        .filter(Objects::nonNull)
        .findFirst()
        .map(codeTypes -> codeTypes.get(0))
        .map(TextType::getValue)
        .map(Boolean::parseBoolean)
        .orElse(Boolean.FALSE);
  }

  private boolean hasConformToEu(SpsNoteType spsNoteType) {
    return spsNoteType.getSubjectCode() != null
        && spsNoteType.getSubjectCode().getValue().equals(CONFORMS_TO_EU.getValue());
  }
}
