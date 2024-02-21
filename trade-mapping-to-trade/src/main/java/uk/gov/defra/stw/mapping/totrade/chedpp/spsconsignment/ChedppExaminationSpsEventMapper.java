package uk.gov.defra.stw.mapping.totrade.chedpp.spsconsignment;

import java.util.Collections;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsEventType;
import uk.gov.defra.stw.mapping.dto.SpsLocationType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.totrade.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;

@Component
public class ChedppExaminationSpsEventMapper implements Mapper<PartOne, SpsEventType> {

  @Override
  public SpsEventType map(PartOne partOne) {
    String pointOfEntryControlPoint = partOne.getPointOfEntryControlPoint();
    if (StringUtils.isNotEmpty(pointOfEntryControlPoint)) {
      return new SpsEventType()
          .withOccurrenceSpsLocation(new SpsLocationType()
              .withName(Collections.singletonList(new TextType()
                  .withValue(pointOfEntryControlPoint))));
    }

    return null;
  }
}
