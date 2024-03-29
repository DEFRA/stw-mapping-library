package uk.gov.defra.stw.mapping.toipaffs.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IncludedSpsConsignmentItem;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.dto.SpsCountrySubDivisionType;
import uk.gov.defra.stw.mapping.dto.SpsCountryType;
import uk.gov.defra.stw.mapping.dto.TextType;

class RegionOfOriginMapperTest {

  private RegionOfOriginMapper regionOfOriginMapper;
  private SpsCertificate spsCertificate;

  @BeforeEach
  void setup() {
    regionOfOriginMapper = new RegionOfOriginMapper();

    SpsCountrySubDivisionType subDivisionType = new SpsCountrySubDivisionType()
        .withName(Collections.singletonList(new TextType().withValue("regionOfOrigin")));

    SpsCountryType spsCountryType = new SpsCountryType()
        .withSubordinateSpsCountrySubDivision(Collections.singletonList(subDivisionType));

    IncludedSpsTradeLineItem tradeLineItem = new IncludedSpsTradeLineItem()
        .withOriginSpsCountry(Collections.singletonList(spsCountryType));

    IncludedSpsConsignmentItem consignmentItem = new IncludedSpsConsignmentItem()
        .withIncludedSpsTradeLineItem(Collections.singletonList(tradeLineItem));

    spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withIncludedSpsConsignmentItem(Collections.singletonList(consignmentItem)));
  }

  @Test
  void map_ReturnsRegionOfOriginString_WhenComplete() {
    assertThat(regionOfOriginMapper.map(spsCertificate)).isEqualTo("regionOfOrigin");
  }

  @Test
  void map_ReturnsNull_WhenNullSubordinateSpsCountrySubDivision() {
    spsCertificate
        .getSpsConsignment().getIncludedSpsConsignmentItem().stream()
        .flatMap(item -> item.getIncludedSpsTradeLineItem().stream())
        .toList().get(0)
        .getOriginSpsCountry().get(0)
        .setSubordinateSpsCountrySubDivision(null);

    assertThat(regionOfOriginMapper.map(spsCertificate)).isNull();
  }

  @Test
  void map_ReturnsNull_WhenEmptySubordinateSpsCountrySubDivision() {
    spsCertificate
        .getSpsConsignment().getIncludedSpsConsignmentItem().stream()
        .flatMap(item -> item.getIncludedSpsTradeLineItem().stream())
        .toList().get(0)
        .getOriginSpsCountry().get(0)
        .setSubordinateSpsCountrySubDivision(Collections.emptyList());

    assertThat(regionOfOriginMapper.map(spsCertificate)).isNull();
  }
}
