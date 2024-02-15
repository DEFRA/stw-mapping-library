package uk.gov.defra.tracesx.enotification.chedp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.tracesx.common.chedp.ChedpTemperatureMapper;
import uk.gov.defra.tracesx.common.chedp.commodities.ChedpCommodityComplementMapper;
import uk.gov.defra.tracesx.common.chedp.commodities.ChedpComplementParameterSetMapper;
import uk.gov.defra.tracesx.common.common.CountryOfOriginMapper;
import uk.gov.defra.tracesx.common.common.RegionOfOriginMapper;
import uk.gov.defra.tracesx.common.common.commodities.TotalGrossWeightMapper;
import uk.gov.defra.tracesx.exceptions.CommoditiesMapperException;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.CommodityTemperature;
import uk.gov.defra.tracesx.testutils.JsonDeserializer;
import uk.gov.defra.tracesx.testutils.ResourceUtils;
import uk.gov.defra.tracesx.testutils.TestUtils;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

@ExtendWith(MockitoExtension.class)
class ChedpCommoditiesMapperTest {

  @Mock
  private ChedpCommodityComplementMapper chedpCommodityComplementMapper;
  @Mock
  private ChedpComplementParameterSetMapper chedpComplementParameterSetMapper;
  @Mock
  private RegionOfOriginMapper regionOfOriginMapper;
  @Mock
  private TotalGrossWeightMapper totalGrossWeightMapper;
  @Mock
  private ChedpTemperatureMapper chedpTemperatureMapper;
  @Mock
  private CountryOfOriginMapper countryOfOriginMapper;

  private ChedpCommoditiesMapper mapper;
  private ObjectMapper objectMapper;
  private SpsCertificate spsCertificate;

  private List<CommodityComplement> commodityComplements;
  private List<ComplementParameterSet> complementParameterSets;

  @BeforeEach
  void setup() throws JsonProcessingException, NotificationMapperException {
    mapper = new ChedpCommoditiesMapper(chedpCommodityComplementMapper,
        chedpComplementParameterSetMapper, regionOfOriginMapper, totalGrossWeightMapper,
        chedpTemperatureMapper, countryOfOriginMapper);
    objectMapper = TestUtils.initObjectMapper();

    spsCertificate = JsonDeserializer
        .get(SpsCertificate.class, "common/chedp/chedp_ehc_complete.json", objectMapper);

    commodityComplements = JsonDeserializer.get(objectMapper.getTypeFactory().constructCollectionType(List.class, CommodityComplement.class),
        "enotification/chedp/partone/commodities/chedp_ipaffs_commodityComplement_complete.json", objectMapper);
    complementParameterSets = JsonDeserializer.get(objectMapper.getTypeFactory().constructCollectionType(List.class, ComplementParameterSet.class),
        "common/chedp/partone/commodities/chedp_ipaffs_complementParameterSet_complete.json", objectMapper);
  }

  @Test
  void map_ReturnsCommodities_WhenComplete() throws NotificationMapperException, JsonProcessingException {
    when(chedpCommodityComplementMapper.map(spsCertificate)).thenReturn(commodityComplements);
    when(chedpComplementParameterSetMapper.map(spsCertificate)).thenReturn(complementParameterSets);
    when(regionOfOriginMapper.map(spsCertificate)).thenReturn(null);
    when(chedpTemperatureMapper.map(spsCertificate)).thenReturn(CommodityTemperature.AMBIENT);
    when(countryOfOriginMapper.map(spsCertificate)).thenReturn("NZ");

    Commodities commodities = mapper.map(spsCertificate);
    String actualCommodities = objectMapper.writeValueAsString(commodities);

    String expectedCommodities = ResourceUtils
        .readFileToString("classpath:enotification/chedp/partone/commodities/chedp_ipaffs_commodities_complete.json");
    assertThat(actualCommodities).isEqualTo(expectedCommodities);
  }

  @Test
  void map_ThrowsNotificationMapperException_WhenCommodityComplementMapperThrowsException()
      throws NotificationMapperException {
    when(chedpCommodityComplementMapper.map(spsCertificate)).thenThrow(new CommoditiesMapperException(""));

    assertThrows(NotificationMapperException.class, () -> mapper.map(spsCertificate));
  }
}
