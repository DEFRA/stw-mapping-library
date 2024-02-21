package uk.gov.defra.stw.mapping.toipaffs.cloning.summary;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.toipaffs.testutils.ResourceUtils;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.NotificationSealsContainers;

class SealAndContainerSummaryMapperTest {

  private SealAndContainerSummaryMapper mapper;
  private Notification notification;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() throws JsonProcessingException {
    mapper = new SealAndContainerSummaryMapper();

    objectMapper = TestUtils.initObjectMapper();

    String notificationString = ResourceUtils.readFileToString("classpath:cloning/summary/chedp_ipaffs_summary_complete.json");
    notification = objectMapper.readValue(notificationString, Notification.class);
  }

  @Test
  void map_ReturnsSealsAndContainers_WhenComplete() throws JsonProcessingException {
    List<SealAndContainerSummary> actualSummarySeals =
        mapper.map(notification.getPartOne().getSealsContainers());

    String actualSummarySealsString = objectMapper.writeValueAsString(actualSummarySeals);

    String expectedSummarySealsString = ResourceUtils.readFileToString("classpath:cloning/summary/chedp_seals_summary_complete.json");

    assertThat(actualSummarySealsString).isEqualTo(expectedSummarySealsString);
  }

  @Test
  void map_ReturnsEmptySealsAndContainers_WhenNullSealsContainers() {
    List<NotificationSealsContainers> sealsContainers = null;
    List<SealAndContainerSummary> actualSummarySeals = mapper.map(sealsContainers);

    assertThat(actualSummarySeals.isEmpty()).isTrue();
  }

  @Test
  void map_ReturnsEmptySealsAndContainers_WhenEmptySealsContainers() {
    List<NotificationSealsContainers> sealsContainers = new ArrayList<>();
    List<SealAndContainerSummary> actualSummarySeals = mapper.map(sealsContainers);

    assertThat(actualSummarySeals.isEmpty()).isTrue();
  }
}
