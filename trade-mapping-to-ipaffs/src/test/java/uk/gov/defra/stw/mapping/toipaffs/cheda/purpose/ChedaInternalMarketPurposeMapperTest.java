package uk.gov.defra.stw.mapping.toipaffs.cheda.purpose;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.ForImportOrAdmissionEnum.DEFINITIVE_IMPORT;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.IMPORT;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.RE_IMPORT;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsClause;
import uk.gov.defra.stw.mapping.dto.SpsAuthenticationType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose;

class ChedaInternalMarketPurposeMapperTest {

  private final ChedaInternalMarketPurposeMapper mapper = new ChedaInternalMarketPurposeMapper();

  @ParameterizedTest
  @EnumSource(names = {
      "COMMERCIAL_SALE_OR_CHANGE_OF_OWNERSHIP", "RESCUE", "BREEDING", "RESEARCH", "RACING",
      "APPROVED_PREMISES", "COMPANION_ANIMAL", "PRODUCTION", "SLAUGHTER", "FATTENING",
      "GAME_RESTOCKING", "REGISTERED_HORSES"})
  void map_ReturnsPurpose_WhenInternalMarketPurposePresent(
      InternalMarketPurpose internalMarketPurpose) {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withSignatorySpsAuthentication(List.of(new SpsAuthenticationType()
                .withIncludedSpsClause(List.of(new IncludedSpsClause()
                    .withId(new IDType().withValue("INTERNAL_MARKET_PURPOSE"))
                    .withContent(List.of(new TextType().withValue(internalMarketPurpose.name()))))
                ))));

    Purpose actual = mapper.map(spsCertificate);

    assertThat(actual).usingRecursiveComparison().isEqualTo(Purpose.builder()
        .purposeGroup(IMPORT)
        .internalMarketPurpose(internalMarketPurpose)
        .build());
  }

  @Test
  void map_ReturnsPurpose_WhenInternalMarketPurposeNotPresent() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withSignatorySpsAuthentication(List.of(new SpsAuthenticationType()
                .withIncludedSpsClause(List.of()))));

    Purpose actual = mapper.map(spsCertificate);

    assertThat(actual).usingRecursiveComparison().isEqualTo(Purpose.builder()
        .purposeGroup(RE_IMPORT)
        .forImportOrAdmission(DEFINITIVE_IMPORT)
        .build());
  }
}
