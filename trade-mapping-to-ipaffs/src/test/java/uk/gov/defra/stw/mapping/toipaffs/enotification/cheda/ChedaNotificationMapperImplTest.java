package uk.gov.defra.stw.mapping.toipaffs.enotification.cheda;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;

class ChedaNotificationMapperImplTest {

  private ChedaNotificationMapperImpl mapper;

  @BeforeEach
  void setup() {
    mapper = new ChedaNotificationMapperImpl();
  }

  @Test
  void map_ThrowsNotImplementedException() {
    assertThrows(NotImplementedException.class, () -> mapper.map(new SpsCertificate()));
  }
}
