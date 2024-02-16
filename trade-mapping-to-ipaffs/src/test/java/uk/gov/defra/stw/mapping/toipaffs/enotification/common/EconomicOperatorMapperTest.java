package uk.gov.defra.stw.mapping.toipaffs.enotification.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.SpsPartyType;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperator;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType;

class EconomicOperatorMapperTest {

  private EconomicOperatorMapper economicOperatorMapper;

  private SpsPartyType spsPartyType;

  @BeforeEach
  void setup() {
    economicOperatorMapper = new EconomicOperatorMapper();
  }

  @Test
  void map_ReturnsEconomicOperator_WhenRequiredFieldsPresent()
      throws NotificationMapperException {
    spsPartyType = new SpsPartyType()
        .withId(new IDType().withValue("uuid"));

    EconomicOperator actual = economicOperatorMapper.map(spsPartyType);

    assertThat(actual).isNotNull();
    assertThat(actual.getId()).isEqualTo("uuid");
  }

  @Test
  void map_ReturnsNull_WhenIdIsNull() throws NotificationMapperException {
    spsPartyType = new SpsPartyType().withId(null);

    EconomicOperator actual = economicOperatorMapper.map(spsPartyType);

    assertThat(actual).isNull();
  }

  @Test
  void map_ReturnsNull_WhenIdValueIsNull() throws NotificationMapperException {
    spsPartyType = new SpsPartyType().withId(new IDType().withValue(null));

    EconomicOperator actual = economicOperatorMapper.map(spsPartyType);

    assertThat(actual).isNull();
  }

  @Test
  void map_ReturnsNull_WhenIdValueIsEmpty() throws NotificationMapperException {
    spsPartyType = new SpsPartyType().withId(new IDType().withValue(""));

    EconomicOperator actual = economicOperatorMapper.map(spsPartyType);

    assertThat(actual).isNull();
  }

  @Test
  void map_ReturnsNull_WhenSpsPartyTypeIsNull() throws NotificationMapperException {
    EconomicOperator actual = economicOperatorMapper.map(spsPartyType);

    assertThat(actual).isNull();
  }

  @Test
  void setEconomicOperatorMapper_ReturnsNull_WhenEconomicOperatorIsNull() {
    EconomicOperator economicOperator = economicOperatorMapper
        .setEconomicOperatorType(null, EconomicOperatorType.EXPORTER);

    assertThat(economicOperator).isNull();
  }
}
