package uk.gov.defra.stw.mapping.toipaffs.chedpp.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

class VarietyMapperTest {

  private VarietyMapper varietyMapper;

  @BeforeEach
  void setup() {
    varietyMapper = new VarietyMapper();
  }

  @Test
  void map_ReturnsComplementParameterSetKeyDataPair_WhenVarietyIsPresent() {
    // Given
    IncludedSpsTradeLineItem includedSpsTradeLineItem =
        buildIncludedSpsTradeLineItem("variety", "Pink Lady");

    // When
    ComplementParameterSetKeyDataPair actual = varietyMapper.map(includedSpsTradeLineItem);

    // Then
    assertThat(actual).isEqualTo(ComplementParameterSetKeyDataPair.builder()
        .key("variety")
        .data("Pink Lady")
        .build());
  }

  @Test
  void map_DoesNotReturnComplementParameterSetKeyDataPair_WhenVarietyIsNotPresent() {
    // Given
    IncludedSpsTradeLineItem includedSpsTradeLineItem =
        buildIncludedSpsTradeLineItem("invalid", "Pink Lady");

    // When
    ComplementParameterSetKeyDataPair actual = varietyMapper.map(includedSpsTradeLineItem);

    // Then
    assertThat(actual).isNull();
  }

  @Test
  void map_DoesNotReturnComplementParameterSetKeyDataPair_WhenContentIsNull() {
    // Given
    IncludedSpsTradeLineItem includedSpsTradeLineItem =
        buildIncludedSpsTradeLineItem("variety", "Pink Lady");
    includedSpsTradeLineItem.getAdditionalInformationSpsNote().get(0).setContent(null);

    // When
    ComplementParameterSetKeyDataPair actual = varietyMapper.map(includedSpsTradeLineItem);

    // Then
    assertThat(actual).isNull();
  }

  @Test
  void map_DoesNotReturnComplementParameterSetKeyDataPair_WhenContentValueIsNull() {
    // Given
    IncludedSpsTradeLineItem includedSpsTradeLineItem =
        buildIncludedSpsTradeLineItem("variety", "Pink Lady");
    includedSpsTradeLineItem
        .getAdditionalInformationSpsNote()
        .get(0)
        .getContent()
        .get(0)
        .setValue(null);

    // When
    ComplementParameterSetKeyDataPair actual = varietyMapper.map(includedSpsTradeLineItem);

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
