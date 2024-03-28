package uk.gov.defra.stw.mapping.toipaffs.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsConsignmentItem;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.dto.SpsCountryType;

class CountryOfOriginMapperTest {

  private final CountryOfOriginMapper countryOfOriginMapper = new CountryOfOriginMapper();

  @Test
  void map_ReturnsCountryOfOrigin_WhenCompleteSpsCertificate() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withIncludedSpsConsignmentItem(List.of(new IncludedSpsConsignmentItem()
                .withIncludedSpsTradeLineItem(List.of(new IncludedSpsTradeLineItem()
                    .withOriginSpsCountry(List.of(new SpsCountryType()
                        .withId(new IDType()
                            .withValue("ID")))))))));

    String actual = countryOfOriginMapper.map(spsCertificate);

    assertThat(actual).isEqualTo("ID");
  }
}
