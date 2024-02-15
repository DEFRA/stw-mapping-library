package uk.gov.defra.tracesx.enotification.chedpp.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.trade.dto.CodeType;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.tracesx.trade.dto.SpsNoteType;
import uk.gov.defra.tracesx.trade.dto.TextType;

class ChedppQuantityMapperTest {
  private static final String QUANTITY = "quantity";

  private final ChedppQuantityMapper chedppQuantityMapper = new ChedppQuantityMapper();

  @Test
  void map_ReturnsKeyDataPair_WhenQuantitySpsNoteIsPresent() {
    // Given
    IncludedSpsTradeLineItem includedSpsTradeLineItem =
        constructIncludedSpsTradeLineItem("15.0", QUANTITY);

    // When
    ComplementParameterSetKeyDataPair result = chedppQuantityMapper.map(includedSpsTradeLineItem);

    // Then
    ComplementParameterSetKeyDataPair expectedResult =
        ComplementParameterSetKeyDataPair.builder().key(QUANTITY).data("15.0").build();

    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void map_ReturnsNull_WhenNoSpsNotePresent() {
    // Given
    IncludedSpsTradeLineItem includedSpsTradeLineItem =
        new IncludedSpsTradeLineItem().withAdditionalInformationSpsNote(Collections.emptyList());

    // When
    ComplementParameterSetKeyDataPair result = chedppQuantityMapper.map(includedSpsTradeLineItem);

    // Then
    assertThat(result).isNull();
  }

  @Test
  void map_ReturnsNull_WhenNoSpsNotesPresent() {
    // Given
    IncludedSpsTradeLineItem includedSpsTradeLineItem = new IncludedSpsTradeLineItem();

    // When
    ComplementParameterSetKeyDataPair result = chedppQuantityMapper.map(includedSpsTradeLineItem);

    // Then
    assertThat(result).isNull();
  }

  @Test
  void map_ReturnsNull_WhenContentValueIsNull(){
    // Given
    IncludedSpsTradeLineItem spsTradeLineItem =
        constructIncludedSpsTradeLineItem(null, QUANTITY);

    // When
    ComplementParameterSetKeyDataPair result = chedppQuantityMapper.map(spsTradeLineItem);

    // Then
    assertThat(result).isNull();
  }

  @Test
  void map_ReturnsNull_WhenContentIsNull(){
    // Given
    IncludedSpsTradeLineItem spsTradeLineItem = new IncludedSpsTradeLineItem()
        .withAdditionalInformationSpsNote(
            Collections.singletonList(
                new SpsNoteType()
                    .withContent(null)
                    .withSubjectCode(new CodeType().withValue(QUANTITY))));

    // When
    ComplementParameterSetKeyDataPair result = chedppQuantityMapper.map(spsTradeLineItem);

    // Then
    assertThat(result).isNull();
  }

  @Test
  void map_ReturnsNull_WhenContentTextTypeIsDefinedButHasNoValue(){
    // Given
    IncludedSpsTradeLineItem spsTradeLineItem = new IncludedSpsTradeLineItem()
        .withAdditionalInformationSpsNote(
            Collections.singletonList(
                new SpsNoteType()
                    .withContent(Collections.singletonList(new TextType().withLanguageID("EN")))
                    .withSubjectCode(new CodeType().withValue(QUANTITY))));

    // When
    ComplementParameterSetKeyDataPair result = chedppQuantityMapper.map(spsTradeLineItem);

    // Then
    assertThat(result).isNull();
  }

  @Test
  void map_ReturnsNull_WhenSubjectCodeIsNull(){
    // Given
    IncludedSpsTradeLineItem spsTradeLineItem =
        constructIncludedSpsTradeLineItem("12.0", null);

    // When
    ComplementParameterSetKeyDataPair result = chedppQuantityMapper.map(spsTradeLineItem);

    // Then
    assertThat(result).isNull();
  }

  private IncludedSpsTradeLineItem constructIncludedSpsTradeLineItem(String contentValue, String subjectCodeValue) {
    return new IncludedSpsTradeLineItem()
        .withAdditionalInformationSpsNote(
            Collections.singletonList(
                new SpsNoteType()
                    .withContent(Collections.singletonList(new TextType().withValue(contentValue)))
                    .withSubjectCode(new CodeType().withValue(subjectCodeValue))));
  }
}
