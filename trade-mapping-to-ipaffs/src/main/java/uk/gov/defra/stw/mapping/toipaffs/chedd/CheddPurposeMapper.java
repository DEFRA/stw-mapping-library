package uk.gov.defra.stw.mapping.toipaffs.chedd;

import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsAuthenticationType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.PurposeMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;

@Component
public class CheddPurposeMapper implements Mapper<SpsCertificate, Purpose> {
  private final PurposeMapper purposeMapper;

  public CheddPurposeMapper(PurposeMapper purposeMapper) {
    this.purposeMapper = purposeMapper;
  }

  @Override
  public Purpose map(SpsCertificate spsCertificate) throws NotificationMapperException {
    List<SpsAuthenticationType> spsAuthenticationTypeList = spsCertificate.getSpsExchangedDocument()
        .getSignatorySpsAuthentication();
    for (SpsAuthenticationType spsAuthenticationType : spsAuthenticationTypeList) {
      Purpose purpose = purposeMapper.map(spsAuthenticationType);
      if (purpose != null) {
        return purpose;
      }
    }
    throw new NotificationMapperException("Unable to map to the Purpose");
  }
}
