
package uk.gov.defra.stw.mapping.toipaffs.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.chedpp.ChedppNotificationMapper;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.ResourceUtils;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = {"uk.gov.defra.stw.mapping.toipaffs"})
@EnableAutoConfiguration
@Component
class ChedppNotificationMapperIntegrationTest {

  @Autowired
  private ChedppNotificationMapper chedppNotificationMapper;

  @Test
  void map_ReturnsChedppNotification_WhenCompleteEphytoSpsCertificate() throws Exception {
    ObjectMapper objectMapper = TestUtils.initObjectMapper();

    SpsCertificate spsCertificate =
        JsonDeserializer.get(
            SpsCertificate.class, "chedpp/chedpp_ehc_complete.json", objectMapper);

    String expectedNotification =
        ResourceUtils.readFileToString(
            "classpath:chedpp/chedpp_ipaffs_integration_complete.json");

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
