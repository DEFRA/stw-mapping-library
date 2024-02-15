package uk.gov.defra.tracesx.common.common.commodities;

import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.trade.dto.PhysicalSpsPackage;

@Component
public class NumberOfPackagesKeyDataMapper implements
    Mapper<PhysicalSpsPackage, ComplementParameterSetKeyDataPair> {

  @Override
  public ComplementParameterSetKeyDataPair map(PhysicalSpsPackage data)
      throws NotificationMapperException {
    return ComplementParameterSetKeyDataPair.builder()
        .key("number_package")
        .data(String.valueOf(data.getItemQuantity().getValue().intValue()))
        .build();
  }
}
