package uk.gov.defra.stw.mapping.toipaffs.chedpp;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsLocationType;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;

@Component
public class ChedppPointOfEntryControlPointMapper implements Mapper<SpsLocationType, String> {

  @Override
  public String map(SpsLocationType spsLocationType) {
    String controlPoint = spsLocationType.getName().get(0).getValue();
    return !controlPoint.isBlank() ? controlPoint : null;
  }
}
