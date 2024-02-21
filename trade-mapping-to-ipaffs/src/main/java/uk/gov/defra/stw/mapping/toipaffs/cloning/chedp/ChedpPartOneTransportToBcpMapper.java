package uk.gov.defra.stw.mapping.toipaffs.cloning.chedp;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.ArrivalDateMapper;
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.ArrivalTimeMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.PointOfEntryMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;

@Component
public class ChedpPartOneTransportToBcpMapper {

  private final PointOfEntryMapper chedpPointOfEntryMapper;
  private final ArrivalDateMapper arrivalDateMapper;
  private final ArrivalTimeMapper arrivalTimeMapper;

  public ChedpPartOneTransportToBcpMapper(PointOfEntryMapper chedpPointOfEntryMapper,
      ArrivalDateMapper arrivalDateMapper,
      ArrivalTimeMapper arrivalTimeMapper) {
    this.chedpPointOfEntryMapper = chedpPointOfEntryMapper;
    this.arrivalDateMapper = arrivalDateMapper;
    this.arrivalTimeMapper = arrivalTimeMapper;
  }

  public String mapPointOfEntry(SpsCertificate spsCertificate)
      throws NotificationMapperException {
    return chedpPointOfEntryMapper
        .map(spsCertificate.getSpsConsignment().getUnloadingBaseportSpsLocation());
  }

  public LocalDate mapArrivalDate(SpsCertificate spsCertificate)
      throws NotificationMapperException {
    return arrivalDateMapper
        .map(spsCertificate.getSpsConsignment().getAvailabilityDueDateTime());
  }

  public LocalTime mapArrivalTime(SpsCertificate spsCertificate)
      throws NotificationMapperException {
    return arrivalTimeMapper
        .map(spsCertificate.getSpsConsignment().getAvailabilityDueDateTime());
  }
}
