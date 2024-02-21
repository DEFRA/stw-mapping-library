package uk.gov.defra.stw.mapping.toipaffs.enotification.cheda;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.common.cheda.ChedaNotificationMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

@Component
public class ChedaNotificationMapperImpl implements ChedaNotificationMapper {

  @Override
  public Notification map(SpsCertificate data) throws NotificationMapperException {
    throw new NotImplementedException("ENotification does not support CHEDA notifications");
  }
}
