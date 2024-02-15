package uk.gov.defra.tracesx.cloning.chedpp;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.DRAFT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.cloning.common.ExternalReferenceMapper;
import uk.gov.defra.tracesx.common.chedpp.ChedppNotificationMapper;
import uk.gov.defra.tracesx.common.common.ReferenceNumberMapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.ExternalSystem;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

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
