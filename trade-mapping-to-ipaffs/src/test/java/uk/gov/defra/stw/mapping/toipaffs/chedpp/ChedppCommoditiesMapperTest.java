package uk.gov.defra.stw.mapping.toipaffs.chedpp;

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
import uk.gov.defra.stw.mapping.toipaffs.common.CountryOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.RegionOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.TotalGrossWeightMapper;
import uk.gov.defra.stw.mapping.toipaffs.chedpp.commodities.ChedppCommodityComplementMapper;
import uk.gov.defra.stw.mapping.toipaffs.chedpp.commodities.ChedppComplementParameterSetMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.CommoditiesMapperException;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.ResourceUtils;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;

@ExtendWith(MockitoExtension.class)
class ChedppCommoditiesMapperTest {

  @Mock
  private ChedppCommodityComplementMapper chedppCommodityComplementMapper;

  @Mock
  private ChedppComplementParameterSetMapper chedppComplementParameterSetMapper;

  @Mock
  private RegionOfOriginMapper regionOfOriginMapper;

  @Mock
  private TotalGrossWeightMapper totalGrossWeightMapper;

  @Mock
  private CountryOfOriginMapper countryOfOriginMapper;

  private ChedppCommoditiesMapper mapper;
  private ObjectMapper objectMapper;
  private SpsCertificate spsCertificate;

  private List<CommodityComplement> commodityComplements;
  private List<ComplementParameterSet> complementParameterSets;

  @BeforeEach
  void setup() throws JsonProcessingException {
    mapper = new ChedppCommoditiesMapper(
        chedppCommodityComplementMapper,
        chedppComplementParameterSetMapper,
        regionOfOriginMapper,
        totalGrossWeightMapper,
        countryOfOriginMapper);
    objectMapper = TestUtils.initObjectMapper();

    spsCertificate = JsonDeserializer
        .get(SpsCertificate.class, "chedpp/chedpp_ehc_complete.json", objectMapper);

    commodityComplements = JsonDeserializer.get(objectMapper.getTypeFactory().constructCollectionType(List.class, CommodityComplement.class),
        "chedpp/partone/commodities/chedpp_ipaffs_commodityComplement_complete.json", objectMapper);
    complementParameterSets = JsonDeserializer.get(objectMapper.getTypeFactory().constructCollectionType(List.class, ComplementParameterSet.class),
        "chedpp/partone/commodities/chedpp_ipaffs_complementParameterSet_complete.json", objectMapper);
  }

  @Test
  void map_ReturnsCommodities_WhenComplete() throws NotificationMapperException, JsonProcessingException {
    when(chedppCommodityComplementMapper.map(spsCertificate)).thenReturn(commodityComplements);
    when(chedppComplementParameterSetMapper.map(spsCertificate)).thenReturn(complementParameterSets);
    when(regionOfOriginMapper.map(spsCertificate)).thenReturn("GE");
    when(countryOfOriginMapper.map(spsCertificate)).thenReturn("NL");

    Commodities commodities = mapper.map(spsCertificate);
    String actualCommodities = objectMapper.writeValueAsString(commodities);

    String expectedCommodities = ResourceUtils
        .readFileToString("classpath:chedpp/partone/commodities/chedpp_ipaffs_commodities_complete.json");
    assertThat(actualCommodities).isEqualTo(expectedCommodities);
  }

  @Test
  void map_ThrowsNotificationMapperException_WhenCommodityComplementMapperThrowsException() {
    when(chedppCommodityComplementMapper.map(spsCertificate)).thenThrow(new CommoditiesMapperException(""));

    assertThrows(NotificationMapperException.class, () -> mapper.map(spsCertificate));
  }

  @Test
  void map_ThrowsNotificationMapperException_WhenComplementParameterSetMapperThrowsException() {
    when(chedppComplementParameterSetMapper.map(spsCertificate)).thenThrow(new CommoditiesMapperException(""));

    assertThrows(NotificationMapperException.class, () -> mapper.map(spsCertificate));
  }
}
