package uk.gov.defra.stw.mapping.toipaffs.common.commodities;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.PhysicalSpsPackage;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

@Component
public class NumberOfPackagesKeyDataMapper implements
    Mapper<PhysicalSpsPackage, ComplementParameterSetKeyDataPair> {

  @Override
  public ComplementParameterSetKeyDataPair map(PhysicalSpsPackage data) {
    return ComplementParameterSetKeyDataPair.builder()
        .key("number_package")
        .data(String.valueOf(data.getItemQuantity().getValue().intValue()))
        .build();
  }
}
