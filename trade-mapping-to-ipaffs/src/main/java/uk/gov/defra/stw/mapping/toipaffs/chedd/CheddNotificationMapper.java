package uk.gov.defra.stw.mapping.toipaffs.chedd;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum.CED;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.SUBMITTED;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

@Component
public class CheddNotificationMapper implements Mapper<SpsCertificate, Notification> {
  private CheddPartOneMapper cheddPartOneMapper;

  public CheddNotificationMapper(CheddPartOneMapper cheddPartOneMapper) {
    this.cheddPartOneMapper = cheddPartOneMapper;
  }

  @Override
  public Notification map(SpsCertificate data) throws NotificationMapperException {
    return Notification.builder()
        .partOne(cheddPartOneMapper.map(data))
        .type(CED)
        .status(SUBMITTED)
        .build();

  }
}