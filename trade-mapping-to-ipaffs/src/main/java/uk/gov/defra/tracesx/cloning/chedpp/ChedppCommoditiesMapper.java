package uk.gov.defra.tracesx.cloning.chedpp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.cloning.chedpp.commodities.ChedppCommodityComplementMapper;
import uk.gov.defra.tracesx.cloning.chedpp.commodities.ChedppComplementParameterSetMapper;
import uk.gov.defra.tracesx.cloning.common.ConsignedCountryMapper;
import uk.gov.defra.tracesx.cloning.common.CountryCodeTransformer;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.common.common.CountryOfOriginMapper;
import uk.gov.defra.tracesx.common.common.RegionOfOriginMapper;
import uk.gov.defra.tracesx.exceptions.CommoditiesMapperException;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

@Component
public class ChedppCommoditiesMapper implements Mapper<SpsCertificate, Commodities> {

  private final ChedppCommodityComplementMapper chedppCommodityComplementMapper;
  private final ChedppComplementParameterSetMapper chedppComplementParameterSetMapper;
  private final RegionOfOriginMapper regionOfOriginMapper;
  private final CountryOfOriginMapper countryOfOriginMapper;
  private final ConsignedCountryMapper consignedCountryMapper;
  private final CountryCodeTransformer countryCodeTransformer;

  @Autowired
  public ChedppCommoditiesMapper(
      ChedppCommodityComplementMapper chedppCommodityComplementMapper,
      ChedppComplementParameterSetMapper chedppComplementParameterSetMapper,
      RegionOfOriginMapper regionOfOriginMapper,
      CountryOfOriginMapper countryOfOriginMapper,
      ConsignedCountryMapper consignedCountryMapper,
      CountryCodeTransformer countryCodeTransformer) {
    this.chedppCommodityComplementMapper = chedppCommodityComplementMapper;
    this.chedppComplementParameterSetMapper = chedppComplementParameterSetMapper;
    this.regionOfOriginMapper = regionOfOriginMapper;
    this.countryOfOriginMapper = countryOfOriginMapper;
    this.consignedCountryMapper = consignedCountryMapper;
    this.countryCodeTransformer = countryCodeTransformer;
  }

  @Override
  public Commodities map(SpsCertificate spsCertificate) throws NotificationMapperException {
    try {
      return Commodities.builder()
          .commodityComplement(chedppCommodityComplementMapper.map(spsCertificate))
          .complementParameterSet(chedppComplementParameterSetMapper.map(spsCertificate))
          .countryOfOrigin(
              countryCodeTransformer.convertCountryCode(countryOfOriginMapper.map(spsCertificate)))
          .regionOfOrigin(regionOfOriginMapper.map(spsCertificate))
          .consignedCountry(consignedCountryMapper.map(spsCertificate))
          .build();
    } catch (CommoditiesMapperException exception) {
      throw new NotificationMapperException(exception.getMessage());
    }
  }
}
