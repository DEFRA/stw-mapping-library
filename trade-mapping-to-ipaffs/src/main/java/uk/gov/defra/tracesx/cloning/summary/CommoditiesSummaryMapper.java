package uk.gov.defra.tracesx.cloning.summary;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

@Component
public class CommoditiesSummaryMapper implements Mapper<Commodities, CommoditiesSummary> {

  @Override
  public CommoditiesSummary map(Commodities commodities) {
    CommoditiesSummary.CommoditiesSummaryBuilder builder = CommoditiesSummary.builder()
        .countryOfOrigin(commodities.getCountryOfOrigin())
        .consignedCountry(commodities.getConsignedCountry())
        .commodityDescriptions(mapCommodityDescriptions(commodities));
    return addTemperatureIfExists(builder, commodities).build();
  }

  private List<CommoditySummary> mapCommodityDescriptions(Commodities commodities) {
    List<CommodityComplement> commodityComplementList = commodities.getCommodityComplement();
    List<ComplementParameterSet> complementParameterSets = commodities.getComplementParameterSet();

    List<CommoditySummary> commoditySummaries = new ArrayList<>();
    for (CommodityComplement commodityComplement : commodityComplementList) {
      for (ComplementParameterSet complementParameterSet : complementParameterSets) {
        if (commodityComplement.getComplementID().equals(
            complementParameterSet.getComplementID())) {
          commoditySummaries.add(CommoditySummary.builder()
              .commodityCode(commodityComplement.getCommodityID())
              .speciesName(commodityComplement.getSpeciesName())
              .description(commodityComplement.getCommodityDescription())
              .netWeight(getDataFromKeyDataPair(
                  complementParameterSet.getKeyDataPair(), "netweight"))
              .packages(getDataFromKeyDataPair(
                  complementParameterSet.getKeyDataPair(), "number_package"))
              .packageType(getDataFromKeyDataPair(
                  complementParameterSet.getKeyDataPair(), "type_package"))
              .quantity(getDataFromKeyDataPair(
                  complementParameterSet.getKeyDataPair(), "quantity"))
              .quantityType(getDataFromKeyDataPair(
                  complementParameterSet.getKeyDataPair(), "type_quantity"))
              .numberOfAnimals(getDataFromKeyDataPair(
                  complementParameterSet.getKeyDataPair(), "number_animal"))
              .build());
        }
      }
    }

    return commoditySummaries;
  }

  private CommoditiesSummary.CommoditiesSummaryBuilder addTemperatureIfExists(
      CommoditiesSummary.CommoditiesSummaryBuilder builder, Commodities commodities) {
    if (commodities.getTemperature() == null) {
      return builder;
    }
    builder.temperature(commodities.getTemperature().getValue());
    return builder;
  }

  private String getDataFromKeyDataPair(
      List<ComplementParameterSetKeyDataPair> keyDataPairs, String key) {
    return keyDataPairs.stream()
        .filter(p -> p.getKey().equals(key))
        .findFirst()
        .map(ComplementParameterSetKeyDataPair::getData)
        .orElse(null);
  }
}
