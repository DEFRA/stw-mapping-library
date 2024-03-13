package uk.gov.defra.stw.mapping.toipaffs.common;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransportBeforeBip;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.TransportMethod;

@Component
public class MeansOfTransportFromEntryPointHelper {
  private final MeansOfTransportFromEntryPointBaseMapper meansOfTransportFromEntryPointBaseMapper;

  @Autowired
  public MeansOfTransportFromEntryPointHelper(
      MeansOfTransportFromEntryPointBaseMapper meansOfTransportFromEntryPointBaseMapper) {
    this.meansOfTransportFromEntryPointBaseMapper = meansOfTransportFromEntryPointBaseMapper;
  }

  public MeansOfTransportBeforeBip map(
      List<MainCarriageSpsTransportMovement> data,
      List<String> beforeBcpSchemeIds,
      Map<String, TransportMethod> referenceTransportMethodMap) {

    if (CollectionUtils.isEmpty(data)) {
      return null;
    }

    return data.stream()
        .filter(
            transportMovementRow -> beforeBcpSchemeIds.contains(
              transportMovementRow.getId().getSchemeID()))
        .findFirst()
        .map(
            transportMovement -> createMeansOfTransportBeforeBip(
              transportMovement, referenceTransportMethodMap))
        .orElse(null);
  }

  private MeansOfTransportBeforeBip createMeansOfTransportBeforeBip(
      MainCarriageSpsTransportMovement transportMovement,
      Map<String, TransportMethod> referenceTransportMethodMap) {

    MeansOfTransportBeforeBip meansOfTransportBeforeBip = meansOfTransportFromEntryPointBaseMapper
        .map(transportMovement);

    meansOfTransportBeforeBip.setType(
        referenceTransportMethodMap.get(transportMovement.getModeCode().getValue()));
    return meansOfTransportBeforeBip;
  }
}