package uk.gov.defra.stw.mapping.toipaffs.cheda;

import static uk.gov.defra.stw.mapping.toipaffs.utils.SpsNoteTypeHelper.getNoteContentBySubjectCode;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;

@Component
public class IsGvmsRouteMapper implements Mapper<SpsCertificate, Boolean> {

  @Override
  public Boolean map(SpsCertificate spsCertificate) {
    return getNoteContentBySubjectCode(spsCertificate, "IS_GVMS_ROUTE")
        .map(Boolean::parseBoolean)
        .orElse(null);
  }
}
