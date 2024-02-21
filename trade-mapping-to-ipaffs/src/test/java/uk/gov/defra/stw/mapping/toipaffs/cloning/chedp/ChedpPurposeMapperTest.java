package uk.gov.defra.stw.mapping.toipaffs.cloning.chedp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.SpsAuthenticationType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.toipaffs.common.common.PurposeMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.ResourceUtils;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum;

class ChedpPurposeMapperTest {

  private static final String ANIMAL_FEEDING_STUFF = "ANIMAL_FEEDINGSTUFF";
  private static final String HUMAN_CONSUMPTION = "HUMAN_CONSUMPTION";
  private static final String PHARMACEUTICAL_USE = "PHARMACEUTICAL_USE";
  private static final String TECHNICAL_USE = "TECHNICAL_USE";
  private static final String OTHER = "OTHER";

  private final PurposeMapper purposeMapper = mock(PurposeMapper.class);

  private ChedpPurposeMapper mapper;
  private ObjectMapper objectMapper;
  private SpsCertificate spsCertificate;
  private List <SpsAuthenticationType> spsAuthenticationTypes;

  @BeforeEach
  void setup() throws JsonProcessingException, NotificationMapperException {
    mapper = new ChedpPurposeMapper(purposeMapper);
    objectMapper = TestUtils.initObjectMapper();

    spsCertificate = JsonDeserializer
        .get(SpsCertificate.class, "common/chedp/chedp_ehc_complete.json", objectMapper);
    spsAuthenticationTypes = spsCertificate.getSpsExchangedDocument()
        .getSignatorySpsAuthentication();
    when(purposeMapper.map(spsAuthenticationTypes.get(0))).thenReturn(
        Purpose.builder()
            .purposeGroup(PurposeGroupEnum.IMPORT)
            .build());
  }

  @Test
  void map_ReturnsPurpose_WhenCompleteEhcIncludedSpsClauses()
      throws NotificationMapperException, JsonProcessingException {
    String expectedPurpose = ResourceUtils
        .readFileToString("classpath:common/chedp/partone/purpose/chedp_ipaffs_purpose_complete.json");

    Purpose purpose = mapper.map(spsCertificate);
    String actualPurpose = objectMapper.writeValueAsString(purpose);

    assertThat(actualPurpose).isEqualTo(expectedPurpose);
  }

  @Test
  void map_ReturnsPurpose_WhenGoodsCertifiedAsAnimalFeedingStuff() throws NotificationMapperException {
    spsAuthenticationTypes.get(0).getIncludedSpsClause().get(1)
        .setContent(Collections.singletonList(new TextType().withValue(ANIMAL_FEEDING_STUFF)));

    Purpose actualPurpose = mapper.map(spsCertificate);

    assertThat(actualPurpose.getInternalMarketPurpose())
        .isEqualTo(InternalMarketPurpose.ANIMAL_FEEDING_STUFF);
  }

  @Test
  void map_ReturnsPurpose_WhenGoodsCertifiedAsHumanConsumption() throws NotificationMapperException {
    spsAuthenticationTypes.get(0).getIncludedSpsClause().get(1)
        .setContent(Collections.singletonList(new TextType().withValue(HUMAN_CONSUMPTION)));

    Purpose actualPurpose = mapper.map(spsCertificate);

    assertThat(actualPurpose.getInternalMarketPurpose())
        .isEqualTo(InternalMarketPurpose.HUMAN_CONSUMPTION);
  }

  @Test
  void map_ReturnsPurpose_WhenGoodsCertifiedAsPharmaceuticalUse() throws NotificationMapperException {
    spsAuthenticationTypes.get(0).getIncludedSpsClause().get(1)
        .setContent(Collections.singletonList(new TextType().withValue(PHARMACEUTICAL_USE)));

    Purpose actualPurpose = mapper.map(spsCertificate);

    assertThat(actualPurpose.getInternalMarketPurpose())
        .isEqualTo(InternalMarketPurpose.PHARMACEUTICAL_USE);
  }

  @Test
  void map_ReturnsPurpose_WhenGoodsCertifiedAsTechnicalUse() throws NotificationMapperException {
    spsAuthenticationTypes.get(0).getIncludedSpsClause().get(1)
        .setContent(Collections.singletonList(new TextType().withValue(TECHNICAL_USE)));

    Purpose actualPurpose = mapper.map(spsCertificate);

    assertThat(actualPurpose.getInternalMarketPurpose())
        .isEqualTo(InternalMarketPurpose.TECHNICAL_USE);
  }

  @Test
  void map_ReturnsPurpose_WhenGoodsCertifiedAsOther()
      throws NotificationMapperException {
    spsAuthenticationTypes.get(0).getIncludedSpsClause().get(1)
        .setContent(Collections.singletonList(new TextType().withValue(OTHER)));
    Purpose actualPurpose = mapper.map(spsCertificate);

    assertThat(actualPurpose.getInternalMarketPurpose())
        .isEqualTo(InternalMarketPurpose.OTHER);
  }

  @Test
  void map_ReturnsPurposeSetToOther_WhenNotInGoodsCertifiedList()
      throws NotificationMapperException {
    Purpose expected = Purpose.builder()
        .purposeGroup(PurposeGroupEnum.IMPORT)
        .internalMarketPurpose(InternalMarketPurpose.OTHER)
        .conformsToEU(true)
        .build();

    List<TextType> incorrectPurpose = Collections.singletonList(
        new TextType().withValue("INCORRECT_PURPOSE"));

    spsCertificate.getSpsExchangedDocument().getSignatorySpsAuthentication().get(0)
        .getIncludedSpsClause().get(1).setContent(incorrectPurpose);

    Purpose purpose = mapper.map(spsCertificate);

    assertThat(purpose).isEqualTo(expected);
  }

  @Test
  void map_ThrowsMapperException_EmptyPurposeClause() throws NotificationMapperException {
    spsAuthenticationTypes.get(0).getIncludedSpsClause().get(0).setId(null);
    when(purposeMapper.map(spsAuthenticationTypes.get(0))).thenReturn(null);

    assertThrows(NotificationMapperException.class, () -> mapper.map(spsCertificate));
  }

  @Test
  void map_ThrowsMapperException_NullGoodsAsCertifiedClauseId() {
    spsAuthenticationTypes.get(0).getIncludedSpsClause().get(1).setId(null);

    assertThrows(NotificationMapperException.class, () -> mapper.map(spsCertificate));
  }

  @Test
  void map_ThrowsMapperException_EmptyGoodsAsCertifiedContent() {
    spsAuthenticationTypes.get(0).getIncludedSpsClause().get(1).setContent(Collections.emptyList());

    assertThrows(NotificationMapperException.class, () -> mapper.map(spsCertificate));
  }

  @Test
  void map_ThrowsMapperException_NullGoodsAsCertifiedContentValue() {
    spsAuthenticationTypes.get(0).getIncludedSpsClause().get(1).getContent().get(0).setValue(null);

    assertThrows(NotificationMapperException.class, () -> mapper.map(spsCertificate));
  }
}
