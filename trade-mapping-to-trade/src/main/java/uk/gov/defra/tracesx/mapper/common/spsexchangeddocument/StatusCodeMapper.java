package uk.gov.defra.tracesx.mapper.common.spsexchangeddocument;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.AMEND;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.CANCELLED;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.DELETED;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.IN_PROGRESS;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.MODIFY;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.PARTIALLY_REJECTED;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.REJECTED;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.SPLIT_CONSIGNMENT;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.SUBMITTED;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.VALIDATED;

import java.util.Map;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum;
import uk.gov.defra.tracesx.trade.dto.StatusCode;

@Component
public class StatusCodeMapper implements Mapper<StatusEnum, StatusCode> {

  private static final String SUBMITTED_VALUE = "1";
  private static final String IN_PROGRESS_VALUE = "42";
  private static final String MODIFY_VALUE = "42";
  private static final String REJECTED_VALUE = "41";
  private static final String VALIDATED_VALUE = "70";
  private static final String CANCELLED_VALUE = "64";
  private static final String DELETED_VALUE = "55";
  private static final String AMEND_VALUE = "85";
  private static final String PARTIALLY_REJECTED_VALUE = "122";
  private static final String SPLIT_CONSIGNMENT_VALUE = "68";

  private final Map<StatusEnum, String> statusMap;

  public StatusCodeMapper() {
    statusMap = Map.of(SUBMITTED, SUBMITTED_VALUE,
        IN_PROGRESS, IN_PROGRESS_VALUE,
        MODIFY, MODIFY_VALUE,
        REJECTED, REJECTED_VALUE,
        VALIDATED, VALIDATED_VALUE,
        CANCELLED, CANCELLED_VALUE,
        DELETED, DELETED_VALUE,
        AMEND, AMEND_VALUE,
        PARTIALLY_REJECTED, PARTIALLY_REJECTED_VALUE,
        SPLIT_CONSIGNMENT, SPLIT_CONSIGNMENT_VALUE);
  }

  @Override
  public StatusCode map(StatusEnum status) {
    return new StatusCode().withValue(statusMap.get(status));
  }
}
