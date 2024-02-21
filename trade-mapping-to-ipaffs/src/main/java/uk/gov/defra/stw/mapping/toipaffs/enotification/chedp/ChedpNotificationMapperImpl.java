package uk.gov.defra.stw.mapping.toipaffs.enotification.chedp;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.DRAFT;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.StatusCode;
import uk.gov.defra.stw.mapping.toipaffs.common.chedp.ChedpNotificationMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.chedp.ChedpPartOneMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ExternalReference;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.ExternalSystem;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum;

@Component
public class ChedpNotificationMapperImpl implements ChedpNotificationMapper {

  private static final String DRAFT_STATUS_CODE_VALUE = "47";

  private final Map<String, StatusEnum> statusMap;
  private final ChedpPartOneMapper chedpPartOneMapper;

  @Autowired
  public ChedpNotificationMapperImpl(ChedpPartOneMapper chedpPartOneMapper) {
    this.chedpPartOneMapper = chedpPartOneMapper;
    statusMap = Map.ofEntries(
        Map.entry(DRAFT_STATUS_CODE_VALUE, DRAFT));
  }

  @Override
  public Notification map(SpsCertificate data) throws NotificationMapperException {
    return Notification.builder()
        .partOne(chedpPartOneMapper.map(data))
        .type(NotificationTypeEnum.CVEDP)
        .status(mapStatus(data.getSpsExchangedDocument().getStatusCode()))
        .externalReferences(mapExternalReference(data))
        .build();
  }

  private List<ExternalReference> mapExternalReference(SpsCertificate data) {
    return Collections.singletonList(
        ExternalReference.builder()
            .system(ExternalSystem.ENOTIFICATION)
            .reference(data.getSpsExchangedDocument().getId().getValue())
            .build());
  }

  private StatusEnum mapStatus(StatusCode statusCode) throws NotificationMapperException {
    StatusEnum status = statusMap.get(statusCode.getValue());
    if (status != null) {
      return status;
    }
    throw new NotificationMapperException(
        String.format("Unable to map to the StatusEnum: %s", statusCode.getValue()));
  }
}
