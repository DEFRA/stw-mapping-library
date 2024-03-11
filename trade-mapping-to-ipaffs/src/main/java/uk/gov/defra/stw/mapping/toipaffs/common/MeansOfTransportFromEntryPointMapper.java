package uk.gov.defra.stw.mapping.toipaffs.common;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransportBeforeBip;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.TransportMethod;

@Component
public class MeansOfTransportFromEntryPointMapper implements
    Mapper<SpsCertificate, MeansOfTransportBeforeBip> {

  private final Map<String, TransportMethod> referenceTransportMethodMap;
  private static final String SHIP = "1";
  private static final String RAILWAY_WAGON = "2";
  private static final String ROAD_VEHICLE = "3";
  private static final String AEROPLANE = "4";

  private static final List<String> BEFORE_BCP_SCHEME_IDS =
  List.of(
      "ship_imo_number_before_bcp",
      "train_identifier_before_bcp",
      "road_vehicle_registration_before_bcp",
      "airplane_flight_number_before_bcp");

  private final MeansOfTransportFromEntryPointHelper meansOfTransportFromEntryPointHelper;

  @Autowired
  public MeansOfTransportFromEntryPointMapper(MeansOfTransportFromEntryPointHelper meansOfTransportFromEntryPointHelper) {
    this.meansOfTransportFromEntryPointHelper = meansOfTransportFromEntryPointHelper;
    referenceTransportMethodMap = Map.of(
        SHIP, TransportMethod.SHIP,
        RAILWAY_WAGON, TransportMethod.RAILWAY_WAGON,
        ROAD_VEHICLE, TransportMethod.ROAD_VEHICLE,
        AEROPLANE, TransportMethod.AEROPLANE);
  }

  @Override
  public MeansOfTransportBeforeBip map(SpsCertificate spsCertificate)
      throws NotificationMapperException {
    List<MainCarriageSpsTransportMovement> mainCarriageSpsTransportMovement = 
        spsCertificate.getSpsConsignment()
        .getMainCarriageSpsTransportMovement();
    if (CollectionUtils.isEmpty(mainCarriageSpsTransportMovement)) {
      return null;
    }
    MeansOfTransportBeforeBip meansOfTransportBeforeBip = meansOfTransportFromEntryPointHelper.map(mainCarriageSpsTransportMovement, BEFORE_BCP_SCHEME_IDS, referenceTransportMethodMap);
    meansOfTransportBeforeBip.setDocument(getTransportToBcpDocument(spsCertificate));
    return meansOfTransportBeforeBip;
  }

  private String getTransportToBcpDocument(SpsCertificate spsCertificate) {
    return spsCertificate.getSpsExchangedDocument().getIncludedSpsNote().stream()
        .filter(includedSpsNote -> includedSpsNote.getSubjectCode()
            .getValue()
            .equals("transport_to_bcp_document"))
        .findAny()
        .orElseThrow()
        .getContent()
        .get(0)
        .getValue();
  }
}
