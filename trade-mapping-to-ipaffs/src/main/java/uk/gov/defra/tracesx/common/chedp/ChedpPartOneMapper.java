package uk.gov.defra.tracesx.common.chedp;

import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

public interface ChedpPartOneMapper {
  PartOne map(SpsCertificate data) throws NotificationMapperException;
}
