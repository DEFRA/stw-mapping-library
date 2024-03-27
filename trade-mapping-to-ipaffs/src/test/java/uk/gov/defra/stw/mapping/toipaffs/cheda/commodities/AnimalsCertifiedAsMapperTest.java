package uk.gov.defra.stw.mapping.toipaffs.cheda.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsClause;
import uk.gov.defra.stw.mapping.dto.SpsAuthenticationType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.AnimalCertification;

class AnimalsCertifiedAsMapperTest {

  private final AnimalsCertifiedAsMapper mapper = new AnimalsCertifiedAsMapper();

  @ParameterizedTest
  @CsvSource({
      "APPROVED_BODIES, APPROVED",
      "BREEDING, BREEDING",
      "CIRCUS, CIRCUS",
      "FATTENING, FATTENING",
      "OTHER, OTHER",
      "PETS, PETS",
      "QUARANTINE, QUARANTINE",
      "REGISTERED_EQUIDAE, REGISTERED",
      "RELAYING, RELAYING",
      "SLAUGHTER, SLAUGHTER"
  })
  void map_ReturnsCorrectAnimalCertification(String value, AnimalCertification expected) {
    SpsCertificate spsCertificate = createTestData(value);

    AnimalCertification actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo(expected);
  }

  private SpsCertificate createTestData(String value) {
    return new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withSignatorySpsAuthentication(List.of(new SpsAuthenticationType()
                .withIncludedSpsClause(List.of(new IncludedSpsClause()
                    .withId(new IDType().withValue("ANIMALS_CERTIFIED_AS"))
                    .withContent(List.of(new TextType().withValue(value)))
                ))
            )));
  }
}
