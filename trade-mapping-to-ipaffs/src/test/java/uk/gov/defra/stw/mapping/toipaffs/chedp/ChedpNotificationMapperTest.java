package uk.gov.defra.stw.mapping.toipaffs.chedp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.StatusCode;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ExternalReference;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.ExternalSystem;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum;

@ExtendWith(MockitoExtension.class)
class ChedpNotificationMapperTest {

  @Mock
  private ChedpPartOneMapper chedpPartOneMapper;

  @InjectMocks
  private ChedpNotificationMapper chedpNotificationMapper;

  @Test
  void map_ReturnsChedpNotification() throws NotificationMapperException {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withId(new IDType().withValue("ID value"))
            .withStatusCode(new StatusCode().withValue("47")));
    PartOne partOne = new PartOne();
    when(chedpPartOneMapper.map(spsCertificate)).thenReturn(partOne);

    Notification actual = chedpNotificationMapper.map(spsCertificate);

    assertThat(actual).isEqualTo(Notification.builder()
        .partOne(partOne)
        .type(NotificationTypeEnum.CVEDP)
        .status(StatusEnum.DRAFT)
        .externalReferences(List.of(ExternalReference.builder()
            .system(ExternalSystem.ENOTIFICATION)
            .reference("ID value")
            .build()))
        .build());
  }

  @Test
  void map_ReturnsNotificationException_WhenInvalidStatus() throws NotificationMapperException {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withId(new IDType().withValue("ID value"))
            .withStatusCode(new StatusCode().withValue("INVALID")));
    PartOne partOne = new PartOne();
    when(chedpPartOneMapper.map(spsCertificate)).thenReturn(partOne);

    assertThatThrownBy(() -> chedpNotificationMapper.map(spsCertificate))
        .isInstanceOf(NotificationMapperException.class)
        .hasMessage("Unable to map to the StatusEnum: INVALID");
  }
}
