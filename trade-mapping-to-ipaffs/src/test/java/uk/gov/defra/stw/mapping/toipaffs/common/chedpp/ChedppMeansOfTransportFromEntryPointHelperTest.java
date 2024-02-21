package uk.gov.defra.stw.mapping.toipaffs.common.chedpp;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.dto.ModeCode;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.dto.UsedSpsTransportMeans;
import uk.gov.defra.stw.mapping.toipaffs.common.common.MeansOfTransportFromEntryPointBaseMapper;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransportBeforeBip;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.TransportMethod;

@ExtendWith(MockitoExtension.class)
class ChedppMeansOfTransportFromEntryPointHelperTest {

  private static final String SHIP = "1";
  private static final String RAILWAY_WAGON = "2";
  private static final String ROAD_VEHICLE = "3";
  private static final String AEROPLANE = "4";

  private static final Map<String, TransportMethod> referenceTransportMethodMap =
      Map.of(
          SHIP, TransportMethod.SHIP,
          RAILWAY_WAGON, TransportMethod.RAILWAY_WAGON,
          ROAD_VEHICLE, TransportMethod.ROAD_VEHICLE,
          AEROPLANE, TransportMethod.AEROPLANE);

  private static final List<String> BEFORE_BCP_SCHEME_IDS =
      List.of(
          "ship_imo_number_before_bcp",
          "train_identifier_before_bcp",
          "road_vehicle_registration_before_bcp",
          "airplane_flight_number_before_bcp");

  private static final String VOYAGE_N_1 = "Voyage N° 1";
  private static final String GB = "GB";
  private static final String OCEAN_VESSEL_GREEN_OPAL = "Ocean Vessel: Green Opal";
  private static final String VOYAGE_N_1_OCEAN_VESSEL_GREEN_OPAL = "Voyage N° 1, Ocean Vessel: Green Opal";
  private static final String SHIP_IMO_NUMBER_BEFORE_BCP = "ship_imo_number_before_bcp";
  private static final String SHIP_IMO_NUMBER_AFTER_BCP = "ship_imo_number_after_bcp";

  private ChedppMeansOfTransportFromEntryPointHelper helper;

  @BeforeEach
  void setup() {
    helper = new ChedppMeansOfTransportFromEntryPointHelper(new MeansOfTransportFromEntryPointBaseMapper());
  }

  @Test
  void map_ReturnsMeansOfTransportBeforeBip_WhenComplete() {
    List<MainCarriageSpsTransportMovement> mainCarriageSpsTransportMovements =
        getMainCarriageSpsTransportMovements();

    MeansOfTransportBeforeBip expectedTransport =
        MeansOfTransportBeforeBip.builder()
            .id(VOYAGE_N_1_OCEAN_VESSEL_GREEN_OPAL)
            .type(TransportMethod.SHIP)
            .build();

    MeansOfTransportBeforeBip actualTransport =
        helper.map(
            mainCarriageSpsTransportMovements,
            BEFORE_BCP_SCHEME_IDS,
            referenceTransportMethodMap);

    assertThat(actualTransport).isEqualTo(expectedTransport);
  }

  @Test
  void map_ReturnsMeansOfTransportBeforeBip_ForAllTypes() {
    List<MainCarriageSpsTransportMovement> mainCarriageSpsTransportMovements =
        getMainCarriageSpsTransportMovements();

    for (String type : referenceTransportMethodMap.keySet()) {
      mainCarriageSpsTransportMovements.get(0).getModeCode().setValue(type);

      MeansOfTransportBeforeBip transport =
          helper.map(
              mainCarriageSpsTransportMovements,
              BEFORE_BCP_SCHEME_IDS,
              referenceTransportMethodMap);

      assertThat(transport.getType()).isEqualTo(referenceTransportMethodMap.get(type));
    }
  }

  @Test
  void map_ReturnsNull_WhenEmptyMainCarriageSpsTransportMovement() {
    assertThat(helper.map(Collections.emptyList(), null, null)).isNull();
  }

  private List<MainCarriageSpsTransportMovement> getMainCarriageSpsTransportMovements() {
    MainCarriageSpsTransportMovement mainCarriageSpsTransportMovement =
        new MainCarriageSpsTransportMovement()
            .withId(
                new IDType()
                    .withSchemeAgencyID(GB)
                    .withValue(VOYAGE_N_1)
                    .withSchemeID(SHIP_IMO_NUMBER_BEFORE_BCP))
            .withModeCode(new ModeCode().withValue(SHIP))
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
            .withModeCode(new ModeCode().withValue(SHIP))
            .withUsedSpsTransportMeans(
                new UsedSpsTransportMeans()
                    .withName(new TextType().withValue(OCEAN_VESSEL_GREEN_OPAL)));

    return List.of(mainCarriageSpsTransportMovement, mainCarriageSpsTransportMovement1);
  }
}
