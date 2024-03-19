package uk.gov.defra.stw.mapping.toipaffs.common;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsConsignmentItem;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.dto.SpsCountryType;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;

class CountryOfOriginMapperTest {

  private CountryOfOriginMapper countryOfOriginMapper;

  @BeforeEach
  void setup() throws JsonProcessingException {
    countryOfOriginMapper = new CountryOfOriginMapper();
  }

  @Test
  void map_ReturnsCountryOfOrigin_WhenCompleteSpsCertificate() throws NotificationMapperException {
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
