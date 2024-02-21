package uk.gov.defra.stw.mapping.toipaffs.common.chedp;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.common.common.MeansOfTransportFromEntryPointBaseMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.MeansOfTransportFromEntryPointMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.ResourceUtils;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransportBeforeBip;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.TransportMethod;

class MeansOfTransportFromEntryPointMapperTest {

  private static final String SHIP = "1";
  private static final String RAILWAY_WAGON = "2";
  private static final String ROAD_VEHICLE = "3";
  private static final String AEROPLANE = "4";

  private final Map<String, TransportMethod> referenceTransportMethodMap = Map.of(
      SHIP, TransportMethod.SHIP,
      RAILWAY_WAGON, TransportMethod.RAILWAY_WAGON,
      ROAD_VEHICLE, TransportMethod.ROAD_VEHICLE,
      AEROPLANE, TransportMethod.AEROPLANE
  );

  private MeansOfTransportFromEntryPointMapper mapper;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() {
    mapper = new MeansOfTransportFromEntryPointMapper(new MeansOfTransportFromEntryPointBaseMapper());
    objectMapper = TestUtils.initObjectMapper();
  }

  @Test
  void map_ReturnsMeansOfTransportBeforeBip_WhenComplete()
      throws NotificationMapperException, JsonProcessingException {
    List<MainCarriageSpsTransportMovement> mainCarriageSpsTransportMovements = getMainCarriageSpsTransportMovements();
    String expectedTransport = ResourceUtils
        .readFileToString("classpath:common/common/transport/common_ipaffs_meansOfTransportFromEntryPoint_complete.json");

    MeansOfTransportBeforeBip transport = mapper.map(mainCarriageSpsTransportMovements);
    String actualTransport = objectMapper.writeValueAsString(transport);

    assertThat(actualTransport).isEqualTo(expectedTransport);
  }

  @Test
  void map_ReturnsMeansOfTransportBeforeBip_ForAllTypes()
      throws NotificationMapperException, JsonProcessingException {
    List<MainCarriageSpsTransportMovement> mainCarriageSpsTransportMovements = getMainCarriageSpsTransportMovements();

    for (String type : referenceTransportMethodMap.keySet()) {
      mainCarriageSpsTransportMovements.get(0).getModeCode().setValue(type);

      MeansOfTransportBeforeBip transport = mapper.map(mainCarriageSpsTransportMovements);

      assertThat(transport.getType()).isEqualTo(referenceTransportMethodMap.get(type));
    }
  }

  @Test
  void map_ReturnsNull_WhenNullMainCarriageSpsTransportMovement() throws NotificationMapperException {
    assertThat(mapper.map(null)).isNull();
  }

  @Test
  void map_ReturnsNull_WhenEmptyMainCarriageSpsTransportMovement() throws NotificationMapperException {
    assertThat(mapper.map(new ArrayList<>())).isNull();
  }

  private List<MainCarriageSpsTransportMovement> getMainCarriageSpsTransportMovements() throws JsonProcessingException {
    return JsonDeserializer
        .get(SpsCertificate.class, "common/chedp/chedp_ehc_complete.json", objectMapper)
        .getSpsConsignment()
        .getMainCarriageSpsTransportMovement();
  }
}
