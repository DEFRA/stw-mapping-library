package uk.gov.defra.stw.mapping.toipaffs.cloning.cheda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.ExternalReferenceMapper;
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.StatusMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.cheda.ChedaNotificationMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.ExternalSystem;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;

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
