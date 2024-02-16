package uk.gov.defra.stw.mapping.toipaffs.common.chedpp.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

class FinishedOrPropagatedMapperTest {

  private static final String FINISHED = "Finished";
  private static final String FINISHED_OR_PROPAGATED = "finished_or_propagated";
  private static final String COMMODITY_GROUP = "commodity_group";
  private static final String OTHER = "Other";

  private FinishedOrPropagatedMapper finishedOrPropagatedMapper;

  @BeforeEach
  void setup() {
    finishedOrPropagatedMapper = new FinishedOrPropagatedMapper();
  }

  @Test
  void map_ReturnsNull_WhenThereAreNoAdditionalInformationSpsNotes() {
    // Given
    IncludedSpsTradeLineItem tradeLineItem =
        new IncludedSpsTradeLineItem().withAdditionalInformationSpsNote(Collections.emptyList());

    // When
    ComplementParameterSetKeyDataPair actual = finishedOrPropagatedMapper.map(tradeLineItem);

    // Then
    assertThat(actual).isNull();
  }

  @Test
  void map_ReturnsNull_WhenContentIsNull() {
    // Given
    CodeType codeType = new CodeType().withValue(FINISHED_OR_PROPAGATED);
    SpsNoteType spsNoteType = new SpsNoteType().withSubjectCode(codeType).withContent(null);
    IncludedSpsTradeLineItem tradeLineItem =
        new IncludedSpsTradeLineItem()
            .withAdditionalInformationSpsNote(Collections.singletonList(spsNoteType));

    // When
    ComplementParameterSetKeyDataPair actual = finishedOrPropagatedMapper.map(tradeLineItem);

    // Then
    assertThat(actual).isNull();
  }

  @Test
  void map_ReturnsNull_WhenContentTextTypeDoesNotHaveAValue() {
    // Given
    List<TextType> textTypes = Collections.singletonList(new TextType());
    CodeType codeType = new CodeType().withValue(FINISHED_OR_PROPAGATED);
    SpsNoteType spsNoteType = new SpsNoteType().withSubjectCode(codeType).withContent(textTypes);
    IncludedSpsTradeLineItem tradeLineItem =
        new IncludedSpsTradeLineItem()
            .withAdditionalInformationSpsNote(Collections.singletonList(spsNoteType));

    // When
    ComplementParameterSetKeyDataPair actual = finishedOrPropagatedMapper.map(tradeLineItem);

    // Then
    assertThat(actual).isNull();
  }

  @Test
  void map_ReturnsNull_WhenContentListIsEmpty() {
    // Given
    List<TextType> textTypes = Collections.emptyList();
    CodeType codeType = new CodeType().withValue(FINISHED_OR_PROPAGATED);
    SpsNoteType spsNoteType = new SpsNoteType().withSubjectCode(codeType).withContent(textTypes);
    IncludedSpsTradeLineItem tradeLineItem =
        new IncludedSpsTradeLineItem()
            .withAdditionalInformationSpsNote(Collections.singletonList(spsNoteType));

    // When
    ComplementParameterSetKeyDataPair actual = finishedOrPropagatedMapper.map(tradeLineItem);

    // Then
    assertThat(actual).isNull();
  }

  @Test
  void map_ReturnsCorrectKeyDataPair_WhenFinishedPropagatingSpsNoteTypeIsPresent() {
    // Given
    List<TextType> textTypes = Collections.singletonList(new TextType().withValue(FINISHED));
    CodeType codeType = new CodeType().withValue(FINISHED_OR_PROPAGATED);
    SpsNoteType spsNoteType = new SpsNoteType().withSubjectCode(codeType).withContent(textTypes);
    IncludedSpsTradeLineItem tradeLineItem =
        new IncludedSpsTradeLineItem()
            .withAdditionalInformationSpsNote(Collections.singletonList(spsNoteType));

    // When
    ComplementParameterSetKeyDataPair actual = finishedOrPropagatedMapper.map(tradeLineItem);

    // Then
    ComplementParameterSetKeyDataPair expected =
        ComplementParameterSetKeyDataPair.builder()
            .key(FINISHED_OR_PROPAGATED)
            .data(FINISHED)
            .build();

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void map_ReturnsNull_WhenAdditionalInformationSubjectCodeIsNull() {
    // Given
    List<TextType> textTypes = Collections.singletonList(new TextType().withValue(FINISHED));
    SpsNoteType spsNoteType = new SpsNoteType().withSubjectCode(null).withContent(textTypes);
    IncludedSpsTradeLineItem tradeLineItem =
        new IncludedSpsTradeLineItem()
            .withAdditionalInformationSpsNote(Collections.singletonList(spsNoteType));

    // When
    ComplementParameterSetKeyDataPair actual = finishedOrPropagatedMapper.map(tradeLineItem);

    // Then
    assertThat(actual).isNull();
  }

  @Test
  void map_ReturnsNull_WhenFinishedPropagatingSpsNoteTypeIsNotPresent() {
    // Given
    List<TextType> textTypes = Collections.singletonList(new TextType().withValue(OTHER));
    CodeType codeType = new CodeType().withValue(COMMODITY_GROUP);
    SpsNoteType spsNoteType = new SpsNoteType().withSubjectCode(codeType).withContent(textTypes);
    IncludedSpsTradeLineItem tradeLineItem =
        new IncludedSpsTradeLineItem()
            .withAdditionalInformationSpsNote(Collections.singletonList(spsNoteType));

    // When
    ComplementParameterSetKeyDataPair actual = finishedOrPropagatedMapper.map(tradeLineItem);

    // Then
    assertThat(actual).isNull();
  }
}
