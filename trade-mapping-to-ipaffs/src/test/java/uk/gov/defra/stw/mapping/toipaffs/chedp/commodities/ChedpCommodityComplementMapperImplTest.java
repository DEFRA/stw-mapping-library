package uk.gov.defra.stw.mapping.toipaffs.chedp.commodities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.ApplicableSpsClassification;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.CommodityComplementFieldMapper;
import uk.gov.defra.stw.mapping.toipaffs.testutils.ResourceUtils;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

@ExtendWith(MockitoExtension.class)
class ChedpCommodityComplementMapperImplTest {

  @Mock
  private CommodityComplementFieldMapper helper;

  @InjectMocks
  private ChedpCommodityComplementMapper mapper;

  private SpsCertificate spsCertificate;
  private List<ApplicableSpsClassification> applicableSpsClassification;

  private static final String fileName = "/chedp/chedp_ehc_complete.json";
  private static final String fileNameSps = "chedp/partone/commodities/chedp_applicable_sps_classification.json";

  @BeforeEach
  void setup() throws JsonProcessingException {
    ObjectMapper objectMapper = TestUtils.initObjectMapper();
    String jsonString = ResourceUtils.readFileToString("classpath:" + fileName);
    spsCertificate = objectMapper.readValue(jsonString, SpsCertificate.class);
    String jsonStringSps = ResourceUtils.readFileToString("classpath:" + fileNameSps);
    applicableSpsClassification = objectMapper.readValue(
        jsonStringSps, new TypeReference<>() {
        });
  }

  @Test
  void map_ReturnsChedpNotification_WhenCompleteEhcSpsCertificate() throws Exception {
    ObjectMapper objectMapper = TestUtils.initObjectMapper();
    // Given
    String expectedNotification =
        ResourceUtils.readFileToString("classpath:/chedp/chedp_ipaffs_complete.json");
    when(helper.mapCommodityIdFromCnCode(applicableSpsClassification)).thenReturn("07099990");
    when(helper.mapDescription(applicableSpsClassification)).thenReturn("Other");
    when(helper.mapSpeciesTypeName(applicableSpsClassification)).thenReturn("Farmed game  ");
    when(helper.mapSpeciesFamilyName(applicableSpsClassification)).thenReturn("Camelidae");
    when(helper.mapSpeciesClassName(applicableSpsClassification)).thenReturn("Artiodactyla");
    Notification notification = objectMapper.readValue(expectedNotification, Notification.class);

    // When
    List<CommodityComplement> commodityComplements = mapper.map(spsCertificate);

    // Then
    assertThat(commodityComplements).isNotNull();
    assertThat(commodityComplements.get(0).getCommodityID()).isEqualTo(
        notification.getPartOne().getCommodities().getCommodityComplement().get(0).getCommodityID());
    assertThat(commodityComplements.get(0).getCommodityDescription()).isEqualTo(
        notification.getPartOne().getCommodities().getCommodityComplement().get(0).getCommodityDescription());
    assertThat(commodityComplements.get(0).getSpeciesTypeName()).isEqualTo(
        notification.getPartOne().getCommodities().getCommodityComplement().get(0).getSpeciesTypeName());
    assertThat(commodityComplements.get(0).getSpeciesClassName()).isEqualTo(
        notification.getPartOne().getCommodities().getCommodityComplement().get(0).getSpeciesClassName());
    assertThat(commodityComplements.get(0).getSpeciesFamilyName()).isEqualTo(
        notification.getPartOne().getCommodities().getCommodityComplement().get(0).getSpeciesFamilyName());
  }

  @Test
  void map_ReturnsEmpty_WhenSequenceNumberIsZero() throws Exception {
    ObjectMapper objectMapper = TestUtils.initObjectMapper();
    // Given
    String fileName = "/chedp/chedp_ehc_complete_zero_sequence.json";
    String jsonString = ResourceUtils.readFileToString("classpath:" + fileName);
    spsCertificate = objectMapper.readValue(jsonString, SpsCertificate.class);
    // When
    List<CommodityComplement> commodityComplements = mapper.map(spsCertificate);

    // Then
    assertThat(commodityComplements).isEmpty();
  }
}
