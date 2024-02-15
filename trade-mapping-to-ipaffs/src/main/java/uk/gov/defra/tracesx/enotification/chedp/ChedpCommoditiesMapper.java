package uk.gov.defra.tracesx.enotification.chedp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.common.chedp.ChedpTemperatureMapper;
import uk.gov.defra.tracesx.common.chedp.commodities.ChedpCommodityComplementMapper;
import uk.gov.defra.tracesx.common.chedp.commodities.ChedpComplementParameterSetMapper;
import uk.gov.defra.tracesx.common.common.CountryOfOriginMapper;
import uk.gov.defra.tracesx.common.common.RegionOfOriginMapper;
import uk.gov.defra.tracesx.common.common.commodities.TotalGrossWeightMapper;
import uk.gov.defra.tracesx.exceptions.CommoditiesMapperException;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

@Component
public class ChedpCommoditiesMapper implements Mapper<SpsCertificate, Commodities> {

  private final ChedpCommodityComplementMapper chedpCommodityComplementMapper;
  private final ChedpComplementParameterSetMapper chedpComplementParameterSetMapper;
  private final RegionOfOriginMapper regionOfOriginMapper;
  private final TotalGrossWeightMapper totalGrossWeightMapper;
  private final ChedpTemperatureMapper chedpTemperatureMapper;
  private final CountryOfOriginMapper countryOfOriginMapper;

  @Autowired
  public ChedpCommoditiesMapper(ChedpCommodityComplementMapper chedpCommodityComplementMapper,
      ChedpComplementParameterSetMapper chedpComplementParameterSetMapper,
      RegionOfOriginMapper regionOfOriginMapper,
      TotalGrossWeightMapper totalGrossWeightMapper,
      ChedpTemperatureMapper chedpTemperatureMapper,
      CountryOfOriginMapper countryOfOriginMapper) {
    this.chedpCommodityComplementMapper = chedpCommodityComplementMapper;
    this.chedpComplementParameterSetMapper = chedpComplementParameterSetMapper;
    this.regionOfOriginMapper = regionOfOriginMapper;
    this.totalGrossWeightMapper = totalGrossWeightMapper;
    this.chedpTemperatureMapper = chedpTemperatureMapper;
    this.countryOfOriginMapper = countryOfOriginMapper;
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
          .build();
    } catch (CommoditiesMapperException exception) {
      throw new NotificationMapperException(exception.getMessage());
    }
  }
}
