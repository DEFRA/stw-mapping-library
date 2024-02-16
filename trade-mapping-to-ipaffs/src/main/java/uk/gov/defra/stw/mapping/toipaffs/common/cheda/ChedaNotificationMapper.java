package uk.gov.defra.stw.mapping.toipaffs.common.cheda;

import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

public interface ChedaNotificationMapper extends Mapper<SpsCertificate, Notification> {
  Notification map(SpsCertificate data) throws NotificationMapperException;
}
