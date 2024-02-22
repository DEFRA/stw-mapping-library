package uk.gov.defra.stw.mapping.toipaffs.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.dto.SpsPartyType;

class TransportToBcpQuestionMapperTest {

  private final TransportToBcpQuestionMapper mapper =
      new TransportToBcpQuestionMapper();

  private SpsConsignment spsConsignment;

  @BeforeEach
  void setup() {
    spsConsignment = new SpsConsignment();
  }

  @ParameterizedTest
  @MethodSource("transportShipmentData")
  void map_ReturnsTrue_ForGivenSchemaId(String schemeName) {
    // Given
    spsConsignment.setMainCarriageSpsTransportMovement(
        List.of(buildSpsTransportMovement(schemeName)));

    // When
    boolean result = mapper.map(spsConsignment);

    // Then
    assertThat(result).isTrue();
  }


  private static Stream<Arguments> transportShipmentData() {
    return Stream.of(
        arguments(named("SpsTransportMovement is ShipImoNumberAfterBcp", "ship_imo_number_after_bcp")),
        arguments(named("SpsTransportMovement is TrainIdentifierAfterBcp", "train_identifier_after_bcp")),
        arguments(named("SpsTransportMovement is RoadVehicleRegistrationAfterBcp","road_vehicle_registration_after_bcp")),
        arguments(named("SpsTransportMovement is AirplaneFlightNumberAfterBcp", "airplane_flight_number_after_bcp"))
    );
  }

  @Test
  void map_ReturnsTrue_WhenSpsTransportMovementIsNotApplicableAndSpsCarrierPartyIsNotNull() {
    // Given
    spsConsignment.setMainCarriageSpsTransportMovement(
        List.of(buildSpsTransportMovement("non applicable value")));
    spsConsignment.setCarrierSpsParty(new SpsPartyType());

    // When
    boolean result = mapper.map(spsConsignment);

    // Then
    assertThat(result).isTrue();
  }

  @Test
  void map_ReturnsTrue_WhenMainCarriageSpsTransportMovementIsEmptyAndSpsCarrierPartyIsNotNull() {
    // Given
    spsConsignment.setMainCarriageSpsTransportMovement(Collections.emptyList());
    spsConsignment.setCarrierSpsParty(new SpsPartyType());

    // When
    boolean result = mapper.map(spsConsignment);

    // Then
    assertThat(result).isTrue();
  }

  @Test
  void map_ReturnsFalse_WhenMainCarriageSpsTransportMovementIsEmptyAndSpsCarrierPartyIsNull() {
    // Given
    spsConsignment.setMainCarriageSpsTransportMovement(Collections.emptyList());
    spsConsignment.setCarrierSpsParty(null);

    // When
    boolean result = mapper.map(spsConsignment);

    // Then
    assertThat(result).isFalse();
  }

  private MainCarriageSpsTransportMovement buildSpsTransportMovement(String schemeId) {
    return new MainCarriageSpsTransportMovement().withId(new IDType().withSchemeID(schemeId));
  }
}
