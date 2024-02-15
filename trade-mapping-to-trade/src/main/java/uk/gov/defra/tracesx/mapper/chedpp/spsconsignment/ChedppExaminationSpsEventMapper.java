package uk.gov.defra.tracesx.mapper.chedpp.spsconsignment;

import java.util.Collections;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;
import uk.gov.defra.tracesx.trade.dto.SpsEventType;
import uk.gov.defra.tracesx.trade.dto.SpsLocationType;
import uk.gov.defra.tracesx.trade.dto.TextType;

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
