package uk.gov.defra.stw.mapping.toipaffs.chedp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.chedp.commodities.ChedpComplementParameterSetMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.CountryOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.RegionOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.CommodityComplementMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.CommodityTemperatureMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.NumberOfPackagesMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.TotalGrossWeightMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.TotalNetWeightMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.CommoditiesMapperException;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.ResourceUtils;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.CommodityTemperature;

@ExtendWith(MockitoExtension.class)
class ChedpCommoditiesMapperTest {

  @Mock
  private CommodityComplementMapper commodityComplementMapper;
  @Mock
  private ChedpComplementParameterSetMapper chedpComplementParameterSetMapper;
  @Mock
  private RegionOfOriginMapper regionOfOriginMapper;
  @Mock
  private TotalGrossWeightMapper totalGrossWeightMapper;
  @Mock
  private CommodityTemperatureMapper commodityTemperatureMapper;
  @Mock
  private CountryOfOriginMapper countryOfOriginMapper;
  @Mock
  private TotalNetWeightMapper totalNetWeightMapper;
  @Mock
  private NumberOfPackagesMapper numberOfPackagesMapper;

  @InjectMocks
  private ChedpCommoditiesMapper mapper;

  private ObjectMapper objectMapper;
  private SpsCertificate spsCertificate;

  private List<CommodityComplement> commodityComplements;
  private List<ComplementParameterSet> complementParameterSets;

  @BeforeEach
  void setup() throws JsonProcessingException, NotificationMapperException {
    objectMapper = TestUtils.initObjectMapper();

    spsCertificate = JsonDeserializer
        .get("chedp/chedp_ehc_complete.json", SpsCertificate.class);
    commodityComplements = JsonDeserializer.get(
        "chedp/partone/commodities/chedp_ipaffs_commodityComplement_complete.json",
        objectMapper.getTypeFactory().constructCollectionType(List.class, CommodityComplement.class)
    );
    complementParameterSets = JsonDeserializer.get(
        "chedp/partone/commodities/chedp_ipaffs_complementParameterSet_complete.json",
        objectMapper.getTypeFactory().constructCollectionType(List.class, ComplementParameterSet.class)
    );
  }

  @Test
  void map_ReturnsCommodities_WhenComplete() throws NotificationMapperException, JsonProcessingException {
    when(commodityComplementMapper.map(spsCertificate)).thenReturn(commodityComplements);
    when(chedpComplementParameterSetMapper.map(spsCertificate)).thenReturn(complementParameterSets);
    when(regionOfOriginMapper.map(spsCertificate)).thenReturn(null);
    when(commodityTemperatureMapper.map(spsCertificate)).thenReturn(CommodityTemperature.AMBIENT);
    when(countryOfOriginMapper.map(spsCertificate)).thenReturn("NZ");
    when(totalGrossWeightMapper.map(spsCertificate)).thenReturn(BigDecimal.valueOf(1800.0));
    when(totalNetWeightMapper.map(spsCertificate)).thenReturn(BigDecimal.valueOf(1678.52));
    when(numberOfPackagesMapper.map(spsCertificate)).thenReturn(10);

    Commodities commodities = mapper.map(spsCertificate);
    String actualCommodities = objectMapper.writeValueAsString(commodities);

    String expectedCommodities = ResourceUtils
        .readFileToString("classpath:chedp/partone/commodities/chedp_ipaffs_commodities_complete.json");
    assertThat(actualCommodities).isEqualTo(expectedCommodities);
  }

  @Test
  void map_ThrowsNotificationMapperException_WhenCommodityComplementMapperThrowsException()
      throws NotificationMapperException {
    when(commodityComplementMapper.map(spsCertificate)).thenThrow(new CommoditiesMapperException(""));

    assertThrows(NotificationMapperException.class, () -> mapper.map(spsCertificate));
  }
}
