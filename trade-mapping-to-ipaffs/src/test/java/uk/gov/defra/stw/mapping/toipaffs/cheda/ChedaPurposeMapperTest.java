package uk.gov.defra.stw.mapping.toipaffs.cheda;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.IMPORT;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.RE_IMPORT;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.TRANSHIPMENT_TO;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.TRANSIT_TO_3RD_COUNTRY;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsClause;
import uk.gov.defra.stw.mapping.dto.SpsAuthenticationType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.toipaffs.cheda.purpose.ChedaInternalMarketPurposeMapper;
import uk.gov.defra.stw.mapping.toipaffs.cheda.purpose.ChedaReEntryPurposeMapper;
import uk.gov.defra.stw.mapping.toipaffs.cheda.purpose.ChedaTemporaryHorsesPurposeMapper;
import uk.gov.defra.stw.mapping.toipaffs.cheda.purpose.ChedaTranshipmentPurposeMapper;
import uk.gov.defra.stw.mapping.toipaffs.cheda.purpose.ChedaTransitPurposeMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;

@ExtendWith(MockitoExtension.class)
class ChedaPurposeMapperTest {

  @Mock
  private ChedaInternalMarketPurposeMapper chedaInternalMarketPurposeMapper;
  @Mock
  private ChedaTranshipmentPurposeMapper chedaTranshipmentPurposeMapper;
  @Mock
  private ChedaTransitPurposeMapper chedaTransitPurposeMapper;
  @Mock
  private ChedaReEntryPurposeMapper chedaReEntryPurposeMapper;
  @Mock
  private ChedaTemporaryHorsesPurposeMapper chedaTemporaryHorsesPurposeMapper;

  @InjectMocks
  private ChedaPurposeMapper mapper;

  @Test
  void map_ReturnsPurpose_WhenPurposeGroupIsInternalMarket() throws NotificationMapperException {
    SpsCertificate spsCertificate = createSpsCertificate("INTERNAL_MARKET");
    Purpose purpose = Purpose.builder().purposeGroup(IMPORT).build();
    when(chedaInternalMarketPurposeMapper.map(spsCertificate)).thenReturn(purpose);

    Purpose actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo(purpose);
  }

  @Test
  void map_ReturnsPurpose_WhenPurposeGroupIsTranshipment() throws NotificationMapperException {
    SpsCertificate spsCertificate = createSpsCertificate("TRANSHIPMENT");
    Purpose purpose = Purpose.builder().purposeGroup(TRANSHIPMENT_TO).build();
    when(chedaTranshipmentPurposeMapper.map(spsCertificate)).thenReturn(purpose);

    Purpose actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo(purpose);
  }

  @Test
  void map_ReturnsPurpose_WhenPurposeGroupIsTransit() throws NotificationMapperException {
    SpsCertificate spsCertificate = createSpsCertificate("DIRECT_TRANSIT");
    Purpose purpose = Purpose.builder().purposeGroup(TRANSIT_TO_3RD_COUNTRY).build();
    when(chedaTransitPurposeMapper.map(spsCertificate)).thenReturn(purpose);

    Purpose actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo(purpose);
  }

  @Test
  void map_ReturnsPurpose_WhenPurposeGroupIsReEntry() throws NotificationMapperException {
    SpsCertificate spsCertificate = createSpsCertificate("RE_ENTRY");
    Purpose purpose = Purpose.builder().purposeGroup(RE_IMPORT).build();
    when(chedaReEntryPurposeMapper.map(spsCertificate)).thenReturn(purpose);

    Purpose actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo(purpose);
  }

  @Test
  void map_ReturnsPurpose_WhenPurposeGroupIsTemporaryAdmissionHorses() throws NotificationMapperException {
    SpsCertificate spsCertificate = createSpsCertificate("TEMPORARY_ADMISSION_HORSES");
    Purpose purpose = Purpose.builder().purposeGroup(RE_IMPORT).build();
    when(chedaTemporaryHorsesPurposeMapper.map(spsCertificate)).thenReturn(purpose);

    Purpose actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo(purpose);
  }

  private SpsCertificate createSpsCertificate(String purposeGroup) {
    return new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withSignatorySpsAuthentication(List.of(new SpsAuthenticationType()
                .withIncludedSpsClause(List.of(new IncludedSpsClause()
                    .withId(new IDType().withValue("PURPOSE"))
                    .withContent(List.of(new TextType().withValue(purposeGroup))))))));
  }
}
