package uk.gov.defra.stw.mapping.toipaffs.cheda.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.MeasureType;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

class NumberOfAnimalsKeyDataMapperTest {

  private final NumberOfAnimalsKeyDataMapper mapper = new NumberOfAnimalsKeyDataMapper();

  @Test
  void map_ReturnsComplementParameterSetKeyDataPair() {
    IncludedSpsTradeLineItem includedSpsTradeLineItem = new IncludedSpsTradeLineItem()
        .withNetVolumeMeasure(new MeasureType().withValue(1.0));

    ComplementParameterSetKeyDataPair actual = mapper.map(includedSpsTradeLineItem);

    assertThat(actual).isEqualTo(ComplementParameterSetKeyDataPair.builder()
        .key("number_animal")
        .data("1")
        .build());
  }

  @Test
  void map_DropsDecimalPart_WhenNumberIsNotWhole() {
    IncludedSpsTradeLineItem includedSpsTradeLineItem = new IncludedSpsTradeLineItem()
        .withNetVolumeMeasure(new MeasureType().withValue(1.9));

    ComplementParameterSetKeyDataPair actual = mapper.map(includedSpsTradeLineItem);

    assertThat(actual).isEqualTo(ComplementParameterSetKeyDataPair.builder()
        .key("number_animal")
        .data("1")
        .build());
  }
}
