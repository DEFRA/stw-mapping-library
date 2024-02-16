package uk.gov.defra.stw.mapping.toipaffs.utils;

import java.util.Optional;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;

public class CertificateHelper {

  private CertificateHelper() {}

  public static NotificationTypeEnum getCertificateType(String certType)
      throws NotificationMapperException {
    return Optional.ofNullable(NotificationTypeEnum.fromValue(certType))
        .orElseThrow(() -> new NotificationMapperException("Invalid certificate type"));
  }
}
