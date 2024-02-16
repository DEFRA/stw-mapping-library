package uk.gov.defra.stw.mapping.toipaffs.common.chedp;

import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;

public interface ChedpPartOneMapper {
  PartOne map(SpsCertificate data) throws NotificationMapperException;
}
