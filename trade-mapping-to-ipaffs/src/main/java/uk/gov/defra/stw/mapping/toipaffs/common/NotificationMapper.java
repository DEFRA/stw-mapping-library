package uk.gov.defra.stw.mapping.toipaffs.common;

import static uk.gov.defra.stw.mapping.toipaffs.utils.CertificateHelper.getCertificateType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

@Component
public class NotificationMapper {

  private final NotificationMapperFactory notificationMapperFactory;

  @Autowired
  public NotificationMapper(NotificationMapperFactory notificationMapperFactory) {
    this.notificationMapperFactory = notificationMapperFactory;
  }

  public Notification processCertificate(SpsCertificate spsCertificate, String certType)
      throws NotificationMapperException {
    return notificationMapperFactory.getMapper(getCertificateType(certType)).map(spsCertificate);
  }
}
