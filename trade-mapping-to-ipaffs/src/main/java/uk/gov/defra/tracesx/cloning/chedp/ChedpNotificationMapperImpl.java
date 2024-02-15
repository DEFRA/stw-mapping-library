package uk.gov.defra.tracesx.cloning.chedp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.cloning.common.ExternalReferenceMapper;
import uk.gov.defra.tracesx.cloning.common.StatusMapper;
import uk.gov.defra.tracesx.common.chedp.ChedpNotificationMapper;
import uk.gov.defra.tracesx.common.chedp.ChedpPartOneMapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.ExternalSystem;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

@Component
public class ChedpNotificationMapperImpl implements ChedpNotificationMapper {

  private final ChedpPartOneMapper chedpPartOneMapper;
  private final ExternalReferenceMapper externalReferenceMapper;
  private final StatusMapper statusMapper;

  @Autowired
  public ChedpNotificationMapperImpl(ChedpPartOneMapper chedpPartOneMapper,
      ExternalReferenceMapper externalReferenceMapper,
      StatusMapper statusMapper) {
    this.chedpPartOneMapper = chedpPartOneMapper;
    this.externalReferenceMapper = externalReferenceMapper;
    this.statusMapper = statusMapper;
  }

  @Override
  public Notification map(SpsCertificate data) throws NotificationMapperException {
    return Notification.builder()
        .partOne(chedpPartOneMapper.map(data))
        .type(NotificationTypeEnum.CVEDP)
        .status(statusMapper.map(data.getSpsExchangedDocument().getStatusCode()))
        .externalReferences(
            externalReferenceMapper.mapExternalReference(data, ExternalSystem.ECERT))
        .build();
  }
}
