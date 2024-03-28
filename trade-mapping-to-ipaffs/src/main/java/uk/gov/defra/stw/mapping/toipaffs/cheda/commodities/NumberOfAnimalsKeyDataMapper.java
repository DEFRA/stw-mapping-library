package uk.gov.defra.stw.mapping.toipaffs.cheda.commodities;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

@Component
public class NumberOfAnimalsKeyDataMapper implements
    Mapper<IncludedSpsTradeLineItem, ComplementParameterSetKeyDataPair> {

  @Override
  public ComplementParameterSetKeyDataPair map(IncludedSpsTradeLineItem tradeLineItem) {
    return ComplementParameterSetKeyDataPair.builder()
        .key("number_animal")
        .data(String.valueOf(tradeLineItem.getNetVolumeMeasure().getValue().intValue()))
        .build();
  }
}
