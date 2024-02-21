package uk.gov.defra.stw.mapping.toipaffs.cloning.summary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.ResourceUtils;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.ExternalSystem;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;

@ExtendWith(MockitoExtension.class)
class NotificationSummaryMapperTest {

  @Mock
  private CommoditiesSummaryMapper commoditiesSummaryMapper;
  @Mock
  private SealAndContainerSummaryMapper sealAndContainerSummaryMapper;

  private NotificationSummaryMapper mapper;
  private Notification notification;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() throws JsonProcessingException {
    mapper = new NotificationSummaryMapper(commoditiesSummaryMapper, sealAndContainerSummaryMapper);

    objectMapper = TestUtils.initObjectMapper();

    String notificationString = ResourceUtils.readFileToString("classpath:cloning/summary/chedp_ipaffs_summary_complete.json");
    notification = objectMapper.readValue(notificationString, Notification.class);

    CommoditiesSummary commoditiesSummary = JsonDeserializer.get(CommoditiesSummary.class,
        "cloning/summary/chedp_commodities_summary_complete.json", objectMapper);
    List<SealAndContainerSummary> sealAndContainerSummaryList = JsonDeserializer.get(
        objectMapper.getTypeFactory().constructCollectionType(List.class, SealAndContainerSummary.class),
        "cloning/summary/chedp_seals_summary_complete.json",
        objectMapper);
    when(commoditiesSummaryMapper.map(notification.getPartOne().getCommodities())).thenReturn(commoditiesSummary);
    when(sealAndContainerSummaryMapper.map(notification.getPartOne().getSealsContainers())).thenReturn(sealAndContainerSummaryList);
  }

  @Test
  void map_ReturnsConsignment_WhenComplete() throws JsonProcessingException {
    NotificationSummary actualSummaryNotification = mapper.map(notification);
    String actualSummaryNotificationString = objectMapper.writeValueAsString(actualSummaryNotification);

    String expectedSummaryNotificationString = ResourceUtils.readFileToString("classpath:cloning/summary/chedp_notification_summary_complete.json");

    assertThat(actualSummaryNotificationString).isEqualTo(expectedSummaryNotificationString);
  }

  @Test
  void map_ReturnPurposeGroup_WhenInternalMarketIsNull() {
    notification.getPartOne().getPurpose().setInternalMarketPurpose(null);
    NotificationSummary notificationSummary = mapper.map(notification);

    assertThat(notificationSummary.getPurpose()).isEqualTo("For Transhipment to");
  }

  @Test
  void map_ReturnEphyto_WhenNotificationTypeIsChedpp() {
    notification.setType(NotificationTypeEnum.CHEDPP);
    notification.getExternalReferences().get(0).setSystem(ExternalSystem.EPHYTO);
    NotificationSummary notificationSummary = mapper.map(notification);

    assertThat(notificationSummary.getReferenceNumber()).isEqualTo("NZL20/AGL4/1004");
  }

  @Test
  void map_ReturnsWithCommoditiesConsolidatedTrue_WhenAllCommoditiesHaveTheSameCommodityAndSpecies() {
    updateCommodity(notification, 0, "3501", "Bos taurus");
    updateCommodity(notification, 1, "3501", "Bos taurus");

    NotificationSummary actualSummaryNotification = mapper.map(notification);

    assertThat(actualSummaryNotification.isConsolidatedCommodities()).isTrue();
  }

  @Test
  void map_ReturnsWithCommoditiesConsolidatedFalse_WhenOneCommodityHasADifferentCommodity() {
    updateCommodity(notification, 0, "3501", "Bos taurus");
    updateCommodity(notification, 1, "3502", "Bos taurus");

    NotificationSummary actualSummaryNotification = mapper.map(notification);

    assertThat(actualSummaryNotification.isConsolidatedCommodities()).isFalse();
  }

  @Test
  void map_ReturnsWithCommoditiesConsolidatedFalse_WhenOneCommodityHasADifferentSpeciesName() {
    updateCommodity(notification, 0, "3501", "Bos taurus");
    updateCommodity(notification, 1, "3501", "Ovis aries");

    NotificationSummary actualSummaryNotification = mapper.map(notification);

    assertThat(actualSummaryNotification.isConsolidatedCommodities()).isFalse();
  }

  private void updateCommodity(Notification notification, int index, String commodityId, String species) {
    Commodities commodities = notification.getPartOne().getCommodities();
    List<CommodityComplement> commodityComplements = commodities.getCommodityComplement();

    commodityComplements.get(index).setCommodityID(commodityId);
    commodityComplements.get(index).setSpeciesName(species);
  }
}
