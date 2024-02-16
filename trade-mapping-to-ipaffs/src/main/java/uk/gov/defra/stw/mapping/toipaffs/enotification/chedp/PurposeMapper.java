package uk.gov.defra.stw.mapping.toipaffs.enotification.chedp;

import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;

public interface PurposeMapper {
  Purpose map(SpsCertificate spsCertificate);
}
