package uk.gov.defra.stw.mapping.totrade;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.DRAFT;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.defra.stw.mapping.totrade.exceptions.TradeValidationException;
import uk.gov.defra.stw.mapping.totrade.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = {"uk.gov.defra.stw.mapping"})
@EnableAutoConfiguration
class SpsCertificateMapperTest {

  private Notification notification;
  private String expectedSpsCertificateString;

  @Autowired
  private SpsCertificateMapper spsCertificateMapper;

  @BeforeEach
  void setup() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);

    expectedSpsCertificateString = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsCertificateIntegration.json");
  }

  @Test
  void processNotification_ReturnsSpsCertificate_WhenNoValidationErrors() throws Exception {
    String actual = spsCertificateMapper.processNotification(notification);
    JSONAssert.assertEquals(
        expectedSpsCertificateString, actual, JSONCompareMode.LENIENT);
  }

  @Test
  void processNotification_ThrowsTradeValidationException_WhenValidationErrors() {
    notification.setStatus(DRAFT);
    assertThrows(
        TradeValidationException.class, () -> spsCertificateMapper.processNotification(notification));
  }
}
