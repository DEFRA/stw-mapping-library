package uk.gov.defra.stw.mapping.toipaffs.cheda;

import static uk.gov.defra.stw.mapping.toipaffs.utils.SpsNoteTypeHelper.getNoteContentBySubjectCode;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;

@Component
public class EstimatedJourneyTimeMapper implements Mapper<SpsCertificate, Integer> {

  @Override
  public Integer map(SpsCertificate spsCertificate) throws NotificationMapperException {
    try {
      return getNoteContentBySubjectCode(spsCertificate, "ESTIMATED_JOURNEY_TIME_IN_MINUTES")
          .map(Integer::parseInt)
          .orElse(null);
    } catch (NumberFormatException exception) {
      throw new NotificationMapperException("Error mapping estimated journey time", exception);
    }
  }
}
