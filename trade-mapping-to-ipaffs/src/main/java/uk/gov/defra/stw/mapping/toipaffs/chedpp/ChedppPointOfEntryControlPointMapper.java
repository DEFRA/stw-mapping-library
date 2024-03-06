package uk.gov.defra.stw.mapping.toipaffs.chedpp;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsLocationType;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;

@Component
public class ChedppPointOfEntryControlPointMapper implements Mapper<SpsLocationType, String> {

  @Override
  public String map(SpsLocationType spsLocationType) throws NotificationMapperException {
    if (spsLocationType != null
        && spsLocationType.getName() != null
        && !spsLocationType.getName().isEmpty()
        && spsLocationType.getName().get(0) != null) {
      return spsLocationType.getName().get(0).getValue();
    }
    return null;
  }
}
