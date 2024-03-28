package uk.gov.defra.stw.mapping.toipaffs.cheda.purpose;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.TRANSIT_TO_3RD_COUNTRY;

import java.util.List;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.dto.SpsCountrySubDivisionType;
import uk.gov.defra.stw.mapping.dto.SpsCountryType;
import uk.gov.defra.stw.mapping.dto.SpsPartyType;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;

class ChedaTransitPurposeMapperTest {

  private final ChedaTransitPurposeMapper mapper = new ChedaTransitPurposeMapper();

  @Test
  void map_ReturnsPurpose_WhenPointOfEntryPresent() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withTransitSpsCountry(List.of(
                new SpsCountryType()
                    .withId(new IDType().withValue("GB"))
                    .withSubordinateSpsCountrySubDivision(List.of(new SpsCountrySubDivisionType()
                        .withId(new IDType().withValue("PORT_OF_EXIT"))
                        .withActivityAuthorizedSpsParty(List.of(new SpsPartyType()
                            .withId(new IDType().withValue("Port of exit")))))),
                new SpsCountryType().withId(new IDType().withValue("Transited country 1")),
                new SpsCountryType().withId(new IDType().withValue("Transited country 2"))
            ))
            .withImportSpsCountry(new SpsCountryType()
                .withId(new IDType().withValue("Destination country"))));

    Purpose actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo(Purpose.builder()
        .purposeGroup(TRANSIT_TO_3RD_COUNTRY)
        .thirdCountry("Destination country")
        .transitThirdCountries(List.of("Transited country 1", "Transited country 2"))
        .exitBIP("GBAPHA1A")
        .build());
  }

  @Test
  void map_ReturnsPurpose_WhenPointOfEntryNotPresent() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withTransitSpsCountry(List.of(
                new SpsCountryType()
                    .withId(new IDType().withValue("GB"))
                    .withSubordinateSpsCountrySubDivision(List.of(new SpsCountrySubDivisionType()
                        .withActivityAuthorizedSpsParty(List.of(new SpsPartyType()
                            .withId(new IDType().withValue("Exit BCP")))))),
                new SpsCountryType().withId(new IDType().withValue("Transited country 1")),
                new SpsCountryType().withId(new IDType().withValue("Transited country 2"))
            ))
            .withImportSpsCountry(new SpsCountryType()
                .withId(new IDType().withValue("Destination country"))));

    Purpose actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo(Purpose.builder()
        .purposeGroup(TRANSIT_TO_3RD_COUNTRY)
        .thirdCountry("Destination country")
        .transitThirdCountries(List.of("Transited country 1", "Transited country 2"))
        .exitBIP("Exit BCP")
        .build());
  }
}
