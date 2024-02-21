package uk.gov.defra.stw.mapping.totrade.chedpp;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.totrade.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = {"uk.gov.defra.stw.mapping"})
@EnableAutoConfiguration
class ChedppSpsCertificateMapperIntegrationTest {

  @Autowired
  private ChedppSpsCertificateMapper spsCertificateMapper;

  @Test
  void map_ReturnsSpsCertificate_WhenValidChedpp() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    Notification notification = objectMapper.readValue(notificationString, Notification.class);

    SpsCertificate spsCertificate = spsCertificateMapper.map(notification);
    String actualSpsCertificateJson = objectMapper.writeValueAsString(spsCertificate);

    String expectedSpsCertificateJson = ResourceUtil.readFileToString("classpath:mapping/chedpp/chedppSpsCertificateIntegration.json");

    assertThat(actualSpsCertificateJson).isEqualTo(expectedSpsCertificateJson);
  }
}
