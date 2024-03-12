package uk.gov.defra.stw.mapping.toipaffs.chedpp;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.NominatedContact;

@Component
public class NominatedContactsMapper implements Mapper<SpsCertificate, List<NominatedContact>> {

  @Override
  public List<NominatedContact> map(SpsCertificate spsCertificate) {
    List<SpsNoteType> notes = spsCertificate.getSpsExchangedDocument().getIncludedSpsNote();

    Map<Integer, String> names = getIndexAndValue(notes, "NOMINATED_CONTACT_NAME");
    Map<Integer, String> phones = getIndexAndValue(notes, "NOMINATED_CONTACT_PHONE");
    Map<Integer, String> emails = getIndexAndValue(notes, "NOMINATED_CONTACT_EMAIL");

    return names.entrySet().stream()
        .map(entry -> NominatedContact.builder()
            .name(entry.getValue())
            .telephone(phones.get(entry.getKey()))
            .email(emails.get(entry.getKey()))
            .build())
        .toList();
  }

  private Map<Integer, String> getIndexAndValue(List<SpsNoteType> notes, String subjectCode) {
    return notes.stream()
        .filter(note -> note.getSubjectCode().getValue().equals(subjectCode))
        .collect(Collectors.toMap(
            note -> Integer.parseInt(note.getContentCode().get(0).getValue()),
            note -> note.getContent().get(0).getValue()
        ));
  }
}
