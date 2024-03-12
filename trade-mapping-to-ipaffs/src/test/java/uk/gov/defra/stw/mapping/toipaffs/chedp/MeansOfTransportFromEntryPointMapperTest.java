package uk.gov.defra.stw.mapping.toipaffs.chedp;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.toipaffs.common.MeansOfTransportFromEntryPointBaseMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.MeansOfTransportFromEntryPointHelper;
import uk.gov.defra.stw.mapping.toipaffs.common.MeansOfTransportFromEntryPointMapper;
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
      AEROPLANE, TransportMethod.AEROPLANE);

  private MeansOfTransportFromEntryPointMapper meansOfTransportFromEntryPointMapper;
  private MeansOfTransportFromEntryPointHelper meansOfTransportFromEntryPointHelper;
  private MeansOfTransportFromEntryPointBaseMapper meansOfTransportFromEntryPointBaseMapper;
  private ObjectMapper objectMapper;
  private SpsCertificate spsCertificate;

  @BeforeEach
  void setup() {
    meansOfTransportFromEntryPointBaseMapper = new MeansOfTransportFromEntryPointBaseMapper();
    meansOfTransportFromEntryPointHelper = new MeansOfTransportFromEntryPointHelper(
        meansOfTransportFromEntryPointBaseMapper);
    meansOfTransportFromEntryPointMapper = new MeansOfTransportFromEntryPointMapper(
        meansOfTransportFromEntryPointHelper);
    objectMapper = TestUtils.initObjectMapper();
    spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(
                List.of(new SpsNoteType().withSubjectCode(new CodeType()
                    .withValue("transport_to_bcp_document"))
                    .withContent(List.of(new TextType().withValue("TESTDOCUMENT"))))));
  }

  @Test
  void map_ReturnsMeansOfTransportBeforeBip_WhenComplete()
      throws NotificationMapperException, JsonProcessingException {
    List<MainCarriageSpsTransportMovement> mainCarriageSpsTransportMovements = getMainCarriageSpsTransportMovements();
    String expectedTransport = ResourceUtils
        .readFileToString("classpath:common/transport/common_ipaffs_meansOfTransportFromEntryPoint_complete.json");

    spsCertificate.setSpsConsignment(new SpsConsignment()
        .withMainCarriageSpsTransportMovement(mainCarriageSpsTransportMovements));
    MeansOfTransportBeforeBip transport = meansOfTransportFromEntryPointMapper.map(spsCertificate);
    String actualTransport = objectMapper.writeValueAsString(transport);

    assertThat(actualTransport).isEqualTo(expectedTransport);
  }

  @Test
  void map_ReturnsMeansOfTransportBeforeBip_ForAllTypes()
      throws NotificationMapperException, JsonProcessingException {
    List<MainCarriageSpsTransportMovement> mainCarriageSpsTransportMovements = getMainCarriageSpsTransportMovements();
    spsCertificate.setSpsConsignment(new SpsConsignment()
        .withMainCarriageSpsTransportMovement(mainCarriageSpsTransportMovements));

    for (String type : referenceTransportMethodMap.keySet()) {
      mainCarriageSpsTransportMovements.get(0).getModeCode().setValue(type);

      MeansOfTransportBeforeBip transport = meansOfTransportFromEntryPointMapper.map(spsCertificate);

      assertThat(transport.getType()).isEqualTo(referenceTransportMethodMap.get(type));
    }
  }

  @Test
  void map_ReturnsNull_WhenEmptyMainCarriageSpsTransportMovement() throws NotificationMapperException {
    spsCertificate.setSpsConsignment(new SpsConsignment()
        .withMainCarriageSpsTransportMovement(new ArrayList<>()));
    assertThat(meansOfTransportFromEntryPointMapper.map(spsCertificate)).isNull();
  }

  private List<MainCarriageSpsTransportMovement> getMainCarriageSpsTransportMovements() throws JsonProcessingException {
    return JsonDeserializer
        .get("chedp/chedp_ehc_complete.json", SpsCertificate.class)
        .getSpsConsignment()
        .getMainCarriageSpsTransportMovement();
  }
}
