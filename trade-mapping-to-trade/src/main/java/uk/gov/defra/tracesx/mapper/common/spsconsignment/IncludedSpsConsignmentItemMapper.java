package uk.gov.defra.tracesx.mapper.common.spsconsignment;

import static uk.gov.defra.tracesx.mapper.common.utils.IpaffsRegionsUtil.getIsoRegionFromIpaffsRegion;
import static uk.gov.defra.tracesx.mapper.common.utils.IpaffsRegionsUtil.isUkRegion;
import static uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter.getCodeType;
import static uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter.getIdType;
import static uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter.getItemQuantity;
import static uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter.getTextType;
import static uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter.getVolumeMeasureType;
import static uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter.getWeightMeasureType;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum.CHEDPP;
import static uk.gov.defra.tracesx.validation.trade.utils.CommodityComplementFinder.getCommodityChecks;
import static uk.gov.defra.tracesx.validation.trade.utils.CommodityComplementFinder.getCommodityComplement;
import static uk.gov.defra.tracesx.validation.trade.utils.CommodityComplementFinder.getData;
import static uk.gov.defra.tracesx.validation.trade.utils.CommodityComplementFinder.getKeyDataPair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityChecks;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperator;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.OfficialVeterinarian;
import uk.gov.defra.tracesx.notificationschema.representation.PartTwo;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType;
import uk.gov.defra.tracesx.trade.dto.ApplicableSpsClassification;
import uk.gov.defra.tracesx.trade.dto.CodeType;
import uk.gov.defra.tracesx.trade.dto.FunctionTypeCode;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsConsignmentItem;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.tracesx.trade.dto.ItemQuantity;
import uk.gov.defra.tracesx.trade.dto.MeasureType;
import uk.gov.defra.tracesx.trade.dto.PackageTypeCodeType;
import uk.gov.defra.tracesx.trade.dto.PhysicalSpsPackage;
import uk.gov.defra.tracesx.trade.dto.SequenceNumeric;
import uk.gov.defra.tracesx.trade.dto.SpsCountrySubDivisionType;
import uk.gov.defra.tracesx.trade.dto.SpsCountryType;
import uk.gov.defra.tracesx.trade.dto.TextType;

public abstract class IncludedSpsConsignmentItemMapper implements
    Mapper<Notification, IncludedSpsConsignmentItem> {

  private static final String NETWEIGHT_KEY = "netweight";
  private static final String NUMBER_PACKAGE_KEY = "number_package";
  private static final String TYPE_PACKAGE_KEY = "type_package";
  private static final String SUBDIVISION_LEVEL_CODE = "1";
  private static final String SUBDIVISION_TYPE_CODE = "106";

  protected static final String SYSTEM_ID = "CN";
  protected static final String SYSTEM_NAME = "CN Code (Combined Nomenclature)";
  protected static final String NO_PACKAGING_HIERARCHY = "4";
  protected final EnumMap<PackageType, PackageTypeCodeType> packageTypeMap;

  public static final String BAG = "BG";
  public static final String BALE = "BL";
  public static final String BOX = "BX";
  public static final String CAN = "CA";
  public static final String CARTON = "CT";
  public static final String CASE = "CS";
  public static final String CASK = "CK";
  public static final String COFFER = "CF";
  public static final String CRATE = "CR";
  public static final String PACKAGE = "PK";
  public static final String PALLET = "PX";
  public static final String POLYSTYRENE_BOX = "ZZ";
  public static final String TRAY = "DT";
  public static final String TUBE = "TU";
  public static final String VIAL = "VI";

  protected IncludedSpsConsignmentItemMapper() {
    packageTypeMap = new EnumMap<>(PackageType.class);
    addElementToMap(packageTypeMap, PackageType.BAG, BAG);
    addElementToMap(packageTypeMap, PackageType.BALE, BALE);
    addElementToMap(packageTypeMap, PackageType.BOX, BOX);
    addElementToMap(packageTypeMap, PackageType.CAN, CAN);
    addElementToMap(packageTypeMap, PackageType.CARTON, CARTON);
    addElementToMap(packageTypeMap, PackageType.CASE, CASE);
    addElementToMap(packageTypeMap, PackageType.CASK, CASK);
    addElementToMap(packageTypeMap, PackageType.COFFER, COFFER);
    addElementToMap(packageTypeMap, PackageType.CRATE, CRATE);
    addElementToMap(packageTypeMap, PackageType.PACKAGE, PACKAGE);
    addElementToMap(packageTypeMap, PackageType.PALLET, PALLET);
    addElementToMap(packageTypeMap, PackageType.POLYSTYRENE_BOX, POLYSTYRENE_BOX);
    addElementToMap(packageTypeMap, PackageType.TRAY, TRAY);
    addElementToMap(packageTypeMap, PackageType.TUBE, TUBE);
    addElementToMap(packageTypeMap, PackageType.VIAL, VIAL);
  }

  protected void addElementToMap(
      EnumMap<PackageType, PackageTypeCodeType> map,
      PackageType description,
      String typeCode) {
    map.put(description, new PackageTypeCodeType().withValue(typeCode));
  }

  @Override
  public IncludedSpsConsignmentItem map(Notification notification) {
    Commodities commodity = notification.getPartOne().getCommodities();
    List<IncludedSpsTradeLineItem> includedSpsTradeLineItems = new ArrayList<>();
    includedSpsTradeLineItems.add(createInitialIncludedSpsTradeLineItem(commodity));

    includedSpsTradeLineItems.addAll(createIncludedSpsTradeLineItems(notification));
    return new IncludedSpsConsignmentItem()
        .withIncludedSpsTradeLineItem(includedSpsTradeLineItems);
  }

  protected List<SpsCountryType> getOriginSpsCountryType(
      TradeLineItemInformation tradeLineItemInformation) {
    var countryOfOrigin = tradeLineItemInformation.getCommodity().getCountryOfOrigin();

    if (countryOfOrigin == null) {
      return Collections.emptyList();
    }

    var countryId = isUkRegion(countryOfOrigin) ? "GB" : countryOfOrigin;
    return Collections.singletonList(new SpsCountryType()
        .withId(getIdType(countryId))
        .withSubordinateSpsCountrySubDivision(
            getSpsCountrySubDivisionType(tradeLineItemInformation.getCommodity())));

  }

  protected List<PhysicalSpsPackage> createPhysicalSpsPackage(
      TradeLineItemInformation tradeLineItemInformation) {
    ComplementParameterSet complementParameterSet =
        tradeLineItemInformation.getComplementParameterSet();

    ItemQuantity numberPackage = getNumberPackage(complementParameterSet);
    PackageTypeCodeType packageType = getPackageType(complementParameterSet);

    if (numberPackage == null || packageType == null) {
      return Collections.emptyList();
    }
    return Collections.singletonList(new PhysicalSpsPackage()
        .withLevelCode(new CodeType()
            .withValue(NO_PACKAGING_HIERARCHY))
        .withTypeCode(packageType)
        .withItemQuantity(numberPackage));
  }

  protected IncludedSpsTradeLineItem createIncludedSpsTradeLineItem(
      TradeLineItemInformation tradeLineItemInformation) {
    IncludedSpsTradeLineItem includedSpsTradeLineItem = new IncludedSpsTradeLineItem()
        .withSequenceNumeric(new SequenceNumeric().withValue(tradeLineItemInformation.getIndex()))
        .withOriginSpsCountry(getOriginSpsCountryType(tradeLineItemInformation))
        .withScientificName(getScientificName(tradeLineItemInformation))
        .withNetWeightMeasure(getNetWeight(tradeLineItemInformation))
        .withApplicableSpsClassification(
            createApplicableSpsClassification(tradeLineItemInformation));

    List<PhysicalSpsPackage> physicalSpsPackage =
        createPhysicalSpsPackage(tradeLineItemInformation);
    if (!physicalSpsPackage.isEmpty()) {
      includedSpsTradeLineItem.withPhysicalSpsPackage(physicalSpsPackage);
    }
    return includedSpsTradeLineItem;
  }

  protected List<ApplicableSpsClassification> createApplicableSpsClassification(
      TradeLineItemInformation tradeLineItemInformation) {
    CommodityComplement commodityComplement = tradeLineItemInformation.getCommodityComplement();
    return Collections.singletonList(new ApplicableSpsClassification()
        .withSystemID(getIdType(SYSTEM_ID))
        .withSystemName(Collections.singletonList(new TextType()
            .withValue(SYSTEM_NAME)))
        .withClassCode(getCodeType(commodityComplement.getCommodityID()))
        .withClassName(Collections.singletonList(
            getTextType(commodityComplement.getCommodityDescription()))));
  }

  private IncludedSpsTradeLineItem createInitialIncludedSpsTradeLineItem(Commodities commodity) {
    return new IncludedSpsTradeLineItem()
        .withSequenceNumeric(new SequenceNumeric().withValue(0))
        .withNetWeightMeasure(getWeightMeasureType(commodity.getTotalNetWeight()))
        .withGrossWeightMeasure(getWeightMeasureType(commodity.getTotalGrossWeight()))
        .withGrossVolumeMeasure(getVolumeMeasureType(commodity.getTotalGrossVolumeUnit(),
            commodity.getTotalGrossVolume()));
  }

  private List<IncludedSpsTradeLineItem> createIncludedSpsTradeLineItems(
      Notification notification) {
    Commodities commodity = notification.getPartOne().getCommodities();

    if (commodity == null) {
      return Collections.emptyList();
    }

    int index = 1;
    List<IncludedSpsTradeLineItem> includedSpsTradeLineItems = new ArrayList<>();

    for (ComplementParameterSet complementParameterSet : commodity.getComplementParameterSet()) {
      CommodityComplement commodityComplement = getCommodityComplement(
          complementParameterSet.getComplementID(),
          complementParameterSet.getSpeciesID(),
          commodity.getCommodityComplement());

      CommodityChecks commodityChecks =
          getCommodityChecks(complementParameterSet,
              notification.getPartTwo());

      OfficialVeterinarian officialVeterinarian =
          getOfficialVeterinarian(notification.getPartTwo());

      TradeLineItemInformation tradeLineItemInformation = new TradeLineItemInformation(
          index++, commodity, commodityComplement, complementParameterSet,
          notification.getPartOne().getPacker(), commodityChecks, officialVeterinarian);

      IncludedSpsTradeLineItem includedSpsTradeLineItem =
          createIncludedSpsTradeLineItem(tradeLineItemInformation);

      if (!(notification.getType() == CHEDPP
          && isArticle72Country(notification.getPartOne().getCommodities())
          && complementParameterSet.isArticle72())) {
        includedSpsTradeLineItems.add(includedSpsTradeLineItem);
      }
    }

    return includedSpsTradeLineItems;
  }

  private boolean isArticle72Country(Commodities commodities) {
    return Optional.ofNullable(commodities.getIsLowRiskArticle72Country()).orElse(false);
  }

  private OfficialVeterinarian getOfficialVeterinarian(PartTwo partTwo) {
    if (partTwo == null || partTwo.getControlAuthority() == null) {
      return null;
    }

    return partTwo.getControlAuthority().getOfficialVeterinarian();
  }

  private List<TextType> getScientificName(TradeLineItemInformation tradeLineItemInformation) {
    String commoditySpeciesName =
        tradeLineItemInformation.getCommodityComplement().getSpeciesName();
    if (commoditySpeciesName == null) {
      return Collections.emptyList();
    }
    return Collections.singletonList(getTextType(commoditySpeciesName));
  }

  private MeasureType getNetWeight(TradeLineItemInformation tradeLineItemInformation) {
    ComplementParameterSet complementParameterSet =
        tradeLineItemInformation.getComplementParameterSet();
    if (complementParameterSet == null) {
      return null;
    }
    return getWeightMeasureType(getData(
        getKeyDataPair(NETWEIGHT_KEY, complementParameterSet.getKeyDataPair())));
  }

  private ItemQuantity getNumberPackage(ComplementParameterSet complementParameterSet) {
    if (complementParameterSet == null) {
      return null;
    }
    return getItemQuantity(getData(
        getKeyDataPair(NUMBER_PACKAGE_KEY, complementParameterSet.getKeyDataPair())));
  }

  private PackageTypeCodeType getPackageType(ComplementParameterSet complementParameterSet) {
    if (complementParameterSet == null) {
      return null;
    }
    return packageTypeMap.get(PackageType.fromValue(getData(
        getKeyDataPair(TYPE_PACKAGE_KEY, complementParameterSet.getKeyDataPair()))));
  }

  private List<SpsCountrySubDivisionType> getSpsCountrySubDivisionType(Commodities commodity) {

    var countryOfOrigin = commodity.getCountryOfOrigin();

    if (commodity.getRegionOfOrigin() == null && !isUkRegion(countryOfOrigin)) {
      return Collections.emptyList();
    }

    var countryText = isUkRegion(countryOfOrigin)
        ? getIsoRegionFromIpaffsRegion(countryOfOrigin) : commodity.getRegionOfOrigin();

    return Collections.singletonList(new SpsCountrySubDivisionType()
        .withHierarchicalLevelCode(new CodeType().withValue(SUBDIVISION_LEVEL_CODE))
        .withFunctionTypeCode(new FunctionTypeCode().withValue(SUBDIVISION_TYPE_CODE))
        .withName(Collections.singletonList(getTextType(countryText))));
  }

  @Getter
  @AllArgsConstructor
  protected static class TradeLineItemInformation {

    private final int index;
    private final Commodities commodity;
    private final CommodityComplement commodityComplement;
    private final ComplementParameterSet complementParameterSet;
    private final EconomicOperator packer;
    private final CommodityChecks commodityChecks;
    private final OfficialVeterinarian officialVeterinarian;
  }
}
