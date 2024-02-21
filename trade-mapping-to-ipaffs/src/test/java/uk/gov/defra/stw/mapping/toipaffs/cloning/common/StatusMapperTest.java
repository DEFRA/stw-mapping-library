package uk.gov.defra.stw.mapping.toipaffs.cloning.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.StatusCode;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum;

class StatusMapperTest {

  private StatusMapper statusMapper;

  @BeforeEach
  void setup() {
    statusMapper = new StatusMapper();
  }

  @Test
  void map_ReturnsDraftStatus_WhenStatusCodeIs47() throws NotificationMapperException {
    assertThat(statusMapper.map(new StatusCode().withValue("47"))).isEqualTo(StatusEnum.DRAFT);
  }

  @Test
  void map_ThrowsNotificationMapperException_WhenStatusCodeIsNot47() {
    assertThrows(NotificationMapperException.class, () -> statusMapper.map(new StatusCode().withValue("50")));
  }
}
