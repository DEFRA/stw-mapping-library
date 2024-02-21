package uk.gov.defra.stw.mapping.toipaffs.enotification.chedpp.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.SequenceNumeric;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

class ChedppSequenceNumericMapperTest {

  private static final String SEQUENCE_NUMERIC = "sequence_numeric";

  private ChedppSequenceNumericMapper sequenceNumericMapper;

  @BeforeEach
  void setup() {
    sequenceNumericMapper = new ChedppSequenceNumericMapper();
  }

  @Test
  void map_ReturnsNewSequenceNumericKeyDataPair_WhenSequenceNumericIsPresent() {
    // Given
    IncludedSpsTradeLineItem includedSpsTradeLineItem =
        constructIncludedSpsTradeLineItem();

    // When
    ComplementParameterSetKeyDataPair result = sequenceNumericMapper.map(includedSpsTradeLineItem);

    // Then
    ComplementParameterSetKeyDataPair expectedResult =
        ComplementParameterSetKeyDataPair.builder().key(SEQUENCE_NUMERIC).data("1").build();

    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void map_ReturnsNull_WhenNoSequenceNumericPresent() {
    // Given
    IncludedSpsTradeLineItem includedSpsTradeLineItem =
        new IncludedSpsTradeLineItem().withSequenceNumeric(null);

    // When
    ComplementParameterSetKeyDataPair result = sequenceNumericMapper.map(includedSpsTradeLineItem);

    // Then
    assertThat(result).isNull();
  }

  private IncludedSpsTradeLineItem constructIncludedSpsTradeLineItem() {
    return new IncludedSpsTradeLineItem()
        .withSequenceNumeric(new SequenceNumeric().withValue(1));
  }
}
