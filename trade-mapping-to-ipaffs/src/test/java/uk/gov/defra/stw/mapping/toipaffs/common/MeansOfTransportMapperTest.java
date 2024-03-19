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
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransportAfterBip;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.TransportMethod;

class MeansOfTransportMapperTest {

  private static final String SHIP_IMO_NUMBER_BEFORE_BCP = "ship_imo_number_before_bcp";
  private static final String SHIP_IMO_NUMBER_AFTER_BCP = "ship_imo_number_after_bcp";
  private static final String DOCUMENT_SUBJECT_CODE = "transport_after_bcp_document";

  private final MeansOfTransportMapper mapper = new MeansOfTransportMapper();

  @Test
  void map_ReturnsMeansOfTransportAfterBip_WhenComplete() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(exchangedDocument())
        .withSpsConsignment(new SpsConsignment()
            .withMainCarriageSpsTransportMovement(List.of(
                transportMovement(SHIP_IMO_NUMBER_BEFORE_BCP, SHIP.getValue()),
                transportMovement(SHIP_IMO_NUMBER_AFTER_BCP, SHIP.getValue())
            )));

    MeansOfTransportAfterBip actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo(MeansOfTransportAfterBip.builder()
        .id("Identification, Transport means")
        .type(TransportMethod.SHIP)
        .document("Document")
        .build());
  }

  @Test
  void map_ReturnsNull_WhenEmptyMainCarriageSpsTransportMovement() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(exchangedDocument())
        .withSpsConsignment(new SpsConsignment()
            .withMainCarriageSpsTransportMovement(List.of()));

    MeansOfTransportAfterBip actual = mapper.map(spsCertificate);

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

  SpsExchangedDocument exchangedDocument() {
    return new SpsExchangedDocument()
        .withIncludedSpsNote(List.of(
            new SpsNoteType()
                .withSubjectCode(new CodeType().withValue(DOCUMENT_SUBJECT_CODE))
                .withContent(List.of(
                    new TextType().withValue("Document")
                ))
        ));
  }
}
