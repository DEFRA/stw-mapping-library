package uk.gov.defra.stw.mapping.toipaffs.enotification.chedp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.chedp.ChedpTemperatureMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.chedp.commodities.ChedpCommodityComplementMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.chedp.commodities.ChedpComplementParameterSetMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.CountryOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.RegionOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.commodities.TotalGrossWeightMapper;
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
