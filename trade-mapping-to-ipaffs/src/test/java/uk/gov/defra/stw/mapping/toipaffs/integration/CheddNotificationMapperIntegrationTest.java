package uk.gov.defra.stw.mapping.toipaffs.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;

import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.chedd.CheddNotificationMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = { "uk.gov.defra.stw.mapping.toipaffs" })
@EnableAutoConfiguration
@Component
public class CheddNotificationMapperIntegrationTest {

  @Autowired
  private CheddNotificationMapper cheddNotificationMapper;

  @Test
  void map_ReturnsCheddNotification_WhenMinimalSpsCertificate()
      throws JsonProcessingException, NotificationMapperException {
    SpsCertificate spsCertificate = JsonDeserializer.get("chedd/chedd_ehc_minimum.json",
        SpsCertificate.class);

    Notification actual = cheddNotificationMapper.map(spsCertificate);
    overrideUniqueComplementIdToStaticValue(actual);

    Notification expected = JsonDeserializer.get("chedd/chedd_ipaffs_minimum.json",
        Notification.class);
    assertThat(actual)
        .usingRecursiveComparison()
        .withStrictTypeChecking()
        .isEqualTo(expected);
  }

  @Test
  void map_ReturnsCheddNotification_WhenCompleteSpsCertificate()
      throws JsonProcessingException, NotificationMapperException {
    SpsCertificate spsCertificate = JsonDeserializer.get("chedd/chedd_ehc_complete.json",
        SpsCertificate.class);

    Notification actual = cheddNotificationMapper.map(spsCertificate);
    overrideUniqueComplementIdToStaticValue(actual);

    Notification expected = JsonDeserializer.get("chedd/chedd_ipaffs_complete.json",
        Notification.class);
    assertThat(actual)
        .usingRecursiveComparison()
        .withStrictTypeChecking()
        .isEqualTo(expected);
  }

  private void overrideUniqueComplementIdToStaticValue(Notification notification) {
    UUID staticComplementId = UUID.fromString("12345678-0000-0000-0000-000000000000");
    notification.getPartOne()
        .getCommodities()
        .getComplementParameterSet()
        .forEach(complementParameterSet ->
            complementParameterSet.setUniqueComplementID(staticComplementId));
  }
}
