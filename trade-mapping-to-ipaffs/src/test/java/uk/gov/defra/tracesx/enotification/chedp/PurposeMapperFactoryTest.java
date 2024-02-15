package uk.gov.defra.tracesx.enotification.chedp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.defra.tracesx.enumeration.DestinationType.FREE_ZONE;
import static uk.gov.defra.tracesx.testutils.TestConstants.CUSTOMS_WAREHOUSE;
import static uk.gov.defra.tracesx.testutils.TestConstants.DIRECT_TRANSIT;
import static uk.gov.defra.tracesx.testutils.TestConstants.GOODS_CERTIFIED_AS;
import static uk.gov.defra.tracesx.testutils.TestConstants.HUMAN_CONSUMPTION;
import static uk.gov.defra.tracesx.testutils.TestConstants.INTERNAL_MARKET;
import static uk.gov.defra.tracesx.testutils.TestConstants.INVALID;
import static uk.gov.defra.tracesx.testutils.TestConstants.NON_CONFORMING_GOODS;
import static uk.gov.defra.tracesx.testutils.TestConstants.NON_CONFORMING_GOODS_DESTINATION_TYPE;
import static uk.gov.defra.tracesx.testutils.TestConstants.RE_ENTRY;
import static uk.gov.defra.tracesx.testutils.TestConstants.SHIP;
import static uk.gov.defra.tracesx.testutils.TestConstants.TRANSHIPMENT;
import static uk.gov.defra.tracesx.testutils.TestHelper.buildSpsAuthenticationTypeForPurpose;
import static uk.gov.defra.tracesx.testutils.TestHelper.buildSpsNoteType;

import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.tracesx.trade.dto.SpsExchangedDocument;

@ExtendWith(MockitoExtension.class)
class PurposeMapperFactoryTest {

  private SpsExchangedDocument spsExchangedDocument;

  @Mock
  private TranshipmentPurposeMapper transhipmentPurposeMapper;

  @Mock
  private FreeZonePurposeMapper freeZonePurposeMapper;

  @Mock
  private CustomsWarehousePurposeMapper customsWarehousePurposeMapper;

  @Mock
  private ReEntryPurposeMapper reEntryPurposeMapper;

  @Mock
  private ShipPurposeMapper shipPurposeMapper;

  @Mock
  private TransitPurposeMapper transitPurposeMapper;

  @Mock
  private InternalMarketPurposeMapper internalMarketPurposeMapper;

  @InjectMocks
  private PurposeMapperFactory purposeMapperFactory;

  @BeforeEach
  void setup() {
    spsExchangedDocument = new SpsExchangedDocument();
  }

  @Test
  void get_ReturnsTranshipmentPurposeMapper_WhenPurposeIsTranshipment() {
    // Given
    var spsAuthenticationType = buildSpsAuthenticationTypeForPurpose(TRANSHIPMENT);
    spsExchangedDocument.getSignatorySpsAuthentication().add(spsAuthenticationType);

    // When
    Optional<PurposeMapper> purposeMapper = purposeMapperFactory.get(spsExchangedDocument);

    // Then
    assertThat(purposeMapper).containsSame(transhipmentPurposeMapper);
  }

  @Test
  void get_ReturnsReEntryPurposeMapper_WhenPurposeIsReEntry() {
    // Given
    var spsAuthenticationType = buildSpsAuthenticationTypeForPurpose(RE_ENTRY);
    spsExchangedDocument.getSignatorySpsAuthentication().add(spsAuthenticationType);

    // When
    Optional<PurposeMapper> purposeMapper = purposeMapperFactory.get(spsExchangedDocument);

    // Then
    assertThat(purposeMapper).containsSame(reEntryPurposeMapper);
  }

  @Test
  void get_ReturnsTransitPurposeMapper_WhenPurposeIsTransit() {
    // Given
    var spsAuthenticationType = buildSpsAuthenticationTypeForPurpose(DIRECT_TRANSIT);
    spsExchangedDocument.getSignatorySpsAuthentication().add(spsAuthenticationType);

    // When
    Optional<PurposeMapper> purposeMapper = purposeMapperFactory.get(spsExchangedDocument);

    // Then
    assertThat(purposeMapper).containsSame(transitPurposeMapper);
  }

  @Test
  void get_ReturnsEmptyOptional_WhenPurposeIsInvalid() {
    // Given
    var spsAuthenticationType = buildSpsAuthenticationTypeForPurpose(INVALID);
    spsExchangedDocument.getSignatorySpsAuthentication().add(spsAuthenticationType);

    // When
    Optional<PurposeMapper> purposeMapper = purposeMapperFactory.get(spsExchangedDocument);

    // Then
    assertThat(purposeMapper).isEmpty();
  }

  @Test
  void get_ReturnsCustomsWarehousePurposeMapper_WhenPurposeIsNonConformingGoodsAndDestinationTypeIsCustomsWarehouse() {
    // Given
    var spsAuthenticationType = buildSpsAuthenticationTypeForPurpose(NON_CONFORMING_GOODS);
    var destinationType = buildSpsNoteType(CUSTOMS_WAREHOUSE, NON_CONFORMING_GOODS_DESTINATION_TYPE);
    spsExchangedDocument.getSignatorySpsAuthentication().add(spsAuthenticationType);
    spsExchangedDocument.getIncludedSpsNote().add(destinationType);

    // When
    Optional<PurposeMapper> purposeMapper = purposeMapperFactory.get(spsExchangedDocument);

    // Then
    assertThat(purposeMapper).containsSame(customsWarehousePurposeMapper);
  }

  @Test
  void get_ReturnsShipPurposeMapper_WhenPurposeIsNonConformingGoodsAndDestinationTypeIsShi() {
    // Given
    var spsAuthenticationType = buildSpsAuthenticationTypeForPurpose(NON_CONFORMING_GOODS);
    var destinationType = buildSpsNoteType(SHIP, NON_CONFORMING_GOODS_DESTINATION_TYPE);
    spsExchangedDocument.getSignatorySpsAuthentication().add(spsAuthenticationType);
    spsExchangedDocument.getIncludedSpsNote().add(destinationType);

    // When
    Optional<PurposeMapper> purposeMapper = purposeMapperFactory.get(spsExchangedDocument);

    // Then
    assertThat(purposeMapper).containsSame(shipPurposeMapper);
  }

  @Test
  void get_ReturnsEmptyOptional_WhenPurposeIsNonConformingGoodsAndDestinationTypeIsInvalid() {
    // Given
    var spsAuthenticationType = buildSpsAuthenticationTypeForPurpose(NON_CONFORMING_GOODS);
    var destinationType = buildSpsNoteType(INVALID, NON_CONFORMING_GOODS_DESTINATION_TYPE);
    spsExchangedDocument.getSignatorySpsAuthentication().add(spsAuthenticationType);
    spsExchangedDocument.getIncludedSpsNote().add(destinationType);

    // When
    Optional<PurposeMapper> purposeMapper = purposeMapperFactory.get(spsExchangedDocument);

    // Then
    assertThat(purposeMapper).isEmpty();
  }

  @Test
  void get_ReturnsEmptyOptional_WhenPurposeContentIsEmpty() {
    // Given
    var spsAuthenticationType = buildSpsAuthenticationTypeForPurpose(NON_CONFORMING_GOODS);
    spsAuthenticationType.getIncludedSpsClause().get(0).setContent(Collections.emptyList());
    spsExchangedDocument.getSignatorySpsAuthentication().add(spsAuthenticationType);

    // When
    Optional<PurposeMapper> purposeMapper = purposeMapperFactory.get(spsExchangedDocument);

    // Then
    assertThat(purposeMapper).isEmpty();
  }

  @Test
  void get_ReturnsFreeZonePurposeMapper_WhenPurposeIsNonConformingGoodsAndDestinationTypeIsFreeZone() {
    // Given
    var spsAuthenticationType = buildSpsAuthenticationTypeForPurpose(NON_CONFORMING_GOODS);
    var destinationType = buildSpsNoteType(FREE_ZONE.name(), NON_CONFORMING_GOODS_DESTINATION_TYPE);
    spsExchangedDocument.getSignatorySpsAuthentication().add(spsAuthenticationType);
    spsExchangedDocument.getIncludedSpsNote().add(destinationType);

    // When
    Optional<PurposeMapper> purposeMapper = purposeMapperFactory.get(spsExchangedDocument);

    // Then
    assertTrue(purposeMapper.isPresent());
    assertThat(purposeMapper).containsSame(freeZonePurposeMapper);
  }

  @Test
  void get_ReturnsInternalMarketPurposeMapper_WhenPurposeIsInternalMarket() {
    // Given
    var spsAuthenticationType = buildSpsAuthenticationTypeForPurpose(INTERNAL_MARKET);
    var goodsCertifiedAs = buildSpsNoteType(HUMAN_CONSUMPTION, GOODS_CERTIFIED_AS);
    spsExchangedDocument.getSignatorySpsAuthentication().add(spsAuthenticationType);
    spsExchangedDocument.getIncludedSpsNote().add(goodsCertifiedAs);

    // When
    Optional<PurposeMapper> purposeMapper = purposeMapperFactory.get(spsExchangedDocument);

    // Then
    assertTrue(purposeMapper.isPresent());
    assertThat(purposeMapper).containsSame(internalMarketPurposeMapper);
  }
}
