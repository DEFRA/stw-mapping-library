package uk.gov.defra.tracesx.enotification.chedpp.commodities;

import static uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet.QUANTITY;
import static uk.gov.defra.tracesx.utils.SpsNoteTypeHelper.findSpsNoteByType;

import java.util.Map;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.trade.dto.CodeType;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;

@Component
public class ChedppQuantityTypeMapper implements
    Mapper<IncludedSpsTradeLineItem, ComplementParameterSetKeyDataPair> {
  private static final String TYPE_QUANTITY = "type_quantity";

  private static final Map<String, String> QUANTITY_TYPE_MAP = Map.of(
      "STM", "Stems",
      "BLB", "Bulbs",
      "CRZ", "Corms and rhizomes",
      "PTC", "Plants in tissue culture",
      "SDS", "Seeds",
      "KGM", "Kilograms",
      "PCS", "Pieces");

  @Override
  public ComplementParameterSetKeyDataPair map(IncludedSpsTradeLineItem tradeLineItem) {
    return findSpsNoteByType(tradeLineItem.getAdditionalInformationSpsNote(), QUANTITY)
        .map(spsNoteType -> spsNoteType.getContentCode().get(0))
        .map(CodeType::getValue)
        .map(QUANTITY_TYPE_MAP::get)
        .map(this::buildKeyDataPair)
        .orElse(null);
  }

  private ComplementParameterSetKeyDataPair buildKeyDataPair(String mappedQuantityType) {
    return ComplementParameterSetKeyDataPair.builder()
        .key(TYPE_QUANTITY)
        .data(mappedQuantityType)
        .build();
  }
}
