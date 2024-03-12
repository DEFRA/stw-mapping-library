package uk.gov.defra.stw.mapping.toipaffs.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.tracesx.notificationschema.representation.NominatedContact;

class NominatedContactsMapperTest {

  private final NominatedContactsMapper nominatedContactsMapper = new NominatedContactsMapper();

  @Test
  void map_ReturnsNominatedContacts_WhenSingle() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of(
                createNote("NOMINATED_CONTACT_NAME", "1", "Joe Bloggs"),
                createNote("NOMINATED_CONTACT_PHONE", "1", "0123456789"),
                createNote("NOMINATED_CONTACT_EMAIL", "1", "joe.bloggs@example.com")
            )));

    List<NominatedContact> actual = nominatedContactsMapper.map(spsCertificate);

    assertThat(actual).containsExactly(NominatedContact.builder()
        .name("Joe Bloggs")
        .telephone("0123456789")
        .email("joe.bloggs@example.com")
        .build());
  }

  @Test
  void map_ReturnsNominatedContacts_WhenMultiple() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of(
                createNote("NOMINATED_CONTACT_NAME", "1", "Joe Bloggs"),
                createNote("NOMINATED_CONTACT_PHONE", "1", "0123456789"),
                createNote("NOMINATED_CONTACT_EMAIL", "1", "joe.bloggs@example.com"),
                createNote("NOMINATED_CONTACT_NAME", "2", "Jane Doe"),
                createNote("NOMINATED_CONTACT_PHONE", "2", "0123456789"),
                createNote("NOMINATED_CONTACT_EMAIL", "2", "jane.doe@example.com")
            )));

    List<NominatedContact> actual = nominatedContactsMapper.map(spsCertificate);

    assertThat(actual).containsExactly(
        NominatedContact.builder()
            .name("Joe Bloggs")
            .telephone("0123456789")
            .email("joe.bloggs@example.com")
            .build(),
        NominatedContact.builder()
            .name("Jane Doe")
            .telephone("0123456789")
            .email("jane.doe@example.com")
            .build());
  }

  @Test
  void map_ReturnsNominatedContacts_WhenOnlyNamePresent() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of(
                createNote("NOMINATED_CONTACT_NAME", "1", "Joe Bloggs")
            )));

    List<NominatedContact> actual = nominatedContactsMapper.map(spsCertificate);

    assertThat(actual).containsExactly(NominatedContact.builder()
        .name("Joe Bloggs")
        .build());
  }

  @Test
  void map_ReturnsNominatedContacts_WhenNameAndPhonePresent() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of(
                createNote("NOMINATED_CONTACT_NAME", "1", "Joe Bloggs"),
                createNote("NOMINATED_CONTACT_PHONE", "1", "0123456789")
            )));

    List<NominatedContact> actual = nominatedContactsMapper.map(spsCertificate);

    assertThat(actual).containsExactly(NominatedContact.builder()
        .name("Joe Bloggs")
        .telephone("0123456789")
        .build());
  }

  @Test
  void map_ReturnsNominatedContacts_WhenNameAndEmailPresent() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of(
                createNote("NOMINATED_CONTACT_NAME", "1", "Joe Bloggs"),
                createNote("NOMINATED_CONTACT_EMAIL", "1", "joe.bloggs@example.com")
            )));

    List<NominatedContact> actual = nominatedContactsMapper.map(spsCertificate);

    assertThat(actual).containsExactly(NominatedContact.builder()
        .name("Joe Bloggs")
        .email("joe.bloggs@example.com")
        .build());
  }

  @Test
  void map_ReturnsEmptyList_WhenOnlyPhonePresent() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of(
                createNote("NOMINATED_CONTACT_PHONE", "1", "0123456789")
            )));

    List<NominatedContact> actual = nominatedContactsMapper.map(spsCertificate);

    assertThat(actual).isEmpty();
  }

  @Test
  void map_ReturnsEmptyList_WhenOnlyEmailPresent() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of(
                createNote("NOMINATED_CONTACT_EMAIL", "1", "joe.bloggs@example.com")
            )));

    List<NominatedContact> actual = nominatedContactsMapper.map(spsCertificate);

    assertThat(actual).isEmpty();
  }

  @Test
  void map_ReturnsEmptyList_WhenNoFieldsPresent() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of()));

    List<NominatedContact> actual = nominatedContactsMapper.map(spsCertificate);

    assertThat(actual).isEmpty();
  }

  private SpsNoteType createNote(String subjectCode, String index, String content) {
    return new SpsNoteType()
        .withSubjectCode(new CodeType().withValue(subjectCode))
        .withContentCode(List.of(new CodeType().withValue(index)))
        .withContent(List.of(new TextType().withValue(content)));
  }
}
