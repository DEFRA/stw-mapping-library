package uk.gov.defra.tracesx.common.common;

import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.trade.dto.SpsLocationType;

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
