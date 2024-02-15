package uk.gov.defra.tracesx.integration.cloning;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.defra.tracesx.common.chedpp.ChedppNotificationMapper;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.testutils.JsonDeserializer;
import uk.gov.defra.tracesx.testutils.ResourceUtils;
import uk.gov.defra.tracesx.testutils.TestUtils;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = {"uk.gov.defra.tracesx.cloning", "uk.gov.defra.tracesx.common"})
@EnableAutoConfiguration
class ChedppNotificationMapperIntegrationTest {

  @Autowired
  private ChedppNotificationMapper chedppNotificationMapper;

  @Test
  void map_ReturnsChedppNotification_WhenCompleteEphytoSpsCertificate() throws Exception {
    ObjectMapper objectMapper = TestUtils.initObjectMapper();

    SpsCertificate spsCertificate =
        JsonDeserializer.get(
            SpsCertificate.class, "cloning/chedpp/chedpp_ehc_complete.json", objectMapper);

    String expectedNotification =
        ResourceUtils.readFileToString(
            "classpath:cloning/chedpp/chedpp_ipaffs_integration_complete.json");

    Notification notification = chedppNotificationMapper.map(spsCertificate);
    String actualNotification = objectMapper.writeValueAsString(notification);

    assertThat(actualNotification)
        .isEqualTo(replaceUniqueComplementId(notification, expectedNotification));
  }

  @Test
  void map_ReturnsChedppNotification_When188044499EphytoSpsCertificate() throws Exception {
    ObjectMapper objectMapper = TestUtils.initObjectMapper();

    SpsCertificate spsCertificate =
        JsonDeserializer.get(
            SpsCertificate.class, "cloning/chedpp/188044499.json", objectMapper);

    String expectedNotification =
        ResourceUtils.readFileToString(
            "classpath:cloning/chedpp/188044499_ipaffs.json");

    Notification notification = chedppNotificationMapper.map(spsCertificate);
    String actualNotification = objectMapper.writeValueAsString(notification);

    assertThat(actualNotification)
        .isEqualTo(replaceUniqueComplementId(notification, expectedNotification));
  }

  private String replaceUniqueComplementId(Notification notification, String expectedNotification) {
    return expectedNotification.replaceAll(
        "REPLACE_ME",
        notification
            .getPartOne()
            .getCommodities()
            .getComplementParameterSet()
            .get(0)
            .getUniqueComplementID()
            .toString());
  }
}
