package uk.gov.defra.stw.mapping.toipaffs.chedd;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.chedd.commodities.CheddComplementParameterSetMapper;
import uk.gov.defra.stw.mapping.toipaffs.chedd.commodities.CommodityIntendedForMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.CountryOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.CommodityComplementMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.CommodityTemperatureMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.TotalGrossWeightMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.TotalNetWeightMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;

@Component
public class CheddCommoditiesMapper implements Mapper<SpsCertificate, Commodities> {
  private final CheddComplementParameterSetMapper cheddComplementParameterSetMapper;
  private final TotalNetWeightMapper totalNetWeightMapper;
  private final TotalGrossWeightMapper totalGrossWeightMapper;
  private final CommodityTemperatureMapper commodityTemperatureMapper;
  private final CommodityIntendedForMapper commodityIntendedForMapper;
  private final CommodityComplementMapper commodityComplementMapper;
  private final CountryOfOriginMapper countryOfOriginMapper;

  public CheddCommoditiesMapper(
      CommodityComplementMapper commodityComplementMapper,
      CheddComplementParameterSetMapper cheddComplementParameterSetMapper,
      TotalGrossWeightMapper totalGrossWeightMapper,
      TotalNetWeightMapper totalNetWeightMapper,
      CommodityTemperatureMapper commodityTemperatureMapper,
      CommodityIntendedForMapper commodityIntendedForMapper,
      CountryOfOriginMapper countryOfOriginMapper) {
    this.commodityComplementMapper = commodityComplementMapper;
    this.cheddComplementParameterSetMapper = cheddComplementParameterSetMapper;
    this.totalGrossWeightMapper = totalGrossWeightMapper;
    this.totalNetWeightMapper = totalNetWeightMapper;
    this.commodityTemperatureMapper = commodityTemperatureMapper;
    this.commodityIntendedForMapper = commodityIntendedForMapper;
    this.countryOfOriginMapper = countryOfOriginMapper;
  }

  @Override
  public Commodities map(SpsCertificate spsCertificate) throws NotificationMapperException {
    return Commodities.builder()
        .commodityComplement(commodityComplementMapper.map(spsCertificate))
        .complementParameterSet(cheddComplementParameterSetMapper.map(spsCertificate))
        .totalGrossWeight(totalGrossWeightMapper.map(spsCertificate))
        .totalNetWeight(totalNetWeightMapper.map(spsCertificate))
        .temperature(commodityTemperatureMapper.map(spsCertificate))
        .consignedCountry(spsCertificate.getSpsConsignment()
            .getExportSpsCountry().getId().getValue())
        .commodityIntendedFor(commodityIntendedForMapper.map(spsCertificate))
        .countryOfOrigin(countryOfOriginMapper.map(spsCertificate))
        .build();
  }
}
