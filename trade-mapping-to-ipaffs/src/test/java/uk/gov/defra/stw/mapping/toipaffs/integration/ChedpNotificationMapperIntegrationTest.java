package uk.gov.defra.stw.mapping.toipaffs.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.chedp.ChedpNotificationMapper;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.ResourceUtils;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = {"uk.gov.defra.stw.mapping.toipaffs"})
@EnableAutoConfiguration
@Component
class ChedpNotificationMapperIntegrationTest {

  @Autowired
  private ChedpNotificationMapper chedpNotificationMapper;

  @Test
  void map_ReturnsChedpNotification_WhenCompleteEhcSpsCertificate() throws Exception {
    ObjectMapper objectMapper = TestUtils.initObjectMapper();

    SpsCertificate spsCertificate =
        JsonDeserializer.get(
            SpsCertificate.class, "chedp/chedp_ehc_complete.json", objectMapper);

    String expectedNotification =
        ResourceUtils.readFileToString("classpath:chedp/chedp_ipaffs_complete.json");

    Notification notification = chedpNotificationMapper.map(spsCertificate);
    overrideUniqueComplementIdToStaticValue(notification);

    String actualNotification = objectMapper.writeValueAsString(notification);

    assertThat(actualNotification).isEqualTo(expectedNotification);
  }

  @Test
  void map_ReturnsChedpNotification_WhenBasicEhcSpsCertificate() throws Exception {
    ObjectMapper objectMapper = TestUtils.initObjectMapper();

    SpsCertificate spsCertificate =
        JsonDeserializer.get(
            SpsCertificate.class, "chedp/chedp_ehc_basic.json", objectMapper);

    String expectedNotification =
        ResourceUtils.readFileToString("classpath:chedp/chedp_ipaffs_basic.json");

    Notification notification = chedpNotificationMapper.map(spsCertificate);
    overrideUniqueComplementIdToStaticValue(notification);

    String actualNotification = objectMapper.writeValueAsString(notification);

    assertThat(actualNotification).isEqualTo(expectedNotification);
  }

  private void overrideUniqueComplementIdToStaticValue(Notification notification) {
    notification
        .getPartOne()
        .getCommodities()
        .getComplementParameterSet()
        .get(0)
        .setUniqueComplementID(UUID.fromString("0d1be0bc-a7c9-430e-8b8c-5f18d83f687d"));
    notification
        .getPartOne()
        .getCommodities()
        .getComplementParameterSet()
        .get(1)
        .setUniqueComplementID(UUID.fromString("1e364d17-5ba6-49e7-953f-338b9792557b"));
  }
}
