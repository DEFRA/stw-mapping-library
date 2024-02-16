package uk.gov.defra.stw.mapping.totrade.chedpp.spsconsignment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum.CED;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.DateTimeType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.dto.SpsCountryType;
import uk.gov.defra.stw.mapping.dto.SpsEventType;
import uk.gov.defra.stw.mapping.dto.SpsLocationType;
import uk.gov.defra.stw.mapping.dto.SpsPartyType;
import uk.gov.defra.stw.mapping.dto.SpsTransportEquipmentType;
import uk.gov.defra.stw.mapping.totrade.common.spsconsignment.AvailabilityDueDateTimeMapper;
import uk.gov.defra.stw.mapping.totrade.common.spsconsignment.CustomsTransitAgentSpsPartyMapper;
import uk.gov.defra.stw.mapping.totrade.common.spsconsignment.ExportSpsCountryMapper;
import uk.gov.defra.stw.mapping.totrade.common.spsconsignment.MainCarriageSpsTransportMovementAfterBcpMapper;
import uk.gov.defra.stw.mapping.totrade.common.spsconsignment.MainCarriageSpsTransportMovementBeforeBcpMapper;
import uk.gov.defra.stw.mapping.totrade.common.spsconsignment.SpsPartyMapper;
import uk.gov.defra.stw.mapping.totrade.common.spsconsignment.UnloadingBaseportSpsLocationMapper;
import uk.gov.defra.stw.mapping.totrade.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

@ExtendWith(MockitoExtension.class)
class ChedppSpsConsignmentMapperTest {

  private static final String BEFORE_BCP_SCHEME_ID_VALUE_KEY = "#{beforeBcpSchemeIdValue}";
  private static final String AFTER_BCP_SCHEME_ID_VALUE_KEY = "#{afterBcpSchemeIdValue}";
  private static final String MODE_CODE_VALUE_KEY = "#{modeCodeValue}";

  private Notification notification;
  private ChedppSpsConsignmentMapper spsConsignmentMapper;
  private ObjectMapper objectMapper;

  private SpsPartyType consignorSpsPartyType;
  private SpsPartyType consigneeSpsPartyType;
  private SpsPartyType deliverySpsPartyType;

  private DateTimeType dateTimeType;

  private List<SpsCountryType> transitSpsCountry;
  private SpsCountryType importSpsCountry;
  private SpsEventType examinationSpsEventType;

  private SpsPartyType customsTransitAgentSpsParty;
  private List<SpsTransportEquipmentType> spsTransportEquipmentTypeList;

  private SpsLocationType spsLocationType;
  private SpsCountryType exportSpsCountryType;
  private List<SpsEventType> podSpsEventTypeList;

  @Mock
  private ChedppConsignorSpsPartyMapper consignorSpsPartyMapper;
  @Mock
  private ChedppConsigneeSpsPartyMapper consigneeSpsPartyMapper;
  @Mock
  private ChedppDeliverySpsPartyMapper deliverySpsPartyMapper;
  @Mock
  private ChedppTransitSpsCountryMapper transitSpsCountryMapper;
  @Mock
  private ChedppImportSpsCountryMapper importSpsCountryMapper;
  @Mock
  private ChedppExaminationSpsEventMapper examinationSpsEventMapper;
  @Mock
  private ChedppPodSpsEventMapper podSpsEventMapper;
  @Mock
  private CustomsTransitAgentSpsPartyMapper customsTransitAgentSpsPartyMapper;
  @Mock
  private AvailabilityDueDateTimeMapper availabilityDueDateTimeMapper;
  @Mock
  private MainCarriageSpsTransportMovementBeforeBcpMapper mainCarriageSpsTransportMovementBeforeBcpMapper;
  @Mock
  private MainCarriageSpsTransportMovementAfterBcpMapper mainCarriageSpsTransportMovementAfterBcpMapper;
  @Mock
  private ChedppUtilizedSpsTransportEquipmentMapper utilizedSpsTransportEquipmentMapper;
  @Mock
  private UnloadingBaseportSpsLocationMapper unloadingBaseportSpsLocationMapper;
  @Mock
  private ExportSpsCountryMapper exportSpsCountryMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);

    ChedppIncludedSpsConsignmentItemMapper includedSpsConsignmentItemMapper =
        new ChedppIncludedSpsConsignmentItemMapper(new SpsPartyMapper());

    spsConsignmentMapper = new ChedppSpsConsignmentMapper(consignorSpsPartyMapper,
        consigneeSpsPartyMapper,
        deliverySpsPartyMapper,
        transitSpsCountryMapper,
        importSpsCountryMapper,
        examinationSpsEventMapper,
        includedSpsConsignmentItemMapper,
        podSpsEventMapper,
        customsTransitAgentSpsPartyMapper,
        availabilityDueDateTimeMapper,
        mainCarriageSpsTransportMovementBeforeBcpMapper,
        mainCarriageSpsTransportMovementAfterBcpMapper,
        utilizedSpsTransportEquipmentMapper,
        exportSpsCountryMapper,
        unloadingBaseportSpsLocationMapper);

    String consignorSpsPartyTypeString = ResourceUtil.readFileToString("classpath:mapping/chedpp/spsconsignment/consignorSpsPartyComplete.json");
    consignorSpsPartyType = objectMapper.readValue(consignorSpsPartyTypeString, SpsPartyType.class);
    String consigneeSpsPartyTypeString = ResourceUtil.readFileToString("classpath:mapping/chedpp/spsconsignment/consigneeSpsPartyComplete.json");
    consigneeSpsPartyType = objectMapper.readValue(consigneeSpsPartyTypeString, SpsPartyType.class);
    String deliverySpsPartyTypeString = ResourceUtil.readFileToString("classpath:mapping/chedpp/spsconsignment/deliverySpsPartyComplete.json");
    deliverySpsPartyType = objectMapper.readValue(deliverySpsPartyTypeString, SpsPartyType.class);
    String transitSpsCountryString = ResourceUtil.readFileToString("classpath:mapping/chedpp/spsconsignment/transitSpsCountryForTransitToThirdCountry.json");
    transitSpsCountry = objectMapper.readValue(transitSpsCountryString, new TypeReference<>() {});
    String importSpsCountryString = ResourceUtil.readFileToString("classpath:mapping/chedpp/spsconsignment/importSpsCountryComplete.json");
    importSpsCountry = objectMapper.readValue(importSpsCountryString, SpsCountryType.class);
    String examinationSpsEventTypeString = ResourceUtil.readFileToString("classpath:mapping/chedpp/spsconsignment/examinationSpsEventComplete.json");
    examinationSpsEventType = objectMapper.readValue(examinationSpsEventTypeString, SpsEventType.class);
    String customsTransitAgentSpsPartyString = ResourceUtil.readFileToString("classpath:mapping/common/spsconsignment/customsTransitSpsPartyComplete.json");
    customsTransitAgentSpsParty = objectMapper.readValue(customsTransitAgentSpsPartyString, SpsPartyType.class);
    String dateTimeTypeString = ResourceUtil.readFileToString("classpath:mapping/common/spsconsignment/availabilityDueDateTimeComplete.json");
    dateTimeType = objectMapper.readValue(dateTimeTypeString, DateTimeType.class);
    String spsTransportEquipment = ResourceUtil.readFileToString(("classpath:mapping/chedpp/spsconsignment/utilizedSpsTransportEquipmentComplete.json"));
    spsTransportEquipmentTypeList = objectMapper.readValue(spsTransportEquipment, new TypeReference<>() {});
    String spsLocationTypeString = ResourceUtil.readFileToString("classpath:mapping/common/spsconsignment/UnloadingBaseportSpsLocationComplete.json");
    spsLocationType = objectMapper.readValue(spsLocationTypeString, SpsLocationType.class);
    String exportSpsCountryTypeString = ResourceUtil.readFileToString("classpath:mapping/common/spsconsignment/exportSpsCountryTypeComplete.json");
    exportSpsCountryType = objectMapper.readValue(exportSpsCountryTypeString, SpsCountryType.class);
    String podSpsEventString = ResourceUtil.readFileToString("classpath:mapping/chedpp/spsconsignment/podSpsEventComplete.json");
    podSpsEventTypeList = objectMapper.readValue(podSpsEventString, new TypeReference<>() {});
  }

  @Test
  void map_ReturnsSpsConsignment_WhenAllFieldsComplete() throws Exception {
    when(consignorSpsPartyMapper.map(notification.getPartOne().getConsignor())).thenReturn(consignorSpsPartyType);
    when(consigneeSpsPartyMapper.map(notification.getPartOne().getConsignee())).thenReturn(consigneeSpsPartyType);
    when(deliverySpsPartyMapper.map(notification.getPartOne().getPlaceOfDestination())).thenReturn(deliverySpsPartyType);
    when(transitSpsCountryMapper.map(notification.getPartOne().getPurpose())).thenReturn(transitSpsCountry);
    when(importSpsCountryMapper.map(notification.getPartOne().getPurpose())).thenReturn(importSpsCountry);
    when(examinationSpsEventMapper.map(notification.getPartOne())).thenReturn(examinationSpsEventType);
    when(customsTransitAgentSpsPartyMapper.map(notification.getPartOne().getPersonResponsible())).thenReturn(customsTransitAgentSpsParty);
    when(availabilityDueDateTimeMapper.map(notification.getPartOne())).thenReturn(dateTimeType);
    mockMeansOfTransportBeforeBcp();
    mockMeansOfTransportAfterBcp();
    when(utilizedSpsTransportEquipmentMapper.map(any())).thenReturn(spsTransportEquipmentTypeList);
    when(unloadingBaseportSpsLocationMapper.map(notification.getPartOne().getPointOfEntry())).thenReturn(spsLocationType);
    when(exportSpsCountryMapper.map(notification.getPartOne().getCommodities())).thenReturn(exportSpsCountryType);
    when(podSpsEventMapper.map(notification.getPartOne())).thenReturn(podSpsEventTypeList);

    SpsConsignment spsConsignment = spsConsignmentMapper.map(notification);

    String actualSpsConsignment = objectMapper.writeValueAsString(spsConsignment);
    String expectedSpsConsignment = ResourceUtil.readFileToString("classpath:mapping/chedpp/spsconsignment/spsConsignmentComplete.json");

    assertThat(actualSpsConsignment).isEqualTo(expectedSpsConsignment);
  }

  @Test
  void map_AddsTradeLineItem_WhenCHEDPPAndCountryAndCommodityNotArticle72() {
    SpsConsignment spsConsignment = spsConsignmentMapper.map(notification);

    List<IncludedSpsTradeLineItem> tradeLineItems = spsConsignment
        .getIncludedSpsConsignmentItem().get(0).getIncludedSpsTradeLineItem();
    assertThat(tradeLineItems).hasSize(4);
    assertThat(tradeLineItems.get(1).getScientificName().get(0).getValue())
        .isEqualTo("Abies chensiensis");
  }

  @Test
  void map_AddsTradeLineItem_WhenNotCHEDPP() {
    notification.setType(CED);

    SpsConsignment spsConsignment = spsConsignmentMapper.map(notification);

    assertThat(spsConsignment
        .getIncludedSpsConsignmentItem().get(0).getIncludedSpsTradeLineItem()).hasSize(4);
  }

  @Test
  void map_AddsTradeLineItem_WhenCHEDPPAndCountryIsArticle72AndCommodityIsNotArticle72() throws Exception {
    notification.getPartOne().getCommodities().setIsLowRiskArticle72Country(true);

    SpsConsignment spsConsignment = spsConsignmentMapper.map(notification);

    assertThat(spsConsignment
        .getIncludedSpsConsignmentItem().get(0).getIncludedSpsTradeLineItem()).hasSize(4);
  }

  @Test
  void map_DoesNotAddTradeLineItem_WhenCHEDPPAndCountryAndCommodityIsArticle72() {
    notification.getPartOne().getCommodities().setIsLowRiskArticle72Country(true);
    notification.getPartOne().getCommodities().getComplementParameterSet().get(0).getKeyDataPair()
        .add(new ComplementParameterSetKeyDataPair(
            "low_risk_article72_commodity", "true"));

    SpsConsignment spsConsignment = spsConsignmentMapper.map(notification);

    List<IncludedSpsTradeLineItem> tradeLineItems = spsConsignment
        .getIncludedSpsConsignmentItem().get(0).getIncludedSpsTradeLineItem();
    assertThat(tradeLineItems).hasSize(3);
    assertThat(tradeLineItems.get(1).getScientificName().get(0).getValue())
        .isNotEqualTo("Abies chensiensis");
  }

  private void mockMeansOfTransportBeforeBcp() throws Exception {
    String spsTransportMovementBeforeBcp = ResourceUtil.readFileToString("classpath:mapping/common/spsconsignment/mainCarriageSpsTransportMovementCompleteBeforeBcp.json");
    spsTransportMovementBeforeBcp = spsTransportMovementBeforeBcp
        .replace(BEFORE_BCP_SCHEME_ID_VALUE_KEY, "train_identifier_before_bcp")
        .replace(MODE_CODE_VALUE_KEY, "2");
    MainCarriageSpsTransportMovement mainCarriageSpsTransportMovementBeforeBcp = objectMapper.readValue(
        spsTransportMovementBeforeBcp, MainCarriageSpsTransportMovement.class);

    when(mainCarriageSpsTransportMovementBeforeBcpMapper.map(notification.getPartOne().getMeansOfTransportFromEntryPoint())).thenReturn(
        mainCarriageSpsTransportMovementBeforeBcp);
  }

  private void mockMeansOfTransportAfterBcp() throws Exception {
    String spsTransportMovementAfterBcp = ResourceUtil.readFileToString("classpath:mapping/common/spsconsignment/mainCarriageSpsTransportMovementCompleteAfterBcp.json");
    spsTransportMovementAfterBcp = spsTransportMovementAfterBcp
        .replace(AFTER_BCP_SCHEME_ID_VALUE_KEY, "train_identifier_after_bcp")
        .replace(MODE_CODE_VALUE_KEY, "2");
    MainCarriageSpsTransportMovement mainCarriageSpsTransportMovementAfterBcp = objectMapper.readValue(
        spsTransportMovementAfterBcp, MainCarriageSpsTransportMovement.class);

    when(mainCarriageSpsTransportMovementAfterBcpMapper.map(notification.getPartOne().getMeansOfTransport())).thenReturn(
        mainCarriageSpsTransportMovementAfterBcp);
  }
}
