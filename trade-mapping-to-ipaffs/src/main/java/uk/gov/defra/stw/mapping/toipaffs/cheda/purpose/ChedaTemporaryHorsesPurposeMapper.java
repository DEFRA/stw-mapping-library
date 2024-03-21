package uk.gov.defra.stw.mapping.toipaffs.cheda.purpose;

import static uk.gov.defra.stw.mapping.toipaffs.cheda.purpose.ChedaPurposeUtils.APHA_BCP_ID;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.ForImportOrAdmissionEnum.TEMPORARY_ADMISSION_HORSES;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.RE_IMPORT;

import java.util.Optional;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose.PurposeBuilder;

@Component
public class ChedaTemporaryHorsesPurposeMapper implements Mapper<SpsCertificate, Purpose> {

  @Override
  public Purpose map(SpsCertificate spsCertificate) {
    Optional<String> portOfExit = ChedaPurposeUtils.getPortOfExit(spsCertificate);
    PurposeBuilder purposeBuilder = Purpose.builder()
        .purposeGroup(RE_IMPORT)
        .forImportOrAdmission(TEMPORARY_ADMISSION_HORSES)
        .exitDate(getExitDate(spsCertificate));
    if (portOfExit.isPresent()) {
      return purposeBuilder
          .exitBIP(APHA_BCP_ID)
          .build();
    } else {
      return purposeBuilder
          .exitBIP(ChedaPurposeUtils.getExitBcp(spsCertificate))
          .build();
    }
  }

  private String getExitDate(SpsCertificate spsCertificate) {
    return spsCertificate.getSpsExchangedDocument().getIncludedSpsNote().stream()
        .filter(note ->
            note.getSubjectCode().getValue().equals("TEMPORARY_ADMISSION_HORSES_EXIT_DATE"))
        .findAny()
        .map(SpsNoteType::getContent)
        .map(contents -> contents.get(0))
        .map(TextType::getValue)
        .orElse(null);
  }
}
