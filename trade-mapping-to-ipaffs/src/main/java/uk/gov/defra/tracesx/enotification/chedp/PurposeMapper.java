package uk.gov.defra.tracesx.enotification.chedp;

import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

public interface PurposeMapper {
  Purpose map(SpsCertificate spsCertificate);
}
