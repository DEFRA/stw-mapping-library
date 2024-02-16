package uk.gov.defra.stw.mapping.toipaffs.cloning.cheda;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose.APPROVED_PREMISES;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose.BREEDING;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose.COMMERCIAL_SALE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose.COMPANION_ANIMAL;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose.FATTENING;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose.PRODUCTION;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose.RACING;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose.REGISTERED_HORSES;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose.RESEARCH;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose.SLAUGHTER;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.IMPORT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose;

class InternalMarketPurposeMapperTest {

  private static final Map<String, InternalMarketPurpose> internalMarketPurposeForMapping = Map.ofEntries(
      Map.entry("APPROVED_BODIES", APPROVED_PREMISES),
      Map.entry("BREEDING", BREEDING),
      Map.entry("FATTENING", FATTENING),
      Map.entry("PETS", COMPANION_ANIMAL),
      Map.entry("REGISTERED_EQUIDAE", REGISTERED_HORSES),
      Map.entry("SLAUGHTER", SLAUGHTER),
      Map.entry("PRODUCTION", PRODUCTION),
      Map.entry("PRODUCTION_OF_PETFOOD", PRODUCTION),
      Map.entry("SALES", COMMERCIAL_SALE),
      Map.entry("RACING", RACING),
      Map.entry("COMPETITION", RACING),
      Map.entry("ORNAMENTAL_USE_RESEARCH", RESEARCH));

  private static final List<String> allOtherInternalMarketPurposes = List.of("BREEDING_AND_PRODUCTION",
      "ARTIFICIAL_REPRODUCTION", "STORAGE", "TRANSHUMANCE", "CIRCUS_EXHIBITION", "RELAYING",
      "PET_FOOD", "ORGANIC_FERTILIZERS", "CAT3_FISH_OIL_MEAL_DETOX", "REG_999", "TRAINING", "RODENT_FOOD",
      "QUARANTINE", "POLLINATION", "LABORATORY", "FURTHER_PROCESS", "UNREGISTERED_EQUIDAE",
      "ORNAMENTAL_BIRD_FOOD", "HUMAN_CONSUMPTION", "ANIMAL_FEEDINGSTUFF", "PHARMACEUTICAL_USE",
      "TECHNICAL_USE", "OTHER");

  private SpsCertificate spsCertificate;

  private InternalMarketPurposeMapper mapper;

  @BeforeEach
  void setup() throws JsonProcessingException {
    ObjectMapper objectMapper = TestUtils.initObjectMapper();
    spsCertificate = JsonDeserializer
        .get(SpsCertificate.class, "cloning/cheda/cheda_trade_all_fields.json", objectMapper);

    mapper = new InternalMarketPurposeMapper();
  }

  @Test
  void map_ReturnsPurposeWithInternalMarketPurposeSet_WhenGoodsCertifiedAsIsInInternalMarketPurposes() {
    for (String value : internalMarketPurposeForMapping.keySet()) {
      setValueForIncludedSpsClause(value);

      Purpose actualPurpose = mapper.map(spsCertificate);

      assertThat(actualPurpose).isEqualTo(createExpectedPurpose(internalMarketPurposeForMapping.get(value)));
    }
  }

  @Test
  void map_ReturnsPurposeWithInternalMarketPurposeNull_WhenGoodsCertifiedAsIsInAllOtherInternalMarketPurposes() {
    for (String value : allOtherInternalMarketPurposes) {
      setValueForIncludedSpsClause(value);

      Purpose actualPurpose = mapper.map(spsCertificate);

      assertThat(actualPurpose).isEqualTo(createExpectedPurpose(null));
    }
  }

  @Test
  void map_ReturnsPurposeWithInternalMarketPurposeNull_WhenContentIsEmpty() {
    setContentEmpty();

    Purpose actualPurpose = mapper.map(spsCertificate);

    assertThat(actualPurpose).isEqualTo(createExpectedPurpose(null));
  }

  private void setValueForIncludedSpsClause(String value) {
    spsCertificate.getSpsExchangedDocument().getSignatorySpsAuthentication().get(0)
        .getIncludedSpsClause().get(1).getContent().get(0).setValue(value);
  }

  private void setContentEmpty() {
    spsCertificate.getSpsExchangedDocument().getSignatorySpsAuthentication().get(0)
        .getIncludedSpsClause().get(1).setContent(Collections.emptyList());
  }

  private Purpose createExpectedPurpose(InternalMarketPurpose internalMarketPurpose) {
    return Purpose.builder()
        .purposeGroup(IMPORT)
        .internalMarketPurpose(internalMarketPurpose)
        .build();
  }
}
