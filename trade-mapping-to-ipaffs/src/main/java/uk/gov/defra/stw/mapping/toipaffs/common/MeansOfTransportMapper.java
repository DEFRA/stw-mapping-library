package uk.gov.defra.stw.mapping.toipaffs.common;

import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.utils.MeansOfTransportUtil;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransportAfterBip;

@Component
public class MeansOfTransportMapper implements Mapper<SpsCertificate, MeansOfTransportAfterBip> {

  private static final List<String> AFTER_BCP_SCHEME_IDS = List.of(
      "ship_imo_number_after_bcp",
      "train_identifier_after_bcp",
      "road_vehicle_registration_after_bcp",
      "airplane_flight_number_after_bcp"
  );

  private static final String DOCUMENT_SUBJECT_CODE = "transport_after_bcp_document";

  @Override
  public MeansOfTransportAfterBip map(SpsCertificate spsCertificate) {
    return spsCertificate.getSpsConsignment().getMainCarriageSpsTransportMovement().stream()
        .filter(this::isAfterBcpMovement)
        .findAny()
        .map(transportMovement -> MeansOfTransportAfterBip.builder()
            .id(MeansOfTransportUtil.mapId(transportMovement))
            .type(MeansOfTransportUtil.mapType(transportMovement))
            .document(
                MeansOfTransportUtil.mapTransportDocument(spsCertificate, DOCUMENT_SUBJECT_CODE))
            .build())
        .orElse(null);
  }

  private boolean isAfterBcpMovement(MainCarriageSpsTransportMovement transportMovement) {
    return AFTER_BCP_SCHEME_IDS.contains(transportMovement.getId().getSchemeID());
  }
}
