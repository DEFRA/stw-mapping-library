package uk.gov.defra.tracesx.cloning.cheda;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.tracesx.cloning.common.ArrivalDateMapper;
import uk.gov.defra.tracesx.cloning.common.ArrivalTimeMapper;
import uk.gov.defra.tracesx.common.common.PointOfEntryMapper;
import uk.gov.defra.tracesx.testutils.JsonDeserializer;
import uk.gov.defra.tracesx.testutils.TestUtils;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

@ExtendWith(MockitoExtension.class)
class ChedaPartOneTransportToBcpMapperTest {
  private final ObjectMapper objectMapper = TestUtils.initObjectMapper();
  private SpsCertificate spsCertificate;

  @Mock
  private ArrivalDateMapper arrivalDateMapper;
  @Mock
  private ArrivalTimeMapper arrivalTimeMapper;
  @Mock
  private PointOfEntryMapper pointOfEntryMapper;

  @InjectMocks
  private ChedaPartOneTransportToBcpMapper chedaPartOneTransportToBcpMapper;

  @BeforeEach
  void setup() throws JsonProcessingException {
    spsCertificate = JsonDeserializer.get(
        SpsCertificate.class, "cloning/cheda/cheda_ehc_complete.json", objectMapper);
  }

  @Test
  void mapPointOfEntry_ReturnsPointOfEntry_WhenCompleteSpsCertificate() throws Exception {
    when(pointOfEntryMapper.map(spsCertificate.getSpsConsignment().getUnloadingBaseportSpsLocation()))
        .thenReturn("GBLHR1P");
    String actual = chedaPartOneTransportToBcpMapper.mapPointOfEntry(spsCertificate);

    assertThat(actual).isEqualTo("GBLHR1P");
  }

  @Test
  void mapArrivalDate_ReturnsArrivalDate_WhenCompleteSpsCertificate() throws Exception {
    when(arrivalDateMapper.map(spsCertificate.getSpsConsignment().getAvailabilityDueDateTime()))
        .thenReturn(LocalDate.parse("2022-12-21"));
    LocalDate actual = chedaPartOneTransportToBcpMapper.mapArrivalDate(spsCertificate);

    assertThat(actual).isEqualTo(LocalDate.parse("2022-12-21"));
  }

  @Test
  void mapArrivalTime_ReturnsArrivalTime_WhenCompleteSpsCertificate() throws Exception {
    when(arrivalTimeMapper.map(spsCertificate.getSpsConsignment().getAvailabilityDueDateTime()))
        .thenReturn(LocalTime.parse("14:30:00"));
    LocalTime actual = chedaPartOneTransportToBcpMapper.mapArrivalTime(spsCertificate);

    assertThat(actual).isEqualTo(LocalTime.parse("14:30:00"));
  }
}
