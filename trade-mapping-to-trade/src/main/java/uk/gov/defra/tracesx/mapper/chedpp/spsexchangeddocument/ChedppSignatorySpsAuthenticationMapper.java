package uk.gov.defra.tracesx.mapper.chedpp.spsexchangeddocument;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.mapper.chedpp.spsexchangeddocument.childmappers.ChedppSpsAuthenticationTypeForClearanceMapper;
import uk.gov.defra.tracesx.mapper.chedpp.spsexchangeddocument.childmappers.ChedppSpsAuthenticationTypeForInspectionMapper;
import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;
import uk.gov.defra.tracesx.notificationschema.representation.PartTwo;
import uk.gov.defra.tracesx.trade.dto.SpsAuthenticationType;

@Component
public class ChedppSignatorySpsAuthenticationMapper
    implements Mapper<Notification, List<SpsAuthenticationType>> {

  private final ChedppSpsAuthenticationTypeForInspectionMapper
      chedppSpsAuthenticationForInspectionMapper;
  private final ChedppSpsAuthenticationTypeForClearanceMapper
      chedppSpsAuthenticationForClearanceMapper;

  @Autowired
  public ChedppSignatorySpsAuthenticationMapper(
      ChedppSpsAuthenticationTypeForInspectionMapper chedppSpsAuthenticationForInspectionMapper,
      ChedppSpsAuthenticationTypeForClearanceMapper chedppSpsAuthenticationForClearanceMapper) {
    this.chedppSpsAuthenticationForInspectionMapper = chedppSpsAuthenticationForInspectionMapper;
    this.chedppSpsAuthenticationForClearanceMapper = chedppSpsAuthenticationForClearanceMapper;
  }

  @Override
  public List<SpsAuthenticationType> map(Notification data) {
    List<SpsAuthenticationType> authenticationTypes = new ArrayList<>();

    PartOne partOne = data.getPartOne();
    authenticationTypes.add(chedppSpsAuthenticationForInspectionMapper.map(partOne));

    PartTwo partTwo = data.getPartTwo();
    if (partTwo != null) {
      SpsAuthenticationType spsAuthenticationType = chedppSpsAuthenticationForClearanceMapper
          .map(partTwo);
      if (spsAuthenticationType != null) {
        authenticationTypes.add(spsAuthenticationType);
      }
    }

    return authenticationTypes;
  }
}
