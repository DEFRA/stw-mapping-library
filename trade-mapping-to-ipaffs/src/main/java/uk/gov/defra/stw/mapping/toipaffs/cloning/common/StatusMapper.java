package uk.gov.defra.stw.mapping.toipaffs.cloning.common;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.DRAFT;

import java.util.Map;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.StatusCode;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum;

@Component
public class StatusMapper implements Mapper<StatusCode, StatusEnum> {

  private static final String DRAFT_STATUS_CODE_VALUE = "47";
  private final Map<String, StatusEnum> statusMap;

  public StatusMapper() {
    this.statusMap = Map.ofEntries(
        Map.entry(DRAFT_STATUS_CODE_VALUE, DRAFT));
  }

  @Override
  public StatusEnum map(StatusCode statusCode) throws NotificationMapperException {
    StatusEnum status = statusMap.get(statusCode.getValue());
    if (status != null) {
      return status;
    }
    throw new NotificationMapperException(
        String.format("Unable to map to the StatusEnum: %s", statusCode.getValue()));
  }
}
