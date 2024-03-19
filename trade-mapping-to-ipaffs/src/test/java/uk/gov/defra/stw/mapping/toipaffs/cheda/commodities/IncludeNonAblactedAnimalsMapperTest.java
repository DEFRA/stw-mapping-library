package uk.gov.defra.stw.mapping.toipaffs.cheda.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsClause;
import uk.gov.defra.stw.mapping.dto.SpsAuthenticationType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.TextType;

class IncludeNonAblactedAnimalsMapperTest {

  private final IncludeNonAblactedAnimalsMapper mapper = new IncludeNonAblactedAnimalsMapper();

  @Test
  void map_ReturnsTrue_WhenClauseIsTrue() {
    SpsCertificate spsCertificate = createTestData("TRUE");

    Boolean actual = mapper.map(spsCertificate);

    assertThat(actual).isTrue();
  }

  @Test
  void map_ReturnsFalse_WhenClauseIsFalse() {
    SpsCertificate spsCertificate = createTestData("FALSE");

    Boolean actual = mapper.map(spsCertificate);

    assertThat(actual).isFalse();
  }

  @Test
  void map_ReturnsNull_WhenClauseIsMissing() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withSignatorySpsAuthentication(List.of(new SpsAuthenticationType()
                .withIncludedSpsClause(List.of())
            )));

    Boolean actual = mapper.map(spsCertificate);

    assertThat(actual).isNull();
  }

  private SpsCertificate createTestData(String value) {
    return new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withSignatorySpsAuthentication(List.of(new SpsAuthenticationType()
                .withIncludedSpsClause(List.of(new IncludedSpsClause()
                    .withId(new IDType().withValue("INCLUDES_NON_ABLACTED_ANIMALS"))
                    .withContent(List.of(new TextType().withValue(value)))
                ))
            )));
  }
}
