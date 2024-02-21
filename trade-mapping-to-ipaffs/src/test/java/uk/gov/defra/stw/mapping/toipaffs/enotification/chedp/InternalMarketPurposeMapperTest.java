package uk.gov.defra.stw.mapping.toipaffs.enotification.chedp;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.stw.mapping.toipaffs.testutils.TestConstants.GOODS_CERTIFIED_AS;
import static uk.gov.defra.stw.mapping.toipaffs.testutils.TestConstants.HUMAN_CONSUMPTION;
import static uk.gov.defra.stw.mapping.toipaffs.testutils.TestHelper.buildSpsAuthenticationTypeForGoodsCertifiedAs;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsClause;
import uk.gov.defra.stw.mapping.dto.SpsAuthenticationType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum;

class InternalMarketPurposeMapperTest {

  private SpsCertificate spsCertificate;

  private InternalMarketPurposeMapper internalMarketPurposeMapper;

  @BeforeEach
  void setup() {
    internalMarketPurposeMapper = new InternalMarketPurposeMapper();
    spsCertificate = new SpsCertificate().withSpsExchangedDocument(new SpsExchangedDocument());
  }

  @Test
  void map_ReturnsCorrectPurpose_WhenGoodsCertifiedAsPresent() {
    // Given
    spsCertificate.getSpsExchangedDocument()
        .setSignatorySpsAuthentication(List.of(
            buildSpsAuthenticationTypeForGoodsCertifiedAs(HUMAN_CONSUMPTION)));

    Purpose expected = Purpose.builder()
        .purposeGroup(PurposeGroupEnum.IMPORT)
        .internalMarketPurpose(InternalMarketPurpose.HUMAN_CONSUMPTION)
        .build();

    // When
    Purpose purpose = internalMarketPurposeMapper.map(spsCertificate);

    // Then
    assertThat(purpose).isEqualTo(expected);
  }

  @Test
  void map_ReturnsCorrectPurpose_WhenGoodsCertifiedAsIsNotPresent() {
    // Given / When
    spsCertificate.getSpsExchangedDocument()
        .setSignatorySpsAuthentication(List.of(new SpsAuthenticationType()));

    Purpose purpose = internalMarketPurposeMapper.map(spsCertificate);

    // Then
    assertThat(purpose.getInternalMarketPurpose()).isNull();
  }

  @Test
  void map_ReturnsNullInternalMarketPurpose_WhenGoodsCertifiedAsContentNotPresent() {
    // Given / When
    SpsAuthenticationType spsAuthenticationType = new SpsAuthenticationType();
    spsAuthenticationType.withIncludedSpsClause(
        List.of(
            new IncludedSpsClause()
                .withId(new IDType().withValue(GOODS_CERTIFIED_AS))
                .withContent(Collections.emptyList())));
    spsCertificate.getSpsExchangedDocument()
        .setSignatorySpsAuthentication(List.of(spsAuthenticationType));

    Purpose purpose = internalMarketPurposeMapper.map(spsCertificate);

    // Then
    assertThat(purpose.getInternalMarketPurpose()).isNull();
  }
}
