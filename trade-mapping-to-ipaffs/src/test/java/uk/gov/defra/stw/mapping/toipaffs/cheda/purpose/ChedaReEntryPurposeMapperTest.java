package uk.gov.defra.stw.mapping.toipaffs.cheda.purpose;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.ForImportOrAdmissionEnum.HORSES_RE_ENTRY;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.RE_IMPORT;

import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;

class ChedaReEntryPurposeMapperTest {

  private final ChedaReEntryPurposeMapper mapper = new ChedaReEntryPurposeMapper();

  @Test
  void map_ReturnsPurpose() {
    Purpose actual = mapper.map(null);

    assertThat(actual).isEqualTo(Purpose.builder()
        .purposeGroup(RE_IMPORT)
        .forImportOrAdmission(HORSES_RE_ENTRY)
        .build());
  }
}
