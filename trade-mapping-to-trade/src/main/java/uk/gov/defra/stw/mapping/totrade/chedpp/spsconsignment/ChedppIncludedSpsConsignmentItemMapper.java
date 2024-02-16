package uk.gov.defra.stw.mapping.totrade.chedpp.spsconsignment;

import static uk.gov.defra.stw.mapping.totrade.common.utils.Format.localDateTime;
import static uk.gov.defra.stw.mapping.totrade.common.utils.SpsTypeConverter.getItemQuantity;
import static uk.gov.defra.stw.mapping.totrade.common.utils.SpsTypeConverter.getQuantityMeasureType;
import static uk.gov.defra.stw.mapping.totrade.common.utils.SpsTypeConverter.getTextType;
import static uk.gov.defra.stw.mapping.validation.utils.CommodityComplementFinder.getData;
import static uk.gov.defra.stw.mapping.validation.utils.CommodityComplementFinder.getKeyDataPair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.ApplicableSpsClassification;
import uk.gov.defra.stw.mapping.dto.AppliedSpsProcess;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.DateTime;
import uk.gov.defra.stw.mapping.dto.DateTimeType;
import uk.gov.defra.stw.mapping.dto.GovernmentActionCodeType;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsClause;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.ItemQuantity;
import uk.gov.defra.stw.mapping.dto.MeasureType;
import uk.gov.defra.stw.mapping.dto.PackageTypeCodeType;
import uk.gov.defra.stw.mapping.dto.PhysicalSpsPackage;
import uk.gov.defra.stw.mapping.dto.ProcessTypeCodeType;
import uk.gov.defra.stw.mapping.dto.RoleCode;
import uk.gov.defra.stw.mapping.dto.RoleCode.Value;
import uk.gov.defra.stw.mapping.dto.SpecifiedSpsPerson;
import uk.gov.defra.stw.mapping.dto.SpsAuthenticationType;
import uk.gov.defra.stw.mapping.dto.SpsCountryType;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.SpsPartyType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.totrade.common.spsconsignment.IncludedSpsConsignmentItemMapper;
import uk.gov.defra.stw.mapping.totrade.common.spsconsignment.SpsPartyMapper;
import uk.gov.defra.stw.mapping.totrade.common.utils.SpsTypeConverter;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityChecks;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperator;
import uk.gov.defra.tracesx.notificationschema.representation.InspectionCheck;
import uk.gov.defra.tracesx.notificationschema.representation.OfficialVeterinarian;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType;

@Component
public class ChedppIncludedSpsConsignmentItemMapper extends IncludedSpsConsignmentItemMapper {

  private static final String COUNTRY_OF_ORIGIN_KEY = "country-of-origin";
  private static final String UNITS_QUANTITY_KEY = "units-quantity";
  private static final String UNITS_TYPE_KEY = "units-type";
  private static final String CONTROLLED_ATMOSPHERE_CONTAINER_KEY = "container";
  private static final String CONTROLLED_ATMOSPHERE_CONTAINER = "CONTROLLED_ATMOSPHERE_CONTAINER";
  private static final String VARIETY_KEY = "variety";
  private static final String VARIETY = "VARIETY";
  private static final String OTHER_VARIETY_KEY = "other_variety";
  private static final String OTHER_VARIETY = "OTHER_VARIETY";
  private static final String CLASS_KEY = "class";
  private static final String CLASS = "CLASS";
  private static final String REGULATORY_AUTHORITY_KEY = "regulatory_authority";
  private static final String REGULATORY_AUTHORITY = "REGULATORY_AUTHORITY";
  private static final String YES = "YES";
  private static final String NO = "NO";
  private static final String IS_WOOD_PACKAGING = "IS_WOOD_PACKAGING";
  private static final String APPLIED_PROCESS_TYPE_CODE = "13";
  private static final String STATUS = "STATUS";
  private static final String REASON = "REASON";
  private static final String OTHER_REASON = "OTHER_REASON";
  private static final String AUTHORITY = "AUTHORITY";
  private static final String CLEARANCE_TYPE_CODE_VALUE = "1";
  private static final String SYSTEM_ID_EPPO_CODE = "EPPT";
  private static final String SYSTEM_NAME_EPPO_CODE = "EPPO Plant Protection Thesaurus";
  private static final String UNIQUE_ID = "UNIQUE_ID";

  private final EnumMap<PackageType, PackageTypeCodeType> unitsTypeMap;

  private static final String VALIDITY_PERIOD_KEY = "validity_period";
  private static final String VALIDITY_PERIOD = "VALIDITY_PERIOD";
  private static final String VALIDITY_PERIOD_AT_INSPECTION = "VALIDITY_PERIOD_AT_INSPECTION";

  private static final String FINISHED_OR_PROPAGATED_KEY = "finished_or_propagated";
  private static final String FINISHED_OR_PROPAGATED_SUBJECT_CODE = "END_USE_CODE";

  private static final String FOR_TRIAL_OR_TESTING_KEY = "for_test_and_trial";
  private static final String FOR_TRIAL_OR_TESTING_SUBJECT_CODE = "FOR_TRIAL_OR_TESTING";

  public static final String BOTTLE_FLASK_GLASS = "GR";
  public static final String BOX = "BX";
  public static final String BULK = "VR";
  public static final String CASE = "CS";
  public static final String COFFER = "CF";
  public static final String CONTAINER = "CN";
  public static final String DRUM = "DR";
  public static final String LOGS = "LZ";
  public static final String NATURAL_WOOD_BOX_WITH_SIFT_PROOF = "QQ";
  public static final String NATURAL_WOOD_ORDINARY_BOX = "QP";
  public static final String OTHER = "NA";
  public static final String PALLET = "PX";
  public static final String WOOD_BARREL = "QH";
  public static final String WOOD_BARREL_WITH_REMOVABLE_HEAD = "QJ";
  public static final String WOOD_BUNDLE = "8C";
  public static final String WOOD_CASE_WITH_PALLET_BASE = "EE";
  public static final String WOOD_CRATE = "8B";
  public static final String WOOD_PACKAGE_DISPLAY = "IA";
  public static final String BLOCK = "OK";
  public static final String CONTAINER_NOT_SPECIFIED = "CN";
  public static final String IN_BULK = "VR";
  public static final String LEGACY_BULK = "VR";
  public static final String DUNNAGE = "OK";
  public static final String LUG = "LU";
  public static final String PERSONAL_LUGGAGE = "LE";
  public static final String PLASTIC_FILM = "XD";
  public static final String PUNNET = "PJ";
  public static final String REDNET = "RT";
  public static final String JAR = "JR";
  public static final String PAIL = "PL";
  public static final String TANK = "TK";
  public static final String TOTE = "TT";

  private final SpsPartyMapper spsPartyMapper;

  @Autowired
  public ChedppIncludedSpsConsignmentItemMapper(SpsPartyMapper spsPartyMapper) {
    unitsTypeMap = new EnumMap<>(PackageType.class);

    addTypeCodesToUnitMap();
    addTypeCodesToPackageMap();

    this.spsPartyMapper = spsPartyMapper;
  }

  @Override
  protected IncludedSpsTradeLineItem createIncludedSpsTradeLineItem(
      TradeLineItemInformation tradeLineItemInformation) {
    IncludedSpsTradeLineItem includedSpsTradeLineItem =
        super.createIncludedSpsTradeLineItem(tradeLineItemInformation);

    includedSpsTradeLineItem
        .withAdditionalInformationSpsNote(
            createAdditionalInformationSpsNotes(tradeLineItemInformation));

    AppliedSpsProcess appliedSpsProcess =
        createAppliedSpsProcess(tradeLineItemInformation.getPacker());
    if (appliedSpsProcess != null) {
      includedSpsTradeLineItem.setAppliedSpsProcess(
          Collections.singletonList(appliedSpsProcess));
    }

    includedSpsTradeLineItem.setAssertedSpsAuthentication(
        createAssertedSpsAuthentication(tradeLineItemInformation));

    includedSpsTradeLineItem.setNetVolumeMeasure(createNetVolumeMeasure(tradeLineItemInformation));

    return includedSpsTradeLineItem;
  }

  private MeasureType createNetVolumeMeasure(TradeLineItemInformation tradeLineItemInformation) {
    List<ComplementParameterSetKeyDataPair> keyDataPairs = tradeLineItemInformation
        .getComplementParameterSet().getKeyDataPair();
    return getQuantityMeasureType(
        getData(getKeyDataPair(ComplementParameterSet.TYPE_QUANTITY, keyDataPairs)),
        getData(getKeyDataPair(ComplementParameterSet.QUANTITY, keyDataPairs)));
  }

  private List<SpsAuthenticationType> createAssertedSpsAuthentication(
      TradeLineItemInformation tradeLineItemInformation) {
    if (tradeLineItemInformation.getCommodityChecks() == null
        || tradeLineItemInformation.getOfficialVeterinarian() == null) {
      return Collections.emptyList();
    }

    return Collections.singletonList(new SpsAuthenticationType()
        .withTypeCode(new GovernmentActionCodeType()
            .withValue(CLEARANCE_TYPE_CODE_VALUE))
        .withActualDateTime(new DateTimeType()
            .withDateTime(new DateTime()
                .withValue(localDateTime.apply(
                    tradeLineItemInformation.getOfficialVeterinarian().getSigned()))))
        .withProviderSpsParty(createProviderSpsParty(
            tradeLineItemInformation.getOfficialVeterinarian()))
        .withIncludedSpsClause(createIncludedSpsClause(
            tradeLineItemInformation.getCommodityChecks())));
  }

  private SpsPartyType createProviderSpsParty(OfficialVeterinarian officialVeterinarian) {
    return new SpsPartyType()
        .withRoleCode(new RoleCode().withValue(Value.AM))
        .withTypeCode(Collections.singletonList(new CodeType().withValue(AUTHORITY)))
        .withSpecifiedSpsPerson(createSpecifiedSpsPerson(officialVeterinarian));
  }

  private SpecifiedSpsPerson createSpecifiedSpsPerson(
      OfficialVeterinarian officialVeterinarian) {
    return new SpecifiedSpsPerson()
        .withName(SpsTypeConverter.getTextType(
            officialVeterinarian.getFirstName() + " " + officialVeterinarian.getLastName()));
  }

  private List<IncludedSpsClause> createIncludedSpsClause(CommodityChecks commodityChecks) {
    List<IncludedSpsClause> includedSpsClauseList = new ArrayList<>();

    for (InspectionCheck inspectionCheck : commodityChecks.getChecks()) {
      includedSpsClauseList.add(createIncludedSpsClause(inspectionCheck,
          STATUS, inspectionCheck.getStatus().getValue()));

      if (StringUtils.isNotEmpty(inspectionCheck.getReason())) {
        includedSpsClauseList.add(createIncludedSpsClause(
            inspectionCheck, REASON, inspectionCheck.getReason()));
      }

      if (StringUtils.isNotEmpty(inspectionCheck.getOtherReason())) {
        includedSpsClauseList.add(createIncludedSpsClause(
            inspectionCheck, OTHER_REASON, inspectionCheck.getOtherReason()));
      }
    }

    return includedSpsClauseList;
  }

  private IncludedSpsClause createIncludedSpsClause(
      InspectionCheck inspectionCheck, String id, String content) {
    return new IncludedSpsClause()
        .withId(SpsTypeConverter.getIdType(
            concatTypeAndStatus(inspectionCheck.getType().getValue(), id)))
        .withContent(Collections.singletonList(
            SpsTypeConverter.getTextType(content)));
  }

  private String concatTypeAndStatus(String type, String status) {
    return type + "_" + status;
  }

  @Override
  protected List<SpsCountryType> getOriginSpsCountryType(
      TradeLineItemInformation tradeLineItemInformation) {
    if (isWoodPackagingRequired(tradeLineItemInformation)) {
      return Collections.singletonList(new SpsCountryType()
          .withId(new IDType()
              .withValue(getCountryOfOrigin(
                  tradeLineItemInformation.getComplementParameterSet()))));
    }

    return super.getOriginSpsCountryType(tradeLineItemInformation);
  }

  @Override
  protected List<PhysicalSpsPackage> createPhysicalSpsPackage(
      TradeLineItemInformation tradeLineItemInformation) {
    if (isWoodPackagingRequired(tradeLineItemInformation)) {
      ComplementParameterSet complementParameterSet =
          tradeLineItemInformation.getComplementParameterSet();
      ItemQuantity unitsQuantity = getUnitsQuantity(complementParameterSet);
      PackageTypeCodeType unitsType = getUnitsType(complementParameterSet);

      if (unitsQuantity == null || unitsType == null) {
        return Collections.emptyList();
      }

      return Collections.singletonList(new PhysicalSpsPackage()
          .withLevelCode(new CodeType()
              .withValue(NO_PACKAGING_HIERARCHY))
          .withTypeCode(unitsType)
          .withItemQuantity(unitsQuantity));
    } else {
      return super.createPhysicalSpsPackage(tradeLineItemInformation);
    }
  }

  @Override
  protected List<ApplicableSpsClassification> createApplicableSpsClassification(
      TradeLineItemInformation tradeLineItemInformation) {
    List<ApplicableSpsClassification> applicableSpsClassifications = new ArrayList<>();
    applicableSpsClassifications.add(mapEppoCode(tradeLineItemInformation));
    applicableSpsClassifications.addAll(
        super.createApplicableSpsClassification(tradeLineItemInformation));

    return applicableSpsClassifications;
  }

  private void addTypeCodesToPackageMap() {
    addElementToMap(packageTypeMap, PackageType.BULK, BULK);
    addElementToMap(packageTypeMap, PackageType.BOTTLE_FLASK_GLASS, BOTTLE_FLASK_GLASS);
    addElementToMap(packageTypeMap, PackageType.CONTAINER, CONTAINER);
    addElementToMap(packageTypeMap, PackageType.OTHER, OTHER);
    addElementToMap(packageTypeMap, PackageType.WOOD_BUNDLE, WOOD_BUNDLE);
    addElementToMap(packageTypeMap, PackageType.WOOD_CRATE, WOOD_CRATE);
    addElementToMap(packageTypeMap, PackageType.WOOD_BARREL, WOOD_BARREL);
    addElementToMap(packageTypeMap, PackageType.WOOD_CASE_WITH_PALLET_BASE,
        WOOD_CASE_WITH_PALLET_BASE);
    addElementToMap(packageTypeMap, PackageType.BLOCK, BLOCK);
    addElementToMap(packageTypeMap, PackageType.CONTAINER_NOT_SPECIFIED, CONTAINER_NOT_SPECIFIED);
    addElementToMap(packageTypeMap, PackageType.IN_BULK, IN_BULK);
    addElementToMap(packageTypeMap, PackageType.LEGACY_BULK, LEGACY_BULK);
    addElementToMap(packageTypeMap, PackageType.DUNNAGE, DUNNAGE);
    addElementToMap(packageTypeMap, PackageType.LOGS, LOGS);
    addElementToMap(packageTypeMap, PackageType.LUG, LUG);
    addElementToMap(packageTypeMap, PackageType.NATURAL_WOOD_BOX_WITH_SIFT_PROOF,
        NATURAL_WOOD_BOX_WITH_SIFT_PROOF);
    addElementToMap(packageTypeMap, PackageType.NATURAL_WOOD_ORDINARY_BOX,
        NATURAL_WOOD_ORDINARY_BOX);
    addElementToMap(packageTypeMap, PackageType.PERSONAL_LUGGAGE, PERSONAL_LUGGAGE);
    addElementToMap(packageTypeMap, PackageType.PLASTIC_FILM, PLASTIC_FILM);
    addElementToMap(packageTypeMap, PackageType.PUNNET, PUNNET);
    addElementToMap(packageTypeMap, PackageType.REDNET, REDNET);
    addElementToMap(packageTypeMap, PackageType.WOOD_PACKAGE_DISPLAY, WOOD_PACKAGE_DISPLAY);
    addElementToMap(packageTypeMap, PackageType.WOOD_BARREL_WITH_REMOVABLE_HEAD,
        WOOD_BARREL_WITH_REMOVABLE_HEAD);
    addElementToMap(packageTypeMap, PackageType.DRUM, DRUM);
    addElementToMap(packageTypeMap, PackageType.JAR, JAR);
    addElementToMap(packageTypeMap, PackageType.PAIL, PAIL);
    addElementToMap(packageTypeMap, PackageType.TANK, TANK);
    addElementToMap(packageTypeMap, PackageType.TOTE, TOTE);
  }

  private void addTypeCodesToUnitMap() {
    addElementToMap(unitsTypeMap, PackageType.BOX, BOX);
    addElementToMap(unitsTypeMap, PackageType.CASE, CASE);
    addElementToMap(unitsTypeMap, PackageType.COFFER, COFFER);
    addElementToMap(unitsTypeMap, PackageType.CONTAINER, CONTAINER);

    addElementToMap(unitsTypeMap, PackageType.DRUM, DRUM);
    addElementToMap(unitsTypeMap, PackageType.LOGS, LOGS);
    addElementToMap(unitsTypeMap, PackageType.NATURAL_WOOD_BOX_WITH_SIFT_PROOF,
        NATURAL_WOOD_BOX_WITH_SIFT_PROOF);
    addElementToMap(unitsTypeMap, PackageType.NATURAL_WOOD_ORDINARY_BOX, NATURAL_WOOD_ORDINARY_BOX);

    addElementToMap(unitsTypeMap, PackageType.PALLET, PALLET);
    addElementToMap(unitsTypeMap, PackageType.WOOD_CRATE, WOOD_CRATE);
    addElementToMap(unitsTypeMap, PackageType.WOOD_PACKAGE_DISPLAY, WOOD_PACKAGE_DISPLAY);
    addElementToMap(unitsTypeMap, PackageType.WOOD_BARREL, WOOD_BARREL);

    addElementToMap(unitsTypeMap, PackageType.WOOD_BARREL_WITH_REMOVABLE_HEAD,
        WOOD_BARREL_WITH_REMOVABLE_HEAD);
    addElementToMap(unitsTypeMap, PackageType.WOOD_CASE_WITH_PALLET_BASE,
        WOOD_CASE_WITH_PALLET_BASE);
  }

  private List<SpsNoteType> createAdditionalInformationSpsNotes(
      TradeLineItemInformation tradeLineItemInformation) {
    List<SpsNoteType> spsNotes = new ArrayList<>();

    SpsNoteType controlledAtmosphereSpsNote =
        createControlledAtmosphereSpsNote(tradeLineItemInformation);
    if (controlledAtmosphereSpsNote != null) {
      spsNotes.add(controlledAtmosphereSpsNote);
    }

    SpsNoteType varietySpsNote =
        createSpsNote(VARIETY_KEY, VARIETY, tradeLineItemInformation);
    if (varietySpsNote != null) {
      spsNotes.add(varietySpsNote);
    }

    SpsNoteType otherVarietySpsNote =
        createSpsNote(OTHER_VARIETY_KEY, OTHER_VARIETY, tradeLineItemInformation);
    if (otherVarietySpsNote != null) {
      spsNotes.add(otherVarietySpsNote);
    }

    SpsNoteType classSpsNote =
        createSpsNote(CLASS_KEY, CLASS, tradeLineItemInformation);
    if (classSpsNote != null) {
      spsNotes.add(classSpsNote);
    }

    SpsNoteType regulatoryAuthoritySpsNote =
        createSpsNote(REGULATORY_AUTHORITY_KEY, REGULATORY_AUTHORITY, tradeLineItemInformation);
    if (regulatoryAuthoritySpsNote != null) {
      spsNotes.add(regulatoryAuthoritySpsNote);
    }

    SpsNoteType isWoodPackagingSpsNote = createIsWoodPackagingSpsNote(tradeLineItemInformation);
    if (isWoodPackagingSpsNote != null) {
      spsNotes.add(isWoodPackagingSpsNote);
    }

    SpsNoteType uniqueIdSpsNote = createUniqueIdSpsNote(tradeLineItemInformation);
    if (uniqueIdSpsNote != null) {
      spsNotes.add(uniqueIdSpsNote);
    }

    SpsNoteType validityPeriod =
        createSpsNote(VALIDITY_PERIOD_KEY, VALIDITY_PERIOD, tradeLineItemInformation);
    if (validityPeriod != null) {
      spsNotes.add(validityPeriod);
    }

    SpsNoteType validityPeriodAtInspection =
        addValidityPeriodAtInspectionSpsNote(VALIDITY_PERIOD_AT_INSPECTION,
            tradeLineItemInformation);
    if (validityPeriodAtInspection != null) {
      spsNotes.add(validityPeriodAtInspection);
    }

    Optional.ofNullable(createSpsNote(
        FINISHED_OR_PROPAGATED_KEY,
        FINISHED_OR_PROPAGATED_SUBJECT_CODE,
        tradeLineItemInformation)).ifPresent(spsNotes::add);

    Optional.ofNullable(createSpsNote(
        FOR_TRIAL_OR_TESTING_KEY,
        FOR_TRIAL_OR_TESTING_SUBJECT_CODE,
        tradeLineItemInformation)).ifPresent(spsNotes::add);

    return spsNotes;
  }

  private String getCountryOfOrigin(ComplementParameterSet complementParameterSet) {
    return getData(
        getKeyDataPair(COUNTRY_OF_ORIGIN_KEY, complementParameterSet.getKeyDataPair()));
  }

  private ItemQuantity getUnitsQuantity(ComplementParameterSet complementParameterSet) {
    return getItemQuantity(getData(
        getKeyDataPair(UNITS_QUANTITY_KEY, complementParameterSet.getKeyDataPair())));
  }

  private PackageTypeCodeType getUnitsType(ComplementParameterSet complementParameterSet) {
    return unitsTypeMap.get(PackageType.fromValue(getData(
        getKeyDataPair(UNITS_TYPE_KEY, complementParameterSet.getKeyDataPair()))));
  }

  private String convertBooleanToStringRepresentation(Boolean bool) {
    return bool.equals(Boolean.TRUE) ? YES : NO;
  }

  private boolean isWoodPackagingRequired(TradeLineItemInformation tradeLineItemInformation) {
    if (tradeLineItemInformation.getComplementParameterSet() != null) {
      CommodityComplement commodityComplement = tradeLineItemInformation.getCommodityComplement();
      Boolean isWoodPackaging = commodityComplement.getIsWoodPackaging();
      return isWoodPackaging != null && isWoodPackaging.equals(Boolean.TRUE);
    }

    return false;
  }

  private SpsNoteType createControlledAtmosphereSpsNote(
      TradeLineItemInformation tradeLineItemInformation) {
    ComplementParameterSet complementParameterSet =
        tradeLineItemInformation.getComplementParameterSet();

    if (complementParameterSet == null) {
      return null;
    }

    ComplementParameterSetKeyDataPair containerKeyDataPair = getKeyDataPair(
        CONTROLLED_ATMOSPHERE_CONTAINER_KEY, complementParameterSet.getKeyDataPair());
    if (containerKeyDataPair != null) {
      return new SpsNoteType()
          .withContent(Collections.singletonList(
              getTextType(tradeLineItemInformation.getCommodityComplement().getSpeciesName())))
          .withSubjectCode(new CodeType().withValue(CONTROLLED_ATMOSPHERE_CONTAINER));
    }

    return null;
  }

  private SpsNoteType createSpsNote(
      String key, String subjectCode, TradeLineItemInformation tradeLineItemInformation) {
    ComplementParameterSet complementParameterSet =
        tradeLineItemInformation.getComplementParameterSet();

    if (complementParameterSet == null) {
      return null;
    }

    ComplementParameterSetKeyDataPair containerKeyDataPair = getKeyDataPair(
        key, complementParameterSet.getKeyDataPair());
    if (containerKeyDataPair != null) {
      return new SpsNoteType()
          .withContent(Collections.singletonList(
              getTextType(containerKeyDataPair.getData())))
          .withSubjectCode(new CodeType().withValue(subjectCode));
    }

    return null;
  }

  private SpsNoteType addValidityPeriodAtInspectionSpsNote(String subjectCode,
      TradeLineItemInformation tradeLineItemInformation) {
    if (tradeLineItemInformation.getCommodityChecks() == null
        || tradeLineItemInformation.getCommodityChecks().getValidityPeriod() == null) {
      return null;
    }

    Integer validityPeriod = tradeLineItemInformation.getCommodityChecks().getValidityPeriod();

    return new SpsNoteType()
        .withContent(Collections.singletonList(
            getTextType(String.valueOf(validityPeriod))))
        .withSubjectCode(new CodeType().withValue(subjectCode));
  }

  private SpsNoteType createIsWoodPackagingSpsNote(
      TradeLineItemInformation tradeLineItemInformation) {
    Boolean isWoodPackaging =
        tradeLineItemInformation.getCommodityComplement().getIsWoodPackaging();

    if (isWoodPackaging != null) {
      return new SpsNoteType()
          .withContent(Collections.singletonList(new TextType()
              .withValue(convertBooleanToStringRepresentation(isWoodPackaging))))
          .withSubjectCode(new CodeType()
              .withValue(IS_WOOD_PACKAGING));
    }

    return null;
  }

  private SpsNoteType createUniqueIdSpsNote(TradeLineItemInformation tradeLineItemInformation) {
    ComplementParameterSet complementParameterSet =
        tradeLineItemInformation.getComplementParameterSet();

    if (complementParameterSet == null || complementParameterSet.getUniqueComplementID() == null) {
      return null;
    }

    return new SpsNoteType()
        .withContent(Collections.singletonList(new TextType()
            .withValue(complementParameterSet.getUniqueComplementID().toString())))
        .withSubjectCode(new CodeType().withValue(UNIQUE_ID));
  }

  private AppliedSpsProcess createAppliedSpsProcess(EconomicOperator packer) {
    if (packer == null) {
      return null;
    }

    return new AppliedSpsProcess()
        .withTypeCode(new ProcessTypeCodeType().withValue(APPLIED_PROCESS_TYPE_CODE))
        .withOperatorSpsParty(spsPartyMapper.map(packer));
  }

  private ApplicableSpsClassification mapEppoCode(
      TradeLineItemInformation tradeLineItemInformation) {
    CommodityComplement commodityComplement = tradeLineItemInformation.getCommodityComplement();

    return new ApplicableSpsClassification()
        .withSystemID(new IDType()
            .withValue(SYSTEM_ID_EPPO_CODE))
        .withSystemName(Collections.singletonList(new TextType()
            .withValue(SYSTEM_NAME_EPPO_CODE)))
        .withClassCode(new CodeType()
            .withValue(commodityComplement.getEppoCode()))
        .withClassName(Collections.singletonList(new TextType()
            .withValue(commodityComplement.getSpeciesName())));
  }
}
