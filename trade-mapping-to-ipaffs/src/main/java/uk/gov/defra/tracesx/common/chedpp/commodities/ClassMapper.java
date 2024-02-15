package uk.gov.defra.tracesx.common.chedpp.commodities;

import static uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet.CLASS;
import static uk.gov.defra.tracesx.utils.SpsNoteTypeHelper.findSpsNoteByType;

import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;

@Component
public class ClassMapper
    implements Mapper<IncludedSpsTradeLineItem, ComplementParameterSetKeyDataPair> {

  @Override
  public ComplementParameterSetKeyDataPair map(IncludedSpsTradeLineItem tradeLineItem) {
    return findSpsNoteByType(tradeLineItem.getAdditionalInformationSpsNote(), CLASS)
        .map(spsNoteType -> spsNoteType.getContent().get(0).getValue())
        .map(this::buildKeyDataPair)
        .orElse(null);
  }

  private ComplementParameterSetKeyDataPair buildKeyDataPair(String data) {
    return ComplementParameterSetKeyDataPair.builder()
        .key(CLASS)
        .data(data)
        .build();
  }
}
