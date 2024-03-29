package uk.gov.defra.stw.mapping.toipaffs.common.commodities;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.PhysicalSpsPackage;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.CommoditiesMapperException;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

@Component
public class ComplementParameterSetMapper {

  private static final String NO_PACKAGING_HIERARCHY = "4";

  public List<ComplementParameterSetKeyDataPair> create(
      Mapper<IncludedSpsTradeLineItem, ComplementParameterSetKeyDataPair> netWeightMeasureMapper,
      Mapper<PhysicalSpsPackage, ComplementParameterSetKeyDataPair> numberOfPackagesMapper,
      Mapper<PhysicalSpsPackage, ComplementParameterSetKeyDataPair> packageTypeMapper,
      IncludedSpsTradeLineItem includedSpsTradeLineItem) {

    List<ComplementParameterSetKeyDataPair> complementParameterSetKeyDataPairList =
        new ArrayList<>();
    try {
      if (includedSpsTradeLineItem.getNetWeightMeasure() != null) {
        ComplementParameterSetKeyDataPair keyDataPair =
            netWeightMeasureMapper.map(includedSpsTradeLineItem);
        if (keyDataPair != null) {
          complementParameterSetKeyDataPairList
              .add(keyDataPair);
        }
      }

      List<PhysicalSpsPackage> physicalSpsPackages = includedSpsTradeLineItem
          .getPhysicalSpsPackage();
      if (physicalSpsPackages != null && !physicalSpsPackages.isEmpty()) {
        PhysicalSpsPackage firstPhysicalSpsPackage = includedSpsTradeLineItem
            .getPhysicalSpsPackage().get(0);
        if (firstPhysicalSpsPackage.getLevelCode().getValue().equals(NO_PACKAGING_HIERARCHY)) {
          complementParameterSetKeyDataPairList
              .add(numberOfPackagesMapper.map(firstPhysicalSpsPackage));
          complementParameterSetKeyDataPairList.add(packageTypeMapper.map(firstPhysicalSpsPackage));
        } else {
          throw new CommoditiesMapperException(
              "Unable to map to the PackageType due to level code");
        }
        return complementParameterSetKeyDataPairList;
      } else {
        throw new CommoditiesMapperException("PhysicalSpsPackages is a mandatory field");
      }
    } catch (NotificationMapperException exception) {
      throw new CommoditiesMapperException(exception.getMessage());
    }
  }
}
