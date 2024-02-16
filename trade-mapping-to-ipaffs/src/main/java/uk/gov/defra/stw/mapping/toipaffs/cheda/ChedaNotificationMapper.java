package uk.gov.defra.stw.mapping.toipaffs.cheda;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

@Component
public class ChedaNotificationMapper implements Mapper<SpsCertificate, Notification> {

  @Override
  public Notification map(SpsCertificate data) throws NotificationMapperException {
    throw new NotImplementedException("ENotification does not support CHEDA notifications");
  }
}
