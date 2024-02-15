package uk.gov.defra.tracesx.mapper.common.spsexchangeddocument.childmappers;

import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.PartTwo;
import uk.gov.defra.tracesx.trade.dto.GovernmentActionCodeType;
import uk.gov.defra.tracesx.trade.dto.SpsAuthenticationType;

public abstract class SpsAuthenticationTypeForClearanceMapper implements
    Mapper<PartTwo, SpsAuthenticationType> {

  private static final String CLEARANCE = "1";

  @Override
  public SpsAuthenticationType map(PartTwo data) {
    return new SpsAuthenticationType()
        .withTypeCode(new GovernmentActionCodeType().withValue(CLEARANCE));
  }
}
