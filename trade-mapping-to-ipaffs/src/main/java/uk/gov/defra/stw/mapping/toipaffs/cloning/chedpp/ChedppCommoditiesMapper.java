package uk.gov.defra.stw.mapping.toipaffs.cloning.chedpp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.cloning.chedpp.commodities.ChedppCommodityComplementMapper;
import uk.gov.defra.stw.mapping.toipaffs.cloning.chedpp.commodities.ChedppComplementParameterSetMapper;
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.ConsignedCountryMapper;
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.CountryCodeTransformer;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.CountryOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.RegionOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.CommoditiesMapperException;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;

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
