package uk.gov.defra.stw.mapping.toipaffs.cloning.cheda.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.MeasureType;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

class ChedaNumberOfAnimalsMapperTest {

  private ChedaNumberOfAnimalsMapper mapper;

  @BeforeEach
  void setup() {
    mapper = new ChedaNumberOfAnimalsMapper();
  }

  @Test
  void map_ReturnsComplementParameterSetKeyDataPair_WhenComplete()
      throws NotificationMapperException {
    IncludedSpsTradeLineItem tradeLineItem = new IncludedSpsTradeLineItem().withNetVolumeMeasure(
        new MeasureType().withUnitCode("H87").withValue(5.0));

    ComplementParameterSetKeyDataPair expectedComplementParameterSetKeyDataPair =
        ComplementParameterSetKeyDataPair.builder()
            .key("number_animal")
            .data("5")
            .build();

    assertThat(mapper.map(tradeLineItem)).isEqualTo(expectedComplementParameterSetKeyDataPair);
  }

  @Test
  void map_ReturnsNull_WhenNetVolumeMeasureIsNull()
      throws NotificationMapperException {
    assertThat(mapper.map(new IncludedSpsTradeLineItem())).isNull();
  }

  @Test
  void map_ReturnsNull_WhenNetVolumeMeasureIsNotH87()
      throws NotificationMapperException {
    IncludedSpsTradeLineItem tradeLineItem = new IncludedSpsTradeLineItem().withNetVolumeMeasure(
        new MeasureType().withUnitCode("Unknown").withValue(5.0));

    assertThat(mapper.map(tradeLineItem)).isNull();
  }

  @Test
  void map_ReturnsNull_WhenNetVolumeMeasureUnitCodeIsNull()
      throws NotificationMapperException {
    IncludedSpsTradeLineItem tradeLineItem = new IncludedSpsTradeLineItem().withNetVolumeMeasure(
        new MeasureType().withUnitCode(null).withValue(5.0));

    assertThat(mapper.map(tradeLineItem)).isNull();
  }
}
