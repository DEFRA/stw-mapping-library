package uk.gov.defra.stw.mapping.toipaffs.enotification.chedpp.commodities;

import java.util.Optional;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

@Component
public class ChedppSequenceNumericMapper implements
    Mapper<IncludedSpsTradeLineItem, ComplementParameterSetKeyDataPair> {

  private static final String SEQUENCE_NUMERIC = "sequence_numeric";

  @Override
  public ComplementParameterSetKeyDataPair map(IncludedSpsTradeLineItem tradeLineItem) {
    return Optional.ofNullable(tradeLineItem.getSequenceNumeric())
        .map(sequenceNumeric -> buildKeyDataPair(sequenceNumeric.getValue().toString()))
        .orElse(null);
  }

  private ComplementParameterSetKeyDataPair buildKeyDataPair(String sequenceNumeric) {
    return ComplementParameterSetKeyDataPair.builder()
        .key(SEQUENCE_NUMERIC)
        .data(sequenceNumeric)
        .build();
  }
}
