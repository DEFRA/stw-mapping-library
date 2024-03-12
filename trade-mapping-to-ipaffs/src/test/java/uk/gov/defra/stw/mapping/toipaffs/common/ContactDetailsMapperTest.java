package uk.gov.defra.stw.mapping.toipaffs.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.tracesx.notificationschema.representation.ContactDetails;

class ContactDetailsMapperTest {

  private final ContactDetailsMapper contactDetailsMapper = new ContactDetailsMapper();

  @Test
  void map_ReturnsContactDetails_WhenAllFieldsPresent() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of(
                createNote("CONTACT_DETAILS_NAME", "Joe Bloggs"),
                createNote("CONTACT_DETAILS_PHONE", "0123456789"),
                createNote("CONTACT_DETAILS_EMAIL", "joe.bloggs@example.com"),
                createNote("CONTACT_DETAILS_AGENT", "Jane Doe")
            )));

    ContactDetails actual = contactDetailsMapper.map(spsCertificate);

    assertThat(actual).isEqualTo(ContactDetails.builder()
        .name("Joe Bloggs")
        .telephone("0123456789")
        .email("joe.bloggs@example.com")
        .agent("Jane Doe")
        .build());
  }

  @Test
  void map_ReturnsContactDetails_WhenOnlyNamePresent() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of(
                createNote("CONTACT_DETAILS_NAME", "Joe Bloggs")
            )));

    ContactDetails actual = contactDetailsMapper.map(spsCertificate);

    assertThat(actual).isEqualTo(ContactDetails.builder()
        .name("Joe Bloggs")
        .build());
  }

  @Test
  void map_ReturnsContactDetails_WhenOnlyTelephonePresent() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of(
                createNote("CONTACT_DETAILS_PHONE", "0123456789")
            )));

    ContactDetails actual = contactDetailsMapper.map(spsCertificate);

    assertThat(actual).isEqualTo(ContactDetails.builder()
        .telephone("0123456789")
        .build());
  }

  @Test
  void map_ReturnsContactDetails_WhenOnlyEmailPresent() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of(
                createNote("CONTACT_DETAILS_EMAIL", "joe.bloggs@example.com")
            )));

    ContactDetails actual = contactDetailsMapper.map(spsCertificate);

    assertThat(actual).isEqualTo(ContactDetails.builder()
        .email("joe.bloggs@example.com")
        .build());
  }

  @Test
  void map_ReturnsContactDetails_WhenOnlyAgentPresent() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of(
                createNote("CONTACT_DETAILS_AGENT", "Jane Doe")
            )));

    ContactDetails actual = contactDetailsMapper.map(spsCertificate);

    assertThat(actual).isEqualTo(ContactDetails.builder()
        .agent("Jane Doe")
        .build());
  }

  @Test
  void map_ReturnsEmptyObject_WhenNoFieldsPresent() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of()));

    ContactDetails actual = contactDetailsMapper.map(spsCertificate);

    assertThat(actual).isEqualTo(new ContactDetails());
  }

  private SpsNoteType createNote(String subjectCode, String content) {
    return new SpsNoteType()
        .withSubjectCode(new CodeType().withValue(subjectCode))
        .withContent(List.of(new TextType().withValue(content)));
  }
}
