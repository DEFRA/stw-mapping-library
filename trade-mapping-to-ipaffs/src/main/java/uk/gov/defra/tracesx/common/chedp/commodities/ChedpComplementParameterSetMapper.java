package uk.gov.defra.tracesx.common.chedp.commodities;

import java.util.List;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

public interface ChedpComplementParameterSetMapper extends
    Mapper<SpsCertificate, List<ComplementParameterSet>> {
  List<ComplementParameterSet> map(SpsCertificate data) throws NotificationMapperException;
}
