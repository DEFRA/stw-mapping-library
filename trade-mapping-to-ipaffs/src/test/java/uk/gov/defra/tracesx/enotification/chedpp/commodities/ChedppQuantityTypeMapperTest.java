package uk.gov.defra.tracesx.enotification.chedpp.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.trade.dto.CodeType;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.tracesx.trade.dto.SpsNoteType;
import uk.gov.defra.tracesx.trade.dto.TextType;

class ChedppQuantityTypeMapperTest {

  private static final String TYPE_QUANTITY = "type_quantity";
  private static final String QUANTITY = "quantity";

  private static final Map<String, String> QUANTITY_TYPE_MAP = Map.of(
      "STM", "Stems",
      "BLB", "Bulbs",
      "CRZ", "Corms and rhizomes",
      "PTC", "Plants in tissue culture",
      "SDS", "Seeds",
      "KGM", "Kilograms",
      "PCS", "Pieces");

  private final ChedppQuantityTypeMapper chedppQuantityTypeMapper = new ChedppQuantityTypeMapper();

  @Test
  void map_ReturnsKeyDataPair_WhenQuantitySpsNoteIsPresent() {
    QUANTITY_TYPE_MAP.keySet()
        .forEach(key -> assertReturnsKeyDataPair(key, QUANTITY_TYPE_MAP.get(key)));
  }

  @Test
  void map_ReturnsNull_WhenNoSpsNotePresent() {
    // Given
    IncludedSpsTradeLineItem includedSpsTradeLineItem =
        new IncludedSpsTradeLineItem().withAdditionalInformationSpsNote(Collections.emptyList());

    // When
    ComplementParameterSetKeyDataPair result = chedppQuantityTypeMapper.map(includedSpsTradeLineItem);

    // Then
    assertThat(result).isNull();
  }

  @Test
  void map_ReturnsNull_WhenNoSpsNotesPresent() {
    // Given
    IncludedSpsTradeLineItem spsTradeLineItem = new IncludedSpsTradeLineItem();

    // When
    ComplementParameterSetKeyDataPair result = chedppQuantityTypeMapper.map(spsTradeLineItem);

    // Then
    assertThat(result).isNull();
  }

  @Test
  void map_ReturnsNull_WhenQuantityTypeNotRecognised() {
    // Given
    IncludedSpsTradeLineItem spsTradeLineItem =
        new IncludedSpsTradeLineItem()
            .withAdditionalInformationSpsNote(
                Collections.singletonList(
                    new SpsNoteType()
                        .withContentCode(Collections.singletonList(new CodeType().withValue("ABC")))
                        .withSubjectCode(new CodeType().withValue(QUANTITY))));
    // When
    ComplementParameterSetKeyDataPair result = chedppQuantityTypeMapper.map(spsTradeLineItem);
    // Then
    assertThat(result).isNull();
  }

  @Test
  void map_ReturnsNull_WhenContentCodeIsNull(){
    // Given
    IncludedSpsTradeLineItem spsTradeLineItem = constructIncludedSpsTradeLineItem(null, QUANTITY);

    // When
    ComplementParameterSetKeyDataPair result = chedppQuantityTypeMapper.map(spsTradeLineItem);

    // Then
    assertThat(result).isNull();
  }

  @Test
  void map_ReturnsNull_WhenSubjectCodeIsNull(){
    // Given
    IncludedSpsTradeLineItem spsTradeLineItem = constructIncludedSpsTradeLineItem("SDS", null);

    // When
    ComplementParameterSetKeyDataPair result = chedppQuantityTypeMapper.map(spsTradeLineItem);

    // Then
    assertThat(result).isNull();
  }

  private void assertReturnsKeyDataPair(String contentCodeValue, String dataValue){
    IncludedSpsTradeLineItem spsTradeLineItem = constructIncludedSpsTradeLineItem(contentCodeValue, QUANTITY);

    ComplementParameterSetKeyDataPair result = chedppQuantityTypeMapper.map(spsTradeLineItem);
    ComplementParameterSetKeyDataPair expectedResult =
        ComplementParameterSetKeyDataPair.builder().key(TYPE_QUANTITY).data(dataValue).build();
    assertThat(result).isEqualTo(expectedResult);
  }

  private IncludedSpsTradeLineItem constructIncludedSpsTradeLineItem(String contentCodeValue, String subjectCodeValue) {
    return new IncludedSpsTradeLineItem()
        .withAdditionalInformationSpsNote(
            Collections.singletonList(
                new SpsNoteType()
                    .withContentCode(Collections.singletonList(new CodeType().withValue(contentCodeValue)))
                    .withContent(Collections.singletonList(new TextType().withValue("TEST")))
                    .withSubjectCode(new CodeType().withValue(subjectCodeValue))));
  }
}
