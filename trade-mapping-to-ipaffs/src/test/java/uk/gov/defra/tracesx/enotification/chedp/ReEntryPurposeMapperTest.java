package uk.gov.defra.tracesx.enotification.chedp;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.RE_IMPORT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

class ReEntryPurposeMapperTest {

  private SpsCertificate spsCertificate;

  private final ReEntryPurposeMapper reEntryPurposeMapper = new ReEntryPurposeMapper();

  @BeforeEach
  void setup() {
    spsCertificate = new SpsCertificate();
  }

  @Test
  void map_ReturnsPurpose_WhenComplete() {
    // Given // When
    Purpose expected = Purpose.builder().purposeGroup(RE_IMPORT).build();

    Purpose purpose = reEntryPurposeMapper.map(spsCertificate);

    // Then
    assertThat(purpose).isEqualTo(expected);
  }
}
