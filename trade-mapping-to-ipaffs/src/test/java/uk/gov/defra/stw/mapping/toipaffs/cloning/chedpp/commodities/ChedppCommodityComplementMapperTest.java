package uk.gov.defra.stw.mapping.toipaffs.cloning.chedpp.commodities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.ApplicableSpsClassification;
import uk.gov.defra.stw.mapping.dto.SequenceNumeric;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.CommoditiesMapperException;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.ResourceUtils;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;

class ChedppCommodityComplementMapperTest {

  private ChedppCommodityComplementMapper mapper;
  private ObjectMapper objectMapper;
  private SpsCertificate spsCertificate;

  @BeforeEach
  void setup() throws JsonProcessingException {
    mapper = new ChedppCommodityComplementMapper();
    objectMapper = TestUtils.initObjectMapper();

    spsCertificate = JsonDeserializer
        .get(SpsCertificate.class, "cloning/chedpp/chedpp_ehc_complete.json", objectMapper);
  }

  @Test
  void map_ReturnsCommodityComplement_WhenComplete() throws JsonProcessingException {
    String expectedCommodityComplement = ResourceUtils
        .readFileToString("classpath:cloning/chedpp/partone/commodities/chedpp_ipaffs_commodityComplement_complete.json");

    List<CommodityComplement> commodityComplement = mapper.map(spsCertificate);
    String actualCommodityComplement = objectMapper.writeValueAsString(commodityComplement);

    assertThat(actualCommodityComplement).isEqualTo(expectedCommodityComplement);
  }

  @Test
  void map_ThrowsCommoditiesMapperException_WhenNotIpaffsCommodityCode() {
    getFirstApplicableSpsClassification().getSystemName().get(0).setValue("Another CN Code");

    assertThrows(CommoditiesMapperException.class, () -> mapper.map(spsCertificate));
  }

  @Test
  void map_ThrowsCommoditiesMapperException_WhenNullClassCode() {
    getFirstApplicableSpsClassification().setClassCode(null);

    assertThrows(CommoditiesMapperException.class, () -> mapper.map(spsCertificate));
  }

  @Test
  void map_ReturnsEmptyList_WhenSequenceNumericIsZero() {
    spsCertificate.getSpsConsignment()
        .getIncludedSpsConsignmentItem().get(0)
        .getIncludedSpsTradeLineItem().get(0).setSequenceNumeric(new SequenceNumeric().withValue(0));

    List<CommodityComplement> actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo(Collections.emptyList());
  }

  @Test
  void map_ThrowsCommoditiesMapperException_WhenNullClassNameValue() {
    getFirstApplicableSpsClassification().getClassName().get(0).setValue(null);

    assertThrows(CommoditiesMapperException.class, () -> mapper.map(spsCertificate));
  }

  private ApplicableSpsClassification getFirstApplicableSpsClassification() {
    return spsCertificate.getSpsConsignment()
        .getIncludedSpsConsignmentItem().get(0)
        .getIncludedSpsTradeLineItem().get(0)
        .getApplicableSpsClassification().get(0);
  }
}
