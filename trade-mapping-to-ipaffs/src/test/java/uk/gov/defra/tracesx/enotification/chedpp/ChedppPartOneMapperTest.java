package uk.gov.defra.tracesx.enotification.chedpp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.tracesx.common.chedpp.ChedppPurposeMapper;
import uk.gov.defra.tracesx.common.chedpp.ChedppVeterinaryInformationMapper;
import uk.gov.defra.tracesx.common.common.PointOfEntryMapper;
import uk.gov.defra.tracesx.common.common.SealsContainersMapper;
import uk.gov.defra.tracesx.common.common.TransportToBcpQuestionMapper;
import uk.gov.defra.tracesx.enotification.common.EconomicOperatorMapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperator;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransportBeforeBip;
import uk.gov.defra.tracesx.notificationschema.representation.NotificationSealsContainers;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.notificationschema.representation.VeterinaryInformation;
import uk.gov.defra.tracesx.testutils.JsonDeserializer;
import uk.gov.defra.tracesx.testutils.ResourceUtils;
import uk.gov.defra.tracesx.testutils.TestUtils;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;
import uk.gov.defra.tracesx.trade.dto.SpsConsignment;

@ExtendWith(MockitoExtension.class)
class ChedppPartOneMapperTest {

  @Mock
  private ChedppPurposeMapper chedppPurposeMapper;

  @Mock
  private EconomicOperatorMapper economicOperatorMapper;

  @Mock
  private ChedppMeansOfTransportFromEntryPointMapperImpl chedppMeansOfTransportFromEntryPointMapper;

  @Mock
  private ChedppMeansOfTransportMapperImpl chedppMeansOfTransportMapper;

  @Mock
  private PointOfEntryMapper pointOfEntryMapper;

  @Mock
  private ChedppVeterinaryInformationMapper chedppVeterinaryInformationMapper;

  @Mock
  private SealsContainersMapper chedppSealsContainersMapper;

  @Mock
  private ChedppCommoditiesMapper chedppCommoditiesMapper;

  @Mock
  private TransportToBcpQuestionMapper transportToBcpQuestionMapper;

  private ObjectMapper objectMapper;
  private ChedppPartOneMapper mapper;
  private SpsCertificate spsCertificate;

  @BeforeEach
  void setup() throws JsonProcessingException, NotificationMapperException {
    mapper = new ChedppPartOneMapper(chedppPurposeMapper, economicOperatorMapper,
        chedppMeansOfTransportFromEntryPointMapper, chedppMeansOfTransportMapper,
        pointOfEntryMapper, chedppVeterinaryInformationMapper, chedppSealsContainersMapper,
        chedppCommoditiesMapper, transportToBcpQuestionMapper);
    objectMapper = TestUtils.initObjectMapper();

    spsCertificate = JsonDeserializer
        .get(SpsCertificate.class, "enotification/chedpp/chedpp_ehc_complete.json", objectMapper);

    Purpose purpose = JsonDeserializer.get(Purpose.class,
        "common/chedpp/partone/purpose/chedpp_ipaffs_purpose_complete.json", objectMapper);
    MeansOfTransportBeforeBip meansOfTransportBeforeBip = JsonDeserializer.get(MeansOfTransportBeforeBip.class,
        "common/chedpp/partone/transport/chedpp_ipaffs_meansOfTransportFromEntryPoint_complete.json", objectMapper);
    VeterinaryInformation veterinaryInformation = JsonDeserializer.get(VeterinaryInformation.class,
        "common/chedpp/partone/veterinaryinformation/chedpp_ipaffs_veterinaryinformation_complete.json", objectMapper);
    List<NotificationSealsContainers> notificationSealsContainers = JsonDeserializer.get(
        objectMapper.getTypeFactory().constructCollectionType(List.class, NotificationSealsContainers.class),
        "common/chedpp/partone/sealscontainers/chedpp_ipaffs_sealscontainers_complete.json",
        objectMapper);
    Commodities commodities = JsonDeserializer.get(Commodities.class,
        "enotification/chedpp/partone/commodities/chedpp_ipaffs_commodities_complete.json", objectMapper);

    when(chedppPurposeMapper.map(spsCertificate.getSpsExchangedDocument()
        .getSignatorySpsAuthentication())).thenReturn(purpose);
    when(economicOperatorMapper.map(spsCertificate.getSpsConsignment()
        .getConsignorSpsParty())).thenAnswer(invocationOnMock -> createNewEconomicOperator());
    when(chedppMeansOfTransportFromEntryPointMapper.map(spsCertificate.getSpsConsignment()
        .getMainCarriageSpsTransportMovement())).thenReturn(meansOfTransportBeforeBip);
    when(pointOfEntryMapper.map(spsCertificate.getSpsConsignment()
        .getUnloadingBaseportSpsLocation())).thenReturn("GBFXT1PP");
    when(chedppVeterinaryInformationMapper.map(spsCertificate.getSpsExchangedDocument().getReferenceSpsReferencedDocument()))
        .thenReturn(veterinaryInformation);
    when(chedppSealsContainersMapper.map(spsCertificate.getSpsConsignment()
        .getUtilizedSpsTransportEquipment())).thenReturn(notificationSealsContainers);
    when(chedppCommoditiesMapper.map(spsCertificate)).thenReturn(commodities);
  }

  @Test
  void map_ReturnsPartOne_WhenCompleteEhcIncludedSpsClauses()
      throws NotificationMapperException, JsonProcessingException {
    when(economicOperatorMapper.setEconomicOperatorType(any(), any()))
        .thenReturn(createNewEconomicOperator());

    String expectedPartOne = ResourceUtils
        .readFileToString("classpath:enotification/chedpp/partone/chedpp_ipaffs_partone_complete.json");

    PartOne partOne = mapper.map(spsCertificate);
    String actualPartOne = objectMapper.writeValueAsString(partOne);

    assertThat(actualPartOne).isEqualTo(expectedPartOne);
  }

  @Test
  void map_ReturnsPartOneWithoutPlaceOfDestination_WhenDeliverySpsPartyIsNull() throws NotificationMapperException {
    spsCertificate.getSpsConsignment().setDeliverySpsParty(null);

    PartOne partOne = mapper.map(spsCertificate);

    assertThat(partOne.getPlaceOfDestination()).isNull();
  }

  private EconomicOperator createNewEconomicOperator() throws JsonProcessingException {
    return JsonDeserializer.get(EconomicOperator.class,
        "enotification/chedpp/partone/economicoperator/chedpp_ipaffs_economicoperator_complete.json", objectMapper);
  }

  @Test
  void map_ReturnsPartOneWithTransporterDetailsRequiredAsTrue_WhenBcpQuestionMapperReturnsTrue()
      throws NotificationMapperException {
    // Given
    when(transportToBcpQuestionMapper.map(any(SpsConsignment.class))).thenReturn(true);

    // When
    PartOne partOne = mapper.map(spsCertificate);

    // Then
    assertThat(partOne.getTransporterDetailsRequired()).isTrue();
  }

  @Test
  void map_ReturnsPartOneWithTransporterDetailsRequiredAsFalse_WhenBcpQuestionMapperReturnsFalse()
      throws NotificationMapperException {
    // Given
    when(transportToBcpQuestionMapper.map(any(SpsConsignment.class))).thenReturn(false);

    // When
    PartOne partOne = mapper.map(spsCertificate);

    // Then
    assertThat(partOne.getTransporterDetailsRequired()).isFalse();
  }

  @Test
  void map_ReturnsPartOneWithoutConsignee_WhenConsigneeSpsPartyIsNull()
      throws NotificationMapperException {
    // Given
    spsCertificate.getSpsConsignment().setConsigneeSpsParty(null);

    // When
    PartOne partOne = mapper.map(spsCertificate);

    // Then
    assertThat(partOne.getConsignee()).isNull();
  }

  @Test
  void map_ReturnsPartOneWithoutConsignee_WhenConsigneeSpsPartyIdIsNull()
      throws NotificationMapperException {
    // Given
    spsCertificate.getSpsConsignment().getConsignorSpsParty().setId(null);

    // When
    PartOne partOne = mapper.map(spsCertificate);

    // Then
    assertThat(partOne.getConsignee()).isNull();
  }

  @Test
  void map_ReturnsPartOneWithoutConsignee_WhenConsigneeSpsPartyIdValueIsNull()
      throws NotificationMapperException {
    // Given
    spsCertificate.getSpsConsignment().getConsignorSpsParty().getId().setValue(null);

    // When
    PartOne partOne = mapper.map(spsCertificate);

    // Then
    assertThat(partOne.getConsignee()).isNull();
  }
}
