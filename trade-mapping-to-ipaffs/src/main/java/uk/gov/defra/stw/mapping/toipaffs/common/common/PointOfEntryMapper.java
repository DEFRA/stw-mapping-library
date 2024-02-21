package uk.gov.defra.stw.mapping.toipaffs.common.common;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsLocationType;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;

@Component
public class PointOfEntryMapper implements Mapper<SpsLocationType, String> {

  @Override
  public String map(SpsLocationType spsLocationType) throws NotificationMapperException {
    if (spsLocationType != null && spsLocationType.getId() != null) {
      return spsLocationType.getId().getValue();
    }
    return null;
  }
}
