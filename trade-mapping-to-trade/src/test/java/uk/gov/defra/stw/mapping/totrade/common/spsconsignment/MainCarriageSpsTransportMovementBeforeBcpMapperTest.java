package uk.gov.defra.stw.mapping.totrade.common.spsconsignment;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.TransportMethod.AEROPLANE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.TransportMethod.RAILWAY_WAGON;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.TransportMethod.ROAD_VEHICLE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.TransportMethod.SHIP;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.totrade.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.TransportMethod;

class MainCarriageSpsTransportMovementBeforeBcpMapperTest {

  private static final String SCHEME_ID_VALUE_KEY = "#{beforeBcpSchemeIdValue}";
  private static final String MODE_CODE_VALUE_KEY = "#{modeCodeValue}";

  private Notification notification;
  private MainCarriageSpsTransportMovementBeforeBcpMapper
      mainCarriageSpsTransportMovementBeforeBcpMapper;
  private ObjectMapper objectMapper;

  public static Collection<Object[]> data() {
    return Arrays.asList(
        new Object[][] {
          {SHIP, "ship_imo_number_before_bcp", "1"},
          {RAILWAY_WAGON, "train_identifier_before_bcp", "2"},
          {ROAD_VEHICLE, "road_vehicle_registration_before_bcp", "3"},
          {AEROPLANE, "airplane_flight_number_before_bcp", "4"}
        });
  }

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);
    mainCarriageSpsTransportMovementBeforeBcpMapper =
        new MainCarriageSpsTransportMovementBeforeBcpMapper();
  }

  @ParameterizedTest
  @MethodSource("data")
  void map_ReturnsMainCarriageSpsTransportMovement_WhenAllFieldsCompleteBeforeBIP(TransportMethod transportMethod, String schemeId, String modeCodeValue)
      throws JsonProcessingException {
    notification.getPartOne().getMeansOfTransportFromEntryPoint().setType(transportMethod);

    MainCarriageSpsTransportMovement mainCarriageSpsTransportMovement =
        mainCarriageSpsTransportMovementBeforeBcpMapper.map(
            notification.getPartOne().getMeansOfTransportFromEntryPoint());
    String actualSps = objectMapper.writeValueAsString(mainCarriageSpsTransportMovement);

    String expectedSps =
        ResourceUtil.readFileToString(
            "classpath:mapping/common/spsconsignment/mainCarriageSpsTransportMovementCompleteBeforeBcp.json");
    expectedSps =
        expectedSps
            .replace(SCHEME_ID_VALUE_KEY, schemeId)
            .replace(MODE_CODE_VALUE_KEY, modeCodeValue);

    assertThat(actualSps).isEqualTo(expectedSps);
  }

  @Test
  void map_ReturnsNull_WhenMeansOfTransportFromEntryPointIsNull() {
    MainCarriageSpsTransportMovement mainCarriageSpsTransportMovement =
        mainCarriageSpsTransportMovementBeforeBcpMapper.map(null);

    assertThat(mainCarriageSpsTransportMovement).isNull();
  }
}
