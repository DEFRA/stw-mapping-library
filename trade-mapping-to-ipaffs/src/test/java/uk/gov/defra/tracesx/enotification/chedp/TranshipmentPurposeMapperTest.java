package uk.gov.defra.tracesx.enotification.chedp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum;
import uk.gov.defra.tracesx.testutils.TestHelper;
import uk.gov.defra.tracesx.trade.dto.SpsAuthenticationType;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;
import uk.gov.defra.tracesx.trade.dto.SpsConsignment;
import uk.gov.defra.tracesx.trade.dto.SpsCountrySubDivisionType;
import uk.gov.defra.tracesx.trade.dto.SpsCountryType;
import uk.gov.defra.tracesx.trade.dto.SpsExchangedDocument;
import uk.gov.defra.tracesx.trade.dto.SpsPartyType;

class TranshipmentPurposeMapperTest {

  private final TranshipmentPurposeMapper transhipmentPurposeMapper = new TranshipmentPurposeMapper();
  private SpsCertificate spsCertificate;

  @BeforeEach
  void setup() {
    spsCertificate = new SpsCertificate();
  }

  @Test
  void map_ReturnsCorrectPurpose_WhenAllFieldsPresent() {
    // Given
    createSpsCertificate();

    // When
    Purpose purpose = transhipmentPurposeMapper.map(spsCertificate);

    // Then
    Purpose expected =
        Purpose.builder()
            .purposeGroup(PurposeGroupEnum.TRANSHIPMENT_TO)
            .finalBIP("BEBRU")
            .thirdCountryTranshipment("FR")
            .build();

    assertThat(purpose).isEqualTo(expected);
  }

  private void createSpsCertificate() {
    SpsConsignment spsConsignment = new SpsConsignment();
    spsConsignment.setImportSpsCountry(TestHelper.buildSpsCountryType("FR", "France"));

    SpsPartyType spsPartyType = TestHelper.buildSpsPartyType("BEBRU", "Brussel-Zaventem");

    SpsCountrySubDivisionType spsCountrySubDivisionType =
        new SpsCountrySubDivisionType().withActivityAuthorizedSpsParty(List.of(spsPartyType));

    SpsCountryType spsCountryType =
        new SpsCountryType()
            .withSubordinateSpsCountrySubDivision(List.of(spsCountrySubDivisionType));

    spsConsignment.setTransitSpsCountry(List.of(spsCountryType));
    spsCertificate = new SpsCertificate(spsConsignment, new SpsExchangedDocument());

    SpsAuthenticationType spsAuthenticationType =
        TestHelper.buildSpsAuthenticationTypeForPurpose("TRANSHIPMENT");

    spsCertificate
        .getSpsExchangedDocument()
        .setSignatorySpsAuthentication(Collections.singletonList(spsAuthenticationType));
  }
}
