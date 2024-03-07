package uk.gov.defra.stw.mapping.toipaffs.chedpp;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransportAfterBip;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.TransportMethod;

@Component
public class ChedppMeansOfTransportMapper implements
    Mapper<List<MainCarriageSpsTransportMovement>, MeansOfTransportAfterBip> {

  private static final String SHIP = "1";
  private static final String RAILWAY_WAGON = "2";
  private static final String ROAD_VEHICLE = "3";
  private static final String AEROPLANE = "4";

  private static final Map<String, TransportMethod> referenceTransportMethodMap = Map.of(
      SHIP, TransportMethod.SHIP,
      RAILWAY_WAGON, TransportMethod.RAILWAY_WAGON,
      ROAD_VEHICLE, TransportMethod.ROAD_VEHICLE,
      AEROPLANE, TransportMethod.AEROPLANE
  );

  private static final List<String> AFTER_BCP_SCHEME_IDS = List.of(
      "ship_imo_number_after_bcp",
      "train_identifier_after_bcp",
      "road_vehicle_registration_after_bcp",
      "airplane_flight_number_after_bcp");

  private final ChedppMeansOfTransportHelper chedppMeansOfTransportHelper;

  @Autowired
  public ChedppMeansOfTransportMapper(
      ChedppMeansOfTransportHelper chedppMeansOfTransportHelper) {
    this.chedppMeansOfTransportHelper = chedppMeansOfTransportHelper;
  }

  @Override
  public MeansOfTransportAfterBip map(List<MainCarriageSpsTransportMovement> data) {
    return chedppMeansOfTransportHelper.map(
        data, AFTER_BCP_SCHEME_IDS, referenceTransportMethodMap);
  }
}