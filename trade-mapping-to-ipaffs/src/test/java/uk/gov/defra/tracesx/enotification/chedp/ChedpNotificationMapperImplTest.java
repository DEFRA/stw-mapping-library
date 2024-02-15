package uk.gov.defra.tracesx.enotification.chedp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.tracesx.common.chedp.ChedpPartOneMapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;
import uk.gov.defra.tracesx.testutils.JsonDeserializer;
import uk.gov.defra.tracesx.testutils.ResourceUtils;
import uk.gov.defra.tracesx.testutils.TestUtils;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

@ExtendWith(MockitoExtension.class)
class ChedpNotificationMapperImplTest {

  @Mock
  private ChedpPartOneMapper chedpPartOneMapper;

  private ObjectMapper objectMapper;
  private ChedpNotificationMapperImpl chedpNotificationMapper;
  private SpsCertificate spsCertificate;

  @BeforeEach
  void setup() throws Exception {
    chedpNotificationMapper = new ChedpNotificationMapperImpl(chedpPartOneMapper);
    objectMapper = TestUtils.initObjectMapper();

    spsCertificate = JsonDeserializer
        .get(SpsCertificate.class, "enotification/chedp/chedp_ehc_complete.json", objectMapper);

    PartOne partOne = JsonDeserializer.get(PartOne.class,
        "enotification/chedp/partone/chedp_ipaffs_partone_complete.json", objectMapper);

    when(chedpPartOneMapper.map(spsCertificate)).thenReturn(partOne);
  }

  @Test
  void map_ReturnsChedpNotification_WhenCompleteEhcSpsCertificate() throws Exception {
    String expectedNotification = ResourceUtils.readFileToString("classpath:enotification/chedp/chedp_ipaffs_complete.json");

    Notification notification = chedpNotificationMapper.map(spsCertificate);
    String actualNotification = objectMapper.writeValueAsString(notification);

    assertThat(actualNotification).isEqualTo(expectedNotification);
  }

  @Test
  void map_ReturnsNotificationException_WhenInvalidStatus() {
    spsCertificate.getSpsExchangedDocument().getStatusCode().setValue("999");
    assertThrows(
        NotificationMapperException.class, () -> chedpNotificationMapper.map(spsCertificate));
  }
}
