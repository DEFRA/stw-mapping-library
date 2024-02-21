package uk.gov.defra.stw.mapping.toipaffs.cloning.common;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;

class ArrivalTimeMapperTest {

  private final ObjectMapper objectMapper = TestUtils.initObjectMapper();

  private ArrivalTimeMapper arrivalTimeMapper;
  private SpsCertificate spsCertificate;

  @BeforeEach
  void setup() throws JsonProcessingException {
    arrivalTimeMapper = new ArrivalTimeMapper();
    spsCertificate = JsonDeserializer.get(
            SpsCertificate.class, "cloning/cheda/cheda_ehc_complete.json", objectMapper);
  }

  @Test
  void map_ReturnsLocalTime_WhenGivenDateTime()
      throws NotificationMapperException {
    LocalTime expected = LocalTime.parse("14:30:00");
    LocalTime actual = arrivalTimeMapper
        .map(spsCertificate.getSpsConsignment().getAvailabilityDueDateTime());

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void map_ReturnsNull_WhenDateTimeIsNull()
      throws NotificationMapperException {
    LocalTime actual = arrivalTimeMapper.map(null);
    assertThat(actual).isNull();
  }
}
