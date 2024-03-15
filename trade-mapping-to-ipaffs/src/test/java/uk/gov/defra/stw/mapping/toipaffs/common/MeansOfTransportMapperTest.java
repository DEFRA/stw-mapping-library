package uk.gov.defra.stw.mapping.toipaffs.common;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.dto.ModeCode;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransportAfterBip;

class MeansOfTransportMapperTest {

  private MeansOfTransportMapper meansOfTransportMapper;
  private ObjectMapper objectMapper;
  private SpsCertificate spsCertificate;

  @BeforeEach
  void setup() throws JsonProcessingException {
    meansOfTransportMapper = new MeansOfTransportMapper(new MeansOfTransportHelper(new MeansOfTransportBaseMapper()));
    objectMapper = TestUtils.initObjectMapper();
    spsCertificate = JsonDeserializer.get(SpsCertificate.class, "chedpp/chedpp_ehc_complete.json", objectMapper);
  }

  @Test
  void map_ReturnsFullMeansOfTransport_WhenComplete() throws JsonProcessingException {
    MeansOfTransportAfterBip meansOfTransportAfterBip = meansOfTransportMapper.map(spsCertificate);
    assertThat(meansOfTransportAfterBip.getId())
        .isEqualTo("Voyage N° 1, Ocean Vessel: Green Opal");
    assertThat(meansOfTransportAfterBip.getDocument())
        .isEqualTo("TESTDOCUMENT");
    
  }

  @Test
  void map_ReturnsString_WhenUsedSpsTransportMeansNull() throws JsonProcessingException {
    List<SpsNoteType> listOfSpsNotes = spsCertificate.getSpsExchangedDocument()
      .getIncludedSpsNote();
    List<MainCarriageSpsTransportMovement> mainCarriageSpsTransportMovement = 
        new ArrayList<MainCarriageSpsTransportMovement>();
    mainCarriageSpsTransportMovement.add(new MainCarriageSpsTransportMovement()
        .withId(new IDType()
            .withSchemeID("ship_imo_number_after_bcp")
            .withValue("Voyage N° 1")
            .withSchemeAgencyID("GB"))
        .withModeCode(new ModeCode()
            .withValue("1")));
      
    spsCertificate = new SpsCertificate()
        .withSpsConsignment(
            new SpsConsignment()
                .withMainCarriageSpsTransportMovement(mainCarriageSpsTransportMovement))
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(listOfSpsNotes));
    assertThat(meansOfTransportMapper.map(spsCertificate).getId())
        .isEqualTo("Voyage N° 1");
  }
}