package uk.gov.defra.stw.mapping.toipaffs.chedpp;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum.CHEDPP;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.SUBMITTED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

@Component
public class ChedppNotificationMapper implements Mapper<SpsCertificate, Notification> {

  private final ChedppPartOneMapper chedppPartOneMapper;

  @Autowired
  public ChedppNotificationMapper(ChedppPartOneMapper chedppPartOneMapper) {
    this.chedppPartOneMapper = chedppPartOneMapper;
  }

  @Override
  public Notification map(SpsCertificate data) throws NotificationMapperException {
    return Notification.builder()
        .partOne(chedppPartOneMapper.map(data))
        .type(CHEDPP)
        .status(SUBMITTED)
        .build();
  }
}
