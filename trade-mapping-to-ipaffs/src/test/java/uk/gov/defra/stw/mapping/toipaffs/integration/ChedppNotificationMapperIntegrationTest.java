
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
import uk.gov.defra.stw.mapping.toipaffs.chedpp.ChedppNotificationMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = {"uk.gov.defra.stw.mapping.toipaffs"})
@EnableAutoConfiguration
@Component
class ChedppNotificationMapperIntegrationTest {

  @Autowired
  private ChedppNotificationMapper chedppNotificationMapper;

  @Test
  void map_ReturnsChedppNotification_WhenCompleteSpsCertificate()
      throws JsonProcessingException, NotificationMapperException {
    SpsCertificate spsCertificate = JsonDeserializer.get("chedpp/chedpp_trade_complete.json",
        SpsCertificate.class);

    Notification actual = chedppNotificationMapper.map(spsCertificate);
    overrideUniqueComplementIdToStaticValue(actual);

    Notification expected = JsonDeserializer.get("chedpp/chedpp_ipaffs_complete.json",
        Notification.class);
    assertThat(actual)
        .usingRecursiveComparison()
        .withStrictTypeChecking()
        .isEqualTo(expected);
  }

  @Test
  void map_ReturnsChedppNotification_WhenMinimalSpsCertificate()
      throws JsonProcessingException, NotificationMapperException {
    SpsCertificate spsCertificate = JsonDeserializer.get("chedpp/chedpp_trade_minimal.json",
        SpsCertificate.class);

    Notification actual = chedppNotificationMapper.map(spsCertificate);
    overrideUniqueComplementIdToStaticValue(actual);

    Notification expected = JsonDeserializer.get("chedpp/chedpp_ipaffs_minimal.json",
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
