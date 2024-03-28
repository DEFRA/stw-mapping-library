package uk.gov.defra.stw.mapping.toipaffs.chedd;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.TotalGrossWeightMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.TotalNetWeightMapper;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;

@Component
public class CheddCommoditiesMapper implements Mapper<SpsCertificate, Commodities> {
  private final TotalNetWeightMapper totalNetWeightMapper;
  private final TotalGrossWeightMapper totalGrossWeightMapper;

  public CheddCommoditiesMapper(
      TotalGrossWeightMapper totalGrossWeightMapper,
      TotalNetWeightMapper totalNetWeightMapper) {
    this.totalGrossWeightMapper = totalGrossWeightMapper;
    this.totalNetWeightMapper = totalNetWeightMapper;
  }

  @Override
  public Commodities map(SpsCertificate spsCertificate) {
    return Commodities.builder()
        .totalGrossWeight(totalGrossWeightMapper.map(spsCertificate))
        .totalNetWeight(totalNetWeightMapper.map(spsCertificate))
        .build();
  }
}
