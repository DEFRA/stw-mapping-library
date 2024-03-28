package uk.gov.defra.stw.mapping.toipaffs.cheda.commodities;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.AppliedSpsProcess;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.IdentifiersMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperator;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperatorAddress;
import uk.gov.defra.tracesx.notificationschema.representation.Identifier;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorStatus;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType;

@Component
public class IdentifiersMapper implements Mapper<IncludedSpsTradeLineItem, List<Identifier>> {

  private static final String STORING_PROCESS_TYPE_CODE = "43";
  private static final Pattern IDENTIFIER_PATTERN = Pattern.compile("^(.+)_(\\d)$");

  public List<Identifier> map(IncludedSpsTradeLineItem tradeLineItem) {
    List<SpsNoteType> notes = tradeLineItem.getAdditionalInformationSpsNote();
    return getNotesWithSubject(notes, "IDENTIFIER")
        .collect(Collectors.groupingBy(this::getIdentifierIndex))
        .entrySet().stream()
        .map(entry -> Identifier.builder()
            .speciesNumber(Integer.valueOf(entry.getKey()))
            .data(createIdentifierData(entry.getValue()))
            .isPlaceOfDestinationThePermanentAddress(mapIsPlaceOfDestinationThePermanentAddress(
                entry.getKey(), tradeLineItem))
            .permanentAddress(mapPermanentAddress(entry.getKey(), tradeLineItem))
            .build())
        .toList();
  }

  private Stream<SpsNoteType> getNotesWithSubject(List<SpsNoteType> notes, String subjectCode) {
    return notes.stream()
        .filter(note -> note.getSubjectCode() != null
            && note.getSubjectCode().getValue().equals(subjectCode));
  }

  private String getIdentifierIndex(SpsNoteType note) {
    String identifier = note.getContentCode().get(0).getValue();
    Matcher matcher = IDENTIFIER_PATTERN.matcher(identifier);
    if (matcher.find()) {
      return matcher.group(2);
    } else {
      throw new IdentifiersMapperException("Unable to extract index from identifier: "
          + identifier);
    }
  }

  private String getIdentifierType(SpsNoteType note) {
    String identifier = note.getContentCode().get(0).getValue();
    Matcher matcher = IDENTIFIER_PATTERN.matcher(identifier);
    if (matcher.find()) {
      return matcher.group(1).toLowerCase();
    } else {
      throw new IdentifiersMapperException("Unable to extract type from identifier: " + identifier);
    }
  }

  private Map<String, String> createIdentifierData(List<SpsNoteType> identifierNotes) {
    return identifierNotes.stream()
        .collect(Collectors.toMap(
            this::getIdentifierType,
            note -> note.getContent().get(0).getValue()
        ));
  }

  private Boolean mapIsPlaceOfDestinationThePermanentAddress(String index,
      IncludedSpsTradeLineItem tradeLineItem) {
    List<SpsNoteType> notes = tradeLineItem.getAdditionalInformationSpsNote();
    return getNotesWithSubject(notes, "IS_PLACE_OF_DESTINATION_THE_PERMANENT_ADDRESS")
        .filter(note -> note.getContentCode().get(0).getValue().equals(index))
        .findAny()
        .map(SpsNoteType::getContent)
        .map(content -> content.get(0))
        .map(TextType::getValue)
        .map(Boolean::parseBoolean)
        .orElse(null);
  }

  private EconomicOperator mapPermanentAddress(String index,
      IncludedSpsTradeLineItem tradeLineItem) {
    List<SpsNoteType> notes = tradeLineItem.getAdditionalInformationSpsNote();
    String telephone = getNotesWithSubject(notes, "PERMANENT_ADDRESS_TELEPHONE")
        .filter(note -> note.getContentCode().get(0).getValue().equals(index))
        .findAny()
        .map(SpsNoteType::getContent)
        .map(content -> content.get(0))
        .map(TextType::getValue)
        .orElse(null);
    String email = getNotesWithSubject(notes, "PERMANENT_ADDRESS_EMAIL")
        .filter(note -> note.getContentCode().get(0).getValue().equals(index))
        .findAny()
        .map(SpsNoteType::getContent)
        .map(content -> content.get(0))
        .map(TextType::getValue)
        .orElse(null);
    return tradeLineItem.getAppliedSpsProcess().stream()
        .filter(process -> process.getTypeCode().getValue().equals(STORING_PROCESS_TYPE_CODE))
        .filter(process -> process.getApplicableSpsProcessCharacteristic().get(0).getDescription()
            .get(0).getValue().equals(index))
        .findAny()
        .map(AppliedSpsProcess::getOperatorSpsParty)
        .map(party -> EconomicOperator.builder()
            .id(UUID.randomUUID().toString())
            .type(EconomicOperatorType.CONSIGNEE)
            .status(EconomicOperatorStatus.NON_APPROVED)
            .companyName(party.getName().getValue())
            .individualName(String.format("%s %s",
                tradeLineItem.getScientificName().get(0).getValue(), index))
            .address(EconomicOperatorAddress.builder()
                .addressLine1(party.getSpecifiedSpsAddress().getLineOne().getValue())
                .addressLine2(party.getSpecifiedSpsAddress().getLineTwo().getValue())
                .city(party.getSpecifiedSpsAddress().getCityName().getValue())
                .postalZipCode(party.getSpecifiedSpsAddress().getPostcodeCode().getValue())
                .telephone(telephone)
                .email(email)
                .build())
            .build())
        .orElse(null);
  }
}
