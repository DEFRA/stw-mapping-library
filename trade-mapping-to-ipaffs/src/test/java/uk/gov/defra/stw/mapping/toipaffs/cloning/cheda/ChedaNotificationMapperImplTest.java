package uk.gov.defra.stw.mapping.toipaffs.cloning.cheda;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.ResourceUtils;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
import uk.gov.defra.tracesx.notificationschema.representation.ExternalReference;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.ExternalSystem;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum;

@ExtendWith(MockitoExtension.class)
class ChedaNotificationMapperImplTest {

  @Mock
  private ChedaPartOneMapper chedaPartOneMapper;
  @Mock
  private ExternalReferenceMapper externalReferenceMapper;
  @Mock
  private StatusMapper statusMapper;

  private ObjectMapper objectMapper;
  private SpsCertificate spsCertificate;
  private ChedaNotificationMapperImpl chedaNotificationMapperImpl;

  @BeforeEach
  void setup() throws JsonProcessingException, NotificationMapperException {
    chedaNotificationMapperImpl =
        new ChedaNotificationMapperImpl(chedaPartOneMapper, externalReferenceMapper, statusMapper);
    objectMapper = TestUtils.initObjectMapper();

    spsCertificate = JsonDeserializer
        .get(SpsCertificate.class, "cloning/cheda/cheda_ehc_complete.json", objectMapper);

    PartOne partOne = JsonDeserializer.get(PartOne.class,
        "cloning/cheda/partone/cheda_ipaffs_partone_complete.json", objectMapper);

    when(chedaPartOneMapper.map(spsCertificate)).thenReturn(partOne);
    when(externalReferenceMapper.mapExternalReference(spsCertificate, ExternalSystem.ECERT)).thenReturn(
        List.of(new ExternalReference(ExternalSystem.ECERT, "reference")));
    when(statusMapper.map(new StatusCode().withName("Draft").withValue("47"))).thenReturn(
        StatusEnum.DRAFT);
  }

  @Test
  void map_ReturnsChedaNotification_WhenCompleteEhcSpsCertificate() throws Exception {
    String expectedNotification = ResourceUtils.readFileToString("classpath:cloning/cheda/cheda_ipaffs_complete.json");

    Notification notification = chedaNotificationMapperImpl.map(spsCertificate);
    String actualNotification = objectMapper.writeValueAsString(notification);

    assertThat(actualNotification).isEqualTo(expectedNotification);
  }
}
