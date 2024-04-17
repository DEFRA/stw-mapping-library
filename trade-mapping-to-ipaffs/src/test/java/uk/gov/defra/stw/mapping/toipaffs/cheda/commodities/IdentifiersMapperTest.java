package uk.gov.defra.stw.mapping.toipaffs.cheda.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.ApplicableSpsProcessCharacteristic;
import uk.gov.defra.stw.mapping.dto.AppliedSpsProcess;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.ProcessTypeCodeType;
import uk.gov.defra.stw.mapping.dto.SpecifiedSpsAddress;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.SpsPartyType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperator;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperatorAddress;
import uk.gov.defra.tracesx.notificationschema.representation.Identifier;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorStatus;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType;

@ExtendWith(MockitoExtension.class)
class IdentifiersMapperTest {

  private static final UUID TEST_UUID = UUID.fromString("12345678-1234-1234-1234-123456789012");

  private final IdentifiersMapper mapper = new IdentifiersMapper();

  @Test
  void map_ReturnsIdentifierAndPermanentAddress_WhenPlaceOfDestinationIsNotPermanentAddress() {
    IncludedSpsTradeLineItem includedSpsTradeLineItem = new IncludedSpsTradeLineItem()
        .withScientificName(List.of(new TextType().withValue("Species name")))
        .withAdditionalInformationSpsNote(List.of(
            noteIdentifier("1", "MICROCHIP", "M-1234"),
            noteIdentifier("1", "PASSPORT", "P-1234"),
            note("IS_PLACE_OF_DESTINATION_THE_PERMANENT_ADDRESS", "1", "FALSE"),
            note("PERMANENT_ADDRESS_TELEPHONE", "1", "0123456789"),
            note("PERMANENT_ADDRESS_EMAIL", "1", "me@example.com")
        ))
        .withAppliedSpsProcess(List.of(new AppliedSpsProcess()
            .withTypeCode(new ProcessTypeCodeType().withValue("43"))
            .withApplicableSpsProcessCharacteristic(List.of(new ApplicableSpsProcessCharacteristic()
                .withDescription(List.of(new TextType().withValue("1")))))
            .withOperatorSpsParty(new SpsPartyType()
                .withName(new TextType().withValue("Address name"))
                .withSpecifiedSpsAddress(new SpecifiedSpsAddress()
                    .withLineOne(new TextType().withValue("Line 1"))
                    .withLineTwo(new TextType().withValue("Line 2"))
                    .withCityName(new TextType().withValue("City"))
                    .withPostcodeCode(new CodeType().withValue("Postcode"))))));

    try (MockedStatic<UUID> mockedUuid = Mockito.mockStatic(UUID.class)) {
      mockedUuid.when(UUID::randomUUID).thenReturn(TEST_UUID);

      List<Identifier> actual = mapper.map(includedSpsTradeLineItem);

      assertThat(actual).containsExactly(Identifier.builder()
          .speciesNumber(1)
          .data(Map.of(
              "microchip", "M-1234",
              "passport", "P-1234"
          ))
          .isPlaceOfDestinationThePermanentAddress(false)
          .permanentAddress(EconomicOperator.builder()
              .id(TEST_UUID.toString())
              .type(EconomicOperatorType.CONSIGNEE)
              .status(EconomicOperatorStatus.NON_APPROVED)
              .companyName("Address name")
              .individualName("Species name 1")
              .address(EconomicOperatorAddress.builder()
                  .addressLine1("Line 1")
                  .addressLine2("Line 2")
                  .city("City")
                  .postalZipCode("Postcode")
                  .telephone("0123456789")
                  .email("me@example.com")
                  .build())
              .build())
          .build());
    }
  }

  @Test
  void map_ReturnsIdentifierAndNoPermanentAddress_WhenPlaceOfDestinationIsPermanentAddress() {
    IncludedSpsTradeLineItem includedSpsTradeLineItem = new IncludedSpsTradeLineItem()
        .withScientificName(List.of(new TextType().withValue("Species name")))
        .withAdditionalInformationSpsNote(List.of(
            noteIdentifier("1", "MICROCHIP", "M-1234"),
            noteIdentifier("1", "PASSPORT", "P-1234"),
            note("IS_PLACE_OF_DESTINATION_THE_PERMANENT_ADDRESS", "1", "TRUE")
        ));

    List<Identifier> actual = mapper.map(includedSpsTradeLineItem);

    assertThat(actual).containsExactly(Identifier.builder()
        .speciesNumber(1)
        .data(Map.of(
            "microchip", "M-1234",
            "passport", "P-1234"
        ))
        .isPlaceOfDestinationThePermanentAddress(true)
        .build());
  }

  @Test
  void map_ReturnsIdentifiersAndPermanentAddresses_WhenMultipleAnimals() {
    IncludedSpsTradeLineItem includedSpsTradeLineItem = new IncludedSpsTradeLineItem()
        .withScientificName(List.of(new TextType().withValue("Species name")))
        .withAdditionalInformationSpsNote(List.of(
            noteIdentifier("1", "MICROCHIP", "M-1234"),
            noteIdentifier("1", "PASSPORT", "P-1234"),
            note("IS_PLACE_OF_DESTINATION_THE_PERMANENT_ADDRESS", "1", "FALSE"),
            note("PERMANENT_ADDRESS_TELEPHONE", "1", "0123456789"),
            note("PERMANENT_ADDRESS_EMAIL", "1", "me@example.com"),
            noteIdentifier("2", "MICROCHIP", "M-5678"),
            noteIdentifier("2", "PASSPORT", "P-5678"),
            note("IS_PLACE_OF_DESTINATION_THE_PERMANENT_ADDRESS", "2", "FALSE"),
            note("PERMANENT_ADDRESS_TELEPHONE", "2", "0987654321"),
            note("PERMANENT_ADDRESS_EMAIL", "2", "joe@example.com")
        ))
        .withAppliedSpsProcess(List.of(
            new AppliedSpsProcess()
                .withTypeCode(new ProcessTypeCodeType().withValue("43"))
                .withApplicableSpsProcessCharacteristic(List.of(
                    new ApplicableSpsProcessCharacteristic()
                        .withDescription(List.of(new TextType().withValue("1")))))
                .withOperatorSpsParty(new SpsPartyType()
                    .withName(new TextType().withValue("Address name 1"))
                    .withSpecifiedSpsAddress(new SpecifiedSpsAddress()
                        .withLineOne(new TextType().withValue("Line 1 1"))
                        .withLineTwo(new TextType().withValue("Line 2 1"))
                        .withCityName(new TextType().withValue("City 1"))
                        .withPostcodeCode(new CodeType().withValue("Postcode 1")))),
            new AppliedSpsProcess()
                .withTypeCode(new ProcessTypeCodeType().withValue("43"))
                .withApplicableSpsProcessCharacteristic(List.of(
                    new ApplicableSpsProcessCharacteristic()
                        .withDescription(List.of(new TextType().withValue("2")))))
                .withOperatorSpsParty(new SpsPartyType()
                    .withName(new TextType().withValue("Address name 2"))
                    .withSpecifiedSpsAddress(new SpecifiedSpsAddress()
                        .withLineOne(new TextType().withValue("Line 1 2"))
                        .withLineTwo(new TextType().withValue("Line 2 2"))
                        .withCityName(new TextType().withValue("City 2"))
                        .withPostcodeCode(new CodeType().withValue("Postcode 2"))))));

    try (MockedStatic<UUID> mockedUuid = Mockito.mockStatic(UUID.class)) {
      mockedUuid.when(UUID::randomUUID).thenReturn(TEST_UUID);

      List<Identifier> actual = mapper.map(includedSpsTradeLineItem);

      assertThat(actual).hasSize(2);
      assertThat(actual.get(0)).usingRecursiveComparison().withStrictTypeChecking().isEqualTo(
          Identifier.builder()
              .speciesNumber(1)
              .data(Map.of(
                  "microchip", "M-1234",
                  "passport", "P-1234"
              ))
              .isPlaceOfDestinationThePermanentAddress(false)
              .permanentAddress(EconomicOperator.builder()
                  .id(TEST_UUID.toString())
                  .type(EconomicOperatorType.CONSIGNEE)
                  .status(EconomicOperatorStatus.NON_APPROVED)
                  .companyName("Address name 1")
                  .individualName("Species name 1")
                  .address(EconomicOperatorAddress.builder()
                      .addressLine1("Line 1 1")
                      .addressLine2("Line 2 1")
                      .city("City 1")
                      .postalZipCode("Postcode 1")
                      .telephone("0123456789")
                      .email("me@example.com")
                      .build())
                  .build())
              .build());
      assertThat(actual.get(1)).usingRecursiveComparison().withStrictTypeChecking().isEqualTo(
          Identifier.builder()
              .speciesNumber(2)
              .data(Map.of(
                  "microchip", "M-5678",
                  "passport", "P-5678"
              ))
              .isPlaceOfDestinationThePermanentAddress(false)
              .permanentAddress(EconomicOperator.builder()
                  .id(TEST_UUID.toString())
                  .type(EconomicOperatorType.CONSIGNEE)
                  .status(EconomicOperatorStatus.NON_APPROVED)
                  .companyName("Address name 2")
                  .individualName("Species name 2")
                  .address(EconomicOperatorAddress.builder()
                      .addressLine1("Line 1 2")
                      .addressLine2("Line 2 2")
                      .city("City 2")
                      .postalZipCode("Postcode 2")
                      .telephone("0987654321")
                      .email("joe@example.com")
                      .build())
                  .build())
              .build());
    }
  }

  @Test
  void map_DoesntError_WhenMoreIdentifiersThanPermanentAddresses() {
    IncludedSpsTradeLineItem includedSpsTradeLineItem = new IncludedSpsTradeLineItem()
        .withScientificName(List.of(new TextType().withValue("Species name")))
        .withAdditionalInformationSpsNote(List.of(
            noteIdentifier("1", "MICROCHIP", "M-1"),
            noteIdentifier("2", "MICROCHIP", "M-2"),
            note("IS_PLACE_OF_DESTINATION_THE_PERMANENT_ADDRESS", "1", "FALSE"),
            note("PERMANENT_ADDRESS_TELEPHONE", "1", "0123456789"),
            note("PERMANENT_ADDRESS_EMAIL", "1", "me@example.com")
        ))
        .withAppliedSpsProcess(List.of(new AppliedSpsProcess()
            .withTypeCode(new ProcessTypeCodeType().withValue("43"))
            .withApplicableSpsProcessCharacteristic(List.of(new ApplicableSpsProcessCharacteristic()
                .withDescription(List.of(new TextType().withValue("1")))))
            .withOperatorSpsParty(new SpsPartyType()
                .withName(new TextType().withValue("Address name"))
                .withSpecifiedSpsAddress(new SpecifiedSpsAddress()
                    .withLineOne(new TextType().withValue("Line 1"))
                    .withLineTwo(new TextType().withValue("Line 2"))
                    .withCityName(new TextType().withValue("City"))
                    .withPostcodeCode(new CodeType().withValue("Postcode"))))));

    try (MockedStatic<UUID> mockedUuid = Mockito.mockStatic(UUID.class)) {
      mockedUuid.when(UUID::randomUUID).thenReturn(TEST_UUID);

      List<Identifier> actual = mapper.map(includedSpsTradeLineItem);

      assertThat(actual).containsExactly(
          Identifier.builder()
              .speciesNumber(1)
              .data(Map.of("microchip", "M-1"))
              .isPlaceOfDestinationThePermanentAddress(false)
              .permanentAddress(EconomicOperator.builder()
                  .id(TEST_UUID.toString())
                  .type(EconomicOperatorType.CONSIGNEE)
                  .status(EconomicOperatorStatus.NON_APPROVED)
                  .companyName("Address name")
                  .individualName("Species name 1")
                  .address(EconomicOperatorAddress.builder()
                      .addressLine1("Line 1")
                      .addressLine2("Line 2")
                      .city("City")
                      .postalZipCode("Postcode")
                      .telephone("0123456789")
                      .email("me@example.com")
                      .build())
                  .build())
              .build(),
          Identifier.builder()
              .speciesNumber(2)
              .data(Map.of("microchip", "M-2"))
              .build());
    }
  }

  @Test
  void map_DoesntError_WhenMorePermanentAddressesThanIdentifiers() {
    IncludedSpsTradeLineItem includedSpsTradeLineItem = new IncludedSpsTradeLineItem()
        .withScientificName(List.of(new TextType().withValue("Species name")))
        .withAdditionalInformationSpsNote(List.of(
            noteIdentifier("1", "MICROCHIP", "M-1"),
            note("IS_PLACE_OF_DESTINATION_THE_PERMANENT_ADDRESS", "1", "FALSE"),
            note("PERMANENT_ADDRESS_TELEPHONE", "1", "0123456789"),
            note("PERMANENT_ADDRESS_EMAIL", "1", "me@example.com"),
            note("IS_PLACE_OF_DESTINATION_THE_PERMANENT_ADDRESS", "2", "TRUE")
        ))
        .withAppliedSpsProcess(List.of(new AppliedSpsProcess()
            .withTypeCode(new ProcessTypeCodeType().withValue("43"))
            .withApplicableSpsProcessCharacteristic(List.of(new ApplicableSpsProcessCharacteristic()
                .withDescription(List.of(new TextType().withValue("1")))))
            .withOperatorSpsParty(new SpsPartyType()
                .withName(new TextType().withValue("Address name"))
                .withSpecifiedSpsAddress(new SpecifiedSpsAddress()
                    .withLineOne(new TextType().withValue("Line 1"))
                    .withLineTwo(new TextType().withValue("Line 2"))
                    .withCityName(new TextType().withValue("City"))
                    .withPostcodeCode(new CodeType().withValue("Postcode"))))));

    try (MockedStatic<UUID> mockedUuid = Mockito.mockStatic(UUID.class)) {
      mockedUuid.when(UUID::randomUUID).thenReturn(TEST_UUID);

      List<Identifier> actual = mapper.map(includedSpsTradeLineItem);

      assertThat(actual).containsExactly(Identifier.builder()
          .speciesNumber(1)
          .data(Map.of("microchip", "M-1"))
          .isPlaceOfDestinationThePermanentAddress(false)
          .permanentAddress(EconomicOperator.builder()
              .id(TEST_UUID.toString())
              .type(EconomicOperatorType.CONSIGNEE)
              .status(EconomicOperatorStatus.NON_APPROVED)
              .companyName("Address name")
              .individualName("Species name 1")
              .address(EconomicOperatorAddress.builder()
                  .addressLine1("Line 1")
                  .addressLine2("Line 2")
                  .city("City")
                  .postalZipCode("Postcode")
                  .telephone("0123456789")
                  .email("me@example.com")
                  .build())
              .build())
          .build());
    }
  }

  private SpsNoteType note(String subjectCode, String contentCode, String content) {
    return new SpsNoteType()
        .withSubjectCode(new CodeType().withValue(subjectCode))
        .withContentCode(List.of(new CodeType().withValue(contentCode)))
        .withContent(List.of(new TextType().withValue(content)));
  }

  private SpsNoteType noteIdentifier(String subject, String contentCode, String content) {
    return new SpsNoteType()
        .withSubjectCode(new CodeType().withValue("IDENTIFIER"))
        .withSubject(new TextType().withValue(subject))
        .withContentCode(List.of(new CodeType().withValue(contentCode)))
        .withContent(List.of(new TextType().withValue(content)));
  }
}
