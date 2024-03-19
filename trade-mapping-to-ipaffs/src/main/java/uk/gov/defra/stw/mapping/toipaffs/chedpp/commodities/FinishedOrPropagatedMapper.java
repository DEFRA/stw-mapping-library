package uk.gov.defra.stw.mapping.toipaffs.chedpp.commodities;

import static uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet.FINISHED_OR_PROPAGATED;

import java.util.Map;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

@Component
public class FinishedOrPropagatedMapper
    implements Mapper<IncludedSpsTradeLineItem, ComplementParameterSetKeyDataPair> {

  private static final Map<String, String> VALUE_MAP = Map.of(
      "FINISHED", "Finished",
      "PROPAGATED", "Propagated"
  );

  @Override
  public ComplementParameterSetKeyDataPair map(IncludedSpsTradeLineItem tradeLineItem) {
    return tradeLineItem.getAdditionalInformationSpsNote().stream()
        .filter(this::isFinishedPropagating)
        .filter(this::hasContentValues)
        .findFirst()
        .map(SpsNoteType::getContent)
        .map(textTypes -> textTypes.get(0))
        .map(TextType::getValue)
        .map(VALUE_MAP::get)
        .map(this::buildKeyDataPair)
        .orElse(null);
  }

  private boolean hasContentValues(SpsNoteType spsNoteType) {
    return spsNoteType.getContent() != null && !spsNoteType.getContent().isEmpty();
  }

  private boolean isFinishedPropagating(SpsNoteType spsNoteType) {
    return spsNoteType.getSubjectCode() != null
        && FINISHED_OR_PROPAGATED.equals(spsNoteType.getSubjectCode().getValue());
  }

  private ComplementParameterSetKeyDataPair buildKeyDataPair(String value) {
    return ComplementParameterSetKeyDataPair.builder()
        .key(FINISHED_OR_PROPAGATED)
        .data(value)
        .build();
  }
}
