package uk.gov.defra.stw.mapping.totrade.common.spsexchangeddocument.childmappers;

import uk.gov.defra.stw.mapping.dto.GovernmentActionCodeType;
import uk.gov.defra.stw.mapping.dto.SpsAuthenticationType;
import uk.gov.defra.stw.mapping.totrade.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.PartTwo;

public abstract class SpsAuthenticationTypeForClearanceMapper implements
    Mapper<PartTwo, SpsAuthenticationType> {

  private static final String CLEARANCE = "1";

  @Override
  public SpsAuthenticationType map(PartTwo data) {
    return new SpsAuthenticationType()
        .withTypeCode(new GovernmentActionCodeType().withValue(CLEARANCE));
  }
}
