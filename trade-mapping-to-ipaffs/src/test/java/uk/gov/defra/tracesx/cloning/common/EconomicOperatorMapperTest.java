package uk.gov.defra.tracesx.cloning.common;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorStatus.NON_APPROVED;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperator;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperatorAddress;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType;
import uk.gov.defra.tracesx.trade.dto.CodeType;
import uk.gov.defra.tracesx.trade.dto.IDType;
import uk.gov.defra.tracesx.trade.dto.SpecifiedSpsAddress;
import uk.gov.defra.tracesx.trade.dto.SpsPartyType;
import uk.gov.defra.tracesx.trade.dto.TextType;

class EconomicOperatorMapperTest {

  private static final String GREATER_THAN_MAX_CHARACTERS = "Isle of Man Government Office of Human "
      + "Resources, Learning Education and Development, The Lodge Education and Training Centre, "
      + "Braddan Road, Strang, Douglas, ISLE OF MAN, Isle of Man Government Office of Human "
      + "Resources, Learning Education";
  private EconomicOperatorMapper mapper;
  private SpsPartyType spsPartyType;

  @BeforeEach
  void setup() {
    mapper = new EconomicOperatorMapper();
    spsPartyType = createSpsPartyType();
  }

  @Test
  void map_ReturnsEconomicOperator_WhenValidSpsPartyType() throws Exception {
    EconomicOperator actualEconomicOperator = mapper.map(spsPartyType);

    EconomicOperator expectedEconomicOperator = createEconomicOperator();
    assertThat(actualEconomicOperator).isEqualTo(expectedEconomicOperator);
  }

  @Test
  void map_ReturnsEconomicOperator_WhenValidSpsPartyTypeWithMissingOptionalAddressLines() throws Exception {
    spsPartyType.getSpecifiedSpsAddress().setLineTwo(null);
    spsPartyType.getSpecifiedSpsAddress().setLineThree(null);
    spsPartyType.getSpecifiedSpsAddress().setLineFour(null);
    spsPartyType.getSpecifiedSpsAddress().setLineFive(null);

    EconomicOperator actualEconomicOperator = mapper.map(spsPartyType);

    EconomicOperator expectedEconomicOperator = createEconomicOperatorWithoutAddressLines();
    assertThat(actualEconomicOperator).isEqualTo(expectedEconomicOperator);
  }

  @Test
  void map_ReturnsEconomicOperator_WhenValidSpsPartyTypeWithMissingPostCode() throws Exception {
    spsPartyType.getSpecifiedSpsAddress().setPostcodeCode(null);

    EconomicOperator actualEconomicOperator = mapper.map(spsPartyType);

    EconomicOperator expectedEconomicOperator = createEconomicOperatorWithoutPostCode();
    assertThat(actualEconomicOperator).isEqualTo(expectedEconomicOperator);
  }

  @Test
  void map_ThrowsNotificationMapperException_WhenIdIsNull() {
    spsPartyType.withId(new IDType().withValue(null));

    assertThrows(NotificationMapperException.class, () -> mapper.map(spsPartyType));
  }

  @Test
  void map_ThrowsNotificationMapperException_WhenIdIsEmpty() {
    spsPartyType.getId().setValue("");

    assertThrows(NotificationMapperException.class, () -> mapper.map(spsPartyType));
  }

  @Test
  void map_ThrowsNotificationMapperException_WhenSpsPartyAddressIsNull() {
    spsPartyType.setSpecifiedSpsAddress(null);

    assertThrows(NotificationMapperException.class, () -> mapper.map(spsPartyType));
  }

  @Test
  void map_ThrowsNotificationMapperException_WhenSpsPartyAddressLine1IsNull() {
    spsPartyType.getSpecifiedSpsAddress().setLineOne(null);

    assertThrows(NotificationMapperException.class, () -> mapper.map(spsPartyType));
  }

  @Test
  void map_ThrowsNotificationMapperException_WhenSpsPartyAddressCityIsNull() {
    spsPartyType.getSpecifiedSpsAddress().setCityName(null);

    assertThrows(NotificationMapperException.class, () -> mapper.map(spsPartyType));
  }

  @Test
  void map_ThrowsNotificationMapperException_WhenSpsPartyAddressCountryIsNull() {
    spsPartyType.getSpecifiedSpsAddress().setCountryID(null);

    assertThrows(NotificationMapperException.class, () -> mapper.map(spsPartyType));
  }

  @Test
  void map_ThrowsNotificationMapperException_WhenLineThreeGreaterThanMaxCharacters() {
    spsPartyType.getSpecifiedSpsAddress().withLineThree(new TextType().withValue(GREATER_THAN_MAX_CHARACTERS));

    assertThrows(NotificationMapperException.class, () -> mapper.map(spsPartyType));
  }

  @Test
  void mapEconomicOperator_ReturnsNull_WhenEconomicOperatorIsNull()
      throws NotificationMapperException {
    EconomicOperator economicOperator = mapper
        .mapEconomicOperator(null, EconomicOperatorType.EXPORTER);

    assertThat(economicOperator).isNull();
  }

  private SpsPartyType createSpsPartyType() {
    return new SpsPartyType()
        .withId(new IDType().withValue("GB00092121"))
        .withName(new TextType().withValue("Oakfield (Foods) Ltd"))
        .withSpecifiedSpsAddress(new SpecifiedSpsAddress()
            .withLineOne(new TextType().withValue("5953 Francisco Plain"))
            .withLineTwo(new TextType().withValue("Suite 175"))
            .withLineThree(new TextType().withValue("Vermont"))
            .withLineFour(new TextType().withValue("Vermont"))
            .withLineFive(new TextType().withValue("Vermont"))
            .withCityName(new TextType().withValue("Lake Marcellus"))
            .withPostcodeCode(new CodeType().withValue("43348"))
            .withCountryID(new IDType().withValue("BR")));
  }

  private EconomicOperator createEconomicOperator() {
    return EconomicOperator.builder()
        .id("GB00092121")
        .status(NON_APPROVED)
        .companyName("Oakfield (Foods) Ltd")
        .otherIdentifier("GB00092121")
        .address(EconomicOperatorAddress.builder()
            .addressLine1("5953 Francisco Plain")
            .addressLine2("Suite 175")
            .addressLine3("Vermont, Vermont, Vermont")
            .city("Lake Marcellus")
            .postalZipCode("43348")
            .countryISOCode("BR")
            .telephone("Not specified")
            .email("not@specified.com")
            .build())
        .build();
  }

  private EconomicOperator createEconomicOperatorWithoutAddressLines() {
    EconomicOperator expectedEconomicOperator = createEconomicOperator();
    expectedEconomicOperator.getAddress().setAddressLine2(null);
    expectedEconomicOperator.getAddress().setAddressLine3(null);
    return expectedEconomicOperator;
  }

  private EconomicOperator createEconomicOperatorWithoutPostCode() {
    EconomicOperator expectedEconomicOperator = createEconomicOperator();
    expectedEconomicOperator.getAddress().setPostalZipCode(null);
    return expectedEconomicOperator;
  }
}
