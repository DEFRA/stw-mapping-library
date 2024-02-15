package uk.gov.defra.tracesx.cloning.cheda;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;
import uk.gov.defra.tracesx.notificationschema.representation.Identifier;
import uk.gov.defra.tracesx.trade.dto.CodeType;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.tracesx.trade.dto.SpsNoteType;
import uk.gov.defra.tracesx.trade.dto.TextType;

class ChedaIdentifiersMapperTest {

  private ChedaIdentifiersMapper chedaIdentifiersMapper;

  @BeforeEach
  void setup() {
    chedaIdentifiersMapper = new ChedaIdentifiersMapper();
  }

  @Test
  void map_ReturnsIdentifiers_WhenTradeLineItemContainsIdentificationMark() {
    IncludedSpsTradeLineItem tradeLineItem = buildTradeLineItem("MK67890", "IDENTIFICATION_MARK");

    List<Identifier> actual = chedaIdentifiersMapper.map(tradeLineItem);

    ComplementParameterSet expected = createComplementParameterSet();
    assertThat(actual).isEqualTo(expected.getIdentifiers());
  }

  @Test
  void map_ReturnsNull_WhenAdditionalInformationSpsNoteDoesntContainIdentificationMark() {
    IncludedSpsTradeLineItem tradeLineItem = buildTradeLineItem("ID67890", "IDENTIFICATION_NUMBER");

    List<Identifier> actual = chedaIdentifiersMapper.map(tradeLineItem);

    assertThat(actual).isNull();
  }

  @Test
  void map_ReturnsNull_WhenTradeLineItemDoesntContainAdditionalInformationSpsNote() {
    IncludedSpsTradeLineItem tradeLineItem = new IncludedSpsTradeLineItem();

    List<Identifier> actual = chedaIdentifiersMapper.map(tradeLineItem);

    assertThat(actual).isNull();
  }

  private IncludedSpsTradeLineItem buildTradeLineItem(String value, String subject) {
    SpsNoteType note = new SpsNoteType()
        .withContent(List.of(new TextType().withValue(value)))
        .withSubjectCode(new CodeType().withValue(subject));

    return new IncludedSpsTradeLineItem().withAdditionalInformationSpsNote(List.of(note));
  }

  private ComplementParameterSet createComplementParameterSet() {
    return ComplementParameterSet.builder()
        .identifiers(List.of(Identifier.builder()
            .speciesNumber(1)
            .data(Map.of("identification_details", "Mark: MK67890"))
            .build()))
        .build();
  }
}
