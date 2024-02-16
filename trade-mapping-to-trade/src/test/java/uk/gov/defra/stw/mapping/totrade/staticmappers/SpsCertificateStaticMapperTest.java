package uk.gov.defra.stw.mapping.totrade.staticmappers;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.totrade.testutils.ResourceUtil;

class SpsCertificateStaticMapperTest {

  private SpsCertificate spsCertificateTypeNoStaticValues;
  private SpsCertificate spsCertificateTypeStaticValuesSet;
  private StaticDataMapper<SpsCertificate> spsCertificateStaticMapper;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    StaticDataMapper<SpsExchangedDocument> spsExchangedDocumentStaticDataMapper = new SpsExchangedDocumentStaticMapper();
    StaticDataMapper<SpsConsignment> spsConsignmentStaticDataMapper = new SpsConsignmentStaticMapper();
    spsCertificateStaticMapper = new SpsCertificateStaticMapper(spsExchangedDocumentStaticDataMapper, spsConsignmentStaticDataMapper);

    String spsCertificateTypeNoStaticString = ResourceUtil.readFileToString(
        "/mapping/staticmappers/trade_no_static_values_set.json");
    String spsCertificateTypeStaticValuesSetString = ResourceUtil.readFileToString(
        "/mapping/staticmappers/trade.json");

    spsCertificateTypeNoStaticValues = objectMapper.readValue(spsCertificateTypeNoStaticString, SpsCertificate.class);
    spsCertificateTypeStaticValuesSet = objectMapper.readValue(spsCertificateTypeStaticValuesSetString, SpsCertificate.class);
  }

  @Test
  void apply_ReturnsAllValuesSet() throws Exception {
    spsCertificateStaticMapper.apply(spsCertificateTypeNoStaticValues);

    String expected = objectMapper.writeValueAsString(spsCertificateTypeStaticValuesSet);
    String actual = objectMapper.writeValueAsString(spsCertificateTypeNoStaticValues);

    assertThat(actual).isEqualTo(expected);
  }
}
