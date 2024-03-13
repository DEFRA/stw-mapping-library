package uk.gov.defra.stw.mapping.toipaffs.common;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransportAfterBip;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.TransportMethod;

@Component
public class MeansOfTransportMapper implements
    Mapper<SpsCertificate, MeansOfTransportAfterBip> {

  private final MeansOfTransportHelper meansOfTransportHelper;

  private static final String SHIP = "1";
  private static final String RAILWAY_WAGON = "2";
  private static final String ROAD_VEHICLE = "3";
  private static final String AEROPLANE = "4";

  private static final Map<String, TransportMethod> referenceTransportMethodMap = Map.of(
      SHIP, TransportMethod.SHIP,
      RAILWAY_WAGON, TransportMethod.RAILWAY_WAGON,
      ROAD_VEHICLE, TransportMethod.ROAD_VEHICLE,
      AEROPLANE, TransportMethod.AEROPLANE);

  private static final List<String> AFTER_BCP_SCHEME_IDS = List.of(
      "ship_imo_number_after_bcp",
      "train_identifier_after_bcp",
      "road_vehicle_registration_after_bcp",
      "airplane_flight_number_after_bcp");

  @Autowired
  public MeansOfTransportMapper(
      MeansOfTransportHelper chedppMeansOfTransportHelper) {
    this.meansOfTransportHelper = chedppMeansOfTransportHelper;
  }

  @Override
  public MeansOfTransportAfterBip map(SpsCertificate spsCertificate) {
    List<MainCarriageSpsTransportMovement> mainCarriageSpsTransportMovement = 
        spsCertificate.getSpsConsignment()
        .getMainCarriageSpsTransportMovement();
    if (CollectionUtils.isEmpty(mainCarriageSpsTransportMovement)) {
      return null;
    }
    MeansOfTransportAfterBip meansOfTransportAfterBip =
        meansOfTransportHelper.map(mainCarriageSpsTransportMovement,
        AFTER_BCP_SCHEME_IDS, referenceTransportMethodMap);

    if (meansOfTransportAfterBip == null) {
      return null;
    }

    meansOfTransportAfterBip.setDocument(getTransportAfterBcpDocument(spsCertificate));

    return meansOfTransportAfterBip;
  }

  private String getTransportAfterBcpDocument(SpsCertificate spsCertificate) {
    return spsCertificate.getSpsExchangedDocument().getIncludedSpsNote().stream()
        .filter(includedSpsNote -> includedSpsNote.getSubjectCode()
            .getValue()
            .equals("transport_after_bcp_document"))
        .findAny()
        .orElseThrow()
        .getContent()
        .get(0)
        .getValue();
  }
}
