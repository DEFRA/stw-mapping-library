package uk.gov.defra.stw.mapping.toipaffs.common.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IncludedSpsConsignmentItem;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.ItemQuantity;
import uk.gov.defra.stw.mapping.dto.PhysicalSpsPackage;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;

class NumberOfPackagesMapperTest {

  private final NumberOfPackagesMapper mapper = new NumberOfPackagesMapper();

  @Test
  void map_ReturnsTotalNumberOfPackages_WhenSingleLineItem() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withIncludedSpsConsignmentItem(List.of(new IncludedSpsConsignmentItem()
                .withIncludedSpsTradeLineItem(List.of(new IncludedSpsTradeLineItem()
                    .withPhysicalSpsPackage(List.of(new PhysicalSpsPackage()
                        .withItemQuantity(new ItemQuantity().withValue(1.0))
                    ))
                ))
            )));

    int actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo(1);
  }

  @Test
  void map_ReturnsTotalNumberOfPackages_WhenMultipleLineItems() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withIncludedSpsConsignmentItem(List.of(new IncludedSpsConsignmentItem()
                .withIncludedSpsTradeLineItem(List.of(
                    new IncludedSpsTradeLineItem()
                        .withPhysicalSpsPackage(List.of(new PhysicalSpsPackage()
                            .withItemQuantity(new ItemQuantity().withValue(1.0))
                        )),
                    new IncludedSpsTradeLineItem()
                        .withPhysicalSpsPackage(List.of(new PhysicalSpsPackage()
                            .withItemQuantity(new ItemQuantity().withValue(2.0))
                        ))
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
                    .withPhysicalSpsPackage(List.of(new PhysicalSpsPackage()
                        .withItemQuantity(new ItemQuantity().withValue(1.9))
                    ))
                ))
            )));

    int actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo(1);
  }
}
