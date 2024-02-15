package uk.gov.defra.tracesx.enotification.cheda;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.cheda.ChedaNotificationMapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

@Component
public class ChedaNotificationMapperImpl implements ChedaNotificationMapper {

  @Override
  public Notification map(SpsCertificate data) throws NotificationMapperException {
    throw new NotImplementedException("ENotification does not support CHEDA notifications");
  }
}
