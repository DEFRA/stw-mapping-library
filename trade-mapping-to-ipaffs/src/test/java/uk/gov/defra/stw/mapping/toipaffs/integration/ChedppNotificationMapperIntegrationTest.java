
package uk.gov.defra.stw.mapping.toipaffs.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.stw.mapping.toipaffs.testutils.ResourceUtils.readFileToString;

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
import uk.gov.defra.stw.mapping.toipaffs.chedpp.ChedppNotificationMapper;
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
    SpsCertificate spsCertificate = objectMapper.readValue(
        readFileToString("classpath:chedpp/chedpp_ehc_complete.json"), SpsCertificate.class);

    Notification actual = chedppNotificationMapper.map(spsCertificate);
    overrideUniqueComplementIdToStaticValue(actual);

    Notification expected = objectMapper.readValue(
        readFileToString("classpath:chedpp/chedpp_ipaffs_complete.json"), Notification.class);
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
