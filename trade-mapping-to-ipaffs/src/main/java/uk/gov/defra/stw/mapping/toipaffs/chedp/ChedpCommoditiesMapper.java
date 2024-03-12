package uk.gov.defra.stw.mapping.toipaffs.chedp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.chedp.commodities.ChedpCommodityComplementMapper;
import uk.gov.defra.stw.mapping.toipaffs.chedp.commodities.ChedpComplementParameterSetMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.CountryOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.RegionOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.TotalGrossWeightMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.TotalNetWeightMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.CommoditiesMapperException;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;

@Component
public class ChedpCommoditiesMapper implements Mapper<SpsCertificate, Commodities> {

  private final ChedpCommodityComplementMapper chedpCommodityComplementMapper;
  private final ChedpComplementParameterSetMapper chedpComplementParameterSetMapper;
  private final RegionOfOriginMapper regionOfOriginMapper;
  private final TotalGrossWeightMapper totalGrossWeightMapper;
  private final ChedpTemperatureMapper chedpTemperatureMapper;
  private final CountryOfOriginMapper countryOfOriginMapper;
  private final TotalNetWeightMapper totalNetWeightMapper;

  @Autowired
  public ChedpCommoditiesMapper(
      ChedpCommodityComplementMapper chedpCommodityComplementMapper,
      ChedpComplementParameterSetMapper chedpComplementParameterSetMapper,
      RegionOfOriginMapper regionOfOriginMapper,
      TotalGrossWeightMapper totalGrossWeightMapper,
      ChedpTemperatureMapper chedpTemperatureMapper,
      CountryOfOriginMapper countryOfOriginMapper,
      TotalNetWeightMapper totalNetWeightMapper) {
    this.chedpCommodityComplementMapper = chedpCommodityComplementMapper;
    this.chedpComplementParameterSetMapper = chedpComplementParameterSetMapper;
    this.regionOfOriginMapper = regionOfOriginMapper;
    this.totalGrossWeightMapper = totalGrossWeightMapper;
    this.chedpTemperatureMapper = chedpTemperatureMapper;
    this.countryOfOriginMapper = countryOfOriginMapper;
    this.totalNetWeightMapper = totalNetWeightMapper;
  }

  @Override
  public Commodities map(SpsCertificate data) throws NotificationMapperException {
    try {
      return Commodities.builder()
          .temperature(chedpTemperatureMapper.map(data))
          .commodityComplement(chedpCommodityComplementMapper.map(data))
          .complementParameterSet(chedpComplementParameterSetMapper.map(data))
          .includeNonAblactedAnimals(Boolean.FALSE)
          .countryOfOrigin(countryOfOriginMapper.map(data))
          .regionOfOrigin(regionOfOriginMapper.map(data))
          .consignedCountry(data.getSpsConsignment()
              .getExportSpsCountry().getId().getValue())
          .totalGrossWeight(totalGrossWeightMapper.map(data))
          .totalNetWeight(totalNetWeightMapper.map(data))
          .build();
    } catch (CommoditiesMapperException exception) {
      throw new NotificationMapperException(exception.getMessage());
    }
  }
}
