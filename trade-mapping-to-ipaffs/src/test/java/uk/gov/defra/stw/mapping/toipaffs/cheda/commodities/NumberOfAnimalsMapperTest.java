package uk.gov.defra.stw.mapping.toipaffs.cheda.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IncludedSpsConsignmentItem;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.MeasureType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;

class NumberOfAnimalsMapperTest {

  private final NumberOfAnimalsMapper mapper = new NumberOfAnimalsMapper();

  @Test
  void map_ReturnsTotalNumberOfAnimals_WhenSingleLineItem() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withIncludedSpsConsignmentItem(List.of(new IncludedSpsConsignmentItem()
                .withIncludedSpsTradeLineItem(List.of(new IncludedSpsTradeLineItem()
                    .withNetVolumeMeasure(new MeasureType()
                        .withValue(1.0))
                ))
            )));

    int actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo(1);
  }

  @Test
  void map_ReturnsTotalNumberOfAnimals_WhenMultipleLineItems() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withIncludedSpsConsignmentItem(List.of(new IncludedSpsConsignmentItem()
                .withIncludedSpsTradeLineItem(List.of(
                    new IncludedSpsTradeLineItem()
                        .withNetVolumeMeasure(new MeasureType()
                            .withValue(1.0)),
                    new IncludedSpsTradeLineItem()
                        .withNetVolumeMeasure(new MeasureType()
                            .withValue(2.0))
                ))
            )));

    int actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo(3);
  }

  @Test
  void map_DropsDecimalPart_WhenNumberIsNotWhole() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withIncludedSpsConsignmentItem(List.of(new IncludedSpsConsignmentItem()
                .withIncludedSpsTradeLineItem(List.of(new IncludedSpsTradeLineItem()
                    .withNetVolumeMeasure(new MeasureType()
                        .withValue(1.9))
                ))
            )));

    int actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo(1);
  }
}
