package uk.gov.defra.tracesx.cloning.cheda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.cloning.common.ExternalReferenceMapper;
import uk.gov.defra.tracesx.cloning.common.StatusMapper;
import uk.gov.defra.tracesx.common.cheda.ChedaNotificationMapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.ExternalSystem;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

@Component
public class ChedaNotificationMapperImpl implements ChedaNotificationMapper {

  private final ChedaPartOneMapper chedaPartOneMapper;
  private final ExternalReferenceMapper externalReferenceMapper;
  private final StatusMapper statusMapper;

  @Autowired
  public ChedaNotificationMapperImpl(
      ChedaPartOneMapper chedaPartOneMapper,
      ExternalReferenceMapper externalReferenceMapper,
      StatusMapper statusMapper) {
    this.chedaPartOneMapper = chedaPartOneMapper;
    this.externalReferenceMapper = externalReferenceMapper;
    this.statusMapper = statusMapper;
  }

  @Override
  public Notification map(SpsCertificate data) throws NotificationMapperException {
    return Notification.builder()
        .partOne(chedaPartOneMapper.map(data))
        .type(NotificationTypeEnum.CVEDA)
        .status(statusMapper.map(data.getSpsExchangedDocument().getStatusCode()))
        .externalReferences(
            externalReferenceMapper.mapExternalReference(data, ExternalSystem.ECERT))
        .build();
  }
}
