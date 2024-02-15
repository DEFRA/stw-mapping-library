package uk.gov.defra.tracesx.enotification.chedpp.commodities;

import static uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet.QUANTITY;
import static uk.gov.defra.tracesx.utils.SpsNoteTypeHelper.findSpsNoteByType;

import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;

@Component
public class ChedppQuantityMapper implements
    Mapper<IncludedSpsTradeLineItem, ComplementParameterSetKeyDataPair> {

  @Override
  public ComplementParameterSetKeyDataPair map(IncludedSpsTradeLineItem tradeLineItem) {
    return findSpsNoteByType(tradeLineItem.getAdditionalInformationSpsNote(), QUANTITY)
        .map(spsNoteType -> spsNoteType.getContent().get(0).getValue())
        .map(this::buildKeyDataPair)
        .orElse(null);
  }

  private ComplementParameterSetKeyDataPair buildKeyDataPair(String quantity) {
    return ComplementParameterSetKeyDataPair.builder()
        .key(QUANTITY)
        .data(quantity)
        .build();
  }
}
