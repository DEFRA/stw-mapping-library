package uk.gov.defra.stw.mapping.toipaffs.cloning.chedp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.ExternalReferenceMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.VeterinaryInformationMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.AccompanyingDocument;
import uk.gov.defra.tracesx.notificationschema.representation.ExternalReference;
import uk.gov.defra.tracesx.notificationschema.representation.VeterinaryInformation;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.ExternalSystem;

@ExtendWith(MockitoExtension.class)
class ChedpVeterinaryInformationMapperTest {

  @Mock
  private SpsCertificate spsCertificate;
  @Mock
  private SpsExchangedDocument exchangedDocument;

  @Mock
  private VeterinaryInformationMapper veterinaryInformationMapper;

  @Mock
  private ExternalReferenceMapper externalReferenceMapper;

  @InjectMocks
  private ChedpVeterinaryInformationMapper chedpVeterinaryInformationMapper;

  @BeforeEach
  void setup() {
    when(spsCertificate.getSpsExchangedDocument()).thenReturn(exchangedDocument);

    when(externalReferenceMapper.mapExternalReference(any(), any())).thenReturn(
        List.of(buildExternalReference())
    );

    when(veterinaryInformationMapper.map(any())).thenReturn(buildVeterinaryInformation());
  }

  @Test
  void map_ReturnsCorrectVeterinaryInformation_WhenCompleteSpsCertificate() throws NotificationMapperException {
    VeterinaryInformation expectedVeterinaryInformation = buildVeterinaryInformation();
    expectedVeterinaryInformation.getAccompanyingDocuments().forEach(
        accompanyingDocument -> accompanyingDocument.setExternalReference(buildExternalReference())
    );

    VeterinaryInformation actualVeterinaryInformation = chedpVeterinaryInformationMapper.map(
        spsCertificate
    );

    assertThat(actualVeterinaryInformation).isEqualTo(expectedVeterinaryInformation);

    verify(veterinaryInformationMapper).map(exchangedDocument);
    verify(externalReferenceMapper).mapExternalReference(spsCertificate, ExternalSystem.ECERT);
  }

  private VeterinaryInformation buildVeterinaryInformation() {
    return VeterinaryInformation.builder()
        .accompanyingDocuments(List.of(AccompanyingDocument.builder().build()))
        .build();
  }

  private ExternalReference buildExternalReference() {
    return ExternalReference.builder()
        .system(ExternalSystem.ECERT)
        .reference("reference")
        .build();
  }
}
