package uk.gov.defra.stw.mapping.toipaffs.cloning.chedp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.cloning.chedp.commodities.ChedpComplementParameterSetMapperImpl;
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.ConsignedCountryMapper;
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.CountryCodeTransformer;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.chedp.ChedpTemperatureMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.chedp.commodities.ChedpCommodityComplementMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.CountryOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.RegionOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.commodities.TotalGrossWeightMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.CommoditiesMapperException;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;

@Component
public class ChedpCommoditiesMapper implements Mapper<SpsCertificate, Commodities> {

  private final ChedpCommodityComplementMapper chedpCommodityComplementMapper;
  private final ChedpComplementParameterSetMapperImpl chedpComplementParameterSetMapper;
  private final RegionOfOriginMapper regionOfOriginMapper;
  private final TotalGrossWeightMapper totalGrossWeightMapper;
  private final CountryOfOriginMapper countryOfOriginMapper;
  private final ConsignedCountryMapper consignedCountryMapper;
  private final ChedpTemperatureMapper chedpTemperatureMapper;
  private final CountryCodeTransformer countryCodeTransformer;

  @Autowired
  public ChedpCommoditiesMapper(ChedpCommodityComplementMapper chedpCommodityComplementMapper,
      ChedpComplementParameterSetMapperImpl chedpComplementParameterSetMapper,
      RegionOfOriginMapper regionOfOriginMapper,
      TotalGrossWeightMapper totalGrossWeightMapper,
      CountryOfOriginMapper countryOfOriginMapper,
      ConsignedCountryMapper consignedCountryMapper,
      ChedpTemperatureMapper chedpTemperatureMapper,
      CountryCodeTransformer countryCodeTransformer) {
    this.chedpCommodityComplementMapper = chedpCommodityComplementMapper;
    this.chedpComplementParameterSetMapper = chedpComplementParameterSetMapper;
    this.regionOfOriginMapper = regionOfOriginMapper;
    this.totalGrossWeightMapper = totalGrossWeightMapper;
    this.countryOfOriginMapper = countryOfOriginMapper;
    this.consignedCountryMapper = consignedCountryMapper;
    this.chedpTemperatureMapper = chedpTemperatureMapper;
    this.countryCodeTransformer = countryCodeTransformer;
  }

  @Override
  public Commodities map(SpsCertificate data) throws NotificationMapperException {
    try {
      return Commodities.builder()
          .temperature(chedpTemperatureMapper.map(data))
          .commodityComplement(chedpCommodityComplementMapper.map(data))
          .complementParameterSet(chedpComplementParameterSetMapper.map(data))
          .includeNonAblactedAnimals(Boolean.FALSE)
          .countryOfOrigin(
              countryCodeTransformer.convertCountryCode(countryOfOriginMapper.map(data)))
          .regionOfOrigin(regionOfOriginMapper.map(data))
          .consignedCountry(consignedCountryMapper.map(data))
          .totalGrossWeight(totalGrossWeightMapper.map(data))
          .build();
    } catch (CommoditiesMapperException exception) {
      throw new NotificationMapperException(exception.getMessage());
    }
  }
}
