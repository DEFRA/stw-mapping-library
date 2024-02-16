package uk.gov.defra.stw.mapping.toipaffs.cloning.chedpp;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.toipaffs.common.chedpp.ChedppMeansOfTransportFromEntryPointHelper;
import uk.gov.defra.stw.mapping.toipaffs.common.chedpp.ChedppMeansOfTransportFromEntryPointMapper;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransportBeforeBip;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.TransportMethod;

@Component
public class ChedppMeansOfTransportFromEntryPointMapperImpl
    implements ChedppMeansOfTransportFromEntryPointMapper {

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

  private final ChedppMeansOfTransportFromEntryPointHelper
      chedppMeansOfTransportFromEntryPointHelper;

  @Autowired
  public ChedppMeansOfTransportFromEntryPointMapperImpl(
      ChedppMeansOfTransportFromEntryPointHelper chedppMeansOfTransportFromEntryPointHelper) {
    this.chedppMeansOfTransportFromEntryPointHelper = chedppMeansOfTransportFromEntryPointHelper;
  }

  @Override
  public MeansOfTransportBeforeBip map(List<MainCarriageSpsTransportMovement> data) {
    return chedppMeansOfTransportFromEntryPointHelper.map(
        data, BEFORE_BCP_SCHEME_IDS, referenceTransportMethodMap);
  }
}
