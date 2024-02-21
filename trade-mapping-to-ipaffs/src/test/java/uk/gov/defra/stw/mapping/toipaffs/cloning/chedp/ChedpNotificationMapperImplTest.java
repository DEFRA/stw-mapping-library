package uk.gov.defra.stw.mapping.toipaffs.cloning.chedp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.StatusCode;
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.ExternalReferenceMapper;
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.StatusMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.chedp.ChedpPartOneMapper;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.ResourceUtils;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
import uk.gov.defra.tracesx.notificationschema.representation.ExternalReference;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.ExternalSystem;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum;

@ExtendWith(MockitoExtension.class)
class ChedpNotificationMapperImplTest {

  @Mock
  private ChedpPartOneMapper chedpPartOneMapper;
  @Mock
  private ExternalReferenceMapper externalReferenceMapper;
  @Mock
  private StatusMapper statusMapper;

  private ObjectMapper objectMapper;
  private ChedpNotificationMapperImpl chedpNotificationMapper;
  private SpsCertificate spsCertificate;

  @BeforeEach
  void setup() throws Exception {
    chedpNotificationMapper =
        new ChedpNotificationMapperImpl(chedpPartOneMapper, externalReferenceMapper, statusMapper);
    objectMapper = TestUtils.initObjectMapper();

    spsCertificate = JsonDeserializer
        .get(SpsCertificate.class, "common/chedp/chedp_ehc_complete.json", objectMapper);

    PartOne partOne = JsonDeserializer.get(PartOne.class,
        "cloning/chedp/partone/chedp_ipaffs_partone_complete.json", objectMapper);

    when(chedpPartOneMapper.map(spsCertificate)).thenReturn(partOne);
    when(externalReferenceMapper.mapExternalReference(spsCertificate, ExternalSystem.ECERT)).thenReturn(
        List.of(new ExternalReference(ExternalSystem.ECERT, "NZL2020/PPCS2/121580")));
    when(statusMapper.map(new StatusCode().withName("Draft").withValue("47"))).thenReturn(StatusEnum.DRAFT);
  }

  @Test
  void map_ReturnsChedpNotification_WhenCompleteEhcSpsCertificate() throws Exception {
    String expectedNotification = ResourceUtils.readFileToString("classpath:cloning/chedp/chedp_ipaffs_complete.json");

    Notification notification = chedpNotificationMapper.map(spsCertificate);
    String actualNotification = objectMapper.writeValueAsString(notification);

    assertThat(actualNotification).isEqualTo(expectedNotification);
  }
}
