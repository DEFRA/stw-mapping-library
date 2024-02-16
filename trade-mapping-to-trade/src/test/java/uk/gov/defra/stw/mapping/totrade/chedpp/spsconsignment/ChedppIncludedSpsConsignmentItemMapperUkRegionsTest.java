package uk.gov.defra.stw.mapping.totrade.chedpp.spsconsignment;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.totrade.common.spsconsignment.SpsPartyMapper;
import uk.gov.defra.stw.mapping.totrade.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

class ChedppIncludedSpsConsignmentItemMapperUkRegionsTest {

  ObjectMapper objectMapper;

  private Notification notification;
  private ChedppIncludedSpsConsignmentItemMapper chedppIncludedSpsConsignmentItemMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    chedppIncludedSpsConsignmentItemMapper = new ChedppIncludedSpsConsignmentItemMapper(new SpsPartyMapper());
  }

  @Test
  void map_ReturnsSPSConsignmentItem_whenUkRegionCountryOfOrigin() throws Exception {
    // Given
    String notificationString = ResourceUtil.readFileToString("classpath:ukCountryOfOriginCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);

    // When
    var includedSpsConsignmentItem =
        chedppIncludedSpsConsignmentItemMapper.map(notification);

    var countryOfOrigin = includedSpsConsignmentItem
        .getIncludedSpsTradeLineItem()
        .get(1)
        .getOriginSpsCountry();

    var countryIdValue = countryOfOrigin.get(0).getId().getValue();
    var countrySubdivisionName = countryOfOrigin.get(0)
        .getSubordinateSpsCountrySubDivision().get(0).getName().get(0).getValue();

    // Then
    assertThat(includedSpsConsignmentItem.getIncludedSpsTradeLineItem().size()).isEqualTo(5);
    assertThat(countryIdValue).isEqualTo("GB");
    assertThat(countrySubdivisionName).isEqualTo("GB-NIR");
  }

  @Test
  void map_ReturnsSPSConsignmentItem_whenNonUkRegionCountryOfOrigin() throws Exception {
    // Given
    String notificationString = ResourceUtil.readFileToString("classpath:nonUkCountryOfOriginCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);

    // When
    var includedSpsConsignmentItem =
        chedppIncludedSpsConsignmentItemMapper.map(notification);

    var countryOfOrigin = includedSpsConsignmentItem
        .getIncludedSpsTradeLineItem()
        .get(1)
        .getOriginSpsCountry();

    var countryIdValue = countryOfOrigin.get(0).getId().getValue();
    var countrySubdivision = countryOfOrigin.get(0)
        .getSubordinateSpsCountrySubDivision();

    // Then
    assertThat(includedSpsConsignmentItem.getIncludedSpsTradeLineItem().size()).isEqualTo(5);
    assertThat(countryIdValue).isEqualTo("AF");
    assertThat(countrySubdivision).isEmpty();
  }

}
