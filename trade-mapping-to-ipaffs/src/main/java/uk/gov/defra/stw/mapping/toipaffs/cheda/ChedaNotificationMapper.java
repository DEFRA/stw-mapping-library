package uk.gov.defra.stw.mapping.toipaffs.cheda;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum.CVEDA;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.SUBMITTED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

@Component
public class ChedaNotificationMapper implements Mapper<SpsCertificate, Notification> {

  private final ChedaPartOneMapper chedaPartOneMapper;

  @Autowired
  public ChedaNotificationMapper(
      ChedaPartOneMapper chedaPartOneMapper) {
    this.chedaPartOneMapper = chedaPartOneMapper;
  }

  @Override
  public Notification map(SpsCertificate spsCertificate) throws NotificationMapperException {
    return Notification.builder()
        .partOne(chedaPartOneMapper.map(spsCertificate))
        .type(CVEDA)
        .status(SUBMITTED)
        .build();
  }
}
