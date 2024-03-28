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

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.chedd.CheddNotificationMapper;
import uk.gov.defra.stw.mapping.toipaffs.chedp.ChedpNotificationMapper;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = {"uk.gov.defra.stw.mapping.toipaffs"})
@EnableAutoConfiguration
@Component
public class CheddNotificationMapperIntegrationTest {

  @Autowired
  private CheddNotificationMapper cheddNotificationMapper;

  @Test
  void map_ReturnsChedpNotification_WhenMinimalEhcSpsCertificate() throws Exception {
    SpsCertificate spsCertificate = JsonDeserializer.get("chedp/chedp_ehc_minimum.json",
        SpsCertificate.class);


    Notification actual = cheddNotificationMapper.map(spsCertificate);

    ObjectMapper objectMapper = TestUtils.initObjectMapper();

    String a = objectMapper.writeValueAsString(actual);



    System.out.println(a);
  }

}
