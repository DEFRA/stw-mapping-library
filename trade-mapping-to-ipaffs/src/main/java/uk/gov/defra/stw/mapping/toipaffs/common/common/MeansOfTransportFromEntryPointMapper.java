package uk.gov.defra.stw.mapping.toipaffs.common.common;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransportBeforeBip;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.TransportMethod;

@Component
public class MeansOfTransportFromEntryPointMapper implements
    Mapper<List<MainCarriageSpsTransportMovement>, MeansOfTransportBeforeBip> {

  private final Map<String, TransportMethod> referenceTransportMethodMap;
  private static final String SHIP = "1";
  private static final String RAILWAY_WAGON = "2";
  private static final String ROAD_VEHICLE = "3";
  private static final String AEROPLANE = "4";

  private final MeansOfTransportFromEntryPointBaseMapper meansOfTransportFromEntryPointBaseMapper;

  @Autowired
  public MeansOfTransportFromEntryPointMapper(
      MeansOfTransportFromEntryPointBaseMapper meansOfTransportFromEntryPointBaseMapper) {
    this.meansOfTransportFromEntryPointBaseMapper = meansOfTransportFromEntryPointBaseMapper;

    referenceTransportMethodMap = Map.of(
        SHIP, TransportMethod.SHIP,
        RAILWAY_WAGON, TransportMethod.RAILWAY_WAGON,
        ROAD_VEHICLE, TransportMethod.ROAD_VEHICLE,
        AEROPLANE, TransportMethod.AEROPLANE
    );
  }

  @Override
  public MeansOfTransportBeforeBip map(List<MainCarriageSpsTransportMovement> data)
      throws NotificationMapperException {
    if (CollectionUtils.isEmpty(data)) {
      return null;
    }

    MainCarriageSpsTransportMovement transportMovement = data.get(0);
    MeansOfTransportBeforeBip meansOfTransportBeforeBip = meansOfTransportFromEntryPointBaseMapper
        .map(transportMovement);
    meansOfTransportBeforeBip
        .setType(referenceTransportMethodMap.get(transportMovement.getModeCode().getValue()));
    return meansOfTransportBeforeBip;
  }
}
