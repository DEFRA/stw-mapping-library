package uk.gov.defra.tracesx.common;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum.CHEDPP;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum.CVEDA;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum.CVEDP;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.cheda.ChedaNotificationMapper;
import uk.gov.defra.tracesx.common.chedp.ChedpNotificationMapper;
import uk.gov.defra.tracesx.common.chedpp.ChedppNotificationMapper;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

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
