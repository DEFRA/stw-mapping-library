package uk.gov.defra.stw.mapping.toipaffs.common.chedp.commodities;

import java.util.List;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;

public interface ChedpComplementParameterSetMapper extends
    Mapper<SpsCertificate, List<ComplementParameterSet>> {
  List<ComplementParameterSet> map(SpsCertificate data) throws NotificationMapperException;
}
