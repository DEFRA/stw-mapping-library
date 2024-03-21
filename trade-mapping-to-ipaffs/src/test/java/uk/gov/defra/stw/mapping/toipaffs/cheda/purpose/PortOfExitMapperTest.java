package uk.gov.defra.stw.mapping.toipaffs.cheda.purpose;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.dto.SpsCountrySubDivisionType;
import uk.gov.defra.stw.mapping.dto.SpsCountryType;
import uk.gov.defra.stw.mapping.dto.SpsPartyType;

class PortOfExitMapperTest {

  private final PortOfExitMapper mapper = new PortOfExitMapper();

  @Test
  void map_ReturnsPortOfExit_WhenPresent() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withTransitSpsCountry(List.of(
                new SpsCountryType()
                    .withId(new IDType().withValue("GB"))
                    .withSubordinateSpsCountrySubDivision(List.of(new SpsCountrySubDivisionType()
                        .withId(new IDType().withValue("PORT_OF_EXIT"))
                        .withActivityAuthorizedSpsParty(List.of(new SpsPartyType()
                            .withId(new IDType().withValue("Port of exit"))))))
            )));

    String actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo("Port of exit");
  }

  @Test
  void map_ReturnsPortOfExit_WhenNotPresent() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withTransitSpsCountry(List.of()));

    String actual = mapper.map(spsCertificate);

    assertThat(actual).isNull();
  }

  @Test
  void map_ReturnsPortOfExit_WhenNoTransitCountryList() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment());

    String actual = mapper.map(spsCertificate);

    assertThat(actual).isNull();
  }
}
