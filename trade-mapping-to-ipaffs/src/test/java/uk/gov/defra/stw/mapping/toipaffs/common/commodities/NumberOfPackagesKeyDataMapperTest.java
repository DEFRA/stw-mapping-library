package uk.gov.defra.stw.mapping.toipaffs.common.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.ItemQuantity;
import uk.gov.defra.stw.mapping.dto.PhysicalSpsPackage;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

class NumberOfPackagesKeyDataMapperTest {

  @Test
  void map_ReturnsComplementParameterSetKeyDataPair_WhenComplete()
      throws NotificationMapperException {
    PhysicalSpsPackage physicalSpsPackage = new PhysicalSpsPackage()
        .withItemQuantity(new ItemQuantity()
            .withValue(2D));

    ComplementParameterSetKeyDataPair expectedComplementParameterSetKeyDataPair =
        ComplementParameterSetKeyDataPair.builder()
            .key("number_package")
            .data("2")
            .build();

    assertThat(new NumberOfPackagesKeyDataMapper().map(physicalSpsPackage))
        .isEqualTo(expectedComplementParameterSetKeyDataPair);
  }
}
