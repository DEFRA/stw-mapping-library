package uk.gov.defra.stw.mapping.toipaffs.chedpp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.chedpp.commodities.ChedppCommodityComplementMapper;
import uk.gov.defra.stw.mapping.toipaffs.chedpp.commodities.ChedppComplementParameterSetMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.CountryOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.RegionOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.TotalGrossWeightMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.CommoditiesMapperException;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;

@Component
public class ChedppCommoditiesMapper implements Mapper<SpsCertificate, Commodities> {

  private final ChedppCommodityComplementMapper chedppCommodityComplementMapper;
  private final ChedppComplementParameterSetMapper chedppComplementParameterSetMapper;
  private final RegionOfOriginMapper regionOfOriginMapper;
  private final TotalGrossWeightMapper totalGrossWeightMapper;
  private final CountryOfOriginMapper countryOfOriginMapper;

  @Autowired
  public ChedppCommoditiesMapper(
      ChedppCommodityComplementMapper chedppCommodityComplementMapper,
      ChedppComplementParameterSetMapper chedppComplementParameterSetMapper,
      RegionOfOriginMapper regionOfOriginMapper,
      TotalGrossWeightMapper totalGrossWeightMapper,
      CountryOfOriginMapper countryOfOriginMapper) {
    this.chedppCommodityComplementMapper = chedppCommodityComplementMapper;
    this.chedppComplementParameterSetMapper = chedppComplementParameterSetMapper;
    this.regionOfOriginMapper = regionOfOriginMapper;
    this.totalGrossWeightMapper = totalGrossWeightMapper;
    this.countryOfOriginMapper = countryOfOriginMapper;
  }

  @Override
  public Commodities map(SpsCertificate spsCertificate) throws NotificationMapperException {
    try {
      return Commodities.builder()
          .commodityComplement(chedppCommodityComplementMapper.map(spsCertificate))
          .complementParameterSet(chedppComplementParameterSetMapper.map(spsCertificate))
          .countryOfOrigin(countryOfOriginMapper.map(spsCertificate))
          .regionOfOrigin(regionOfOriginMapper.map(spsCertificate))
          .consignedCountry(
              spsCertificate.getSpsConsignment().getExportSpsCountry().getId().getValue())
          .totalGrossWeight(totalGrossWeightMapper.map(spsCertificate))
          .build();
    } catch (CommoditiesMapperException exception) {
      throw new NotificationMapperException(exception.getMessage());
    }
  }
}
