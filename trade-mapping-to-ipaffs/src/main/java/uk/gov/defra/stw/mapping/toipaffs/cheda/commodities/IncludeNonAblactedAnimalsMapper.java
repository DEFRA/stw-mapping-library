package uk.gov.defra.stw.mapping.toipaffs.cheda.commodities;

import static uk.gov.defra.stw.mapping.toipaffs.utils.SpsNoteTypeHelper.getNoteContentBySubjectCode;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;

@Component
public class IncludeNonAblactedAnimalsMapper implements Mapper<SpsCertificate, Boolean> {

  @Override
  public Boolean map(SpsCertificate spsCertificate) {
    return getNoteContentBySubjectCode(spsCertificate, "INCLUDES_NON_ABLACTED_ANIMALS")
        .map(Boolean::parseBoolean)
        .orElse(null);
  }
}
