package uk.gov.defra.stw.mapping.toipaffs.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import uk.gov.defra.stw.mapping.dto.DateTime;
import uk.gov.defra.stw.mapping.dto.DateTimeType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;

public class ArrivalDateMapperTest {
  private ArrivalDateMapper arrivalDateMapper;
  private SpsCertificate spsCertificate;

  @Test
  void map_ReturnsArrivalDate_WhenProvided() {
    arrivalDateMapper = new ArrivalDateMapper(); 
    spsCertificate = new SpsCertificate();
    spsCertificate.withSpsConsignment(new SpsConsignment()
    .withAvailabilityDueDateTime(new DateTimeType()
        .withDateTime(new DateTime()
            .withValue("2020-01-01T22:30:00Z"))));

    LocalDate actualDate = arrivalDateMapper.map(spsCertificate);

    assertThat(actualDate).isEqualTo("2020-01-01");
  }

  @Test
  void map_ReturnsArrivalDate_WhenDaylightSavings() {
    arrivalDateMapper = new ArrivalDateMapper(); 
    spsCertificate = new SpsCertificate();
    spsCertificate.withSpsConsignment(new SpsConsignment()
    .withAvailabilityDueDateTime(new DateTimeType()
        .withDateTime(new DateTime()
            .withValue("2024-03-31T23:59:00Z"))));

    LocalDate actualDate = arrivalDateMapper.map(spsCertificate);

    assertThat(actualDate).isEqualTo("2024-04-01");
  }
}
