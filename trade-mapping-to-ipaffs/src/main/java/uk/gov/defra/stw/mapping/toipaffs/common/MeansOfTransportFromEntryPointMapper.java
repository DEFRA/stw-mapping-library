package uk.gov.defra.stw.mapping.toipaffs.common;

import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.utils.MeansOfTransportUtil;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransportBeforeBip;

@Component
public class MeansOfTransportFromEntryPointMapper implements
    Mapper<SpsCertificate, MeansOfTransportBeforeBip> {

  private static final List<String> BEFORE_BCP_SCHEME_IDS = List.of(
      "ship_imo_number_before_bcp",
      "train_identifier_before_bcp",
      "road_vehicle_registration_before_bcp",
      "airplane_flight_number_before_bcp"
  );

  private static final String DOCUMENT_SUBJECT_CODE = "transport_before_bcp_document";

  @Override
  public MeansOfTransportBeforeBip map(SpsCertificate spsCertificate) {
    return spsCertificate
        .getSpsConsignment().getMainCarriageSpsTransportMovement().stream()
        .filter(this::isBeforeBcpMovement)
        .findAny()
        .map(transportMovement -> MeansOfTransportBeforeBip.builder()
            .id(MeansOfTransportUtil.mapId(transportMovement))
            .type(MeansOfTransportUtil.mapType(transportMovement))
            .document(
                MeansOfTransportUtil.mapTransportDocument(spsCertificate, DOCUMENT_SUBJECT_CODE))
            .build())
        .orElse(null);
  }

  private boolean isBeforeBcpMovement(MainCarriageSpsTransportMovement transportMovement) {
    return BEFORE_BCP_SCHEME_IDS.contains(transportMovement.getId().getSchemeID());
  }
}
