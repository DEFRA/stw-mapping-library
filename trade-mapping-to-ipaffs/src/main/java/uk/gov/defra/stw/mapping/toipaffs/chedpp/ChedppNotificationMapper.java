package uk.gov.defra.stw.mapping.toipaffs.chedpp;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.DRAFT;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.ReferenceNumberMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ExternalReference;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.ExternalSystem;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;

@Component
public class ChedppNotificationMapper implements Mapper<SpsCertificate, Notification> {

  private final ChedppPartOneMapper chedppPartOneMapper;
  private final ReferenceNumberMapper referenceNumberMapper;

  @Autowired
  public ChedppNotificationMapper(
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
