package uk.gov.defra.stw.mapping.toipaffs.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.dto.SpsCountryType;

class ConsignedCountryMapperTest {

  private final ConsignedCountryMapper consignedCountryMapper = new ConsignedCountryMapper();

  @Test
  void map_ReturnsConsignedCountry() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withExportSpsCountry(new SpsCountryType()
                .withId(new IDType()
                    .withValue("COUNTRY_CODE"))));

    String actual = consignedCountryMapper.map(spsCertificate);

    assertThat(actual).isEqualTo("COUNTRY_CODE");
  }
}
