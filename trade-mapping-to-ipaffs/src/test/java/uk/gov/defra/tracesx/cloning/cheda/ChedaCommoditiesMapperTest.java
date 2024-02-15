package uk.gov.defra.tracesx.cloning.cheda;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.tracesx.cloning.cheda.commodities.ChedaCommodityComplementMapper;
import uk.gov.defra.tracesx.cloning.cheda.commodities.ChedaComplementParameterSetMapper;
import uk.gov.defra.tracesx.common.common.CountryOfOriginMapper;
import uk.gov.defra.tracesx.common.common.RegionOfOriginMapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;
import uk.gov.defra.tracesx.testutils.ResourceUtils;
import uk.gov.defra.tracesx.testutils.TestUtils;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

@ExtendWith(MockitoExtension.class)
class ChedaCommoditiesMapperTest {

  private SpsCertificate spsCertificate;

  private final ObjectMapper objectMapper = TestUtils.initObjectMapper();

  @Mock
  private ChedaCommodityComplementMapper commodityComplementMapper;
  @Mock
  private ChedaComplementParameterSetMapper complementParameterSetMapper;
  @Mock
  private CountryOfOriginMapper countryOfOriginMapper;
  @Mock
  private RegionOfOriginMapper regionOfOriginMapper;

  @InjectMocks
  private ChedaCommoditiesMapper commoditiesMapper;

  @BeforeEach
  void setup() {
    spsCertificate = new SpsCertificate();
  }

  @Test
  void map_ReturnsChedaCommodities_WhenCompleteEhcCertificate()
      throws JsonProcessingException, NotificationMapperException {
    String expectedCommodities =
        ResourceUtils.readFileToString("classpath:cloning/cheda/partone/commodities/cheda_ipaffs_commodities.json");

    String expectedCommodityComplement =
        ResourceUtils.readFileToString(
            "classpath:cloning/cheda/partone/commodities/cheda_ipaffs_commodityComplement_complete.json");
    List<CommodityComplement> commodityComplements = objectMapper.readValue(expectedCommodityComplement, new TypeReference<>() {});

    String expectedComplementParameterSet = ResourceUtils.readFileToString(
        "classpath:cloning/cheda/partone/commodities/cheda_ipaffs_complementParameterSet_complete.json");

    List<ComplementParameterSet> complementParameterSet = objectMapper.readValue(expectedComplementParameterSet, new TypeReference<>() {});

    when(commodityComplementMapper.map(spsCertificate)).thenReturn(commodityComplements);
    when(complementParameterSetMapper.map(spsCertificate)).thenReturn(complementParameterSet);
    when(countryOfOriginMapper.map(spsCertificate)).thenReturn("NZ");
    when(regionOfOriginMapper.map(spsCertificate)).thenReturn("NZ");

    Commodities commodities = commoditiesMapper.map(spsCertificate);
    String actualCommodities = objectMapper.writeValueAsString(commodities);

    assertThat(actualCommodities).isEqualTo(expectedCommodities);
  }
}
