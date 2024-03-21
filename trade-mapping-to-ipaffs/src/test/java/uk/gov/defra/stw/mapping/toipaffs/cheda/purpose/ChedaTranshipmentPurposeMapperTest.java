package uk.gov.defra.stw.mapping.toipaffs.cheda.purpose;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.TRANSHIPMENT_TO;

import java.util.List;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.dto.SpsCountrySubDivisionType;
import uk.gov.defra.stw.mapping.dto.SpsCountryType;
import uk.gov.defra.stw.mapping.dto.SpsPartyType;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;

class ChedaTranshipmentPurposeMapperTest {

  private final ChedaTranshipmentPurposeMapper mapper = new ChedaTranshipmentPurposeMapper();

  @Test
  void map_ReturnsPurpose() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withImportSpsCountry(new SpsCountryType()
                .withId(new IDType().withValue("Destination country")))
            .withTransitSpsCountry(List.of(new SpsCountryType()
                .withId(new IDType().withValue("GB"))
                .withSubordinateSpsCountrySubDivision(List.of(new SpsCountrySubDivisionType()
                    .withActivityAuthorizedSpsParty(List.of(new SpsPartyType()
                        .withId(new IDType().withValue("Exit BCP")))))))));

    Purpose actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo(Purpose.builder()
        .purposeGroup(TRANSHIPMENT_TO)
        .thirdCountryTranshipment("Destination country")
        .finalBIP("Exit BCP")
        .build());
  }
}
