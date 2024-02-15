package uk.gov.defra.tracesx.mapper.common.spsconsignment;

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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.defra.tracesx.mapper.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.TransportMethod;
import uk.gov.defra.tracesx.trade.dto.MainCarriageSpsTransportMovement;

class MainCarriageSpsTransportMovementAfterBcpMapperTest {

  private static final String AFTER_BCP_SCHEME_ID_VALUE_KEY = "#{afterBcpSchemeIdValue}";
  private static final String MODE_CODE_VALUE_KEY = "#{modeCodeValue}";

  private Notification notification;
  private MainCarriageSpsTransportMovementAfterBcpMapper
      mainCarriageSpsTransportMovementAfterBcpMapper;
  private ObjectMapper objectMapper;
  public static Collection<Object[]> data() {
    return Arrays.asList(
        new Object[][] {
          {SHIP, "ship_imo_number_after_bcp", "1"},
          {RAILWAY_WAGON, "train_identifier_after_bcp", "2"},
          {ROAD_VEHICLE, "road_vehicle_registration_after_bcp", "3"},
          {AEROPLANE, "airplane_flight_number_after_bcp", "4"}
        });
  }

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);
    mainCarriageSpsTransportMovementAfterBcpMapper =
        new MainCarriageSpsTransportMovementAfterBcpMapper();
  }

  @ParameterizedTest
  @MethodSource("data")
  void map_ReturnsMainCarriageSpsTransportMovement_WhenAllFieldsCompleteAfterBIP(TransportMethod transportMethod, String schemeId, String modeCodeValue)
      throws JsonProcessingException {
    notification.getPartOne().getMeansOfTransport().setType(transportMethod);

    MainCarriageSpsTransportMovement mainCarriageSpsTransportMovement =
        mainCarriageSpsTransportMovementAfterBcpMapper.map(
            notification.getPartOne().getMeansOfTransport());
    String actualSps = objectMapper.writeValueAsString(mainCarriageSpsTransportMovement);

    String expectedSps =
        ResourceUtil.readFileToString(
            "classpath:mapping/common/spsconsignment/mainCarriageSpsTransportMovementCompleteAfterBcp.json");
    expectedSps =
        expectedSps
            .replace(AFTER_BCP_SCHEME_ID_VALUE_KEY, schemeId)
            .replace(MODE_CODE_VALUE_KEY, modeCodeValue);

    assertThat(actualSps).isEqualTo(expectedSps);
  }

  @ParameterizedTest
  @MethodSource("data")
  void map_ReturnsNull_WhenMeansOfTransportIsNull(TransportMethod transportMethod) {
    notification.getPartOne().getMeansOfTransport().setType(transportMethod);
    notification.getPartOne().setMeansOfTransport(null);

    MainCarriageSpsTransportMovement mainCarriageSpsTransportMovement =
        mainCarriageSpsTransportMovementAfterBcpMapper.map(
            notification.getPartOne().getMeansOfTransport());

    assertThat(mainCarriageSpsTransportMovement).isNull();
  }
}
