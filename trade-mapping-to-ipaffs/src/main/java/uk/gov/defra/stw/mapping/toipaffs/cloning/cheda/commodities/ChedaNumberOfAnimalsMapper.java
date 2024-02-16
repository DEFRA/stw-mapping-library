package uk.gov.defra.stw.mapping.toipaffs.cloning.cheda.commodities;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

@Component
public class ChedaNumberOfAnimalsMapper implements
    Mapper<IncludedSpsTradeLineItem, ComplementParameterSetKeyDataPair> {

  private static final String PIECE = "H87";

  @Override
  public ComplementParameterSetKeyDataPair map(IncludedSpsTradeLineItem data)
      throws NotificationMapperException {
    if (data.getNetVolumeMeasure() == null
        || !PIECE.equals(data.getNetVolumeMeasure().getUnitCode())) {
      return null;
    }
    return ComplementParameterSetKeyDataPair.builder()
        .key("number_animal")
        .data(stripDouble(data.getNetVolumeMeasure().getValue()))
        .build();
  }

  private static String stripDouble(Double value) {
    return String.valueOf(value.intValue());
  }
}
