package uk.gov.defra.stw.mapping.toipaffs.chedp;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.stw.mapping.toipaffs.testutils.TestConstants.DIRECT_TRANSIT;

import java.util.Collections;
import java.util.List;
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

class TransitPurposeMapperTest {

  private final TransitPurposeMapper transitPurposeMapper = new TransitPurposeMapper();

  @Test
  void map_ReturnsCorrectPurpose_WhenAllFieldsPresent() {
    // Given
    SpsCertificate spsCertificate = createSpsCertificate();

    // When
    Purpose purpose = transitPurposeMapper.map(spsCertificate);

    // Then
    Purpose expected = Purpose.builder()
        .purposeGroup(PurposeGroupEnum.TRANSIT_TO_3RD_COUNTRY)
        .exitBIP("BEBRU")
        .transitThirdCountries(List.of("BE", "AF"))
        .thirdCountry("FR")
        .build();

    assertThat(purpose).isEqualTo(expected);
  }

  private SpsCertificate createSpsCertificate() {
    SpsConsignment spsConsignment = new SpsConsignment();
    spsConsignment.setImportSpsCountry(TestHelper.buildSpsCountryType("FR", "France"));

    SpsCountryType spsCountryType =
        TestHelper.buildSpsCountryType(
            "GB", "United Kingdom of Great Britain and Northern Ireland");
    SpsCountryType spsCountryType2 = TestHelper.buildSpsCountryType("BE", "Belgium");
    SpsCountryType spsCountryType3 = TestHelper.buildSpsCountryType("AF", "Afghanistan");

    SpsPartyType spsPartyType = TestHelper.buildSpsPartyType("BEBRU", "Brussel-Zaventem");

    List<SpsPartyType> spsPartyTypes = List.of(spsPartyType);

    List<SpsCountrySubDivisionType> spsCountrySubDivisionTypes =
        List.of(new SpsCountrySubDivisionType().withActivityAuthorizedSpsParty(spsPartyTypes));

    spsConsignment.withTransitSpsCountry(
        (List.of(spsCountryType, spsCountryType2, spsCountryType3)));
    spsConsignment
        .getTransitSpsCountry()
        .get(0)
        .setSubordinateSpsCountrySubDivision(spsCountrySubDivisionTypes);
    SpsCertificate spsCertificate = new SpsCertificate(spsConsignment, new SpsExchangedDocument());

    SpsAuthenticationType spsAuthenticationType =
        TestHelper.buildSpsAuthenticationTypeForPurpose(DIRECT_TRANSIT);

    spsCertificate
        .getSpsExchangedDocument()
        .setSignatorySpsAuthentication(Collections.singletonList(spsAuthenticationType));

    return spsCertificate;
  }
}
