package uk.gov.defra.stw.mapping.toipaffs.chedpp.commodities;

import static uk.gov.defra.stw.mapping.toipaffs.utils.SpsNoteTypeHelper.findSpsNoteByType;
import static uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet.QUANTITY;

import java.util.Map;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

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
