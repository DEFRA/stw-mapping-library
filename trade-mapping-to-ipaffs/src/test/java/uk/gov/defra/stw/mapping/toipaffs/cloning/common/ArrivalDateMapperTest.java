package uk.gov.defra.stw.mapping.toipaffs.cloning.common;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;

class ArrivalDateMapperTest {

  private final ObjectMapper objectMapper = TestUtils.initObjectMapper();

  private ArrivalDateMapper arrivalDateMapper;
  private SpsCertificate spsCertificate;

  @BeforeEach
  void setup() throws JsonProcessingException {
    arrivalDateMapper = new ArrivalDateMapper();
    spsCertificate = JsonDeserializer.get(
            SpsCertificate.class, "cloning/cheda/cheda_ehc_complete.json", objectMapper);
  }

  @Test
  void map_ReturnsLocalDate_WhenGivenDateTime()
      throws NotificationMapperException {
    LocalDate expected = LocalDate.parse("2022-12-21");
    LocalDate actual = arrivalDateMapper
        .map(spsCertificate.getSpsConsignment().getAvailabilityDueDateTime());

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void map_ReturnsNull_WhenDateTimeIsNull()
      throws NotificationMapperException {
    LocalDate actual = arrivalDateMapper.map(null);
    assertThat(actual).isNull();
  }
}
