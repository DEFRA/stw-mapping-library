package uk.gov.defra.stw.mapping.toipaffs.cheda;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.dto.SpsLocationType;

class ChedaPointOfEntryMapperTest {

  private final ChedaPointOfEntryMapper mapper = new ChedaPointOfEntryMapper();

  @Test
  void map_ReturnsPointOfEntry_WhenRestOfWorldImport() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withUnloadingBaseportSpsLocation(new SpsLocationType()
                .withId(new IDType()
                    .withSchemeID("un_locode")
                    .withValue("Point of entry"))));

    String actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo("Point of entry");
  }

  @Test
  void map_ReturnsPointOfEntry_WhenEuImport() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withUnloadingBaseportSpsLocation(new SpsLocationType()
                .withId(new IDType()
                    .withSchemeID("IPAFFS_POE")
                    .withValue("Port of entry"))));

    String actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo("GBAPHA1A");
  }
}
