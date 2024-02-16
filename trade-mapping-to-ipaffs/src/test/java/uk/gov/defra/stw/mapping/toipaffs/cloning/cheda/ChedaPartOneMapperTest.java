package uk.gov.defra.stw.mapping.toipaffs.cloning.cheda;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.ForImportOrAdmissionEnum.DEFINITIVE_IMPORT;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.RE_IMPORT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
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
import uk.gov.defra.stw.mapping.toipaffs.common.common.SealsContainersMapper;
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
import uk.gov.defra.tracesx.notificationschema.representation.Route;
import uk.gov.defra.tracesx.notificationschema.representation.VeterinaryInformation;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.ExternalSystem;

@ExtendWith(MockitoExtension.class)
class ChedaPartOneMapperTest {

  private final ObjectMapper objectMapper = TestUtils.initObjectMapper();
  private SpsCertificate spsCertificate;

  @Mock
  private ChedaPartOneTransportToBcpMapper chedaPartOneTransportToBcpMapper;
  @Mock
  private MeansOfTransportFromEntryPointMapper meansOfTransportFromEntryPointMapper;
  @Mock
  private ChedaVeterinaryInformationMapper veterinaryInformationMapper;
  @Mock
  private SealsContainersMapper sealsContainersMapper;
  @Mock
  private ChedaCommoditiesMapper chedaCommoditiesMapper;
  @Mock
  private EconomicOperatorMapper economicOperatorMapper;
  @Mock
  ChedaPurposeMapper chedaPurposeMapper;
  @Mock
  ChedaRouteMapper chedaRouteMapper;

  @InjectMocks
  private ChedaPartOneMapper chedaPartOneMapper;

  @BeforeEach
  void setup() throws JsonProcessingException, NotificationMapperException {
    String jsonString =
        ResourceUtils.readFileToString("classpath:cloning/cheda/cheda_ehc_complete.json");
    spsCertificate = objectMapper.readValue(jsonString, SpsCertificate.class);

    MeansOfTransportBeforeBip meansOfTransportBeforeBip = JsonDeserializer.get(MeansOfTransportBeforeBip.class,
        "common/common/transport/common_ipaffs_meansOfTransportFromEntryPoint_complete.json", objectMapper);
    VeterinaryInformation veterinaryInformation = JsonDeserializer.get(VeterinaryInformation.class,
        "common/common/veterinaryinformation/common_ipaffs_veterinaryinformation_complete.json", objectMapper);
    veterinaryInformation.getAccompanyingDocuments()
        .forEach(accompanyingDocument -> accompanyingDocument.setExternalReference(ExternalReference.builder()
            .system(ExternalSystem.ECERT)
            .reference("reference")
            .build()));
    List<NotificationSealsContainers> notificationSealsContainers = JsonDeserializer.get(
        objectMapper.getTypeFactory().constructCollectionType(List.class, NotificationSealsContainers.class),
        "common/common/sealscontainers/common_ipaffs_sealscontainers_complete.json",
        objectMapper);
    Commodities commodities = JsonDeserializer.get(Commodities.class,
        "cloning/cheda/partone/commodities/cheda_ipaffs_commodities.json", objectMapper);

    when(chedaPartOneTransportToBcpMapper.mapPointOfEntry(spsCertificate)).thenReturn("GBLHR1P");
    when(meansOfTransportFromEntryPointMapper.map(spsCertificate.getSpsConsignment()
        .getMainCarriageSpsTransportMovement())).thenReturn(meansOfTransportBeforeBip);
    when(veterinaryInformationMapper.map(spsCertificate))
        .thenReturn(veterinaryInformation);
    when(sealsContainersMapper.map(spsCertificate.getSpsConsignment()
        .getUtilizedSpsTransportEquipment())).thenReturn(notificationSealsContainers);
    when(chedaCommoditiesMapper.map(spsCertificate)).thenReturn(commodities);
    when(economicOperatorMapper.mapEconomicOperator(any(), eq(EconomicOperatorType.EXPORTER)))
        .thenReturn(createNewEconomicOperator(EconomicOperatorType.EXPORTER));
    when(economicOperatorMapper.mapEconomicOperator(any(), eq(EconomicOperatorType.CONSIGNEE)))
        .thenReturn(createNewEconomicOperator(EconomicOperatorType.CONSIGNEE));
    when(economicOperatorMapper.mapEconomicOperator(any(), eq(EconomicOperatorType.IMPORTER)))
        .thenReturn(createNewEconomicOperator(EconomicOperatorType.IMPORTER));
    when(economicOperatorMapper.mapEconomicOperator(any(), eq(EconomicOperatorType.PRIVATE_TRANSPORTER)))
        .thenReturn(createNewEconomicOperator(EconomicOperatorType.PRIVATE_TRANSPORTER));
    when(chedaPartOneTransportToBcpMapper.mapArrivalDate(spsCertificate))
        .thenReturn(LocalDate.parse("2022-12-21"));
    when(chedaRouteMapper.map(spsCertificate))
        .thenReturn(Route.builder().transitingStates(List.of("JO", "PL")).build());
    when(chedaPurposeMapper.map(spsCertificate))
        .thenReturn(Purpose.builder().purposeGroup(RE_IMPORT)
            .forImportOrAdmission(DEFINITIVE_IMPORT).build());
  }

  @Test
  void map_ReturnsPartOne_WhenCompleteSpsCertificate() throws Exception {
    String expectedPartOne = ResourceUtils
        .readFileToString("classpath:cloning/cheda/partone/cheda_ipaffs_partone_complete.json");

    PartOne actualPartOne = chedaPartOneMapper.map(spsCertificate);

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
