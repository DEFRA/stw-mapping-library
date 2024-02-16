package uk.gov.defra.stw.mapping.toipaffs.enotification.chedp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.SpsAuthenticationType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.dto.SpsCountrySubDivisionType;
import uk.gov.defra.stw.mapping.dto.SpsCountryType;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.SpsPartyType;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestHelper;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum;

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
