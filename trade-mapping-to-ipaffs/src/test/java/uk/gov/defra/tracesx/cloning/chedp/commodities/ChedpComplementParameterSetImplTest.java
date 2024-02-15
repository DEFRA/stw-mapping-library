package uk.gov.defra.tracesx.cloning.chedp.commodities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.tracesx.cloning.common.CommoditySetKeyDataPairMapper;
import uk.gov.defra.tracesx.cloning.common.NumberOfPackagesMapper;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.testutils.ResourceUtils;
import uk.gov.defra.tracesx.testutils.TestUtils;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsConsignmentItem;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

@ExtendWith(MockitoExtension.class)
class ChedpComplementParameterSetImplTest {

  private SpsCertificate spsCertificate;

  private final ObjectMapper objectMapper = TestUtils.initObjectMapper();

  @Mock
  private CommoditySetKeyDataPairMapper commoditySetKeyDataPairMapper;
  @Mock
  private ChedpNetWeightMeasureMapper chedpNetWeightMeasureMapper;
  @Mock
  private NumberOfPackagesMapper numberOfPackagesMapper;
  @Mock
  private ChedpPackageTypeMapper chedpPackageTypeMapper;

  @InjectMocks
  private ChedpComplementParameterSetMapperImpl mapper;

  @BeforeEach
  void setup() throws JsonProcessingException {
    String jsonString =
        ResourceUtils.readFileToString("classpath:common/chedp/chedp_ehc_complete.json");
    spsCertificate = objectMapper.readValue(jsonString, SpsCertificate.class);
  }

  @Test
  void create_ReturnsComplementParameterSetKeyDataPairList_WhenComplete()
      throws JsonProcessingException {
    String expectedCommodityComplement =
        ResourceUtils.readFileToString(
            "classpath:common/chedp/partone/commodities/chedp_ipaffs_complementParameterSet_complete.json");

    List<IncludedSpsConsignmentItem> consignmentItems = spsCertificate.getSpsConsignment().getIncludedSpsConsignmentItem();

    when(commoditySetKeyDataPairMapper.map(
        consignmentItems.get(0).getIncludedSpsTradeLineItem().get(0), chedpNetWeightMeasureMapper,
        numberOfPackagesMapper, chedpPackageTypeMapper))
        .thenReturn(createComplementParameterSet("939.26", "2", "Bag"));
    when(commoditySetKeyDataPairMapper.map(
        consignmentItems.get(1).getIncludedSpsTradeLineItem().get(0), chedpNetWeightMeasureMapper,
        numberOfPackagesMapper, chedpPackageTypeMapper))
        .thenReturn(createComplementParameterSet("739.26", "3", "Package"));

    List<ComplementParameterSet> commodityComplement = mapper.map(spsCertificate);
    String actualCommodityComplement = objectMapper.writeValueAsString(commodityComplement);
    expectedCommodityComplement = expectedCommodityComplement.replace("0d1be0bc-a7c9-430e-8b8c-5f18d83f687d",
        commodityComplement.get(0).getUniqueComplementID().toString());
    expectedCommodityComplement = expectedCommodityComplement.replace("1e364d17-5ba6-49e7-953f-338b9792557b",
        commodityComplement.get(1).getUniqueComplementID().toString());

    assertThat(actualCommodityComplement).isEqualTo(expectedCommodityComplement);
  }

  private List<ComplementParameterSetKeyDataPair> createComplementParameterSet(String netWeight, String numberPackage, String typePackage) {
    return List.of(
        new ComplementParameterSetKeyDataPair("netweight", netWeight),
        new ComplementParameterSetKeyDataPair("number_package", numberPackage),
        new ComplementParameterSetKeyDataPair("type_package", typePackage));
  }
}
