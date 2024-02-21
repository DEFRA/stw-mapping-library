package uk.gov.defra.stw.mapping.toipaffs.common.common;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsTransportEquipmentType;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.ResourceUtils;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
import uk.gov.defra.tracesx.notificationschema.representation.NotificationSealsContainers;

class SealsContainersMapperTest {

  private ObjectMapper objectMapper;
  private SealsContainersMapper chedppSealsContainersMapper;
  private List<SpsTransportEquipmentType> spsTransportEquipmentTypeList;

  @BeforeEach
  void setup() throws JsonProcessingException {
    chedppSealsContainersMapper = new SealsContainersMapper();
    objectMapper = TestUtils.initObjectMapper();
    spsTransportEquipmentTypeList = JsonDeserializer
        .get(SpsCertificate.class, "cloning/chedpp/chedpp_ehc_complete.json", objectMapper)
        .getSpsConsignment().getUtilizedSpsTransportEquipment();
  }

  @Test
  void map_ReturnsSealsContainersList_WhenComplete() throws JsonProcessingException {
    String expectedSealsContainers = ResourceUtils
        .readFileToString(
            "classpath:common/chedpp/partone/sealscontainers/chedpp_ipaffs_sealscontainers_complete.json");

    List<NotificationSealsContainers> sealsContainers = chedppSealsContainersMapper
        .map(spsTransportEquipmentTypeList);
    String actualSealsContainers = objectMapper.writeValueAsString(sealsContainers);

    assertThat(actualSealsContainers).isEqualTo(expectedSealsContainers);
  }

  @Test
  void map_ReturnsEmptyContainerNumber_WhenEmptyIDType() {
    spsTransportEquipmentTypeList.get(0).setId(new IDType().withSchemeID("").withValue(" "));

    List<NotificationSealsContainers> sealsContainers = chedppSealsContainersMapper
        .map(spsTransportEquipmentTypeList);

    assertThat(sealsContainers.get(0).getContainerNumber()).isEmpty();
  }

  @Test
  void map_ReturnsNullContainerNumber_WhenNullId() {
    spsTransportEquipmentTypeList.get(0).setId(null);

    List<NotificationSealsContainers> sealsContainers = chedppSealsContainersMapper
        .map(spsTransportEquipmentTypeList);

    assertThat(sealsContainers.get(0).getContainerNumber()).isNull();
  }

  @Test
  void map_ReturnsNullContainerNumber_WhenNullValue() {
    spsTransportEquipmentTypeList.get(0).setId(new IDType().withValue(null));

    List<NotificationSealsContainers> sealsContainers = chedppSealsContainersMapper
        .map(spsTransportEquipmentTypeList);

    assertThat(sealsContainers.get(0).getContainerNumber()).isNull();
  }

  @Test
  void map_ReturnsNullSealNumber_WhenNullId() {
    spsTransportEquipmentTypeList.get(0).getAffixedSpsSeal().get(0).setId(null);

    List<NotificationSealsContainers> sealsContainers = chedppSealsContainersMapper
        .map(spsTransportEquipmentTypeList);

    assertThat(sealsContainers.get(0).getSealNumber()).isNull();
  }

  @Test
  void map_ReturnsNullSealNumberWithFalseOfficialSeal_WhenNullValueAndSealNumberSeal() {
    spsTransportEquipmentTypeList.get(0).getAffixedSpsSeal().get(0).setId(new IDType().withSchemeID("seal_number").withValue(null));

    List<NotificationSealsContainers> sealsContainers = chedppSealsContainersMapper
        .map(spsTransportEquipmentTypeList);

    assertThat(sealsContainers.get(0).getSealNumber()).isNull();
    assertThat(sealsContainers.get(0).isOfficialSeal()).isFalse();
  }

  @Test
  void map_ReturnsNullSealNumberAndFalseOfficialSeal_WhenIncorrectSchemeIdValue() {
    spsTransportEquipmentTypeList.get(0).getAffixedSpsSeal().get(0).setId(new IDType().withSchemeID("invalid").withValue(null));

    List<NotificationSealsContainers> sealsContainers = chedppSealsContainersMapper
        .map(spsTransportEquipmentTypeList);

    assertThat(sealsContainers.get(0).getSealNumber()).isNull();
    assertThat(sealsContainers.get(0).isOfficialSeal()).isFalse();
  }

  @Test
  void map_MapsOfficialSeal_WhenOfficialSealNumber() {
    spsTransportEquipmentTypeList.get(0).getAffixedSpsSeal().get(0).setId(
        new IDType().withSchemeID("official_seal_number").withValue("s1"));

    List<NotificationSealsContainers> sealsContainers = chedppSealsContainersMapper
        .map(spsTransportEquipmentTypeList);

    assertThat(sealsContainers.get(0).getSealNumber()).isEqualTo("s1");
    assertThat(sealsContainers.get(0).isOfficialSeal()).isTrue();
  }

  @Test
  void map_DoesNotMapOfficialSeal_WhenNonOfficialSealNumber() {
    spsTransportEquipmentTypeList.get(0).getAffixedSpsSeal().get(0).setId(
        new IDType().withSchemeID("seal_number").withValue("s1"));

    List<NotificationSealsContainers> sealsContainers = chedppSealsContainersMapper
        .map(spsTransportEquipmentTypeList);

    assertThat(sealsContainers.get(0).getSealNumber()).isEqualTo("s1");
    assertThat(sealsContainers.get(0).isOfficialSeal()).isFalse();
  }

  @Test
  void map_DoesNotMapSealNumber_WhenAffixedSpsSealIsNull() {
    spsTransportEquipmentTypeList.get(0).setAffixedSpsSeal(null);

    List<NotificationSealsContainers> sealsContainers = chedppSealsContainersMapper
        .map(spsTransportEquipmentTypeList);

    assertThat(sealsContainers.get(0).getSealNumber()).isNull();
  }

  @Test
  void map_DoesNotMapSealNumber_WhenSchemeIdIsNull() {
    spsTransportEquipmentTypeList.get(0).getAffixedSpsSeal().get(0).setId(
        new IDType().withSchemeID(null).withValue("s1"));

    List<NotificationSealsContainers> sealsContainers = chedppSealsContainersMapper
        .map(spsTransportEquipmentTypeList);

    assertThat(sealsContainers.get(0).getSealNumber()).isNull();
  }

  @Test
  void map_ReturnsNullSealsContainersList_WhenNullUtilizedSpsTransportEquipmentList() {
    List<NotificationSealsContainers> actualNotificationSealsContainers = chedppSealsContainersMapper
        .map(null);

    assertThat(actualNotificationSealsContainers).isNull();
  }

  @Test
  void map_ReturnsNullSealsContainersList_WhenEmptyUtilizedSpsTransportEquipmentList() {
    List<SpsTransportEquipmentType> spsTransportEquipmentTypes = Collections.emptyList();
    List<NotificationSealsContainers> actualNotificationSealsContainers = chedppSealsContainersMapper
        .map(spsTransportEquipmentTypes);

    assertThat(actualNotificationSealsContainers).isNull();
  }
}
