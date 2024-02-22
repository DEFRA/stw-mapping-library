package uk.gov.defra.stw.mapping.toipaffs.chedpp;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.toipaffs.common.MeansOfTransportMapper;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransportAfterBip;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.TransportMethod;

@Component
public class ChedppMeansOfTransportHelper {

  private final MeansOfTransportMapper meansOfTransportMapper;

  @Autowired
  public ChedppMeansOfTransportHelper(MeansOfTransportMapper meansOfTransportMapper) {
    this.meansOfTransportMapper = meansOfTransportMapper;
  }

  public MeansOfTransportAfterBip map(
      List<MainCarriageSpsTransportMovement> data,
      List<String> afterBcpSchemeIds,
      Map<String, TransportMethod> referenceTransportMethodMap) {

    if (CollectionUtils.isEmpty(data)) {
      return null;
    }

    return data.stream()
        .filter(
            transportMovementRow ->
                afterBcpSchemeIds.contains(transportMovementRow.getId().getSchemeID()))
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
