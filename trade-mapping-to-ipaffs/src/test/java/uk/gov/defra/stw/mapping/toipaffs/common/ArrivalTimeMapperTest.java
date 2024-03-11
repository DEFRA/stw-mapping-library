package uk.gov.defra.stw.mapping.toipaffs.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import uk.gov.defra.stw.mapping.dto.DateTime;
import uk.gov.defra.stw.mapping.dto.DateTimeType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;

public class ArrivalTimeMapperTest {
  private ArrivalTimeMapper arrivalTimeMapper;
  private SpsCertificate spsCertificate;

  @Test
  void map_ReturnsArrivalTime_WhenProvided() {
    arrivalTimeMapper = new ArrivalTimeMapper();
    spsCertificate = new SpsCertificate();
    spsCertificate.withSpsConsignment(new SpsConsignment()
        .withAvailabilityDueDateTime(new DateTimeType()
            .withDateTime(new DateTime()
                .withValue("2020-01-01T22:30:00Z"))));

    LocalTime actualTime = arrivalTimeMapper.map(spsCertificate);

    assertThat(actualTime).isEqualTo("22:30:00");
  }

  @Test
  void map_ReturnsArrivalTime_WhenDaylightSavings() {
    arrivalTimeMapper = new ArrivalTimeMapper();
    spsCertificate = new SpsCertificate();
    spsCertificate.withSpsConsignment(new SpsConsignment()
        .withAvailabilityDueDateTime(new DateTimeType()
            .withDateTime(new DateTime()
                .withValue("2024-04-01T00:00:00Z"))));

    LocalTime actualTime = arrivalTimeMapper.map(spsCertificate);

    assertThat(actualTime).isEqualTo("01:00:00");
  }
}
