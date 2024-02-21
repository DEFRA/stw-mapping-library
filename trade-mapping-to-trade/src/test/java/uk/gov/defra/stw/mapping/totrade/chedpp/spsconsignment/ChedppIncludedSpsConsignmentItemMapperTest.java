package uk.gov.defra.stw.mapping.totrade.chedpp.spsconsignment;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IncludedSpsConsignmentItem;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.totrade.common.spsconsignment.SpsPartyMapper;
import uk.gov.defra.stw.mapping.totrade.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.notificationschema.representation.InspectionCheck;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.CheckStatus;

class ChedppIncludedSpsConsignmentItemMapperTest {

  private static final int UNITS_QUANTITY = 0;
  private static final int UNITS_TYPE_PACKAGE = 1;

  private ObjectMapper objectMapper;
  private Notification notification;
  private ChedppIncludedSpsConsignmentItemMapper chedppIncludedSpsConsignmentItemMapper;

  private static final String VALIDITY_PERIOD = "VALIDITY_PERIOD";
  private static final String VALIDITY_PERIOD_AT_INSPECTION = "VALIDITY_PERIOD_AT_INSPECTION";

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);
    chedppIncludedSpsConsignmentItemMapper = new ChedppIncludedSpsConsignmentItemMapper(new SpsPartyMapper());
  }

  @Test
  void map_ReturnsSpsConsignmentItem_WhenComplete() throws Exception {
    IncludedSpsConsignmentItem includedSpsConsignmentItem = chedppIncludedSpsConsignmentItemMapper.map(notification);

    String actualSpsConsignmentItem = objectMapper.writeValueAsString(includedSpsConsignmentItem);
    String expectedSpsConsignmentItem = ResourceUtil.readFileToString("classpath:mapping/chedpp/spsconsignment/includedSpsConsignmentItemComplete.json");
    assertThat(actualSpsConsignmentItem).isEqualTo(expectedSpsConsignmentItem);
  }

  @Test
  void map_ReturnsSpsConsignmentItem_WhenMultipleCommodityComplementsWithDifferentVarieties() throws Exception {
    ComplementParameterSet complementParameterSet = notification.getPartOne().getCommodities().getComplementParameterSet().get(0);
    ComplementParameterSet newComplementParameterSet =
        objectMapper.readValue(objectMapper.writeValueAsString(complementParameterSet), ComplementParameterSet.class);

    newComplementParameterSet.getKeyDataPair().get(4).setData("NAVELS");
    notification.getPartOne().getCommodities().getComplementParameterSet().add(newComplementParameterSet);

    IncludedSpsConsignmentItem includedSpsConsignmentItem = chedppIncludedSpsConsignmentItemMapper.map(notification);

    String actualSpsConsignmentItem = objectMapper.writeValueAsString(includedSpsConsignmentItem);
    String expectedSpsConsignmentItem = ResourceUtil.readFileToString("classpath:mapping/chedpp/spsconsignment/includedSpsConsignmentItemMultipleVarieties.json");
    assertThat(actualSpsConsignmentItem).isEqualTo(expectedSpsConsignmentItem);
  }



  @Test
  void map_ReturnsSpsConsignmentItem_WhenAutoClearedComplete() throws Exception {
    InspectionCheck check = getCompliantInspectionCheckWithNoReasons();
    check.setStatus(CheckStatus.AUTO_CLEARED);
    check.setReason(null);
    check.setOtherReason(null);

    IncludedSpsConsignmentItem includedSpsConsignmentItem = chedppIncludedSpsConsignmentItemMapper.map(notification);
    String actualSpsConsignmentItem = objectMapper.writeValueAsString(includedSpsConsignmentItem);
    String expectedSpsConsignmentItem = ResourceUtil.readFileToString("classpath:mapping/chedpp/spsconsignment/includedSpsConsignmentItemAutocleared.json");
    assertThat(actualSpsConsignmentItem).isEqualTo(expectedSpsConsignmentItem);
  }

  @Test
  void map_ReturnsSpsConsignmentItem_WhenToBeInspected() throws Exception {
    InspectionCheck check = getCompliantInspectionCheckWithNoReasons();
    check.setStatus(CheckStatus.TO_BE_INSPECTED);
    check.setReason(null);
    check.setOtherReason(null);

    IncludedSpsConsignmentItem includedSpsConsignmentItem = chedppIncludedSpsConsignmentItemMapper.map(notification);
    String actualSpsConsignmentItem = objectMapper.writeValueAsString(includedSpsConsignmentItem);
    String expectedSpsConsignmentItem = ResourceUtil.readFileToString("classpath:mapping/chedpp/spsconsignment/includedSpsConsignmentItemToBeInspected.json");
    assertThat(actualSpsConsignmentItem).isEqualTo(expectedSpsConsignmentItem);
  }

  @Test
  void map_ReturnsSpsConsignmentItem_WhenEmptyUnitsQuantity() throws Exception {
    notification.getPartOne().getCommodities().getComplementParameterSet()
        .get(1).getKeyDataPair().get(UNITS_QUANTITY).setData(null);

    IncludedSpsConsignmentItem includedSpsConsignmentItem = chedppIncludedSpsConsignmentItemMapper.map(notification);

    String actualSpsConsignmentItem = objectMapper.writeValueAsString(includedSpsConsignmentItem);
    String expectedSpsConsignmentItem = ResourceUtil.readFileToString("classpath:mapping/chedpp/spsconsignment/includedSpsConsignmentItemNoPhysicalSpsPackage.json");
    assertThat(actualSpsConsignmentItem).isEqualTo(expectedSpsConsignmentItem);
  }

  @Test
  void map_ReturnsSpsConsignmentItem_WhenEmptyUnitsType() throws Exception {
    notification.getPartOne().getCommodities().getComplementParameterSet()
        .get(1).getKeyDataPair().get(UNITS_TYPE_PACKAGE).setData(null);

    IncludedSpsConsignmentItem includedSpsConsignmentItem = chedppIncludedSpsConsignmentItemMapper.map(notification);

    String actualSpsConsignmentItem = objectMapper.writeValueAsString(includedSpsConsignmentItem);
    String expectedSpsConsignmentItem = ResourceUtil.readFileToString("classpath:mapping/chedpp/spsconsignment/includedSpsConsignmentItemNoPhysicalSpsPackage.json");
    assertThat(actualSpsConsignmentItem).isEqualTo(expectedSpsConsignmentItem);
  }

  @Test
  void map_ReturnsSPSConsignmentItem_whenValidityPeriodInPartOneOnly() {
    ComplementParameterSetKeyDataPair complementParameterSetKeyDataPair = ComplementParameterSetKeyDataPair.builder().build();
    complementParameterSetKeyDataPair.setKey("validity_period");
    complementParameterSetKeyDataPair.setData("2");
    notification.getPartOne().getCommodities().getComplementParameterSet()
        .get(1).getKeyDataPair().set(4, complementParameterSetKeyDataPair);

    IncludedSpsConsignmentItem includedSpsConsignmentItem = chedppIncludedSpsConsignmentItemMapper.map(notification);
    SpsNoteType spsNoteType = getSpsNoteType(includedSpsConsignmentItem, VALIDITY_PERIOD);
    assertThat(spsNoteType).isNotNull();
    assertThat(spsNoteType.getSubjectCode().getValue()).isEqualTo(VALIDITY_PERIOD);
    assertThat(spsNoteType.getContent().get(0).getValue()).isEqualTo("2");
  }

  @Test
  void map_ReturnsSPSConsignmentItem_whenValidityPeriodInPartTwo() {
    ComplementParameterSetKeyDataPair complementParameterSetKeyDataPair = ComplementParameterSetKeyDataPair.builder().build();
    complementParameterSetKeyDataPair.setKey("validity_period");
    complementParameterSetKeyDataPair.setData("2");
    notification.getPartOne().getCommodities().getComplementParameterSet()
        .get(1).getKeyDataPair().set(4, complementParameterSetKeyDataPair);

    notification.getPartTwo().getCommodityChecks().get(0).setValidityPeriod(5);
    notification.getPartTwo().getCommodityChecks().get(2).setValidityPeriod(2);

    IncludedSpsConsignmentItem includedSpsConsignmentItem = chedppIncludedSpsConsignmentItemMapper.map(notification);

    SpsNoteType spsNoteType = getSpsNoteType(includedSpsConsignmentItem, VALIDITY_PERIOD_AT_INSPECTION);
    assertThat(spsNoteType).isNotNull();
    assertThat(spsNoteType.getSubjectCode().getValue()).isEqualTo(VALIDITY_PERIOD_AT_INSPECTION);
    assertThat(spsNoteType.getContent().get(0).getValue()).isEqualTo("2");
  }

  @Test
  void map_ReturnsSpsConsignmentItemWithPhysicalSpsPackage_WhenCHEDPPWithLegacyBulkPackageType() throws Exception {
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPPWithBulkPackageType.json");
    Notification notification = objectMapper.readValue(notificationString, Notification.class);
    IncludedSpsConsignmentItem includedSpsConsignmentItem = chedppIncludedSpsConsignmentItemMapper.map(notification);

    String actualSpsConsignmentItem = objectMapper.writeValueAsString(includedSpsConsignmentItem);
    String expectedSpsConsignmentItem = ResourceUtil.readFileToString("classpath:mapping/chedpp/spsconsignment/includedSpsConsignmentItemWithBulkPackageTypeComplete.json");
    assertThat(actualSpsConsignmentItem).isEqualTo(expectedSpsConsignmentItem);
  }

  private SpsNoteType getSpsNoteType(IncludedSpsConsignmentItem includedSpsConsignmentItem, String key) {
    return includedSpsConsignmentItem.getIncludedSpsTradeLineItem()
        .stream()
        .flatMap(includedSpsTradeLineItem -> includedSpsTradeLineItem.getAdditionalInformationSpsNote().stream())
        .filter(spsNoteType -> spsNoteType.getSubjectCode().getValue().equals(key))
        .findFirst()
        .orElse(null);
  }

  private InspectionCheck getCompliantInspectionCheckWithNoReasons() {
    return notification.getPartTwo().getCommodityChecks().stream()
        .flatMap(commodityCheck -> commodityCheck.getChecks().stream())
        .filter(inspectionCheck -> inspectionCheck.getStatus().equals(CheckStatus.COMPLIANT))
        .filter(inspectionCheck -> inspectionCheck.getReason() == null)
        .filter(inspectionCheck -> inspectionCheck.getOtherReason() == null)
        .findFirst().orElse(null);
  }
}
