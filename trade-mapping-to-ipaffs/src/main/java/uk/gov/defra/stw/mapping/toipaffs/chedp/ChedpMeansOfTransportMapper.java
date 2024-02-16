package uk.gov.defra.stw.mapping.toipaffs.chedp;

import static uk.gov.defra.stw.mapping.toipaffs.enumeration.TransportType.AEROPLANE;
import static uk.gov.defra.stw.mapping.toipaffs.enumeration.TransportType.RAILWAY_WAGON;
import static uk.gov.defra.stw.mapping.toipaffs.enumeration.TransportType.ROAD_VEHICLE;
import static uk.gov.defra.stw.mapping.toipaffs.enumeration.TransportType.SHIP;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.MeansOfTransportMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransportAfterBip;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.TransportMethod;

@Component
public class ChedpMeansOfTransportMapper implements
    Mapper<List<MainCarriageSpsTransportMovement>, MeansOfTransportAfterBip> {

  private static final List<String> AFTER_BCP_SCHEME_IDS = List.of(
      "ship_imo_number_after_bcp",
      "train_identifier_after_bcp",
      "road_vehicle_registration_after_bcp",
      "airplane_flight_number_after_bcp");

  private static final Map<String, TransportMethod> referenceTransportMethodMap = Map.of(
      SHIP.getValue(), TransportMethod.SHIP,
      RAILWAY_WAGON.getValue(), TransportMethod.RAILWAY_WAGON,
      ROAD_VEHICLE.getValue(), TransportMethod.ROAD_VEHICLE,
      AEROPLANE.getValue(), TransportMethod.AEROPLANE);

  private final MeansOfTransportMapper meansOfTransportMapper;

  @Autowired
  public ChedpMeansOfTransportMapper(
      MeansOfTransportMapper meansOfTransportMapper) {
    this.meansOfTransportMapper = meansOfTransportMapper;
  }

  @Override
  public MeansOfTransportAfterBip map(List<MainCarriageSpsTransportMovement> data)
      throws NotificationMapperException {
    if (CollectionUtils.isEmpty(data)) {
      return null;
    }
    return data.stream()
        .filter(
            transportMovementRow ->
                AFTER_BCP_SCHEME_IDS.contains(transportMovementRow.getId().getSchemeID()))
        .findFirst()
        .map(
            transportMovement ->
                createMeansOfTransportAfterBip(transportMovement, referenceTransportMethodMap))
        .orElse(null);
  }

  private MeansOfTransportAfterBip createMeansOfTransportAfterBip(
      MainCarriageSpsTransportMovement transportMovement,
      Map<String, TransportMethod> referenceTransportMethodMap) {

    MeansOfTransportAfterBip meansOfTransportAfterBip =
        meansOfTransportMapper.map(transportMovement);

    meansOfTransportAfterBip.setType(
        referenceTransportMethodMap.get(transportMovement.getModeCode().getValue()));
    return meansOfTransportAfterBip;
  }
}
