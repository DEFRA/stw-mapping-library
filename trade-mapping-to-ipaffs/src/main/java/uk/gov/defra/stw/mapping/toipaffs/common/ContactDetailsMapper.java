package uk.gov.defra.stw.mapping.toipaffs.common;

import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.ContactDetails;

@Component
public class ContactDetailsMapper implements Mapper<SpsCertificate, ContactDetails> {

  @Override
  public ContactDetails map(SpsCertificate spsCertificate) {
    List<SpsNoteType> notes = spsCertificate.getSpsExchangedDocument().getIncludedSpsNote();
    return ContactDetails.builder()
        .name(getValue(notes, "CONTACT_DETAILS_NAME"))
        .telephone(getValue(notes, "CONTACT_DETAILS_PHONE"))
        .email(getValue(notes, "CONTACT_DETAILS_EMAIL"))
        .agent(getValue(notes, "CONTACT_DETAILS_AGENT"))
        .build();
  }

  private String getValue(List<SpsNoteType> notes, String subjectCode) {
    return notes.stream()
        .filter(note -> note.getSubjectCode().getValue().equals(subjectCode))
        .findAny()
        .map(SpsNoteType::getContent)
        .map(content -> content.get(0))
        .map(TextType::getValue)
        .orElse(null);
  }
}
