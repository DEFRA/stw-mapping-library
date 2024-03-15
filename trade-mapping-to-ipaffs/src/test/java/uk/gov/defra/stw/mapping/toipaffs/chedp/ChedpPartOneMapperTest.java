package uk.gov.defra.stw.mapping.toipaffs.chedp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
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
import uk.gov.defra.stw.mapping.toipaffs.common.ArrivalDateMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.ArrivalTimeMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.DepartureDateMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.DepartureTimeMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.EconomicOperatorMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.MeansOfTransportFromEntryPointMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.MeansOfTransportMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.PointOfEntryMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.SealsContainersMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.TransportToBcpQuestionMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.VeterinaryInformationMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.ResourceUtils;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
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

@ExtendWith(MockitoExtension.class)
class ChedpPartOneMapperTest {

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
  private MeansOfTransportMapper meansOfTransportMapper;
  @Mock
  private ArrivalDateMapper arrivalDateMapper;
  @Mock
  private ArrivalTimeMapper arrivalTimeMapper;
  @Mock
  private DepartureDateMapper departureDateMapper;
  @Mock
  private DepartureTimeMapper departureTimeMapper;

  @InjectMocks
  private ChedpPartOneMapper chedpPartOneMapper;

  private SpsCertificate spsCertificate;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() throws JsonProcessingException, NotificationMapperException {
    objectMapper = TestUtils.initObjectMapper();

    spsCertificate = JsonDeserializer
        .get("chedp/chedp_ehc_complete.json", SpsCertificate.class);

    Purpose purpose = Purpose.builder()
        .conformsToEU(true)
        .thirdCountryTranshipment("FR")
        .finalBIP("BEBRU")
        .purposeGroup(PurposeGroupEnum.TRANSHIPMENT_TO)
        .build();

    MeansOfTransportBeforeBip meansOfTransportBeforeBip = JsonDeserializer.get(
        "common/transport/common_ipaffs_meansOfTransportFromEntryPoint_complete.json",
        MeansOfTransportBeforeBip.class
    );
    List<NotificationSealsContainers> notificationSealsContainers = JsonDeserializer.get(
        "common/sealscontainers/common_ipaffs_sealscontainers_complete.json",
        objectMapper.getTypeFactory().constructCollectionType(List.class, NotificationSealsContainers.class)
    );
    Commodities commodities = JsonDeserializer.get(
        "chedp/partone/commodities/chedp_ipaffs_commodities_complete.json", Commodities.class
    );
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
    when(meansOfTransportFromEntryPointMapper.map(spsCertificate)).thenReturn(meansOfTransportBeforeBip);

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

    when(arrivalDateMapper.map(spsCertificate)).thenReturn(LocalDate.parse("2020-01-01"));
    when(arrivalTimeMapper.map(spsCertificate)).thenReturn(LocalTime.parse("22:30:00"));
    when(departureDateMapper.map(spsCertificate)).thenReturn(LocalDate.parse("2020-06-15"));
    when(departureTimeMapper.map(spsCertificate)).thenReturn(LocalTime.parse("22:30:00"));
  }

  @Test
  void map_ReturnsPartOne_WhenCompleteSpsCertificate() throws Exception {
    VeterinaryInformation veterinaryInformation = JsonDeserializer.get(
        "common/veterinaryinformation/common_ipaffs_veterinaryinformation_complete.json",
        VeterinaryInformation.class);
    ApprovedEstablishment approvedEstablishment = JsonDeserializer.get(
        "chedp/partone/chedp_approved_establishments.json", ApprovedEstablishment.class);

    when(veterinaryInformationMapper.map(spsCertificate.getSpsExchangedDocument()))
        .thenReturn(veterinaryInformation);
    when(approvedEstablishmentMapper.map(spsCertificate.getSpsConsignment().getIncludedSpsConsignmentItem()))
        .thenReturn(List.of(approvedEstablishment));

    String expectedPartOne = ResourceUtils
        .readFileToString("classpath:chedp/partone/chedp_ipaffs_partone_complete.json");

    PartOne actualPartOne = chedpPartOneMapper.map(spsCertificate);

    assertThat(objectMapper.writeValueAsString(actualPartOne)).isEqualTo(expectedPartOne);
  }

  @Test
  void map_ReturnsPartOneWithoutApprovedEstablishment_WhenVeterinaryInformationIsNull() throws Exception {
    when(veterinaryInformationMapper.map(spsCertificate.getSpsExchangedDocument()))
        .thenReturn(null);

    String expectedPartOne = ResourceUtils
        .readFileToString("classpath:chedp/partone/chedp_ipaffs_without_approved_establishment.json");

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
