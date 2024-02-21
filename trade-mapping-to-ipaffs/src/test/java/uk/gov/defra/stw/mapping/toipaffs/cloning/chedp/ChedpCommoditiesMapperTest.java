package uk.gov.defra.stw.mapping.toipaffs.cloning.chedp;

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
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.cloning.chedp.commodities.ChedpComplementParameterSetMapperImpl;
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.ConsignedCountryMapper;
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.CountryCodeTransformer;
import uk.gov.defra.stw.mapping.toipaffs.common.chedp.ChedpTemperatureMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.chedp.commodities.ChedpCommodityComplementMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.CountryOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.RegionOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.commodities.TotalGrossWeightMapper;
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
  private ChedpCommodityComplementMapper chedpCommodityComplementMapper;
  @Mock
  private ChedpComplementParameterSetMapperImpl chedpComplementParameterSetMapper;
  @Mock
  private RegionOfOriginMapper regionOfOriginMapper;
  @Mock
  private TotalGrossWeightMapper totalGrossWeightMapper;
  @Mock
  private ChedpTemperatureMapper chedpTemperatureMapper;
  @Mock
  private CountryOfOriginMapper countryOfOriginMapper;
  @Mock
  private ConsignedCountryMapper consignedCountryMapper;
  @Mock
  private CountryCodeTransformer countryCodeTransformer;

  private ChedpCommoditiesMapper mapper;
  private ObjectMapper objectMapper;
  private SpsCertificate spsCertificate;

  private List<CommodityComplement> commodityComplements;
  private List<ComplementParameterSet> complementParameterSets;

  @BeforeEach
  void setup() throws JsonProcessingException {
    mapper = new ChedpCommoditiesMapper(chedpCommodityComplementMapper,
        chedpComplementParameterSetMapper, regionOfOriginMapper, totalGrossWeightMapper,
        countryOfOriginMapper, consignedCountryMapper, chedpTemperatureMapper, countryCodeTransformer);

    objectMapper = TestUtils.initObjectMapper();

    spsCertificate = JsonDeserializer
        .get(SpsCertificate.class, "common/chedp/chedp_ehc_complete.json", objectMapper);

    commodityComplements = JsonDeserializer.get(objectMapper.getTypeFactory().constructCollectionType(List.class, CommodityComplement.class),
        "cloning/chedp/partone/commodities/chedp_ipaffs_commodityComplement_complete.json", objectMapper);
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
    when(consignedCountryMapper.map(spsCertificate)).thenReturn("NZ");
    when(countryCodeTransformer.convertCountryCode("NZ")).thenReturn("NZ");

    Commodities commodities = mapper.map(spsCertificate);
    String actualCommodities = objectMapper.writeValueAsString(commodities);

    String expectedCommodities = ResourceUtils
        .readFileToString("classpath:cloning/chedp/partone/commodities/chedp_ipaffs_commodities_complete.json");
    assertThat(actualCommodities).isEqualTo(expectedCommodities);
  }

  @Test
  void map_ThrowsNotificationMapperException_WhenCommodityComplementMapperThrowsException()
      throws NotificationMapperException {
    when(chedpCommodityComplementMapper.map(spsCertificate)).thenThrow(new CommoditiesMapperException(""));

    assertThrows(NotificationMapperException.class, () -> mapper.map(spsCertificate));
  }
}
