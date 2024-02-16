package uk.gov.defra.stw.mapping.toipaffs.cloning.chedp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.EconomicOperatorMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.MeansOfTransportFromEntryPointMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.ResourceUtils;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperator;
import uk.gov.defra.tracesx.notificationschema.representation.ExternalReference;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransportBeforeBip;
import uk.gov.defra.tracesx.notificationschema.representation.NotificationSealsContainers;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.notificationschema.representation.VeterinaryInformation;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.ExternalSystem;

@ExtendWith(MockitoExtension.class)
class ChedpPartOneMapperImplTest {

  @Mock
  private ChedpPurposeMapper chedpPurposeMapper;
  @Mock
  private MeansOfTransportFromEntryPointMapper meansOfTransportFromEntryPointMapper;
  @Mock
  private ChedpVeterinaryInformationMapper veterinaryInformationMapper;
  @Mock
  private ChedpSealsContainersMapper chedpSealsContainersMapper;
  @Mock
  private ChedpCommoditiesMapper chedpCommoditiesMapper;
  @Mock
  private EconomicOperatorMapper economicOperatorMapper;
  @Mock
  private ChedpPartOneTransportToBcpMapper chedpPartOneTransportToBcpMapper;

  @InjectMocks
  private ChedpPartOneMapperImpl chedpPartOneMapper;

  private ObjectMapper objectMapper;
  private SpsCertificate spsCertificate;

  @BeforeEach
  void setup() throws JsonProcessingException, NotificationMapperException {
    objectMapper = TestUtils.initObjectMapper();

    spsCertificate = JsonDeserializer
        .get(SpsCertificate.class, "common/chedp/chedp_ehc_complete.json", objectMapper);

    Purpose purpose = JsonDeserializer.get(Purpose.class,
        "common/chedp/partone/purpose/chedp_ipaffs_purpose_complete.json", objectMapper);
    MeansOfTransportBeforeBip meansOfTransportBeforeBip = JsonDeserializer.get(MeansOfTransportBeforeBip.class,
        "common/common/transport/common_ipaffs_meansOfTransportFromEntryPoint_complete.json", objectMapper);
    VeterinaryInformation veterinaryInformation = JsonDeserializer.get(VeterinaryInformation.class,
        "common/common/veterinaryinformation/common_ipaffs_veterinaryinformation_complete.json", objectMapper);
    veterinaryInformation.getAccompanyingDocuments()
        .forEach(accompanyingDocument -> accompanyingDocument.setExternalReference(ExternalReference.builder()
            .system(ExternalSystem.ECERT)
            .reference("NZL2020/PPCS2/121580")
            .build()));
    List<NotificationSealsContainers> notificationSealsContainers = JsonDeserializer.get(
        objectMapper.getTypeFactory().constructCollectionType(List.class, NotificationSealsContainers.class),
        "cloning/chedp/partone/chedp_ipaffs_sealscontainers_complete.json",
        objectMapper);
    Commodities commodities = JsonDeserializer.get(Commodities.class,
        "common/chedp/partone/commodities/chedp_ipaffs_commodities_complete.json", objectMapper);

    when(chedpPurposeMapper.map(spsCertificate)).thenReturn(purpose);
    when(chedpPartOneTransportToBcpMapper.mapPointOfEntry(spsCertificate)).thenReturn("GBLHR1P");
    when(meansOfTransportFromEntryPointMapper.map(spsCertificate.getSpsConsignment()
        .getMainCarriageSpsTransportMovement())).thenReturn(meansOfTransportBeforeBip);
    when(veterinaryInformationMapper.map(spsCertificate))
        .thenReturn(veterinaryInformation);
    when(chedpSealsContainersMapper.map(spsCertificate.getSpsConsignment()))
        .thenReturn(notificationSealsContainers);
    when(chedpCommoditiesMapper.map(spsCertificate)).thenReturn(commodities);
    when(economicOperatorMapper.mapEconomicOperator(any(), eq(EconomicOperatorType.EXPORTER)))
        .thenReturn(createNewEconomicOperator(EconomicOperatorType.EXPORTER));
    when(economicOperatorMapper.mapEconomicOperator(any(), eq(EconomicOperatorType.CONSIGNEE)))
        .thenReturn(createNewEconomicOperator(EconomicOperatorType.CONSIGNEE));
    when(economicOperatorMapper.mapEconomicOperator(any(), eq(EconomicOperatorType.IMPORTER)))
        .thenReturn(createNewEconomicOperator(EconomicOperatorType.IMPORTER));
    when(economicOperatorMapper.mapEconomicOperator(any(), eq(EconomicOperatorType.PRIVATE_TRANSPORTER)))
        .thenReturn(createNewEconomicOperator(EconomicOperatorType.PRIVATE_TRANSPORTER));
    when(chedpPartOneTransportToBcpMapper.mapArrivalTime(spsCertificate))
        .thenReturn(LocalTime.parse("14:30:00"));
    when(chedpPartOneTransportToBcpMapper.mapArrivalDate(spsCertificate))
        .thenReturn(LocalDate.parse("2022-12-21"));
  }

  @Test
  void map_ReturnsPartOne_WhenCompleteSpsCertificate() throws Exception {
    String expectedPartOne = ResourceUtils
        .readFileToString("classpath:cloning/chedp/partone/chedp_ipaffs_partone_complete.json");

    PartOne actualPartOne = chedpPartOneMapper.map(spsCertificate);

    assertThat(objectMapper.writeValueAsString(actualPartOne)).isEqualTo(expectedPartOne);
  }

  private EconomicOperator createNewEconomicOperator(EconomicOperatorType type)
      throws JsonProcessingException {
    EconomicOperator economicOperator = JsonDeserializer.get(EconomicOperator.class,
        "common/common/economicoperator/common_ipaffs_complete.json", objectMapper);
    economicOperator.setType(type);
    return economicOperator;
  }
}
