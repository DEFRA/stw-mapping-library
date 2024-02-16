package uk.gov.defra.stw.mapping.totrade.chedpp.spsconsignment;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.stw.mapping.validation.utils.CommodityComplementFinder.getKeyDataPair;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.defra.stw.mapping.dto.IncludedSpsConsignmentItem;
import uk.gov.defra.stw.mapping.totrade.common.spsconsignment.SpsPartyMapper;
import uk.gov.defra.stw.mapping.totrade.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

class ChedppIncludedSpsConsignmentItemMapperForQuantityUnitTest {

  private static final String QUANTITY_UNIT_CODE_PLACEHOLDER = "#{quantityUnitCode}";

  private ChedppIncludedSpsConsignmentItemMapper mapper;
  private Notification notification;
  private ObjectMapper objectMapper;

  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {"Stems", "STM"},
        {"Bulbs", "BLB"},
        {"Corms and rhizomes", "CRZ"},
        {"Plants in tissue culture", "PTC"},
        {"Seeds", "SDS"},
        {"Kilograms", "KGM"}
    });
  }

  @BeforeEach
  void setup() throws JsonProcessingException {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);
    mapper = new ChedppIncludedSpsConsignmentItemMapper(new SpsPartyMapper());
  }

  @ParameterizedTest
  @MethodSource("data")
  void map_returnsSpsConsignmentItem_forDifferentQuantityTypes(String quantityUnit,
      String quantityUnitCode)
      throws JsonProcessingException {
    // Given
    List<ComplementParameterSetKeyDataPair> keyDataPairs = notification.getPartOne()
        .getCommodities().getComplementParameterSet().get(0).getKeyDataPair();
    getKeyDataPair(ComplementParameterSet.TYPE_QUANTITY, keyDataPairs).setData(quantityUnit);

    // When
    IncludedSpsConsignmentItem result = mapper.map(notification);

    // Then
    String actual = objectMapper.writeValueAsString(result);
    String expected = ResourceUtil.readFileToString("classpath:mapping/chedpp/spsconsignment/"
        + "includedSpsConsignmentItemQuantityUnitComplete.json");
    expected = expected.replace(QUANTITY_UNIT_CODE_PLACEHOLDER, quantityUnitCode);
    assertThat(actual).isEqualTo(expected);
  }
}
