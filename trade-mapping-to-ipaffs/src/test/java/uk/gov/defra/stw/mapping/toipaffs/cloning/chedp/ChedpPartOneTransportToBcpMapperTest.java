package uk.gov.defra.stw.mapping.toipaffs.cloning.chedp;

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
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.ArrivalDateMapper;
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.ArrivalTimeMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.PointOfEntryMapper;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;

@ExtendWith(MockitoExtension.class)
class ChedpPartOneTransportToBcpMapperTest {

  @Mock
  private PointOfEntryMapper chedpPointOfEntryMapper;
  @Mock
  private ArrivalDateMapper arrivalDateMapper;
  @Mock
  private ArrivalTimeMapper arrivalTimeMapper;

  @InjectMocks
  private ChedpPartOneTransportToBcpMapper chedpPartOneTransportToBcpMapper;

  private SpsCertificate spsCertificate;

  @BeforeEach
  void setup() throws JsonProcessingException {
    ObjectMapper objectMapper = TestUtils.initObjectMapper();

    spsCertificate = JsonDeserializer
        .get(SpsCertificate.class, "common/chedp/chedp_ehc_complete.json", objectMapper);
  }

  @Test
  void mapPointOfEntry_ReturnsPointOfEntry_WhenCompleteSpsCertificate() throws Exception {
    when(chedpPointOfEntryMapper.map(spsCertificate.getSpsConsignment().getUnloadingBaseportSpsLocation()))
        .thenReturn("GBLHR1P");
    String actual = chedpPartOneTransportToBcpMapper.mapPointOfEntry(spsCertificate);

    assertThat(actual).isEqualTo("GBLHR1P");
  }
  @Test
  void mapArrivalDate_ReturnsArrivalDate_WhenCompleteSpsCertificate() throws Exception {
    when(arrivalDateMapper.map(spsCertificate.getSpsConsignment().getAvailabilityDueDateTime()))
        .thenReturn(LocalDate.parse("2022-12-21"));
    LocalDate actual = chedpPartOneTransportToBcpMapper.mapArrivalDate(spsCertificate);

    assertThat(actual).isEqualTo(LocalDate.parse("2022-12-21"));
  }
  @Test
  void mapArrivalTime_ReturnsArrivalTime_WhenCompleteSpsCertificate() throws Exception {
    when(arrivalTimeMapper.map(spsCertificate.getSpsConsignment().getAvailabilityDueDateTime()))
        .thenReturn(LocalTime.parse("14:30:00"));
    LocalTime actual = chedpPartOneTransportToBcpMapper.mapArrivalTime(spsCertificate);

    assertThat(actual).isEqualTo(LocalTime.parse("14:30:00"));
  }
}
