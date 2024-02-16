package uk.gov.defra.stw.mapping.totrade.chedpp.spsexchangeddocument;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.totrade.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

class ChedppSpsNoteTypeChildNotificationMapperTest {

  private static final String CONTENT_VALUE_KEY = "#{contentValue}";
  private static final String YES = "YES";
  private static final String NO = "NO";

  private Notification notification;
  private ChedppSpsNoteTypeChildNotificationMapper spsNoteTypeChildNotificationMapper;
  private ObjectMapper objectMapper;

  public ChedppSpsNoteTypeChildNotificationMapperTest() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);

    spsNoteTypeChildNotificationMapper = new ChedppSpsNoteTypeChildNotificationMapper();
  }

  @Test
  void map_ReturnsSpsNoteType_WhenNullChildNotification() throws JsonProcessingException {
    notification.setChildNotification(null);
    SpsNoteType spsNoteType = spsNoteTypeChildNotificationMapper.map(notification);
    String actualSps = objectMapper.writeValueAsString(spsNoteType);

    String expectedSps = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsexchangeddocument/spsNoteTypeChildNotification.json")
        .replace(CONTENT_VALUE_KEY, NO);

    assertThat(actualSps).isEqualTo(expectedSps);
  }

  @Test
  void map_ReturnsSpsNoteType_WhenTrueChildNotification() throws JsonProcessingException {
    notification.setChildNotification(Boolean.TRUE);
    SpsNoteType spsNoteType = spsNoteTypeChildNotificationMapper.map(notification);
    String actualSps = objectMapper.writeValueAsString(spsNoteType);

    String expectedSps = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsexchangeddocument/spsNoteTypeChildNotification.json")
        .replace(CONTENT_VALUE_KEY, YES);

    assertThat(actualSps).isEqualTo(expectedSps);
  }

  @Test
  void map_ReturnsSpsNoteType_WhenFalseChildNotification() throws JsonProcessingException {
    notification.setChildNotification(Boolean.FALSE);
    SpsNoteType spsNoteType = spsNoteTypeChildNotificationMapper.map(notification);
    String actualSps = objectMapper.writeValueAsString(spsNoteType);

    String expectedSps = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsexchangeddocument/spsNoteTypeChildNotification.json")
        .replace(CONTENT_VALUE_KEY, NO);

    assertThat(actualSps).isEqualTo(expectedSps);
  }
}
