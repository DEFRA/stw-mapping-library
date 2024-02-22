package uk.gov.defra.stw.mapping.toipaffs.chedp;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static uk.gov.defra.stw.mapping.toipaffs.enumeration.TransportType.AEROPLANE;
import static uk.gov.defra.stw.mapping.toipaffs.enumeration.TransportType.RAILWAY_WAGON;
import static uk.gov.defra.stw.mapping.toipaffs.enumeration.TransportType.ROAD_VEHICLE;
import static uk.gov.defra.stw.mapping.toipaffs.enumeration.TransportType.SHIP;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.dto.ModeCode;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.dto.UsedSpsTransportMeans;
import uk.gov.defra.stw.mapping.toipaffs.common.MeansOfTransportMapper;
import uk.gov.defra.stw.mapping.toipaffs.enumeration.TransportType;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransportAfterBip;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.TransportMethod;

class ChedpMeansOfTransportMapperTest {

  private static final Map<TransportType, TransportMethod> referenceTransportMethodMap =
      Map.of(
          SHIP, TransportMethod.SHIP,
          RAILWAY_WAGON, TransportMethod.RAILWAY_WAGON,
          ROAD_VEHICLE, TransportMethod.ROAD_VEHICLE,
          AEROPLANE, TransportMethod.AEROPLANE);

  private static final String VOYAGE_N_1 = "Voyage N° 1";
  private static final String GB = "GB";
  private static final String OCEAN_VESSEL_GREEN_OPAL = "Ocean Vessel: Green Opal";
  private static final String VOYAGE_N_1_OCEAN_VESSEL_GREEN_OPAL = "Voyage N° 1, Ocean Vessel: Green Opal";
  private static final String SHIP_IMO_NUMBER_BEFORE_BCP = "ship_imo_number_before_bcp";
  private static final String SHIP_IMO_NUMBER_AFTER_BCP = "ship_imo_number_after_bcp";

  private ChedpMeansOfTransportMapper mapper;

  @BeforeEach
  void setup() {
    mapper = new ChedpMeansOfTransportMapper(new MeansOfTransportMapper());
  }

  @Test
  void map_ReturnsMeansOfTransportAfterBip_WhenComplete()
      throws NotificationMapperException {
    List<MainCarriageSpsTransportMovement> mainCarriageSpsTransportMovements =
        getMainCarriageSpsTransportMovements();

    MeansOfTransportAfterBip expectedTransport =
        MeansOfTransportAfterBip.builder()
            .id(VOYAGE_N_1_OCEAN_VESSEL_GREEN_OPAL)
            .type(TransportMethod.SHIP)
            .build();

    MeansOfTransportAfterBip actualTransport =
        mapper.map(mainCarriageSpsTransportMovements);

    assertThat(actualTransport).isEqualTo(expectedTransport);
  }

  @Test
  void map_ReturnsMeansOfTransportAfterBip_ForAllTypes() throws NotificationMapperException {
    List<MainCarriageSpsTransportMovement> mainCarriageSpsTransportMovements =
        getMainCarriageSpsTransportMovements();

    for (TransportType type : referenceTransportMethodMap.keySet()) {
      mainCarriageSpsTransportMovements.get(1).getModeCode().setValue(type.getValue());

      MeansOfTransportAfterBip transport =
          mapper.map(mainCarriageSpsTransportMovements);
      assertThat(transport.getType()).isEqualTo(referenceTransportMethodMap.get(type));
    }
  }

  @Test
  void map_ReturnsNull_WhenEmptyMainCarriageSpsTransportMovement()
      throws NotificationMapperException {
    assertThat(mapper.map(Collections.emptyList())).isNull();
  }

  private List<MainCarriageSpsTransportMovement> getMainCarriageSpsTransportMovements() {
    MainCarriageSpsTransportMovement mainCarriageSpsTransportMovement =
        new MainCarriageSpsTransportMovement()
            .withId(
                new IDType()
                    .withSchemeAgencyID(GB)
                    .withValue(VOYAGE_N_1)
                    .withSchemeID(SHIP_IMO_NUMBER_BEFORE_BCP))
            .withModeCode(new ModeCode().withValue(SHIP.getValue()))
            .withUsedSpsTransportMeans(
                new UsedSpsTransportMeans()
                    .withName(new TextType().withValue(OCEAN_VESSEL_GREEN_OPAL)));

    MainCarriageSpsTransportMovement mainCarriageSpsTransportMovement1 =
        new MainCarriageSpsTransportMovement()
            .withId(
                new IDType()
                    .withSchemeAgencyID(GB)
                    .withValue(VOYAGE_N_1)
                    .withSchemeID(SHIP_IMO_NUMBER_AFTER_BCP))
            .withModeCode(new ModeCode().withValue(SHIP.getValue()))
            .withUsedSpsTransportMeans(
                new UsedSpsTransportMeans()
                    .withName(new TextType().withValue(OCEAN_VESSEL_GREEN_OPAL)));

    return List.of(mainCarriageSpsTransportMovement, mainCarriageSpsTransportMovement1);
  }
}
