package uk.gov.defra.stw.mapping.toipaffs.cloning.chedpp;

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
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.ExternalReferenceMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.chedpp.ChedppNotificationMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.ReferenceNumberMapper;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.ResourceUtils;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
import uk.gov.defra.tracesx.notificationschema.representation.ExternalReference;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.ExternalSystem;

@ExtendWith(MockitoExtension.class)
class ChedppNotificationMapperImplTest {

  @Mock
  private ChedppPartOneMapper chedppPartOneMapper;
  @Mock
  private ReferenceNumberMapper referenceNumberMapper;
  @Mock
  private ExternalReferenceMapper externalReferenceMapper;

  private ObjectMapper objectMapper;
  private ChedppNotificationMapper chedppNotificationMapper;
  private SpsCertificate spsCertificate;

  @BeforeEach
  void setup() throws Exception {
    chedppNotificationMapper =
        new ChedppNotificationMapperImpl(chedppPartOneMapper, referenceNumberMapper, externalReferenceMapper);
    objectMapper = TestUtils.initObjectMapper();

    spsCertificate = JsonDeserializer
        .get(SpsCertificate.class, "cloning/chedpp/chedpp_ehc_complete.json", objectMapper);

    PartOne partOne = JsonDeserializer.get(PartOne.class,
        "cloning/chedpp/partone/chedpp_ipaffs_partone_complete.json", objectMapper);

    when(chedppPartOneMapper.map(spsCertificate)).thenReturn(partOne);
    when(externalReferenceMapper.mapExternalReference(spsCertificate, ExternalSystem.EPHYTO)).thenReturn(
        List.of(new ExternalReference(ExternalSystem.EPHYTO, "188044499")));
  }

  @Test
  void map_ReturnsChedppNotification_WhenCompleteEhcSpsCertificate() throws Exception {
    String expectedNotification = ResourceUtils.readFileToString("classpath:cloning/chedpp/chedpp_ipaffs_complete.json");

    Notification notification = chedppNotificationMapper.map(spsCertificate);
    String actualNotification = objectMapper.writeValueAsString(notification);

    assertThat(actualNotification).isEqualTo(expectedNotification);
  }
}
