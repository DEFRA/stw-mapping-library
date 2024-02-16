package uk.gov.defra.stw.mapping.toipaffs.chedp;

import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;

public interface PurposeMapper extends Mapper<SpsCertificate, Purpose> {

  @Override
  Purpose map(SpsCertificate spsCertificate);
}
