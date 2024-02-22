package uk.gov.defra.stw.mapping.toipaffs.chedpp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.common.ReferenceNumberMapper;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.ResourceUtils;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;

@ExtendWith(MockitoExtension.class)
class ChedppNotificationMapperTest {

  @Mock
  private ChedppPartOneMapper chedppPartOneMapper;

  @Mock
  private ReferenceNumberMapper referenceNumberMapper;

  private ObjectMapper objectMapper;
  private ChedppNotificationMapper chedppNotificationMapper;
  private SpsCertificate spsCertificate;

  @BeforeEach
  void setup() throws Exception {
    chedppNotificationMapper = new ChedppNotificationMapper(chedppPartOneMapper, referenceNumberMapper);
    objectMapper = TestUtils.initObjectMapper();

    spsCertificate = JsonDeserializer
        .get(SpsCertificate.class, "chedpp/chedpp_ehc_complete.json", objectMapper);

    PartOne partOne = JsonDeserializer.get(PartOne.class,
        "chedpp/partone/chedpp_ipaffs_partone_complete.json", objectMapper);

    when(chedppPartOneMapper.map(spsCertificate)).thenReturn(partOne);
  }

  @Test
  void map_ReturnsChedppNotification_WhenCompleteEhcSpsCertificate() throws Exception {
    String expectedNotification = ResourceUtils.readFileToString("classpath:chedpp/chedpp_ipaffs_complete.json");

    Notification notification = chedppNotificationMapper.map(spsCertificate);
    String actualNotification = objectMapper.writeValueAsString(notification);

    assertThat(actualNotification).isEqualTo(expectedNotification);
  }
}
