package uk.gov.defra.stw.mapping.toipaffs.cheda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.cheda.commodities.AnimalsCertifiedAsMapper;
import uk.gov.defra.stw.mapping.toipaffs.cheda.commodities.ChedaComplementParameterSetMapper;
import uk.gov.defra.stw.mapping.toipaffs.cheda.commodities.IncludeNonAblactedAnimalsMapper;
import uk.gov.defra.stw.mapping.toipaffs.cheda.commodities.NumberOfAnimalsMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.CountryOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.RegionOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.CommodityComplementMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.NumberOfPackagesMapper;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;

@Component
public class ChedaCommoditiesMapper implements Mapper<SpsCertificate, Commodities> {

  private final NumberOfPackagesMapper numberOfPackagesMapper;
  private final NumberOfAnimalsMapper numberOfAnimalsMapper;
  private final IncludeNonAblactedAnimalsMapper includeNonAblactedAnimalsMapper;
  private final CountryOfOriginMapper countryOfOriginMapper;
  private final RegionOfOriginMapper regionOfOriginMapper;
  private final AnimalsCertifiedAsMapper animalsCertifiedAsMapper;
  private final CommodityComplementMapper commodityComplementMapper;
  private final ChedaComplementParameterSetMapper chedaComplementParameterSetMapper;

  @Autowired
  public ChedaCommoditiesMapper(
      NumberOfPackagesMapper numberOfPackagesMapper,
      NumberOfAnimalsMapper numberOfAnimalsMapper,
      IncludeNonAblactedAnimalsMapper includeNonAblactedAnimalsMapper,
      CountryOfOriginMapper countryOfOriginMapper,
      RegionOfOriginMapper regionOfOriginMapper,
      AnimalsCertifiedAsMapper animalsCertifiedAsMapper,
      CommodityComplementMapper commodityComplementMapper,
      ChedaComplementParameterSetMapper chedaComplementParameterSetMapper) {
    this.numberOfPackagesMapper = numberOfPackagesMapper;
    this.numberOfAnimalsMapper = numberOfAnimalsMapper;
    this.includeNonAblactedAnimalsMapper = includeNonAblactedAnimalsMapper;
    this.countryOfOriginMapper = countryOfOriginMapper;
    this.regionOfOriginMapper = regionOfOriginMapper;
    this.animalsCertifiedAsMapper = animalsCertifiedAsMapper;
    this.commodityComplementMapper = commodityComplementMapper;
    this.chedaComplementParameterSetMapper = chedaComplementParameterSetMapper;
  }

  @Override
  public Commodities map(SpsCertificate spsCertificate) {
    return Commodities.builder()
        .numberOfPackages(numberOfPackagesMapper.map(spsCertificate))
        .numberOfAnimals(numberOfAnimalsMapper.map(spsCertificate))
        .includeNonAblactedAnimals(includeNonAblactedAnimalsMapper.map(spsCertificate))
        .countryOfOrigin(countryOfOriginMapper.map(spsCertificate))
        .regionOfOrigin(regionOfOriginMapper.map(spsCertificate))
        .animalsCertifiedAs(animalsCertifiedAsMapper.map(spsCertificate))
        .commodityComplement(commodityComplementMapper.map(spsCertificate))
        .complementParameterSet(chedaComplementParameterSetMapper.map(spsCertificate))
        .build();
  }
}
