package uk.gov.defra.tracesx.mapper.chedpp.spsconsignment;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.mapper.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.NotificationSealsContainers;
import uk.gov.defra.tracesx.trade.dto.SpsTransportEquipmentType;

class ChedppUtilizedSpsTransportEquipmentMapperTest {

  private Notification notification;
  private ChedppUtilizedSpsTransportEquipmentMapper utilizedSpsTransportEquipmentMapper;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);
    utilizedSpsTransportEquipmentMapper = new ChedppUtilizedSpsTransportEquipmentMapper();
  }

  @Test
  void map_ReturnsSpsTransportEquipmentType_WhenAllFieldsComplete() throws JsonProcessingException {
    List<SpsTransportEquipmentType> spsTransportEquipmentTypeList = utilizedSpsTransportEquipmentMapper
        .map(notification);
    String actualSps = objectMapper.writeValueAsString(spsTransportEquipmentTypeList);

    String expectedSps = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsconsignment/utilizedSpsTransportEquipmentComplete.json");

    assertThat(actualSps).isEqualTo(expectedSps);
  }

  @Test
  void map_ReturnsEmptyList_WhenNotificationIsNull() throws JsonProcessingException {
    notification = null;

    List<SpsTransportEquipmentType> spsTransportEquipmentTypeList = utilizedSpsTransportEquipmentMapper
        .map(notification);
    String actualSps = objectMapper.writeValueAsString(spsTransportEquipmentTypeList);

    assertThat(actualSps).isEqualTo("[ ]");
  }

  @Test
  void map_ReturnsEmptyList_WhenSealsContainersIsNull() throws JsonProcessingException {
    notification.getPartOne().setSealsContainers(null);

    List<SpsTransportEquipmentType> spsTransportEquipmentTypeList = utilizedSpsTransportEquipmentMapper
        .map(notification);
    String actualSps = objectMapper.writeValueAsString(spsTransportEquipmentTypeList);

    assertThat(actualSps).isEqualTo("[ ]");
  }

  @Test
  void map_ReturnsNASealNumber_WhenMappedSealNumberIsNull() throws JsonProcessingException {
    notification.getPartOne().setSealsContainers(List.of(
        new NotificationSealsContainers(null, "5678", false, null)));

    List<SpsTransportEquipmentType> spsTransportEquipmentTypeList = utilizedSpsTransportEquipmentMapper
        .map(notification);
    String actualSps = objectMapper.writeValueAsString(spsTransportEquipmentTypeList);

    String expectedSps = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsconsignment/utilizedSpsTransportEquipmentEmptyAffixedSpsSeal.json");

    assertThat(actualSps).isEqualTo(expectedSps);
  }

  @Test
  void map_ReturnsNASealNumber_WhenMappedSealNumberIsEmpty() throws JsonProcessingException {
    notification.getPartOne().setSealsContainers(List.of(
        new NotificationSealsContainers("", "5678", false, null)));

    List<SpsTransportEquipmentType> spsTransportEquipmentTypeList = utilizedSpsTransportEquipmentMapper
        .map(notification);
    String actualSps = objectMapper.writeValueAsString(spsTransportEquipmentTypeList);

    String expectedSps = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsconsignment/utilizedSpsTransportEquipmentEmptyAffixedSpsSeal.json");

    assertThat(actualSps).isEqualTo(expectedSps);
  }
}
