package uk.gov.defra.stw.mapping.toipaffs.chedpp.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IncludedSpsConsignmentItem;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.MeasureType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;

class TotalGrossVolumeMapperTest {

  private TotalGrossVolumeMapper totalGrossVolumeMapper;

  @BeforeEach
  void setup() {
    totalGrossVolumeMapper = new TotalGrossVolumeMapper();
  }

  @Test
  void map_ReturnsTotalGrossVolume_WhenSingleGrossVolume() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withIncludedSpsConsignmentItem(List.of(new IncludedSpsConsignmentItem()
                .withIncludedSpsTradeLineItem(List.of(new IncludedSpsTradeLineItem()
                    .withGrossVolumeMeasure(new MeasureType()
                        .withValue(1.0)))))));

    BigDecimal actual = totalGrossVolumeMapper.map(spsCertificate);

    assertThat(actual).isEqualTo(BigDecimal.valueOf(1.0));
  }

  @Test
  void map_ReturnsTotalGrossVolume_WhenMultipleGrossVolumes() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withIncludedSpsConsignmentItem(List.of(new IncludedSpsConsignmentItem()
                .withIncludedSpsTradeLineItem(List.of(
                    new IncludedSpsTradeLineItem()
                        .withGrossVolumeMeasure(new MeasureType()
                            .withValue(1.0)),
                    new IncludedSpsTradeLineItem()
                        .withGrossVolumeMeasure(new MeasureType()
                            .withValue(2.0))
                )))));

    BigDecimal actual = totalGrossVolumeMapper.map(spsCertificate);

    assertThat(actual).isEqualTo(BigDecimal.valueOf(3.0));
  }

  @Test
  void map_ReturnsNull_WhenNoGrossVolumes() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withIncludedSpsConsignmentItem(List.of(new IncludedSpsConsignmentItem()
                .withIncludedSpsTradeLineItem(List.of(new IncludedSpsTradeLineItem())))));

    BigDecimal actual = totalGrossVolumeMapper.map(spsCertificate);

    assertThat(actual).isNull();
  }
}
