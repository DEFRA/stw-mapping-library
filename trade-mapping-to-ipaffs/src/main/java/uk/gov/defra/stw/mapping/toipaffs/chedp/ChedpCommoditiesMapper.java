package uk.gov.defra.stw.mapping.toipaffs.chedp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.chedp.commodities.ChedpComplementParameterSetMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.CountryOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.RegionOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.CommodityComplementMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.CommodityTemperatureMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.NumberOfPackagesMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.TotalGrossWeightMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.TotalNetWeightMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.CommoditiesMapperException;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;

@Component
public class ChedpCommoditiesMapper implements Mapper<SpsCertificate, Commodities> {

  private final CommodityComplementMapper commodityComplementMapper;
  private final ChedpComplementParameterSetMapper chedpComplementParameterSetMapper;
  private final RegionOfOriginMapper regionOfOriginMapper;
  private final TotalGrossWeightMapper totalGrossWeightMapper;
  private final CommodityTemperatureMapper commodityTemperatureMapper;
  private final CountryOfOriginMapper countryOfOriginMapper;
  private final TotalNetWeightMapper totalNetWeightMapper;
  private final NumberOfPackagesMapper numberOfPackagesMapper;

  @Autowired
  public ChedpCommoditiesMapper(
      CommodityComplementMapper commodityComplementMapper,
      ChedpComplementParameterSetMapper chedpComplementParameterSetMapper,
      RegionOfOriginMapper regionOfOriginMapper,
      TotalGrossWeightMapper totalGrossWeightMapper,
      CommodityTemperatureMapper commodityTemperatureMapper,
      CountryOfOriginMapper countryOfOriginMapper,
      TotalNetWeightMapper totalNetWeightMapper,
      NumberOfPackagesMapper numberOfPackagesMapper) {
    this.commodityComplementMapper = commodityComplementMapper;
    this.chedpComplementParameterSetMapper = chedpComplementParameterSetMapper;
    this.regionOfOriginMapper = regionOfOriginMapper;
    this.totalGrossWeightMapper = totalGrossWeightMapper;
    this.commodityTemperatureMapper = commodityTemperatureMapper;
    this.countryOfOriginMapper = countryOfOriginMapper;
    this.totalNetWeightMapper = totalNetWeightMapper;
    this.numberOfPackagesMapper = numberOfPackagesMapper;
  }

  @Override
  public Commodities map(SpsCertificate data) throws NotificationMapperException {
    try {
      return Commodities.builder()
          .temperature(commodityTemperatureMapper.map(data))
          .commodityComplement(commodityComplementMapper.map(data))
          .complementParameterSet(chedpComplementParameterSetMapper.map(data))
          .includeNonAblactedAnimals(Boolean.FALSE)
          .countryOfOrigin(countryOfOriginMapper.map(data))
          .regionOfOrigin(regionOfOriginMapper.map(data))
          .consignedCountry(data.getSpsConsignment()
              .getExportSpsCountry().getId().getValue())
          .totalGrossWeight(totalGrossWeightMapper.map(data))
          .totalNetWeight(totalNetWeightMapper.map(data))
          .numberOfPackages(numberOfPackagesMapper.map(data))
          .build();
    } catch (CommoditiesMapperException exception) {
      throw new NotificationMapperException(exception.getMessage());
    }
  }
}
