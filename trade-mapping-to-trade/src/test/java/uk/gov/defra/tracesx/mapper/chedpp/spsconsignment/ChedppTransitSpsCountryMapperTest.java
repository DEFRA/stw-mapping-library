package uk.gov.defra.tracesx.mapper.chedpp.spsconsignment;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.TRANSHIPMENT_TO;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.TRANSIT_TO_3RD_COUNTRY;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.mapper.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum;
import uk.gov.defra.tracesx.trade.dto.SpsCountryType;

class ChedppTransitSpsCountryMapperTest {

  private static final String COUNTRY = "GBFXT1";

  private Purpose purpose;
  private ChedppTransitSpsCountryMapper mapper;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    purpose = objectMapper.readValue(notificationString, Notification.class).getPartOne().getPurpose();

    mapper = new ChedppTransitSpsCountryMapper();
  }

  @Test
  void map_ReturnsSpsCountryType_WhenTranshipmentTo() throws Exception {
    purpose.setPurposeGroup(TRANSHIPMENT_TO);
    purpose.setFinalBIP(COUNTRY);

    List<SpsCountryType> spsCountryType = mapper.map(purpose);

    String actualSpsCountryType = objectMapper.writeValueAsString(spsCountryType);
    String expectedSpsCountryType = ResourceUtil
        .readFileToString("classpath:mapping/chedpp/spsconsignment/transitSpsCountryForTranshipmentTo.json");

    assertThat(actualSpsCountryType).isEqualTo(expectedSpsCountryType);
  }

  @Test
  void map_ReturnsSpsCountryType_WhenTransitToThirdCountry() throws Exception {
    purpose.setPurposeGroup(TRANSIT_TO_3RD_COUNTRY);
    purpose.setExitBIP(COUNTRY);
    purpose.setTransitThirdCountries(List.of("BH", "CM"));

    List<SpsCountryType> spsCountryType = mapper.map(purpose);

    String actualSpsCountryType = objectMapper.writeValueAsString(spsCountryType);
    String expectedSpsCountryType = ResourceUtil
        .readFileToString("classpath:mapping/chedpp/spsconsignment/transitSpsCountryForTransitToThirdCountry.json");

    assertThat(actualSpsCountryType).isEqualTo(expectedSpsCountryType);
  }

  @Test
  void map_ReturnsEmptyList_WhenAllOtherPurposes() {
    Arrays.stream(PurposeGroupEnum.values())
        .filter(p -> p != TRANSHIPMENT_TO)
        .filter(p -> p != TRANSIT_TO_3RD_COUNTRY)
        .forEach(p -> {
          purpose.setPurposeGroup(p);
          assertThat(mapper.map(purpose)).isEmpty();
        });
  }
}
