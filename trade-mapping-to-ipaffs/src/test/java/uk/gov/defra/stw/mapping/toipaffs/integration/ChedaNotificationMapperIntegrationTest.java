
package uk.gov.defra.stw.mapping.toipaffs.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.cheda.ChedaNotificationMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = {"uk.gov.defra.stw.mapping.toipaffs"})
@EnableAutoConfiguration
@Component
class ChedaNotificationMapperIntegrationTest {

  @Autowired
  private ChedaNotificationMapper chedaNotificationMapper;

  @Test
  void map_ReturnsChedaNotification_WhenCompleteRestOfWorldSpsCertificate()
      throws JsonProcessingException, NotificationMapperException {
    SpsCertificate spsCertificate = JsonDeserializer.get("cheda/cheda_trade_row_complete.json",
        SpsCertificate.class);

    Notification actual = chedaNotificationMapper.map(spsCertificate);
    overrideUniqueComplementIdToStaticValue(actual);

    Notification expected = JsonDeserializer.get("cheda/cheda_ipaffs_row_complete.json",
        Notification.class);
    assertThat(actual)
        .usingRecursiveComparison()
        .withStrictTypeChecking()
        .isEqualTo(expected);
  }

  @Test
  void map_ReturnsChedaNotification_WhenMinimalRestOfWorldSpsCertificate()
      throws JsonProcessingException, NotificationMapperException {
    SpsCertificate spsCertificate = JsonDeserializer.get("cheda/cheda_trade_row_minimal.json",
        SpsCertificate.class);

    Notification actual = chedaNotificationMapper.map(spsCertificate);
    overrideUniqueComplementIdToStaticValue(actual);

    Notification expected = JsonDeserializer.get("cheda/cheda_ipaffs_row_minimal.json",
        Notification.class);
    assertThat(actual)
        .usingRecursiveComparison()
        .withStrictTypeChecking()
        .isEqualTo(expected);
  }

  @Test
  void map_ReturnsChedaNotification_WhenCompleteEuSpsCertificate()
      throws JsonProcessingException, NotificationMapperException {
    SpsCertificate spsCertificate = JsonDeserializer.get("cheda/cheda_trade_eu_complete.json",
        SpsCertificate.class);

    Notification actual = chedaNotificationMapper.map(spsCertificate);
    overrideUniqueComplementIdToStaticValue(actual);

    Notification expected = JsonDeserializer.get("cheda/cheda_ipaffs_eu_complete.json",
        Notification.class);
    assertThat(actual)
        .usingRecursiveComparison()
        .withStrictTypeChecking()
        .isEqualTo(expected);
  }

  @Test
  void map_ReturnsChedaNotification_WhenMinimalEuSpsCertificate()
      throws JsonProcessingException, NotificationMapperException {
    SpsCertificate spsCertificate = JsonDeserializer.get("cheda/cheda_trade_eu_minimal.json",
        SpsCertificate.class);

    Notification actual = chedaNotificationMapper.map(spsCertificate);
    overrideUniqueComplementIdToStaticValue(actual);

    Notification expected = JsonDeserializer.get("cheda/cheda_ipaffs_eu_minimal.json",
        Notification.class);
    assertThat(actual)
        .usingRecursiveComparison()
        .withStrictTypeChecking()
        .isEqualTo(expected);
  }

  private void overrideUniqueComplementIdToStaticValue(Notification notification) {
    UUID staticComplementId = UUID.fromString("00000000-0000-0000-0000-000000000000");
    notification.getPartOne()
        .getCommodities()
        .getComplementParameterSet()
        .forEach(complementParameterSet ->
            complementParameterSet.setUniqueComplementID(staticComplementId));
  }
}
