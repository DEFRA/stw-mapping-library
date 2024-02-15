package uk.gov.defra.tracesx.common.common.commodities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.testutils.JsonDeserializer;
import uk.gov.defra.tracesx.testutils.TestUtils;
import uk.gov.defra.tracesx.trade.dto.CodeType;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;
import uk.gov.defra.tracesx.trade.dto.SpsNoteType;
import uk.gov.defra.tracesx.trade.dto.TextType;

class TotalGrossWeightMapperTest {

  private TotalGrossWeightMapper totalGrossWeightMapper;
  private SpsCertificate spsCertificate;

  private final ObjectMapper objectMapper = TestUtils.initObjectMapper();

  @BeforeEach
  void setup() throws JsonProcessingException {
    totalGrossWeightMapper = new TotalGrossWeightMapper();

    spsCertificate = JsonDeserializer
        .get(SpsCertificate.class, "cloning/chedpp/chedpp_ehc_complete.json", objectMapper);
  }

  @Test
  void map_ReturnsGrossTotalWeight_WhenNoteExists() {
    // Given
    spsCertificate.getSpsExchangedDocument().getIncludedSpsNote().add(
        createTotalGrossWeightSpsNoteType("420.69"));

    // When
    BigDecimal actual = totalGrossWeightMapper.map(spsCertificate);

    // Then
    assertThat(actual).isEqualTo(new BigDecimal("420.69"));
  }

  @Test
  void map_ReturnsNull_WhenNoteDoesNotExist() {
    // Given / When
    BigDecimal actual = totalGrossWeightMapper.map(spsCertificate);

    // Then
    assertThat(actual).isNull();
  }

  @Test
  void map_ReturnsNull_WhenNoteHasNullValue() {
    // Given
    spsCertificate.getSpsExchangedDocument().getIncludedSpsNote().add(
        createTotalGrossWeightSpsNoteType(null));

    // When
    BigDecimal actual = totalGrossWeightMapper.map(spsCertificate);

    // Then
    assertThat(actual).isNull();
  }

  @Test
  void map_ReturnsNull_WhenGivenAnEmptyNote() {
    // Given
    spsCertificate.getSpsExchangedDocument().getIncludedSpsNote().add(new SpsNoteType());

    // When
    BigDecimal actual = totalGrossWeightMapper.map(spsCertificate);

    // Then
    assertThat(actual).isNull();
  }

  @Test
  void map_ReturnsNull_WhenNoteHasNullSubjectCode() {
    // Given
    spsCertificate.getSpsExchangedDocument().getIncludedSpsNote().add(new SpsNoteType()
        .withContent(List.of(new TextType().withValue("value")))
        .withSubjectCode(null));

    // When
    BigDecimal actual = totalGrossWeightMapper.map(spsCertificate);

    // Then
    assertThat(actual).isNull();
  }

  @Test
  void map_ReturnsNull_WhenNoteHasNullContent() {
    // Given
    spsCertificate.getSpsExchangedDocument().getIncludedSpsNote().add(new SpsNoteType()
        .withContent(null));

    // When
    BigDecimal actual = totalGrossWeightMapper.map(spsCertificate);

    // Then
    assertThat(actual).isNull();
  }

  @Test
  void map_ReturnsNumberFormatException_WhenGivenInvalidInput() {
    // Given
    spsCertificate.getSpsExchangedDocument().getIncludedSpsNote().add(
        createTotalGrossWeightSpsNoteType("this is not a number"));

    // When / Then
    assertThatThrownBy(() -> totalGrossWeightMapper.map(spsCertificate))
        .isInstanceOf(NumberFormatException.class);
  }

  private SpsNoteType createTotalGrossWeightSpsNoteType(String value) {
    return new SpsNoteType()
        .withContent(List.of(new TextType().withValue(value)))
        .withSubjectCode(new CodeType().withValue("total_gross_weight"));
  }
}
