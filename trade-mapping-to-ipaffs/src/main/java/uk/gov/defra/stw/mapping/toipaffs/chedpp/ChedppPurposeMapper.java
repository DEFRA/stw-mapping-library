package uk.gov.defra.stw.mapping.toipaffs.chedpp;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsAuthenticationType;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.PurposeMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;

@Component
public class ChedppPurposeMapper implements Mapper<List<SpsAuthenticationType>, Purpose> {

  private final PurposeMapper purposeMapper;

  @Autowired
  public ChedppPurposeMapper(PurposeMapper purposeMapper) {
    this.purposeMapper = purposeMapper;
  }

  @Override
  public Purpose map(List<SpsAuthenticationType> spsAuthenticationTypeList)
      throws NotificationMapperException {
    for (SpsAuthenticationType spsAuthenticationType : spsAuthenticationTypeList) {
      Purpose purpose = purposeMapper.map(spsAuthenticationType);
      if (purpose != null) {
        return purpose;
      }
    }
    throw new NotificationMapperException("Unable to map to the Purpose");
  }
}
