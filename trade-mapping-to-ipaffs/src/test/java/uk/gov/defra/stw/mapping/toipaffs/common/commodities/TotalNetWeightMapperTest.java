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

class TotalNetWeightMapperTest {

  private final TotalNetWeightMapper totalNetWeightMapper = new TotalNetWeightMapper();

  @Test
  void map_ReturnsTotalNetWeight_WhenSingleNetWeight() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withIncludedSpsConsignmentItem(List.of(new IncludedSpsConsignmentItem()
                .withIncludedSpsTradeLineItem(List.of(new IncludedSpsTradeLineItem()
                    .withNetWeightMeasure(new MeasureType()
                        .withValue(1.0)))))));

    BigDecimal actual = totalNetWeightMapper.map(spsCertificate);

    assertThat(actual).isEqualTo(BigDecimal.valueOf(1.0));
  }

  @Test
  void map_ReturnsTotalNetWeight_WhenMultipleNetWeights() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withIncludedSpsConsignmentItem(List.of(new IncludedSpsConsignmentItem()
                .withIncludedSpsTradeLineItem(List.of(
                    new IncludedSpsTradeLineItem()
                        .withNetWeightMeasure(new MeasureType()
                            .withValue(1.0)),
                    new IncludedSpsTradeLineItem()
                        .withNetWeightMeasure(new MeasureType()
                            .withValue(2.0))
                )))));

    BigDecimal actual = totalNetWeightMapper.map(spsCertificate);

    assertThat(actual).isEqualTo(BigDecimal.valueOf(3.0));
  }
}
