package uk.gov.defra.stw.mapping.toipaffs;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum.CHEDPP;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum.CVEDA;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum.CVEDP;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.cheda.ChedaNotificationMapper;
import uk.gov.defra.stw.mapping.toipaffs.chedp.ChedpNotificationMapper;
import uk.gov.defra.stw.mapping.toipaffs.chedpp.ChedppNotificationMapper;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;

@Component
public class NotificationMapperFactory {
  private final Map<NotificationTypeEnum, Mapper<SpsCertificate, Notification>> mapperMap;

  @Autowired
  public NotificationMapperFactory(
      ChedaNotificationMapper chedaNotificationMapper,
      ChedpNotificationMapper chedpNotificationMapper,
      ChedppNotificationMapper chedppNotificationMapper) {
    mapperMap = Map.of(
        CVEDA, chedaNotificationMapper,
        CVEDP, chedpNotificationMapper,
        CHEDPP, chedppNotificationMapper);
  }

  public Mapper<SpsCertificate, Notification> getMapper(NotificationTypeEnum type) {
    return mapperMap.get(type);
  }
}
