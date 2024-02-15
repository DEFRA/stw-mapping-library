package uk.gov.defra.tracesx.cloning.chedp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.tracesx.common.common.SealsContainersMapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.NotificationSealsContainers;
import uk.gov.defra.tracesx.testutils.JsonDeserializer;
import uk.gov.defra.tracesx.testutils.ResourceUtils;
import uk.gov.defra.tracesx.testutils.TestUtils;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;
import uk.gov.defra.tracesx.trade.dto.SpsConsignment;
import uk.gov.defra.tracesx.trade.dto.SpsTransportEquipmentType;

@ExtendWith(MockitoExtension.class)
class ChedpSealsContainersMapperTest {

  @Mock
  private SealsContainersMapper sealsContainersMapper;

  private ChedpSealsContainersMapper mapper;
  private ObjectMapper objectMapper;
  private SpsConsignment spsConsignment;
  private List<SpsTransportEquipmentType> associatedSpsTransportTypeList;
  private List<NotificationSealsContainers> consignmentSealsContainers;


  @BeforeEach
  void setup() throws JsonProcessingException {
    mapper = new ChedpSealsContainersMapper(sealsContainersMapper);
    objectMapper = TestUtils.initObjectMapper();
    spsConsignment = JsonDeserializer
        .get(SpsCertificate.class, "cloning/chedp/chedp_ehc_complete.json", objectMapper)
        .getSpsConsignment();
    associatedSpsTransportTypeList = getAssociatedSpsTransportEquipmentList(spsConsignment);
    consignmentSealsContainers = List.of(
        NotificationSealsContainers.builder()
            .sealNumber("S12345")
            .containerNumber("C12345")
            .build()
    );
    when(sealsContainersMapper.map(spsConsignment.getUtilizedSpsTransportEquipment()))
        .thenReturn(consignmentSealsContainers);
  }

  @Test
  void map_ReturnsSealsContainers_WhenSpsCertificateWithDuplicateSpsTransportEquipment()
      throws NotificationMapperException, JsonProcessingException {
    String expectedSealsContainers =
        ResourceUtils.readFileToString("classpath:/cloning/chedp/partone/chedp_ipaffs_sealscontainers_complete.json");
    when(sealsContainersMapper.map(associatedSpsTransportTypeList))
        .thenReturn(buildCommoditySealsContainersListWithNoDuplicates());
    when(sealsContainersMapper.map(associatedSpsTransportTypeList))
        .thenReturn(buildCommoditySealsContainersListWithDuplicates());

    List<NotificationSealsContainers> sealsContainers = mapper.map(spsConsignment);
    String actualSealsContainers = objectMapper.writeValueAsString(sealsContainers);

    verify(sealsContainersMapper, times(1)).map(spsConsignment
        .getUtilizedSpsTransportEquipment());
    verify(sealsContainersMapper, times(1))
        .map(associatedSpsTransportTypeList);
    assertThat(actualSealsContainers).isEqualTo(expectedSealsContainers);
  }

  @Test
  void map_ReturnsSealsContainersList_WhenSpsCertificateWithNoDuplicateSpsTransportEquipment()
      throws NotificationMapperException {
    when(sealsContainersMapper.map(associatedSpsTransportTypeList))
        .thenReturn(buildCommoditySealsContainersListWithNoDuplicates());
    List<NotificationSealsContainers> expectedSealsContainers = new ArrayList<>();
    List<NotificationSealsContainers> commoditySealsContainers =
        buildCommoditySealsContainersListWithNoDuplicates();
    expectedSealsContainers.add(
        NotificationSealsContainers.builder()
            .sealNumber("S12345")
            .containerNumber("C12345")
            .build());
    expectedSealsContainers.addAll(commoditySealsContainers);

    List<NotificationSealsContainers> actualSealsContainers = mapper.map(spsConsignment);

    assertThat(actualSealsContainers).isEqualTo(expectedSealsContainers);
  }

  @Test
  void map_ReturnsSealsContainersListWithoutEmptyElements_WhenSpsCertificateWithEmptySpsTransportEquipment()
      throws NotificationMapperException {
    spsConsignment.getIncludedSpsConsignmentItem().get(0).getIncludedSpsTradeLineItem().get(0)
        .setAssociatedSpsTransportEquipment(Collections.emptyList());
    spsConsignment.getIncludedSpsConsignmentItem().get(1).getIncludedSpsTradeLineItem().get(0)
        .setAssociatedSpsTransportEquipment(Collections.emptyList());
    when(sealsContainersMapper.map(Collections.emptyList()))
        .thenReturn(null);

    List<NotificationSealsContainers> actualSealsContainers = mapper.map(spsConsignment);

    verify(sealsContainersMapper, times(1)).map(spsConsignment
        .getUtilizedSpsTransportEquipment());
    verify(sealsContainersMapper, times(1))
        .map(Collections.emptyList());
    assertThat(actualSealsContainers).isEqualTo(consignmentSealsContainers);
  }

  @Test
  void map_ReturnsSealsContainersListWithoutNullElements_WhenSpsCertificateWithNullSpsTransportEquipment()
      throws NotificationMapperException {
    spsConsignment.getIncludedSpsConsignmentItem().get(0).getIncludedSpsTradeLineItem().get(0)
        .setAssociatedSpsTransportEquipment(null);
    spsConsignment.getIncludedSpsConsignmentItem().get(1).getIncludedSpsTradeLineItem().get(0)
        .setAssociatedSpsTransportEquipment(null);
    when(sealsContainersMapper.map(Collections.emptyList()))
        .thenReturn(null);

    List<NotificationSealsContainers> actualSealsContainers = mapper.map(spsConsignment);

    verify(sealsContainersMapper, times(1)).map(spsConsignment
        .getUtilizedSpsTransportEquipment());
    verify(sealsContainersMapper, times(1))
        .map(Collections.emptyList());
    assertThat(actualSealsContainers).isEqualTo(consignmentSealsContainers);
  }

  @Test
  void map_ReturnsNull_WhenSpsCertificateWithNoSpsTransportEquipment()
      throws NotificationMapperException {
    when(sealsContainersMapper.map(associatedSpsTransportTypeList))
        .thenReturn(buildCommoditySealsContainersListWithNoDuplicates());
    when(sealsContainersMapper.map(associatedSpsTransportTypeList))
        .thenReturn(null);
    when(sealsContainersMapper.map(spsConsignment.getUtilizedSpsTransportEquipment()))
        .thenReturn(null);

    List<NotificationSealsContainers> actualSealsContainers = mapper.map(spsConsignment);

    assertThat(actualSealsContainers).isNull();
  }

  private List<NotificationSealsContainers> buildCommoditySealsContainersListWithDuplicates() {
    return List.of(
        NotificationSealsContainers.builder()
            .sealNumber("S123456")
            .containerNumber("C123456")
            .build(),
        NotificationSealsContainers.builder()
            .sealNumber("S54321")
            .containerNumber("C54321")
            .build(),
        NotificationSealsContainers.builder()
            .sealNumber("S12345")
            .containerNumber("C12345")
            .build(),
        NotificationSealsContainers.builder()
            .sealNumber("S123456")
            .containerNumber("C123456")
            .build()
    );
  }

  private List<NotificationSealsContainers> buildCommoditySealsContainersListWithNoDuplicates() {
    return List.of(
        NotificationSealsContainers.builder()
            .sealNumber("S54321")
            .containerNumber("C54321")
            .build(),
        NotificationSealsContainers.builder()
            .sealNumber("S123456")
            .containerNumber("C123456")
            .build(),
        NotificationSealsContainers.builder()
            .sealNumber("S123455")
            .containerNumber("C123455")
            .build(),
        NotificationSealsContainers.builder()
            .sealNumber("S1234567")
            .containerNumber("C1234567")
            .build()
    );
  }

  private List<SpsTransportEquipmentType> getAssociatedSpsTransportEquipmentList(SpsConsignment
      spsConsignment) {
    List<SpsTransportEquipmentType> transportEquipmentTypeList = new ArrayList<>();
    transportEquipmentTypeList.addAll(spsConsignment.getIncludedSpsConsignmentItem()
        .get(0).getIncludedSpsTradeLineItem().get(0).getAssociatedSpsTransportEquipment());
    transportEquipmentTypeList.addAll(spsConsignment.getIncludedSpsConsignmentItem()
        .get(1).getIncludedSpsTradeLineItem().get(0).getAssociatedSpsTransportEquipment());
    return transportEquipmentTypeList;
  }
}
