package uk.gov.defra.stw.mapping.totrade.chedpp.spsconsignment;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.TRANSHIPMENT_TO;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.TRANSIT_TO_3RD_COUNTRY;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.SpsCountryType;
import uk.gov.defra.stw.mapping.totrade.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum;

class ChedppImportSpsCountryMapperTest {

  private static final String COUNTRY = "BE";

  private Purpose purpose;
  private ChedppImportSpsCountryMapper mapper;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    purpose = objectMapper.readValue(notificationString, Notification.class).getPartOne().getPurpose();

    mapper = new ChedppImportSpsCountryMapper();
  }

  @Test
  void map_ReturnsSpsCountryType_WhenTranshipmentTo() throws Exception {
    purpose.setPurposeGroup(TRANSHIPMENT_TO);
    purpose.setThirdCountryTranshipment(COUNTRY);

    assertSpsCountryType();
  }

  @Test
  void map_ReturnsSpsCountryType_WhenTransitToThirdCountry() throws Exception {
    purpose.setPurposeGroup(TRANSIT_TO_3RD_COUNTRY);
    purpose.setThirdCountry(COUNTRY);

    assertSpsCountryType();
  }

  @Test
  void map_ReturnsNull_WhenAllOtherPurposes() {
    Arrays.stream(PurposeGroupEnum.values())
        .filter(p -> p != TRANSHIPMENT_TO)
        .filter(p -> p != TRANSIT_TO_3RD_COUNTRY)
        .forEach(p -> {
          purpose.setPurposeGroup(p);
          assertThat(mapper.map(purpose)).isNull();
        });
  }

  private void assertSpsCountryType() throws JsonProcessingException {
    SpsCountryType spsCountryType = mapper.map(purpose);

    String actualSpsCountryType = objectMapper.writeValueAsString(spsCountryType);
    String expectedSpsCountryType = ResourceUtil.readFileToString("classpath:mapping/chedpp/spsconsignment/importSpsCountryComplete.json");

    assertThat(actualSpsCountryType).isEqualTo(expectedSpsCountryType);
  }
}
