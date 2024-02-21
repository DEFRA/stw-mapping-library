package uk.gov.defra.stw.mapping.toipaffs.enotification.chedpp;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.DRAFT;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.common.chedpp.ChedppNotificationMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.ReferenceNumberMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ExternalReference;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.ExternalSystem;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;

@Component
public class ChedppNotificationMapperImpl implements ChedppNotificationMapper {

  private final ChedppPartOneMapper chedppPartOneMapper;
  private final ReferenceNumberMapper referenceNumberMapper;

  @Autowired
  public ChedppNotificationMapperImpl(
          ChedppPartOneMapper chedppPartOneMapper,
          ReferenceNumberMapper referenceNumberMapper) {
    this.chedppPartOneMapper = chedppPartOneMapper;
    this.referenceNumberMapper = referenceNumberMapper;
  }

  @Override
  public Notification map(SpsCertificate data) throws NotificationMapperException {
    return Notification.builder()
        .partOne(chedppPartOneMapper.map(data))
        .type(NotificationTypeEnum.CHEDPP)
        .status(DRAFT)
        .externalReferences(mapExternalReference(data))
        .referenceNumber(referenceNumberMapper.map(data))
        .build();
  }

  private List<ExternalReference> mapExternalReference(SpsCertificate data) {
    return Collections.singletonList(
        ExternalReference.builder()
            .system(ExternalSystem.ENOTIFICATION)
            .reference(data.getSpsExchangedDocument().getId().getValue())
            .build());
  }
}
