package uk.gov.defra.tracesx.common.chedpp.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.trade.dto.CodeType;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.tracesx.trade.dto.SpsNoteType;
import uk.gov.defra.tracesx.trade.dto.TextType;

class ClassMapperTest {

  private ClassMapper classMapper;

  @BeforeEach
  void setup() {
    classMapper = new ClassMapper();
  }

  @Test
  void map_ReturnsComplementParameterSetKeyDataPair_WhenClassIsPresent() {
    // Given
    IncludedSpsTradeLineItem includedSpsTradeLineItem =
        buildIncludedSpsTradeLineItem("class", "Class I");

    // When
    ComplementParameterSetKeyDataPair actual = classMapper.map(includedSpsTradeLineItem);

    // Then
    assertThat(actual).isEqualTo(ComplementParameterSetKeyDataPair.builder()
        .key("class")
        .data("Class I")
        .build());
  }

  @Test
  void map_DoesNotReturnComplementParameterSetKeyDataPair_WhenClassIsNotPresent() {
    // Given
    IncludedSpsTradeLineItem includedSpsTradeLineItem =
        buildIncludedSpsTradeLineItem("invalid", "Class I");

    // When
    ComplementParameterSetKeyDataPair actual = classMapper.map(includedSpsTradeLineItem);

    // Then
    assertThat(actual).isNull();
  }

  @Test
  void map_DoesNotReturnComplementParameterSetKeyDataPair_WhenContentIsNull() {
    // Given
    IncludedSpsTradeLineItem includedSpsTradeLineItem =
        buildIncludedSpsTradeLineItem("class", "Class I");
    includedSpsTradeLineItem.getAdditionalInformationSpsNote().get(0).setContent(null);

    // When
    ComplementParameterSetKeyDataPair actual = classMapper.map(includedSpsTradeLineItem);

    // Then
    assertThat(actual).isNull();
  }

  @Test
  void map_DoesNotReturnComplementParameterSetKeyDataPair_WhenContentValueIsNull() {
    // Given
    IncludedSpsTradeLineItem includedSpsTradeLineItem =
        buildIncludedSpsTradeLineItem("class", "Class I");
    includedSpsTradeLineItem
        .getAdditionalInformationSpsNote()
        .get(0)
        .getContent()
        .get(0)
        .setValue(null);

    // When
    ComplementParameterSetKeyDataPair actual = classMapper.map(includedSpsTradeLineItem);

    // Then
    assertThat(actual).isNull();
  }

  private IncludedSpsTradeLineItem buildIncludedSpsTradeLineItem(String code, String value) {
    SpsNoteType spsNoteType =
        new SpsNoteType()
            .withSubjectCode(new CodeType().withValue(code))
            .withContent(List.of(new TextType().withValue(value)));
    IncludedSpsTradeLineItem spsTradeLineItem = new IncludedSpsTradeLineItem();
    spsTradeLineItem.setAdditionalInformationSpsNote(List.of(spsNoteType));

    return spsTradeLineItem;
  }
}
