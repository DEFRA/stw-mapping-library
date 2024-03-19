package uk.gov.defra.stw.mapping.toipaffs.common;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static uk.gov.defra.stw.mapping.toipaffs.enumeration.TransportType.SHIP;

import java.util.List;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.dto.ModeCode;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.dto.UsedSpsTransportMeans;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransportBeforeBip;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.TransportMethod;

class MeansOfTransportFromEntryPointMapperTest {

  private static final String SHIP_IMO_NUMBER_BEFORE_BCP = "ship_imo_number_before_bcp";
  private static final String SHIP_IMO_NUMBER_AFTER_BCP = "ship_imo_number_after_bcp";
  private static final String DOCUMENT_SUBJECT_CODE = "transport_before_bcp_document";

  private final MeansOfTransportFromEntryPointMapper mapper =
      new MeansOfTransportFromEntryPointMapper();

  @Test
  void map_ReturnsMeansOfTransportBeforeBip_WhenComplete() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of(
                new SpsNoteType()
                    .withSubjectCode(new CodeType().withValue(DOCUMENT_SUBJECT_CODE))
                    .withContent(List.of(
                        new TextType().withValue("Document")
                    ))
            )))
        .withSpsConsignment(new SpsConsignment()
            .withMainCarriageSpsTransportMovement(List.of(
                transportMovement(SHIP_IMO_NUMBER_BEFORE_BCP, SHIP.getValue()),
                transportMovement(SHIP_IMO_NUMBER_AFTER_BCP, SHIP.getValue())
            )));

    MeansOfTransportBeforeBip actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo(MeansOfTransportBeforeBip.builder()
        .id("Identification, Transport means")
        .type(TransportMethod.SHIP)
        .document("Document")
        .build());
  }

  @Test
  void map_ReturnsNull_WhenEmptyMainCarriageSpsTransportMovement() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withMainCarriageSpsTransportMovement(List.of()));

    MeansOfTransportBeforeBip actual = mapper.map(spsCertificate);

    assertThat(actual).isNull();
  }

  MainCarriageSpsTransportMovement transportMovement(String schemeId, String modeCode) {
    return new MainCarriageSpsTransportMovement()
        .withId(new IDType()
            .withSchemeID(schemeId)
            .withSchemeAgencyID("GB")
            .withValue("Identification"))
        .withModeCode(new ModeCode().withValue(modeCode))
        .withUsedSpsTransportMeans(new UsedSpsTransportMeans()
            .withName(new TextType().withValue("Transport means")));
  }
}
