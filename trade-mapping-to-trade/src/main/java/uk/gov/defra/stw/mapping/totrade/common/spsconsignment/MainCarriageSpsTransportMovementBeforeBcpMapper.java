package uk.gov.defra.stw.mapping.totrade.common.spsconsignment;

import java.util.Map;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.dto.ModeCode;
import uk.gov.defra.stw.mapping.dto.UsedSpsTransportMeans;
import uk.gov.defra.stw.mapping.totrade.common.Mapper;
import uk.gov.defra.stw.mapping.totrade.common.utils.SpsTypeConverter;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransport;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.TransportMethod;

@Component
public class MainCarriageSpsTransportMovementBeforeBcpMapper extends
    MainCarriageSpsTransportMovementMapper
    implements Mapper<MeansOfTransport, MainCarriageSpsTransportMovement> {

  private static final Map<TransportMethod, TypeMap> BEFORE_BCP_TYPE_MAP =
      Map.of(
          TransportMethod.SHIP, new TypeMap("1", "ship_imo_number_before_bcp"),
          TransportMethod.RAILWAY_WAGON, new TypeMap("2", "train_identifier_before_bcp"),
          TransportMethod.ROAD_VEHICLE, new TypeMap("3", "road_vehicle_registration_before_bcp"),
          TransportMethod.AEROPLANE, new TypeMap("4", "airplane_flight_number_before_bcp"));

  @Override
  public MainCarriageSpsTransportMovement map(MeansOfTransport data) {
    if (isNull(data)) {
      return null;
    }

    return new MainCarriageSpsTransportMovement()
        .withId(
            SpsTypeConverter.getIdTypeWithSchemeIdAndSchemeAgencyIdAndSchemeAgencyName(
                BEFORE_BCP_TYPE_MAP.get(data.getType()).getSchemeId(),
                data.getId(),
                SCHEME_AGENCY_ID,
                SCHEME_AGENCY_NAME))
        .withModeCode(new ModeCode().withValue(BEFORE_BCP_TYPE_MAP.get(data.getType()).getId()))
        .withUsedSpsTransportMeans(new UsedSpsTransportMeans()
            .withName(SpsTypeConverter.getTextType(data.getDocument())));
  }
}
