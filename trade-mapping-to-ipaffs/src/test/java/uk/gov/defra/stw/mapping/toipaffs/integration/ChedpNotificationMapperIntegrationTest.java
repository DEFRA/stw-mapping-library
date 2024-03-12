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
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.chedp.ChedpNotificationMapper;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
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
    SpsCertificate spsCertificate = JsonDeserializer.get("chedp/chedp_ehc_complete.json",
        SpsCertificate.class);


    Notification actual = chedpNotificationMapper.map(spsCertificate);
    overrideUniqueComplementIdToStaticValue(actual);

    Notification expected = JsonDeserializer.get("chedp/chedp_ipaffs_complete.json",
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
