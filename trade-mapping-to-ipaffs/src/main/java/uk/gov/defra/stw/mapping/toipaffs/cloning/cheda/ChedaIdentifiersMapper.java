package uk.gov.defra.stw.mapping.toipaffs.cloning.cheda;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.Identifier;

@Component
public class ChedaIdentifiersMapper implements
    Mapper<IncludedSpsTradeLineItem, List<Identifier>> {

  private static final String IDENTIFICATION_MARK = "IDENTIFICATION_MARK";

  @Override
  public List<Identifier> map(IncludedSpsTradeLineItem data) {
    return data.getAdditionalInformationSpsNote()
        .stream()
        .filter(note -> Objects.equals(note.getSubjectCode().getValue(), IDENTIFICATION_MARK))
        .findFirst()
        .map(note -> Identifier.builder()
                .speciesNumber(1)
                .data(Map.of("identification_details", "Mark: "
                        + note.getContent().get(0).getValue()))
                .build()
        )
        .map(List::of)
        .orElse(null);
  }
}
