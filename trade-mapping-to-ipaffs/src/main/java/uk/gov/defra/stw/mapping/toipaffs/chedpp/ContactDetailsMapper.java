package uk.gov.defra.stw.mapping.toipaffs.chedpp;

import static uk.gov.defra.stw.mapping.toipaffs.utils.SpsNoteTypeHelper.getNoteContentBySubjectCode;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.ContactDetails;

@Component
public class ContactDetailsMapper implements Mapper<SpsCertificate, ContactDetails> {

  @Override
  public ContactDetails map(SpsCertificate spsCertificate) {
    return ContactDetails.builder()
        .name(getNoteContentBySubjectCode(spsCertificate, "CONTACT_DETAILS_NAME").orElse(null))
        .telephone(getNoteContentBySubjectCode(spsCertificate, "CONTACT_DETAILS_PHONE")
            .orElse(null))
        .email(getNoteContentBySubjectCode(spsCertificate, "CONTACT_DETAILS_EMAIL").orElse(null))
        .agent(getNoteContentBySubjectCode(spsCertificate, "CONTACT_DETAILS_AGENT").orElse(null))
        .build();
  }
}
