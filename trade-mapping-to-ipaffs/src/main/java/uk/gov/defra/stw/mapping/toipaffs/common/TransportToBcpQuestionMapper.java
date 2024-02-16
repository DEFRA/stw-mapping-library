package uk.gov.defra.stw.mapping.toipaffs.common;

import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;

@Component
public class TransportToBcpQuestionMapper implements Mapper<SpsConsignment, Boolean> {

  private static final List<String> APPLICABLE_SCHEME_IDS =
      List.of(
          "ship_imo_number_after_bcp",
          "train_identifier_after_bcp",
          "road_vehicle_registration_after_bcp",
          "airplane_flight_number_after_bcp");

  @Override
  public Boolean map(SpsConsignment spsConsignment) {
    return hasApplicableMeansOfTransporterAfter(spsConsignment)
        || hasCarrierSpsParty(spsConsignment);
  }

  private boolean hasApplicableMeansOfTransporterAfter(SpsConsignment spsConsignment) {
    return spsConsignment.getMainCarriageSpsTransportMovement().stream()
        .map(MainCarriageSpsTransportMovement::getId)
        .map(IDType::getSchemeID)
        .anyMatch(APPLICABLE_SCHEME_IDS::contains);
  }

  private boolean hasCarrierSpsParty(SpsConsignment spsConsignment) {
    return spsConsignment.getCarrierSpsParty() != null;
  }
}
