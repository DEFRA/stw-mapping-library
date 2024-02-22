package uk.gov.defra.stw.mapping.toipaffs.common;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.dto.UsedSpsTransportMeans;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransportBeforeBip;

@Component
public class MeansOfTransportFromEntryPointBaseMapper implements
    Mapper<MainCarriageSpsTransportMovement, MeansOfTransportBeforeBip> {

  @Override
  public MeansOfTransportBeforeBip map(MainCarriageSpsTransportMovement data) {
    return MeansOfTransportBeforeBip.builder()
        .id(mapId(data))
        .build();
  }

  private String mapId(MainCarriageSpsTransportMovement transportMovement) {
    String transportMovementId = transportMovement.getId().getValue();
    String usedSpsTransportMeansName = mapUsedTransportMeansName(
        transportMovement.getUsedSpsTransportMeans());

    String identification = Stream.of(transportMovementId, usedSpsTransportMeansName)
        .filter(StringUtils::isNotBlank)
        .collect(Collectors.joining(", "));

    return StringUtils.isNotBlank(identification) ? identification : null;
  }

  private String mapUsedTransportMeansName(UsedSpsTransportMeans usedSpsTransportMeans) {
    return usedSpsTransportMeans != null && StringUtils.isNotBlank(
        usedSpsTransportMeans.getName().getValue())
        ? usedSpsTransportMeans.getName().getValue() : null;
  }
}
