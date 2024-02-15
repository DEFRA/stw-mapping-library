package uk.gov.defra.tracesx.enotification.chedpp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.tracesx.common.chedpp.ChedppNotificationMapper;
import uk.gov.defra.tracesx.common.common.ReferenceNumberMapper;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;
import uk.gov.defra.tracesx.testutils.JsonDeserializer;
import uk.gov.defra.tracesx.testutils.ResourceUtils;
import uk.gov.defra.tracesx.testutils.TestUtils;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

@ExtendWith(MockitoExtension.class)
class ChedppNotificationMapperImplTest {

  @Mock
  private ChedppPartOneMapper chedppPartOneMapper;

  @Mock
  private ReferenceNumberMapper referenceNumberMapper;

  private ObjectMapper objectMapper;
  private ChedppNotificationMapper chedppNotificationMapper;
  private SpsCertificate spsCertificate;

  @BeforeEach
  void setup() throws Exception {
    chedppNotificationMapper = new ChedppNotificationMapperImpl(chedppPartOneMapper, referenceNumberMapper);
    objectMapper = TestUtils.initObjectMapper();

    spsCertificate = JsonDeserializer
        .get(SpsCertificate.class, "enotification/chedpp/chedpp_ehc_complete.json", objectMapper);

    PartOne partOne = JsonDeserializer.get(PartOne.class,
        "enotification/chedpp/partone/chedpp_ipaffs_partone_complete.json", objectMapper);

    when(chedppPartOneMapper.map(spsCertificate)).thenReturn(partOne);
  }

  @Test
  void map_ReturnsChedppNotification_WhenCompleteEhcSpsCertificate() throws Exception {
    String expectedNotification = ResourceUtils.readFileToString("classpath:enotification/chedpp/chedpp_ipaffs_complete.json");

    Notification notification = chedppNotificationMapper.map(spsCertificate);
    String actualNotification = objectMapper.writeValueAsString(notification);

    assertThat(actualNotification).isEqualTo(expectedNotification);
  }
}
