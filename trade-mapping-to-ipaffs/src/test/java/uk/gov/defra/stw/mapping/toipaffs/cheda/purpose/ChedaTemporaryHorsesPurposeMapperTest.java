package uk.gov.defra.stw.mapping.toipaffs.cheda.purpose;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.ForImportOrAdmissionEnum.TEMPORARY_ADMISSION_HORSES;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.RE_IMPORT;

import java.util.List;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.dto.SpsCountrySubDivisionType;
import uk.gov.defra.stw.mapping.dto.SpsCountryType;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.SpsPartyType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;

class ChedaTemporaryHorsesPurposeMapperTest {

  private final ChedaTemporaryHorsesPurposeMapper mapper = new ChedaTemporaryHorsesPurposeMapper();

  @Test
  void map_ReturnsPurpose_WhenPointOfEntryPresent() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withTransitSpsCountry(List.of(new SpsCountryType()
                .withId(new IDType().withValue("GB"))
                .withSubordinateSpsCountrySubDivision(List.of(new SpsCountrySubDivisionType()
                        .withId(new IDType().withValue("PORT_OF_EXIT"))
                    .withActivityAuthorizedSpsParty(List.of(new SpsPartyType()
                        .withId(new IDType().withValue("Port of exit")))))))))
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of(new SpsNoteType()
                .withSubjectCode(new CodeType().withValue("TEMPORARY_ADMISSION_HORSES_EXIT_DATE"))
                .withContent(List.of(new TextType().withValue("Exit date")))
            )));

    Purpose actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo(Purpose.builder()
        .purposeGroup(RE_IMPORT)
        .forImportOrAdmission(TEMPORARY_ADMISSION_HORSES)
        .exitDate("Exit date")
        .exitBIP("GBAPHA1A")
        .build());
  }

  @Test
  void map_ReturnsPurpose_WhenPointOfEntryNotPresent() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withTransitSpsCountry(List.of(new SpsCountryType()
                .withId(new IDType().withValue("GB"))
                .withSubordinateSpsCountrySubDivision(List.of(new SpsCountrySubDivisionType()
                    .withActivityAuthorizedSpsParty(List.of(new SpsPartyType()
                        .withId(new IDType().withValue("Exit BCP")))))))))
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of(new SpsNoteType()
                .withSubjectCode(new CodeType().withValue("TEMPORARY_ADMISSION_HORSES_EXIT_DATE"))
                .withContent(List.of(new TextType().withValue("Exit date")))
            )));

    Purpose actual = mapper.map(spsCertificate);

    assertThat(actual).usingRecursiveComparison().isEqualTo(Purpose.builder()
        .purposeGroup(RE_IMPORT)
        .forImportOrAdmission(TEMPORARY_ADMISSION_HORSES)
        .exitDate("Exit date")
        .exitBIP("Exit BCP")
        .build());
  }
}
