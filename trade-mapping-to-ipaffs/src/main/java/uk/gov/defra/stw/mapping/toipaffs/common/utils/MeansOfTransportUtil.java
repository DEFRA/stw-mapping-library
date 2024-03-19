package uk.gov.defra.stw.mapping.toipaffs.common.utils;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static uk.gov.defra.stw.mapping.toipaffs.enumeration.TransportType.AEROPLANE;
import static uk.gov.defra.stw.mapping.toipaffs.enumeration.TransportType.RAILWAY_WAGON;
import static uk.gov.defra.stw.mapping.toipaffs.enumeration.TransportType.ROAD_VEHICLE;
import static uk.gov.defra.stw.mapping.toipaffs.enumeration.TransportType.SHIP;

import java.util.Map;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.TransportMethod;

public class MeansOfTransportUtil {

  private static final Map<String, TransportMethod> TRANSPORT_METHOD_MAP = Map.of(
      SHIP.getValue(), TransportMethod.SHIP,
      RAILWAY_WAGON.getValue(), TransportMethod.RAILWAY_WAGON,
      ROAD_VEHICLE.getValue(), TransportMethod.ROAD_VEHICLE,
      AEROPLANE.getValue(), TransportMethod.AEROPLANE
  );

  public static TransportMethod mapType(MainCarriageSpsTransportMovement transportMovement) {
    return TRANSPORT_METHOD_MAP.get(transportMovement.getModeCode().getValue());
  }

  public static String mapId(MainCarriageSpsTransportMovement transportMovement) {
    String identification = transportMovement.getId().getValue();
    if (transportMovement.getUsedSpsTransportMeans() != null
        && transportMovement.getUsedSpsTransportMeans().getName() != null
        && isNotBlank(transportMovement.getUsedSpsTransportMeans().getName().getValue())) {
      identification += ", " + transportMovement.getUsedSpsTransportMeans().getName().getValue();
    }
    return isNotBlank(identification) ? identification : null;
  }

  public static String mapTransportDocument(SpsCertificate spsCertificate, String subjectCode) {
    return spsCertificate.getSpsExchangedDocument().getIncludedSpsNote().stream()
        .filter(includedSpsNote -> includedSpsNote.getSubjectCode()
            .getValue()
            .equals(subjectCode))
        .findAny()
        .map(SpsNoteType::getContent)
        .map(content -> content.get(0))
        .map(TextType::getValue)
        .orElse(null);
  }
}
