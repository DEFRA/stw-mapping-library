package uk.gov.defra.stw.mapping.toipaffs.cloning.cheda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.cloning.cheda.commodities.ChedaCommodityComplementMapper;
import uk.gov.defra.stw.mapping.toipaffs.cloning.cheda.commodities.ChedaComplementParameterSetMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.CountryOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.RegionOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;

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
