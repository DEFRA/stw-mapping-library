package uk.gov.defra.stw.mapping.toipaffs.common.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IncludedSpsConsignmentItem;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.MeasureType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;

class TotalGrossWeightMapperTest {

  private final TotalGrossWeightMapper totalGrossWeightMapper = new TotalGrossWeightMapper();

  @Test
  void map_ReturnsTotalGrossWeight_WhenSingleGrossWeight() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withIncludedSpsConsignmentItem(List.of(new IncludedSpsConsignmentItem()
                .withIncludedSpsTradeLineItem(List.of(new IncludedSpsTradeLineItem()
                    .withGrossWeightMeasure(new MeasureType().withValue(1.0)))))));

    BigDecimal actual = totalGrossWeightMapper.map(spsCertificate);

    assertThat(actual).isEqualTo(BigDecimal.valueOf(1.0));
  }

  @Test
  void map_ReturnsTotalGrossWeight_WhenMultipleGrossWeights() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withIncludedSpsConsignmentItem(List.of(new IncludedSpsConsignmentItem()
                .withIncludedSpsTradeLineItem(List.of(
                    new IncludedSpsTradeLineItem()
                        .withGrossWeightMeasure(new MeasureType().withValue(1.0)),
                    new IncludedSpsTradeLineItem()
                        .withGrossWeightMeasure(new MeasureType().withValue(2.0))
                )))));

    BigDecimal actual = totalGrossWeightMapper.map(spsCertificate);

    assertThat(actual).isEqualTo(BigDecimal.valueOf(3.0));
  }
}
