package uk.gov.defra.tracesx.integration.cloning;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.defra.tracesx.common.cheda.ChedaNotificationMapper;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.testutils.JsonDeserializer;
import uk.gov.defra.tracesx.testutils.ResourceUtils;
import uk.gov.defra.tracesx.testutils.TestUtils;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = {"uk.gov.defra.tracesx.cloning", "uk.gov.defra.tracesx.common"})
@EnableAutoConfiguration
class ChedaNotificationMapperIntegrationTest {

  @Autowired
  private ChedaNotificationMapper chedaNotificationMapper;

  @Test
  void map_ReturnsChedaNotification_WhenCompleteEhcSpsCertificate() throws Exception {
    ObjectMapper objectMapper = TestUtils.initObjectMapper();

    SpsCertificate spsCertificate =
        JsonDeserializer.get(
            SpsCertificate.class, "cloning/cheda/cheda_ehc_complete.json", objectMapper);

    String expectedNotification =
        ResourceUtils.readFileToString("classpath:cloning/cheda/cheda_ipaffs_integration_complete.json");

    Notification notification = chedaNotificationMapper.map(spsCertificate);
    overrideUniqueComplementIdToStaticValue(notification, 0, "3f8bd1d2-199c-447f-955e-c5a5a9160c95");

    String actualNotification = objectMapper.writeValueAsString(notification);

    assertThat(actualNotification).isEqualTo(expectedNotification);
  }

  @Test
  void map_ReturnsChedaNotification_WhenAllTradeFields() throws Exception {
    ObjectMapper objectMapper = TestUtils.initObjectMapper();

    SpsCertificate spsCertificate =
        JsonDeserializer.get(
            SpsCertificate.class, "cloning/cheda/cheda_trade_all_fields.json", objectMapper);

    String expectedNotification =
        ResourceUtils.readFileToString("classpath:cloning/cheda/cheda_ipaffs_all_fields.json");

    Notification notification = chedaNotificationMapper.map(spsCertificate);
    overrideUniqueComplementIdToStaticValue(notification, 0, "3f8bd1d2-199c-447f-955e-c5a5a9160c95");
    overrideUniqueComplementIdToStaticValue(notification, 1, "691bb5ee-f0a6-4238-ab9a-6e96d5fa0ba0");
    overrideUniqueComplementIdToStaticValue(notification, 2, "f2fea9cc-2ade-45d2-8cfc-d827b02ac1eb");
    overrideUniqueComplementIdToStaticValue(notification, 3, "9e7310dc-845e-4531-9805-8c3148cb12f6");
    overrideUniqueComplementIdToStaticValue(notification, 4, "53d32aea-6c31-4f64-8be2-c4d8816afc5b");

    String actualNotification = objectMapper.writeValueAsString(notification);

    assertThat(actualNotification).isEqualTo(expectedNotification);
  }

  @Test
  void map_ReturnsChedaNotification_WhenMandatoryTradeFieldsOnly() throws Exception {
    ObjectMapper objectMapper = TestUtils.initObjectMapper();

    SpsCertificate spsCertificate =
        JsonDeserializer.get(
            SpsCertificate.class, "cloning/cheda/cheda_trade_mandatory_fields.json", objectMapper);

    String expectedNotification =
        ResourceUtils.readFileToString("classpath:cloning/cheda/cheda_ipaffs_mandatory_fields.json");

    Notification notification = chedaNotificationMapper.map(spsCertificate);
    overrideUniqueComplementIdToStaticValue(notification, 0, "3f8bd1d2-199c-447f-955e-c5a5a9160c95");
    overrideUniqueComplementIdToStaticValue(notification, 1, "691bb5ee-f0a6-4238-ab9a-6e96d5fa0ba0");
    overrideUniqueComplementIdToStaticValue(notification, 2, "f2fea9cc-2ade-45d2-8cfc-d827b02ac1eb");
    overrideUniqueComplementIdToStaticValue(notification, 3, "9e7310dc-845e-4531-9805-8c3148cb12f6");
    overrideUniqueComplementIdToStaticValue(notification, 4, "53d32aea-6c31-4f64-8be2-c4d8816afc5b");

    String actualNotification = objectMapper.writeValueAsString(notification);

    assertThat(actualNotification).isEqualTo(expectedNotification);
  }

  private void overrideUniqueComplementIdToStaticValue(Notification notification, int index, String uuid) {
    notification
        .getPartOne()
        .getCommodities()
        .getComplementParameterSet()
        .get(index)
        .setUniqueComplementID(UUID.fromString(uuid));
  }
}
