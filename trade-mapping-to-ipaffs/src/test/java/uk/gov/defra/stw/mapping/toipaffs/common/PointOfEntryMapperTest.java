package uk.gov.defra.stw.mapping.toipaffs.common;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.SpsLocationType;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;

class PointOfEntryMapperTest {

  private static final String LOCATION_ID = "GBLHR1P";

  private PointOfEntryMapper pointOfEntryMapper;

  @BeforeEach
  void setup() {
    pointOfEntryMapper = new PointOfEntryMapper();
  }

  @Test
  void map_ReturnsString_WhenComplete() throws NotificationMapperException {
    SpsLocationType spsLocationType = new SpsLocationType()
        .withId(new IDType().withValue(LOCATION_ID));
    assertThat(pointOfEntryMapper.map(spsLocationType)).isEqualTo(LOCATION_ID);
  }

  @Test
  void map_ReturnsNull_WhenNullSpsLocationType() throws NotificationMapperException {
    assertThat(pointOfEntryMapper.map(null)).isNull();
  }

  @Test
  void map_ReturnsNull_WhenNullSpsLocationTypeID() throws NotificationMapperException {
    assertThat(pointOfEntryMapper.map(new SpsLocationType().withId(null))).isNull();
  }
}
