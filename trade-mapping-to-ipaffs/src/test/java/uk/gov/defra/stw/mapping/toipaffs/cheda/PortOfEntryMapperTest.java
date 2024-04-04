package uk.gov.defra.stw.mapping.toipaffs.cheda;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.dto.SpsLocationType;

class PortOfEntryMapperTest {

  private final PortOfEntryMapper mapper = new PortOfEntryMapper();

  @Test
  void map_ReturnsPortOfEntry_WhenEuImport() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withUnloadingBaseportSpsLocation(new SpsLocationType()
                .withId(new IDType()
                    .withSchemeID("IPAFFS_POE")
                    .withValue("Port of entry"))));

    String actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo("Port of entry");
  }

  @Test
  void map_ReturnsNull_WhenRestOfWorldImport() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withUnloadingBaseportSpsLocation(new SpsLocationType()
                .withId(new IDType()
                    .withSchemeID("un_locode")
                    .withValue("Point of entry"))));

    String actual = mapper.map(spsCertificate);

    assertThat(actual).isNull();
  }
}
