package uk.gov.defra.stw.mapping.toipaffs.common.chedp.commodities;

import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;

@Component
public interface ChedpCommodityComplementMapper extends
    Mapper<SpsCertificate, List<CommodityComplement>> {
  List<CommodityComplement> map(SpsCertificate data) throws NotificationMapperException;
}
