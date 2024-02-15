package uk.gov.defra.tracesx.common.chedpp.commodities;

import java.util.Objects;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;

@Component
public class ChedppControlledAtmosphereContainerMapper implements
    Mapper<IncludedSpsTradeLineItem, ComplementParameterSetKeyDataPair> {

  private static final String CONTAINER_KEY = "container";
  private static final String TRUE = "true";

  @Override
  public ComplementParameterSetKeyDataPair map(IncludedSpsTradeLineItem data) {
    return data.getAdditionalInformationSpsNote()
        .stream()
        .filter(note -> Objects.equals(note.getSubjectCode().getValue(), CONTAINER_KEY))
        .findFirst()
        .filter(note -> TRUE.equals(note.getContent().get(0).getValue()))
        .map(note -> ComplementParameterSetKeyDataPair.builder()
            .key(CONTAINER_KEY)
            .data(note.getContent().get(0).getValue())
            .build())
        .orElse(null);
  }
}
