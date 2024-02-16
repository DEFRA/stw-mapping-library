package uk.gov.defra.stw.mapping.toipaffs.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.dto.UsedSpsTransportMeans;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransportAfterBip;

@Component
public class MeansOfTransportMapper implements
    Mapper<MainCarriageSpsTransportMovement, MeansOfTransportAfterBip> {

  @Override
  public MeansOfTransportAfterBip map(MainCarriageSpsTransportMovement data) {
    return MeansOfTransportAfterBip.builder()
        .id(mapId(data))
        .build();
  }

  private String mapId(MainCarriageSpsTransportMovement transportMovement) {
    String id = transportMovement.getId().getValue();
    if (StringUtils.isBlank(id)) {
      return null;
    }

    UsedSpsTransportMeans usedSpsTransportMeans = transportMovement.getUsedSpsTransportMeans();
    if (usedSpsTransportMeans != null) {
      id = id.concat(", " + usedSpsTransportMeans.getName().getValue());
    }
    return id;
  }
}
