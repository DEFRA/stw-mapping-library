package uk.gov.defra.tracesx.cloning.cheda;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.cloning.common.ArrivalDateMapper;
import uk.gov.defra.tracesx.cloning.common.ArrivalTimeMapper;
import uk.gov.defra.tracesx.common.common.PointOfEntryMapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

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
