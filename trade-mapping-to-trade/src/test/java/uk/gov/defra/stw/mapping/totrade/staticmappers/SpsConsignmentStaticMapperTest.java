package uk.gov.defra.stw.mapping.totrade.staticmappers;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.totrade.testutils.ResourceUtil;

class SpsConsignmentStaticMapperTest {

  private SpsConsignment spsConsignmentTypeNoStaticValues;
  private SpsConsignment spsConsignmentTypeStaticValuesSet;
  private SpsConsignmentStaticMapper spsConsignmentStaticMapper;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    spsConsignmentStaticMapper = new SpsConsignmentStaticMapper();

    String spsCertificateTypeNoStaticString = ResourceUtil.readFileToString(
        "/mapping/staticmappers/trade_no_static_values_set.json");
    String spsCertificateTypeStaticValuesSetString = ResourceUtil.readFileToString(
        "/mapping/staticmappers/trade.json");

    SpsCertificate spsCertificateTypeNoStaticValues = objectMapper.readValue(spsCertificateTypeNoStaticString, SpsCertificate.class);
    SpsCertificate spsCertificateTypeStaticValuesSet = objectMapper.readValue(spsCertificateTypeStaticValuesSetString, SpsCertificate.class);

    spsConsignmentTypeNoStaticValues = spsCertificateTypeNoStaticValues.getSpsConsignment();
    spsConsignmentTypeStaticValuesSet = spsCertificateTypeStaticValuesSet.getSpsConsignment();
  }

  @Test
  void apply_ReturnsAllValuesSet() throws Exception {
    spsConsignmentStaticMapper.apply(spsConsignmentTypeNoStaticValues);

    String expected = objectMapper.writeValueAsString(spsConsignmentTypeStaticValuesSet);
    String actual = objectMapper.writeValueAsString(spsConsignmentTypeNoStaticValues);

    assertThat(actual).isEqualTo(expected);
  }
}
