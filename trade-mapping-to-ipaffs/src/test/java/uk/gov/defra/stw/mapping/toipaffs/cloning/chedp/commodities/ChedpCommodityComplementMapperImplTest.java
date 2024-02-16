package uk.gov.defra.stw.mapping.toipaffs.cloning.chedp.commodities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.common.common.commodities.CommodityComplementFieldMapper;
import uk.gov.defra.stw.mapping.toipaffs.testutils.ResourceUtils;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

@ExtendWith(MockitoExtension.class)
class ChedpCommodityComplementMapperImplTest {

  private SpsCertificate spsCertificate;

  private final ObjectMapper objectMapper = TestUtils.initObjectMapper();

  @Mock
  private CommodityComplementFieldMapper commodityComplementFieldMapper;

  @InjectMocks
  private ChedpCommodityComplementMapperImpl mapper;

  @BeforeEach
  void setup() throws JsonProcessingException {
    String jsonString =
        ResourceUtils.readFileToString("classpath:common/chedp/chedp_ehc_complete.json");
    spsCertificate = objectMapper.readValue(jsonString, SpsCertificate.class);
  }

  @Test
  void map_ReturnsChedpNotification_WhenCompleteEhcSpsCertificateAndIpaffsCode()
      throws Exception {
    //Given
    String expectedNotification =
        ResourceUtils.readFileToString("classpath:/cloning/chedp/chedp_ipaffs_complete.json");
    when(commodityComplementFieldMapper.mapCommodityId(any())).thenReturn(
        "07099990");
    when(commodityComplementFieldMapper.mapDescription(anyList())).thenReturn("Other");
    Notification notification = objectMapper.readValue(expectedNotification, Notification.class);

    //When
    List<CommodityComplement> commodityComplements = mapper.map(spsCertificate);
    CommodityComplement commodityComplement = notification.getPartOne()
        .getCommodities().getCommodityComplement().get(0);

    //Then
    assertThat(commodityComplements).isNotEmpty();
    assertThat(commodityComplements.get(0).getCommodityID())
        .isEqualTo(commodityComplement.getCommodityID());
    assertThat(commodityComplements.get(0).getCommodityDescription())
        .isEqualTo(commodityComplement.getCommodityDescription());
  }

  @Test
  void map_ReturnsEmpty_WhenSequenceNumberIsZero() throws Exception {
    //Given
    String fileName = "/cloning/chedp/chedp_ehc_complete_zero_sequence.json";
    String jsonString = ResourceUtils.readFileToString("classpath:" + fileName);
    spsCertificate = objectMapper.readValue(jsonString, SpsCertificate.class);

    //When
    List<CommodityComplement> commodityComplements = mapper.map(spsCertificate);

    //Then
    assertThat(commodityComplements).isEmpty();
  }
}
