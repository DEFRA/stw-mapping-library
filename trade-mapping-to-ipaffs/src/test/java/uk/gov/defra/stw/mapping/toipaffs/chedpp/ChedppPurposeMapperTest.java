package uk.gov.defra.stw.mapping.toipaffs.chedpp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.SpsAuthenticationType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.common.PurposeMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.ResourceUtils;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum;

@ExtendWith(MockitoExtension.class)
class ChedppPurposeMapperTest {
  @Mock
  private PurposeMapper purposeMapper;

  private ChedppPurposeMapper mapper;
  private ObjectMapper objectMapper;
  private List<SpsAuthenticationType> spsAuthenticationTypes;

  @BeforeEach
  void setup() throws JsonProcessingException {
    mapper = new ChedppPurposeMapper(purposeMapper);
    objectMapper = TestUtils.initObjectMapper();

    spsAuthenticationTypes = JsonDeserializer
        .get("chedpp/chedpp_trade_complete.json", SpsCertificate.class)
        .getSpsExchangedDocument().getSignatorySpsAuthentication();
  }

  @Test
  void map_ThrowsNotificationMapperException_WhenPurposeNullCheckThrowsException() {
    assertThrows(NotificationMapperException.class, () -> mapper.map(Collections.emptyList()));
  }

  @Test
  void map_ThrowsNotificationMapperException_WhenPurposeMapperReturnsNull()
      throws NotificationMapperException {
    when(purposeMapper.map(spsAuthenticationTypes.get(0))).thenReturn(null);
    assertThrows(NotificationMapperException.class, () -> mapper.map(spsAuthenticationTypes));
  }

  @Test
  void map_ReturnsPurpose_WhenPurposeIsComplete()
      throws NotificationMapperException, JsonProcessingException {
    when(purposeMapper.map(spsAuthenticationTypes.get(0))).thenReturn(
        Purpose.builder()
            .purposeGroup(PurposeGroupEnum.IMPORT)
            .build());

    String expectedPurpose = ResourceUtils
        .readFileToString(
            "classpath:chedpp/partone/purpose/chedpp_ipaffs_purpose_complete.json");

    Purpose purpose = mapper.map(spsAuthenticationTypes);
    String actualPurpose = objectMapper.writeValueAsString(purpose);

    assertThat(actualPurpose).isEqualTo(expectedPurpose);
  }
}
