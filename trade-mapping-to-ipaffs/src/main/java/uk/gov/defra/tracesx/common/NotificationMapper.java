package uk.gov.defra.tracesx.common;

import static uk.gov.defra.tracesx.utils.CertificateHelper.getCertificateType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

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