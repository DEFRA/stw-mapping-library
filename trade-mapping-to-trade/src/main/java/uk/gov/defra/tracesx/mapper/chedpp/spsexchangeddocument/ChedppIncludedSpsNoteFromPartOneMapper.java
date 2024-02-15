package uk.gov.defra.tracesx.mapper.chedpp.spsexchangeddocument;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.mapper.common.spsexchangeddocument.IncludedSpsNoteFromPartOneMapper;
import uk.gov.defra.tracesx.mapper.common.utils.MapperVersion;
import uk.gov.defra.tracesx.notificationschema.representation.ContactDetails;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;
import uk.gov.defra.tracesx.trade.dto.CodeType;
import uk.gov.defra.tracesx.trade.dto.SpsNoteType;
import uk.gov.defra.tracesx.trade.dto.TextType;

@Component
public class ChedppIncludedSpsNoteFromPartOneMapper implements Mapper<PartOne, List<SpsNoteType>> {

  private static final String YES = "YES";
  private static final String NO = "NO";
  private static final String CONSIGNMENT_ARRIVED = "CONSIGNMENT_ARRIVED";
  private static final String CONTAINS_WOOD_PACKAGING = "CONTAINS_WOOD_PACKAGING";
  private static final String CONTACT_DETAILS_NAME = "CONTACT_DETAILS_NAME";
  private static final String CONTACT_DETAILS_TELEPHONE = "CONTACT_DETAILS_TELEPHONE";
  private static final String CONTACT_DETAILS_EMAIL = "CONTACT_DETAILS_EMAIL";
  private static final String CONTACT_DETAILS_AGENT = "CONTACT_DETAILS_AGENT";

  private final IncludedSpsNoteFromPartOneMapper includedSpsNoteFromPartOneMapper;

  private final Map<Boolean, String> consignmentArrivedMap;

  @Autowired
  public ChedppIncludedSpsNoteFromPartOneMapper(
      IncludedSpsNoteFromPartOneMapper includedSpsNoteFromPartOneMapper) {
    this.includedSpsNoteFromPartOneMapper = includedSpsNoteFromPartOneMapper;

    consignmentArrivedMap = Map.of(
        Boolean.TRUE, YES,
        Boolean.FALSE, NO);
  }

  @Override
  public List<SpsNoteType> map(PartOne partOne) {
    List<SpsNoteType> notes = new ArrayList<>();

    SpsNoteType consignmentArrived = mapConsignmentArrived(partOne);
    if (consignmentArrived != null) {
      notes.add(consignmentArrived);
    }

    SpsNoteType containsWoodPackaging = mapContainsWoodPackaging(partOne);
    if (containsWoodPackaging != null) {
      notes.add(containsWoodPackaging);
    }

    notes.addAll(includedSpsNoteFromPartOneMapper.map(partOne));

    notes.addAll(mapContactDetails(partOne));

    notes.add(mapVersion());

    return notes;
  }

  private SpsNoteType mapConsignmentArrived(PartOne partOne) {
    Boolean consignmentArrived = partOne.getConsignmentArrived();

    return createSpsNoteType(consignmentArrived, CONSIGNMENT_ARRIVED);
  }

  private SpsNoteType mapContainsWoodPackaging(PartOne partOne) {
    Boolean containsWoodPackaging = partOne.getContainsWoodPackaging();

    return createSpsNoteType(containsWoodPackaging, CONTAINS_WOOD_PACKAGING);
  }

  private SpsNoteType createSpsNoteType(Boolean flag, String codeTypeValue) {
    if (flag != null) {
      return new SpsNoteType()
          .withContent(Collections.singletonList(new TextType()
              .withValue(consignmentArrivedMap.get(flag))))
          .withSubjectCode(new CodeType()
              .withValue(codeTypeValue));
    }

    return null;
  }

  private List<SpsNoteType> mapContactDetails(PartOne partOne) {
    if (partOne.getContactDetails() == null) {
      return Collections.emptyList();
    }
    ContactDetails contactDetails = partOne.getContactDetails();
    List<SpsNoteType> contactDetailsList = new ArrayList<>();

    contactDetailsList.add(createContactDetails(contactDetails.getName(), CONTACT_DETAILS_NAME));
    contactDetailsList.add(createContactDetails(contactDetails.getTelephone(),
        CONTACT_DETAILS_TELEPHONE));
    contactDetailsList.add(createContactDetails(contactDetails.getEmail(), CONTACT_DETAILS_EMAIL));
    contactDetailsList.add(createContactDetails(contactDetails.getAgent(), CONTACT_DETAILS_AGENT));

    return contactDetailsList.stream().filter(Objects::nonNull).collect(Collectors.toList());
  }

  private SpsNoteType createContactDetails(String content, String subjectCode) {
    if (StringUtils.isEmpty(content)) {
      return null;
    }
    return new SpsNoteType()
        .withContent(Collections.singletonList(new TextType().withValue(content)))
        .withSubjectCode(new CodeType().withValue(subjectCode));
  }

  private SpsNoteType mapVersion() {
    return new SpsNoteType()
        .withContent(Collections.singletonList(
            new TextType().withValue(MapperVersion.getVersion())))
        .withSubjectCode(new CodeType().withValue(MapperVersion.getDescription()));
  }
}
