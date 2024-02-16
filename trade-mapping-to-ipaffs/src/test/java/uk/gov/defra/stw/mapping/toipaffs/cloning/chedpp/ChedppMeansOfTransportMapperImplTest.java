package uk.gov.defra.stw.mapping.toipaffs.cloning.chedpp;

import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.toipaffs.common.chedpp.ChedppMeansOfTransportHelper;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.TransportMethod;

@ExtendWith(MockitoExtension.class)
class ChedppMeansOfTransportMapperImplTest {

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

  private static final List<String> AFTER_BCP_SCHEME_IDS =
      List.of(
          "ship_imo_number_after_bcp",
          "train_identifier_after_bcp",
          "road_vehicle_registration_after_bcp",
          "airplane_flight_number_after_bcp");

  @Mock
  private ChedppMeansOfTransportHelper chedppMeansOfTransportHelper;

  @InjectMocks
  private ChedppMeansOfTransportMapperImpl chedppMeansOfTransportMapper;

  @Test
  void map_SuccessfullyCallsHelper_WhenCalled() {
    List<MainCarriageSpsTransportMovement> data = List.of(new MainCarriageSpsTransportMovement());

    chedppMeansOfTransportMapper.map(data);

    verify(chedppMeansOfTransportHelper)
        .map(data, AFTER_BCP_SCHEME_IDS, referenceTransportMethodMap);
  }
}
