package uk.gov.defra.stw.mapping.toipaffs.cloning.cheda.commodities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
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
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.common.common.commodities.CommodityComplementFieldMapper;
import uk.gov.defra.stw.mapping.toipaffs.testutils.ResourceUtils;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;

@ExtendWith(MockitoExtension.class)
class ChedaCommodityComplementMapperTest {

  private final ObjectMapper objectMapper = TestUtils.initObjectMapper();
  private SpsCertificate spsCertificate;

  @Mock
  private CommodityComplementFieldMapper commodityComplementFieldMapper;

  @InjectMocks
  private ChedaCommodityComplementMapper mapper;

  @BeforeEach
  void setup() throws JsonProcessingException {
    String jsonString =
        ResourceUtils.readFileToString("classpath:cloning/cheda/cheda_ehc_complete.json");
    spsCertificate = objectMapper.readValue(jsonString, SpsCertificate.class);
  }

  @Test
  void map_ReturnsChedpCommodityComplement_WhenCompleteEhcSpsCertificate()
      throws Exception {
    String expectedCommodityComplement =
        ResourceUtils.readFileToString("classpath:cloning/cheda/partone/commodities/cheda_ipaffs_commodityComplement_complete.json");
    when(commodityComplementFieldMapper.mapCommodityIdFromIpaffsCode(anyList())).thenReturn(
        "0101");
    when(commodityComplementFieldMapper.mapDescription(anyList())).thenReturn("Live horses, asses, mules and hinnies");

    List<CommodityComplement> commodityComplement = mapper.map(spsCertificate);
    String actualCommodityComplement = objectMapper.writeValueAsString(commodityComplement);

    assertThat(actualCommodityComplement).isEqualTo(expectedCommodityComplement);
  }
}
