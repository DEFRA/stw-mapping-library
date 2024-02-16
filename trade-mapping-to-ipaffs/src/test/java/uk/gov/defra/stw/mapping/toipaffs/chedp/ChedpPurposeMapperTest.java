package uk.gov.defra.stw.mapping.toipaffs.chedp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static uk.gov.defra.stw.mapping.toipaffs.testutils.TestConstants.CONFORMS_TO_EU;
import static uk.gov.defra.stw.mapping.toipaffs.testutils.TestConstants.FALSE;
import static uk.gov.defra.stw.mapping.toipaffs.testutils.TestConstants.TRUE;
import static uk.gov.defra.stw.mapping.toipaffs.testutils.TestHelper.buildSpsNoteType;

import java.util.Optional;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum;

@ExtendWith(MockitoExtension.class)
class ChedpPurposeMapperTest {

  private static final String BEBRU = "BEBRU";
  private static final String FR = "FR";

  private SpsCertificate spsCertificate;

  @Mock
  private PurposeMapperFactory purposeMapperFactory;

  @Mock
  private TranshipmentPurposeMapper transhipmentPurposeMapper;

  @InjectMocks
  private ChedpPurposeMapper mapper;

  @BeforeEach
  void setup() {
    spsCertificate = new SpsCertificate().withSpsExchangedDocument(new SpsExchangedDocument());
  }

  @Test
  void map_ReturnsTranshipmentPurposeAndConformsToEu_WhenCalledWithSpsCertificate()
      throws NotificationMapperException {
    // Given
    SpsNoteType euConformitySpsNote = buildSpsNoteType(TRUE, CONFORMS_TO_EU);
    spsCertificate.getSpsExchangedDocument().getIncludedSpsNote().add(euConformitySpsNote);
    when(transhipmentPurposeMapper.map(any(SpsCertificate.class)))
        .thenReturn(
            Purpose.builder()
                .finalBIP(BEBRU)
                .thirdCountryTranshipment(FR)
                .purposeGroup(PurposeGroupEnum.TRANSHIPMENT_TO)
                .build());
    when(purposeMapperFactory.get(any(SpsExchangedDocument.class)))
        .thenReturn(Optional.of(transhipmentPurposeMapper));

    // When
    Purpose purpose = mapper.map(spsCertificate);

    // Then
    assertThat(purpose.getPurposeGroup()).isEqualTo(PurposeGroupEnum.TRANSHIPMENT_TO);
    assertThat(purpose.getFinalBIP()).isEqualTo(BEBRU);
    assertThat(purpose.getThirdCountryTranshipment()).isEqualTo(FR);
    assertThat(purpose.getConformsToEU()).isTrue();
  }

  @Test
  void map_ReturnsTranshipmentPurposeAndDoesNotConformToEu_WhenCalledWithSpsCertificate()
      throws NotificationMapperException {
    // Given
    SpsNoteType euConformitySpsNote = buildSpsNoteType(FALSE, CONFORMS_TO_EU);
    spsCertificate.getSpsExchangedDocument().getIncludedSpsNote().add(euConformitySpsNote);
    when(transhipmentPurposeMapper.map(any(SpsCertificate.class)))
        .thenReturn(
            Purpose.builder()
                .finalBIP(BEBRU)
                .thirdCountryTranshipment(FR)
                .purposeGroup(PurposeGroupEnum.TRANSHIPMENT_TO)
                .build());
    when(purposeMapperFactory.get(any(SpsExchangedDocument.class)))
        .thenReturn(Optional.of(transhipmentPurposeMapper));

    // When
    Purpose purpose = mapper.map(spsCertificate);

    // Then
    assertThat(purpose.getPurposeGroup()).isEqualTo(PurposeGroupEnum.TRANSHIPMENT_TO);
    assertThat(purpose.getFinalBIP()).isEqualTo(BEBRU);
    assertThat(purpose.getThirdCountryTranshipment()).isEqualTo(FR);
    assertThat(purpose.getConformsToEU()).isFalse();
  }

  @Test
  void map_ThrowsException_WhenPurposeMapperNotFound() {
    // Given
    when(purposeMapperFactory.get(any(SpsExchangedDocument.class))).thenReturn(Optional.empty());

    // When / Then
    assertThatThrownBy(() -> mapper.map(spsCertificate))
        .isInstanceOf(NotificationMapperException.class)
        .asInstanceOf(InstanceOfAssertFactories.type(NotificationMapperException.class))
        .extracting(NotificationMapperException::getMessage)
        .isEqualTo("Unable to map to the Purpose");
  }
}
