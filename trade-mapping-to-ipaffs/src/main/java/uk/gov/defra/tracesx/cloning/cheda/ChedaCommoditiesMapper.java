package uk.gov.defra.tracesx.cloning.cheda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.cloning.cheda.commodities.ChedaCommodityComplementMapper;
import uk.gov.defra.tracesx.cloning.cheda.commodities.ChedaComplementParameterSetMapper;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.common.common.CountryOfOriginMapper;
import uk.gov.defra.tracesx.common.common.RegionOfOriginMapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

@Component
public class ChedaCommoditiesMapper implements Mapper<SpsCertificate, Commodities> {

  private final ChedaCommodityComplementMapper commodityComplementMapper;
  private final ChedaComplementParameterSetMapper complementParameterSetMapper;
  private final CountryOfOriginMapper countryOfOriginMapper;
  private final RegionOfOriginMapper regionOfOriginMapper;

  @Autowired
  public ChedaCommoditiesMapper(
      ChedaCommodityComplementMapper commodityComplementMapper,
      ChedaComplementParameterSetMapper complementParameterSetMapper,
      CountryOfOriginMapper countryOfOriginMapper,
      RegionOfOriginMapper regionOfOriginMapper) {
    this.commodityComplementMapper = commodityComplementMapper;
    this.complementParameterSetMapper = complementParameterSetMapper;
    this.countryOfOriginMapper = countryOfOriginMapper;
    this.regionOfOriginMapper = regionOfOriginMapper;
  }

  @Override
  public Commodities map(SpsCertificate data) throws NotificationMapperException {
    return Commodities.builder()
        .commodityComplement(commodityComplementMapper.map(data))
        .complementParameterSet(complementParameterSetMapper.map(data))
        .countryOfOrigin(countryOfOriginMapper.map(data))
        .regionOfOrigin(regionOfOriginMapper.map(data))
        .build();
  }
}
