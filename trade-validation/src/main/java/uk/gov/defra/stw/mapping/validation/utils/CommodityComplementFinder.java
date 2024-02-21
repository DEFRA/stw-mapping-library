package uk.gov.defra.stw.mapping.validation.utils;

import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityChecks;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.notificationschema.representation.PartTwo;

public class CommodityComplementFinder {

  private CommodityComplementFinder() {
  }

  public static CommodityComplement getCommodityComplement(
      Integer complementId,
      String speciesId,
      List<CommodityComplement> commodityComplements) {

    if (StringUtils.isNotEmpty(speciesId)) {
      return commodityComplements.stream()
          .filter(list -> list.getComplementID().equals(complementId))
          .filter(list -> list.getSpeciesID().equals(speciesId))
          .findFirst()
          .orElse(null);
    }

    return commodityComplements.stream()
        .filter(list -> list.getComplementID().equals(complementId))
        .findFirst()
        .orElse(null);
  }

  public static List<ComplementParameterSet> getComplementParameterSet(
      Integer complementId,
      String speciesId,
      List<ComplementParameterSet> parameterSetList) {
    if (parameterSetList == null) {
      return Collections.emptyList();
    }

    if (StringUtils.isNotEmpty(speciesId)) {
      return parameterSetList.stream()
          .filter(list -> list.getComplementID().equals(complementId))
          .filter(list -> list.getSpeciesID().equals(speciesId))
          .toList();
    }

    return parameterSetList.stream()
        .filter(list -> list.getComplementID().equals(complementId))
        .toList();
  }

  public static ComplementParameterSetKeyDataPair getKeyDataPair(
      String key,
      List<ComplementParameterSetKeyDataPair> keyDataPairs) {
    if (keyDataPairs == null) {
      return null;
    }
    return keyDataPairs.stream()
        .filter(x -> x.getKey().equals(key))
        .findAny()
        .orElse(null);
  }

  public static String getData(ComplementParameterSetKeyDataPair keyDataPair) {
    return keyDataPair == null ? null : keyDataPair.getData();
  }

  public static CommodityChecks getCommodityChecks(ComplementParameterSet complementParameterSet,
      PartTwo partTwo) {
    if (complementParameterSet == null
        || partTwo == null
        || partTwo.getCommodityChecks() == null) {
      return null;
    }

    return partTwo.getCommodityChecks().stream()
        .filter(commodityChecks ->
            commodityChecks.getUniqueComplementId()
                .equals(complementParameterSet.getUniqueComplementID()))
        .findFirst()
        .orElse(null);
  }
}
