package uk.gov.defra.tracesx.common.cheda;

import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

public interface ChedaNotificationMapper extends Mapper<SpsCertificate, Notification> {
  Notification map(SpsCertificate data) throws NotificationMapperException;
}
