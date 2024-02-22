package uk.gov.defra.stw.mapping.toipaffs.chedpp.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

class ChedppControlledAtmosphereContainerMapperTest {

  private static final String CONTAINER_KEY = "container";

  private ChedppControlledAtmosphereContainerMapper containerMapper;

  @BeforeEach
  void setup() {
    containerMapper = new ChedppControlledAtmosphereContainerMapper();
  }

  @Test
  void map_ReturnsKeyDataPairWithContainer_WhenContainerIsPresentWithTrueValue() {
    // Given
    IncludedSpsTradeLineItem tradeLineItem = buildContainerTradeLineItem("true");

    // When
    ComplementParameterSetKeyDataPair result = containerMapper.map(tradeLineItem);

    // Then
    assertThat(result).isEqualTo(buildContainerComplementParameterSetKeyDataPair("true"));
  }

  @Test
  void map_ReturnsNullKeyDataPair_WhenContainerIsPresentWithFalseValue() {
    // Given
    IncludedSpsTradeLineItem tradeLineItem = buildContainerTradeLineItem("false");

    // When
    ComplementParameterSetKeyDataPair result = containerMapper.map(tradeLineItem);

    // Then
    assertThat(result).isNull();
  }

  @Test
  void map_ReturnsNullKeyDataPair_WhenContainerIsNotPresent() {
    // Given
    IncludedSpsTradeLineItem tradeLineItem = new IncludedSpsTradeLineItem();

    // When
    ComplementParameterSetKeyDataPair result = containerMapper.map(tradeLineItem);

    // Then
    assertThat(result).isNull();
  }

  private ComplementParameterSetKeyDataPair buildContainerComplementParameterSetKeyDataPair(String data) {
    return ComplementParameterSetKeyDataPair.builder().key(CONTAINER_KEY).data(data).build();
  }

  private IncludedSpsTradeLineItem buildContainerTradeLineItem(String value) {
    SpsNoteType note = new SpsNoteType()
        .withContent(List.of(new TextType().withValue(value)))
        .withSubjectCode(new CodeType().withValue(CONTAINER_KEY));

    return new IncludedSpsTradeLineItem().withAdditionalInformationSpsNote(List.of(note));
  }
}
