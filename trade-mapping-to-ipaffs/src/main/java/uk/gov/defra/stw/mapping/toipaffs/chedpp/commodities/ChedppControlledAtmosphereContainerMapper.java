package uk.gov.defra.stw.mapping.toipaffs.chedpp.commodities;

import java.util.Objects;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

@Component
public class ChedppControlledAtmosphereContainerMapper implements
    Mapper<IncludedSpsTradeLineItem, ComplementParameterSetKeyDataPair> {

  private static final String CONTAINER_KEY = "container";
  private static final String TRUE = "TRUE";

  @Override
  public ComplementParameterSetKeyDataPair map(IncludedSpsTradeLineItem data) {
    return data.getAdditionalInformationSpsNote()
        .stream()
        .filter(note -> Objects.equals(note.getSubjectCode().getValue(), CONTAINER_KEY))
        .findFirst()
        .filter(note -> TRUE.equals(note.getContent().get(0).getValue()))
        .map(note -> ComplementParameterSetKeyDataPair.builder()
            .key(CONTAINER_KEY)
            .data(TRUE)
            .build())
        .orElse(null);
  }
}
