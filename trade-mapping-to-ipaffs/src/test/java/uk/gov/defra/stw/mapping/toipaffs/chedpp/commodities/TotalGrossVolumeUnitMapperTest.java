package uk.gov.defra.stw.mapping.toipaffs.chedpp.commodities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IncludedSpsConsignmentItem;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.MeasureType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;

class TotalGrossVolumeUnitUnitMapperTest {

  private TotalGrossVolumeUnitMapper totalGrossVolumeUnitMapper;

  @BeforeEach
  void setup() {
    totalGrossVolumeUnitMapper = new TotalGrossVolumeUnitMapper();
  }

  @Test
  void map_ReturnsGrossVolumeUnit_WhenSingleGrossVolume() throws NotificationMapperException {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withIncludedSpsConsignmentItem(List.of(new IncludedSpsConsignmentItem()
                .withIncludedSpsTradeLineItem(List.of(new IncludedSpsTradeLineItem()
                    .withGrossVolumeMeasure(new MeasureType()
                        .withUnitCode("TEST_VALUE")))))));

    String actual = totalGrossVolumeUnitMapper.map(spsCertificate);

    assertThat(actual).isEqualTo("TEST_VALUE");
  }

  @Test
  void map_ReturnsGrossVolumeUnit_WhenMultipleGrossVolumesWithSameUnit() throws NotificationMapperException {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withIncludedSpsConsignmentItem(List.of(new IncludedSpsConsignmentItem()
                .withIncludedSpsTradeLineItem(List.of(
                    new IncludedSpsTradeLineItem()
                        .withGrossVolumeMeasure(new MeasureType()
                            .withUnitCode("TEST_VALUE")),
                    new IncludedSpsTradeLineItem()
                        .withGrossVolumeMeasure(new MeasureType()
                            .withUnitCode("TEST_VALUE"))
                )))));

    String actual = totalGrossVolumeUnitMapper.map(spsCertificate);

    assertThat(actual).isEqualTo("TEST_VALUE");
  }

  @Test
  void map_ThrowsException_WhenMultipleGrossVolumesWithDifferentUnit() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withIncludedSpsConsignmentItem(List.of(new IncludedSpsConsignmentItem()
                .withIncludedSpsTradeLineItem(List.of(
                    new IncludedSpsTradeLineItem()
                        .withGrossVolumeMeasure(new MeasureType()
                            .withUnitCode("TEST_VALUE")),
                    new IncludedSpsTradeLineItem()
                        .withGrossVolumeMeasure(new MeasureType()
                            .withUnitCode("DIFFERENT_VALUE"))
                )))));

    assertThatThrownBy(() -> totalGrossVolumeUnitMapper.map(spsCertificate))
        .isInstanceOf(NotificationMapperException.class)
        .hasMessage("Gross volume units are not all the same");
  }
}
