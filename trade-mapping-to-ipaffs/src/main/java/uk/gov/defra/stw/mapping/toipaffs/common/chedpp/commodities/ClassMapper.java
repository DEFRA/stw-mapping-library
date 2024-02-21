package uk.gov.defra.stw.mapping.toipaffs.common.chedpp.commodities;

import static uk.gov.defra.stw.mapping.toipaffs.utils.SpsNoteTypeHelper.findSpsNoteByType;
import static uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet.CLASS;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

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
