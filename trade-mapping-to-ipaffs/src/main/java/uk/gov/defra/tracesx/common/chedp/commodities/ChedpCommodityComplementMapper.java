package uk.gov.defra.tracesx.common.chedp.commodities;

import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

@Component
public interface ChedpCommodityComplementMapper extends
    Mapper<SpsCertificate, List<CommodityComplement>> {
  List<CommodityComplement> map(SpsCertificate data) throws NotificationMapperException;
}
