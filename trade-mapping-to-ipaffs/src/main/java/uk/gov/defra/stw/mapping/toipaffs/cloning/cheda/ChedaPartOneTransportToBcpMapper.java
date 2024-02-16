package uk.gov.defra.stw.mapping.toipaffs.cloning.cheda;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.ArrivalDateMapper;
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.ArrivalTimeMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.PointOfEntryMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;

@Component
public class ChedaPartOneTransportToBcpMapper {
  
  private final ArrivalDateMapper arrivalDateMapper;
  private final ArrivalTimeMapper arrivalTimeMapper;
  private final PointOfEntryMapper pointOfEntryMapper;
  
  public ChedaPartOneTransportToBcpMapper(ArrivalDateMapper arrivalDateMapper,
                                          ArrivalTimeMapper arrivalTimeMapper,
                                          PointOfEntryMapper pointOfEntryMapper) {
    this.arrivalDateMapper = arrivalDateMapper;
    this.arrivalTimeMapper = arrivalTimeMapper;
    this.pointOfEntryMapper = pointOfEntryMapper;
  }
  
  public String mapPointOfEntry(SpsCertificate spsCertificate)
      throws NotificationMapperException {
    return pointOfEntryMapper
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
