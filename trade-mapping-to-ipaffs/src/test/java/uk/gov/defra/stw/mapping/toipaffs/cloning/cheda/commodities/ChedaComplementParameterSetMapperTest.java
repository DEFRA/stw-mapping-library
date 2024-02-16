package uk.gov.defra.stw.mapping.toipaffs.cloning.cheda.commodities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.cloning.cheda.ChedaIdentifiersMapper;
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.CommoditySetKeyDataPairMapper;
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.NumberOfPackagesMapper;
import uk.gov.defra.stw.mapping.toipaffs.testutils.ResourceUtils;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.notificationschema.representation.Identifier;

@ExtendWith(MockitoExtension.class)
class ChedaComplementParameterSetMapperTest {

  private SpsCertificate spsCertificate;

  private final ObjectMapper objectMapper = TestUtils.initObjectMapper();

  @Mock
  private CommoditySetKeyDataPairMapper commoditySetKeyDataPairMapper;
  @Mock
  private NumberOfPackagesMapper numberOfPackagesMapper;
  @Mock
  private ChedaNumberOfAnimalsMapper chedaNumberOfAnimalsMapper;
  @Mock
  private ChedaIdentifiersMapper chedaIdentifiersMapper;

  @InjectMocks
  private ChedaComplementParameterSetMapper mapper;

  @BeforeEach
  void setup() throws JsonProcessingException {
    String jsonString =
        ResourceUtils.readFileToString("classpath:cloning/cheda/cheda_ehc_complete.json");
    spsCertificate = objectMapper.readValue(jsonString, SpsCertificate.class);
  }

  @Test
  void map_ReturnsChedaComplementParameterSet_WhenCompleteEhcSpsCertificate()
      throws Exception {
    String expectedCommodityComplement =
        ResourceUtils.readFileToString(
            "classpath:cloning/cheda/partone/commodities/cheda_ipaffs_complementParameterSet_complete.json");

    when(commoditySetKeyDataPairMapper.map(
        spsCertificate.getSpsConsignment().getIncludedSpsConsignmentItem().get(0)
            .getIncludedSpsTradeLineItem().get(0), numberOfPackagesMapper,
        chedaNumberOfAnimalsMapper)).thenReturn(
        List.of(new ComplementParameterSetKeyDataPair("number_package", "2"),
            new ComplementParameterSetKeyDataPair("number_animal", "5")));

    when(chedaIdentifiersMapper.map(
        spsCertificate.getSpsConsignment().getIncludedSpsConsignmentItem().get(0)
            .getIncludedSpsTradeLineItem().get(0))).thenReturn(
        List.of(Identifier.builder()
            .speciesNumber(1)
            .data(Map.of("identification_details", "Mark: MK67890"))
            .build()));

    List<ComplementParameterSet> commodityComplement = mapper.map(spsCertificate);
    String actualCommodityComplement = objectMapper.writeValueAsString(commodityComplement);
    expectedCommodityComplement = expectedCommodityComplement.replace("3f8bd1d2-199c-447f-955e-c5a5a9160c95",
        commodityComplement.get(0).getUniqueComplementID().toString());

    assertThat(actualCommodityComplement).isEqualTo(expectedCommodityComplement);
  }
}
