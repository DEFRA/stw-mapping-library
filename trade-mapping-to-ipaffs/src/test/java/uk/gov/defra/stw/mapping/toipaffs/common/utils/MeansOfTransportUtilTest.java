package uk.gov.defra.stw.mapping.toipaffs.common.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.dto.ModeCode;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.dto.UsedSpsTransportMeans;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.TransportMethod;

class MeansOfTransportUtilTest {

  @ParameterizedTest
  @CsvSource({
      "1, SHIP",
      "2, RAILWAY_WAGON",
      "3, ROAD_VEHICLE",
      "4, AEROPLANE"
  })
  void mapType_ReturnsCorrectTransportMethod(String modeCode, TransportMethod transportMethod) {
    MainCarriageSpsTransportMovement transportMovement = new MainCarriageSpsTransportMovement()
        .withModeCode(new ModeCode().withValue(modeCode));

    TransportMethod actual = MeansOfTransportUtil.mapType(transportMovement);

    assertThat(actual).isEqualTo(transportMethod);
  }

  @Test
  void mapId_ReturnsId_WhenIdAndTransportMeansPresent() {
    MainCarriageSpsTransportMovement transportMovement = new MainCarriageSpsTransportMovement()
        .withId(new IDType().withValue("id"))
        .withUsedSpsTransportMeans(new UsedSpsTransportMeans()
            .withName(new TextType().withValue("Transport means")));

    String actual = MeansOfTransportUtil.mapId(transportMovement);

    assertThat(actual).isEqualTo("id, Transport means");
  }

  @Test
  void mapId_ReturnsId_WhenOnlyIdPresent() {
    MainCarriageSpsTransportMovement transportMovement = new MainCarriageSpsTransportMovement()
        .withId(new IDType().withValue("id"));

    String actual = MeansOfTransportUtil.mapId(transportMovement);

    assertThat(actual).isEqualTo("id");
  }

  @Test
  void mapId_ReturnsId_WhenTransportMeansBlank() {
    MainCarriageSpsTransportMovement transportMovement = new MainCarriageSpsTransportMovement()
        .withId(new IDType().withValue("id"))
        .withUsedSpsTransportMeans(new UsedSpsTransportMeans()
            .withName(new TextType().withValue("  ")));

    String actual = MeansOfTransportUtil.mapId(transportMovement);

    assertThat(actual).isEqualTo("id");
  }

  @Test
  void mapTransportDocument_ReturnsDocument_WhenNotePresent() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of(
                new SpsNoteType()
                    .withSubjectCode(new CodeType().withValue("SUBJECT_CODE"))
                    .withContent(List.of(new TextType().withValue("Document")))
            )));

    String actual = MeansOfTransportUtil.mapTransportDocument(spsCertificate, "SUBJECT_CODE");

    assertThat(actual).isEqualTo("Document");
  }

  @Test
  void mapTransportDocument_ReturnsNull_WhenNoteMissing() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of(
                new SpsNoteType()
                    .withSubjectCode(new CodeType().withValue("OTHER_SUBJECT_CODE"))
                    .withContent(List.of(new TextType().withValue("Other content")))
            )));

    String actual = MeansOfTransportUtil.mapTransportDocument(spsCertificate, "SUBJECT_CODE");

    assertThat(actual).isNull();
  }
}
