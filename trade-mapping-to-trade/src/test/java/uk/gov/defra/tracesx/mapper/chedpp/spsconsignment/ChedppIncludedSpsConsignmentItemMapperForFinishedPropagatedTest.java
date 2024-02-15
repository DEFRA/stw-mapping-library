package uk.gov.defra.tracesx.mapper.chedpp.spsconsignment;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import uk.gov.defra.tracesx.mapper.common.spsconsignment.SpsPartyMapper;
import uk.gov.defra.tracesx.mapper.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.trade.dto.CodeType;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsConsignmentItem;
import uk.gov.defra.tracesx.trade.dto.SpsNoteType;
import uk.gov.defra.tracesx.trade.dto.TextType;

class ChedppIncludedSpsConsignmentItemMapperForFinishedPropagatedTest {

  private Notification notification;
  private ChedppIncludedSpsConsignmentItemMapper chedppIncludedSpsConsignmentItemMapper;

  private static final String FINISHED_OR_PROPAGATED_SUBJECT_CODE = "END_USE_CODE";

  @BeforeEach
  void setup() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);
    chedppIncludedSpsConsignmentItemMapper = new ChedppIncludedSpsConsignmentItemMapper(new SpsPartyMapper());
  }

  @ParameterizedTest
  @ValueSource(strings = {"Finished", "Propagated", "None"})
  void map_ReturnsSPSConsignmentItem_whenFinishedOrPropagatedIsSet(String value) {
    // Given
    ComplementParameterSetKeyDataPair complementParameterSetKeyDataPair =
        ComplementParameterSetKeyDataPair.builder()
            .key("finished_or_propagated")
            .data(value)
            .build();
    notification.getPartOne().getCommodities().getComplementParameterSet()
        .get(1).getKeyDataPair().set(4, complementParameterSetKeyDataPair);

    // When
    IncludedSpsConsignmentItem includedSpsConsignmentItem =
            chedppIncludedSpsConsignmentItemMapper.map(notification);
    Optional<SpsNoteType> spsNoteType =
            getSpsNoteType(includedSpsConsignmentItem, FINISHED_OR_PROPAGATED_SUBJECT_CODE);
    String spsNoteSubjectCode = spsNoteType
            .map(SpsNoteType::getSubjectCode)
            .map(CodeType::getValue)
            .orElse("");
    String spsNoteContentValue = spsNoteType
            .map(nt -> nt.getContent().get(0))
            .map(TextType::getValue)
            .orElse("");

    // Then
    assertThat(spsNoteSubjectCode).isEqualTo(FINISHED_OR_PROPAGATED_SUBJECT_CODE);
    assertThat(spsNoteContentValue).isEqualTo(value);
  }

  private Optional<SpsNoteType> getSpsNoteType(IncludedSpsConsignmentItem includedSpsConsignmentItem, String key) {
    return includedSpsConsignmentItem.getIncludedSpsTradeLineItem()
        .stream()
        .flatMap(includedSpsTradeLineItem -> includedSpsTradeLineItem.getAdditionalInformationSpsNote().stream())
        .filter(spsNoteType -> spsNoteType.getSubjectCode().getValue().equals(key))
        .findFirst();
  }
}
