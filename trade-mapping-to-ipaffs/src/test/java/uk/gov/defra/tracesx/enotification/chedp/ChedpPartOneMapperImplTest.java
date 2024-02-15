package uk.gov.defra.tracesx.enotification.chedp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.tracesx.common.chedp.ChedpMeansOfTransportMapper;
import uk.gov.defra.tracesx.common.common.MeansOfTransportFromEntryPointMapper;
import uk.gov.defra.tracesx.common.common.PointOfEntryMapper;
import uk.gov.defra.tracesx.common.common.SealsContainersMapper;
import uk.gov.defra.tracesx.common.common.TransportToBcpQuestionMapper;
import uk.gov.defra.tracesx.common.common.VeterinaryInformationMapper;
import uk.gov.defra.tracesx.enotification.common.EconomicOperatorMapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ApprovedEstablishment;
import uk.gov.defra.tracesx.notificationschema.representation.CatchCertificate;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperator;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransportBeforeBip;
import uk.gov.defra.tracesx.notificationschema.representation.NotificationSealsContainers;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.notificationschema.representation.VeterinaryInformation;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum;
import uk.gov.defra.tracesx.testutils.JsonDeserializer;
import uk.gov.defra.tracesx.testutils.ResourceUtils;
import uk.gov.defra.tracesx.testutils.TestUtils;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

@ExtendWith(MockitoExtension.class)
class ChedpPartOneMapperImplTest {

  @Mock
  private ChedpPurposeMapper chedpPurposeMapper;
  @Mock
  private PointOfEntryMapper chedpPointOfEntryMapper;
  @Mock
  private MeansOfTransportFromEntryPointMapper meansOfTransportFromEntryPointMapper;
  @Mock
  private VeterinaryInformationMapper veterinaryInformationMapper;
  @Mock
  private SealsContainersMapper chedpSealsContainersMapper;
  @Mock
  private ChedpCommoditiesMapper chedpCommoditiesMapper;
  @Mock
  private EconomicOperatorMapper economicOperatorMapper;
  @Mock
  private ApprovedEstablishmentMapper approvedEstablishmentMapper;
  @Mock
  private TransportToBcpQuestionMapper transportToBcpQuestionMapper;
  @Mock
  private ChedpMeansOfTransportMapper chedpMeansOfTransportMapper;

  @InjectMocks
  private ChedpPartOneMapperImpl chedpPartOneMapper;

  private SpsCertificate spsCertificate;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() throws JsonProcessingException, NotificationMapperException {
    objectMapper = TestUtils.initObjectMapper();

    spsCertificate = JsonDeserializer
        .get(SpsCertificate.class, "common/chedp/chedp_ehc_complete.json", objectMapper);

    Purpose purpose = Purpose.builder()
        .conformsToEU(true)
        .thirdCountryTranshipment("FR")
        .finalBIP("BEBRU")
        .purposeGroup(PurposeGroupEnum.TRANSHIPMENT_TO)
        .build();

    MeansOfTransportBeforeBip meansOfTransportBeforeBip = JsonDeserializer.get(MeansOfTransportBeforeBip.class,
        "common/common/transport/common_ipaffs_meansOfTransportFromEntryPoint_complete.json", objectMapper);
    List<NotificationSealsContainers> notificationSealsContainers = JsonDeserializer.get(
        objectMapper.getTypeFactory().constructCollectionType(List.class, NotificationSealsContainers.class),
        "common/common/sealscontainers/common_ipaffs_sealscontainers_complete.json",
        objectMapper);
    Commodities commodities = JsonDeserializer.get(Commodities.class,
        "common/chedp/partone/commodities/chedp_ipaffs_commodities_complete.json", objectMapper);
    commodities.getCommodityComplement().forEach(commodityComplement -> {
      commodityComplement.setSpeciesTypeName("Farmed game  ");
      commodityComplement.setSpeciesClassName("Artiodactyla");
      commodityComplement.setSpeciesFamilyName("Camelidae");
    });
    commodities.getComplementParameterSet().get(0).setCatchCertificates(List.of(
        new CatchCertificate("12345", new BigDecimal(100))));

    when(chedpPurposeMapper.map(spsCertificate)).thenReturn(purpose);
    when(chedpPointOfEntryMapper.map(spsCertificate.getSpsConsignment()
        .getUnloadingBaseportSpsLocation())).thenReturn("GBLHR1P");
    when(meansOfTransportFromEntryPointMapper.map(spsCertificate.getSpsConsignment()
        .getMainCarriageSpsTransportMovement())).thenReturn(meansOfTransportBeforeBip);

    when(chedpSealsContainersMapper.map(spsCertificate.getSpsConsignment()
        .getUtilizedSpsTransportEquipment())).thenReturn(notificationSealsContainers);
    when(chedpCommoditiesMapper.map(spsCertificate)).thenReturn(commodities);

    when(economicOperatorMapper.map(any())).thenReturn(new EconomicOperator());
    when(economicOperatorMapper.setEconomicOperatorType(any(), eq(EconomicOperatorType.EXPORTER)))
        .thenReturn(createNewEconomicOperator(EconomicOperatorType.EXPORTER));
    when(economicOperatorMapper.setEconomicOperatorType(any(), eq(EconomicOperatorType.CONSIGNEE)))
        .thenReturn(createNewEconomicOperator(EconomicOperatorType.CONSIGNEE));
    when(economicOperatorMapper.setEconomicOperatorType(any(), eq(EconomicOperatorType.IMPORTER)))
        .thenReturn(createNewEconomicOperator(EconomicOperatorType.IMPORTER));
    when(economicOperatorMapper.setEconomicOperatorType(any(), eq(EconomicOperatorType.DESTINATION)))
        .thenReturn(createNewEconomicOperator(EconomicOperatorType.DESTINATION));
    when(economicOperatorMapper.setEconomicOperatorType(any(), eq(EconomicOperatorType.PRIVATE_TRANSPORTER)))
        .thenReturn(createNewEconomicOperator(EconomicOperatorType.PRIVATE_TRANSPORTER));

    when(transportToBcpQuestionMapper.map(spsCertificate.getSpsConsignment())).thenReturn(true);


  }

  @Test
  void map_ReturnsPartOne_WhenCompleteSpsCertificate() throws Exception {
    VeterinaryInformation veterinaryInformation = JsonDeserializer.get(VeterinaryInformation.class,
        "common/common/veterinaryinformation/common_ipaffs_veterinaryinformation_complete.json", objectMapper);
    ApprovedEstablishment approvedEstablishment = JsonDeserializer.get(ApprovedEstablishment.class,
        "enotification/chedp/partone/chedp_approved_establishments.json", objectMapper);

    when(veterinaryInformationMapper.map(spsCertificate.getSpsExchangedDocument()))
        .thenReturn(veterinaryInformation);
    when(approvedEstablishmentMapper.map(spsCertificate.getSpsConsignment().getIncludedSpsConsignmentItem()))
        .thenReturn(List.of(approvedEstablishment));

    String expectedPartOne = ResourceUtils
        .readFileToString("classpath:enotification/chedp/partone/chedp_ipaffs_partone_complete.json");

    PartOne actualPartOne = chedpPartOneMapper.map(spsCertificate);

    assertThat(objectMapper.writeValueAsString(actualPartOne)).isEqualTo(expectedPartOne);
  }

  @Test
  void map_ReturnsPartOneWithoutApprovedEstablishment_WhenVeterinaryInformationIsNull() throws Exception {
    when(veterinaryInformationMapper.map(spsCertificate.getSpsExchangedDocument()))
        .thenReturn(null);

    String expectedPartOne = ResourceUtils
        .readFileToString("classpath:enotification/chedp/partone/chedp_ipaffs_without_approved_establishment.json");

    PartOne actualPartOne = chedpPartOneMapper.map(spsCertificate);

    assertThat(objectMapper.writeValueAsString(actualPartOne)).isEqualTo(expectedPartOne);
  }

  private EconomicOperator createNewEconomicOperator(EconomicOperatorType type) {
    return EconomicOperator.builder()
        .id("6fcfe3ec-a316-43db-a516-ce34566e96ad")
        .type(type)
        .build();
  }
}
