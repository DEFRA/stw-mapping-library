package uk.gov.defra.stw.mapping.toipaffs.cloning.chedpp;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.DRAFT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.ExternalReferenceMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.chedpp.ChedppNotificationMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.ReferenceNumberMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.ExternalSystem;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;

@Component
public class ChedppNotificationMapperImpl implements ChedppNotificationMapper {

  private final ChedppPartOneMapper chedppPartOneMapper;
  private final ReferenceNumberMapper referenceNumberMapper;
  private final ExternalReferenceMapper externalReferenceMapper;

  @Autowired
  public ChedppNotificationMapperImpl(ChedppPartOneMapper chedppPartOneMapper,
      ReferenceNumberMapper referenceNumberMapper,
      ExternalReferenceMapper externalReferenceMapper) {
    this.chedppPartOneMapper = chedppPartOneMapper;
    this.referenceNumberMapper = referenceNumberMapper;
    this.externalReferenceMapper = externalReferenceMapper;
  }

  @Override
  public Notification map(SpsCertificate data) throws NotificationMapperException {
    return Notification.builder()
        .partOne(chedppPartOneMapper.map(data))
        .type(NotificationTypeEnum.CHEDPP)
        .status(DRAFT)
        .externalReferences(
            externalReferenceMapper.mapExternalReference(data, ExternalSystem.EPHYTO))
        .referenceNumber(referenceNumberMapper.map(data))
        .build();
  }
}
