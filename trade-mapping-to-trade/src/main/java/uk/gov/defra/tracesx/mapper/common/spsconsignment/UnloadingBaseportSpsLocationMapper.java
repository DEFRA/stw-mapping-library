package uk.gov.defra.tracesx.mapper.common.spsconsignment;

import java.util.Collections;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter;
import uk.gov.defra.tracesx.trade.dto.SpsLocationType;

@Component
public class UnloadingBaseportSpsLocationMapper implements Mapper<String, SpsLocationType> {

  private static final String SCHEME_ID = "un_locode";

  @Override
  public SpsLocationType map(String pointOfEntry) {
    if (StringUtils.isEmpty(pointOfEntry)) {
      return null;
    }
    return new SpsLocationType()
        .withId(SpsTypeConverter.getIdTypeWithSchemeId(SCHEME_ID, pointOfEntry))
        .withName(
            Collections.singletonList(SpsTypeConverter.getTextType(pointOfEntry.substring(0, 2))));
  }
}
