package uk.gov.defra.tracesx.cloning.summary;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.testutils.ResourceUtils;
import uk.gov.defra.tracesx.testutils.TestUtils;

class CommoditiesSummaryMapperTest {

  private CommoditiesSummaryMapper mapper;
  private Notification notification;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() throws JsonProcessingException {
    mapper = new CommoditiesSummaryMapper();

    objectMapper = TestUtils.initObjectMapper();

    String notificationString = ResourceUtils.readFileToString("classpath:cloning/summary/chedp_ipaffs_summary_complete.json");
    notification = objectMapper.readValue(notificationString, Notification.class);
  }

  @Test
  void map_ReturnsCommodities_WhenComplete() throws JsonProcessingException {
    CommoditiesSummary actualCommoditiesSummary =
        mapper.map(notification.getPartOne().getCommodities());

    String actualSummaryCommoditiesString = objectMapper.writeValueAsString(actualCommoditiesSummary);

    String expectedSummaryCommoditiesString = ResourceUtils.readFileToString("classpath:cloning/summary/chedp_commodities_summary_complete.json");

    assertThat(actualSummaryCommoditiesString).isEqualTo(expectedSummaryCommoditiesString);
  }

  @Test
  void map_ReturnsCommoditiesWithoutTemperature_WhenNullTemperature() {
    notification.getPartOne().getCommodities().setTemperature(null);

    CommoditiesSummary commoditiesSummary =
        mapper.map(notification.getPartOne().getCommodities());

    assertThat(commoditiesSummary.getTemperature()).isNull();
  }

  @Test
  void map_NumberOfAnimals_WhenCommoditiesContainsNumberAnimalsKeyDataPair() {
    notification.getPartOne().getCommodities().getComplementParameterSet().get(0).getKeyDataPair()
        .add(new ComplementParameterSetKeyDataPair("number_animal", "2"));

    CommoditiesSummary commoditiesSummary =
        mapper.map(notification.getPartOne().getCommodities());

    assertThat(commoditiesSummary.getCommodityDescriptions().get(0).getNumberOfAnimals()).isEqualTo("2");
    assertThat(commoditiesSummary.getCommodityDescriptions().get(1).getNumberOfAnimals()).isNull();
  }

  @Test
  void map_ReturnsCommoditiesWithEmptyValues_WhenMissingKeyDataPairNetWeightValue() {
    notification.getPartOne().getCommodities().getComplementParameterSet()
        .forEach(set -> removeKeyDataPair(set.getKeyDataPair()));

    CommoditiesSummary actualCommoditiesSummary =
        mapper.map(notification.getPartOne().getCommodities());

    assertThat(actualCommoditiesSummary.getCommodityDescriptions().get(0).getNetWeight()).isNull();
    assertThat(actualCommoditiesSummary.getCommodityDescriptions().get(1).getNetWeight()).isNull();
  }

  private void removeKeyDataPair(List<ComplementParameterSetKeyDataPair> keyDataPairs) {
    keyDataPairs.removeIf(pair -> pair.getKey().equals("netweight"));
  }
}
