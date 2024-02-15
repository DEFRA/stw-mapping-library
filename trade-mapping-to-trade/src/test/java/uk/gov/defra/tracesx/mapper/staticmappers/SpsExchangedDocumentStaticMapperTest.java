package uk.gov.defra.tracesx.mapper.staticmappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.mapper.testutils.ResourceUtil;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;
import uk.gov.defra.tracesx.trade.dto.SpsExchangedDocument;
import uk.gov.defra.tracesx.trade.dto.SpsPartyType;
import uk.gov.defra.tracesx.trade.dto.TextType;

class SpsExchangedDocumentStaticMapperTest {

  private static final String DEFAULT_VALUE = "#{34b7a82a-83e3-4c64-a561-d648860fbe8a}";
  private SpsExchangedDocument spsExchangedDocumentTypeNoStaticValues;
  private SpsExchangedDocument spsExchangedDocumentTypeStaticValuesSet;
  private SpsExchangedDocumentStaticMapper spsExchangedDocumentStaticMapper;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    spsExchangedDocumentStaticMapper = new SpsExchangedDocumentStaticMapper();

    String spsCertificateTypeNoStaticString = ResourceUtil.readFileToString(
        "/mapping/staticmappers/trade_no_static_values_set.json");
    String spsCertificateTypeStaticValuesSetString = ResourceUtil.readFileToString(
        "/mapping/staticmappers/trade.json");

    SpsCertificate spsCertificateTypeNoStaticValues = objectMapper.readValue(spsCertificateTypeNoStaticString, SpsCertificate.class);
    SpsCertificate spsCertificateTypeStaticValuesSet = objectMapper.readValue(spsCertificateTypeStaticValuesSetString, SpsCertificate.class);

    spsExchangedDocumentTypeNoStaticValues = spsCertificateTypeNoStaticValues.getSpsExchangedDocument();
    spsExchangedDocumentTypeStaticValuesSet = spsCertificateTypeStaticValuesSet.getSpsExchangedDocument();
  }

  @Test
  void apply_ReturnsAllValuesSet() throws Exception {
    spsExchangedDocumentStaticMapper.apply(spsExchangedDocumentTypeNoStaticValues);

    String expected = objectMapper.writeValueAsString(spsExchangedDocumentTypeStaticValuesSet);
    String actual = objectMapper.writeValueAsString(spsExchangedDocumentTypeNoStaticValues);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void apply_UpdatesSpsPartyTypeName_WhenPresentWithoutValue() {
    SpsPartyType party = new SpsPartyType();
    SpsExchangedDocument doc = new SpsExchangedDocument()
        .withIssuerSpsParty(party);

    spsExchangedDocumentStaticMapper.apply(doc);

    assertThat(party.getName()).isEqualTo(new TextType().withValue(DEFAULT_VALUE));
  }

  @Test
  void apply_DoesNotThrowExceptionWhenNoIssuerSpsParty() {
    SpsExchangedDocument doc = new SpsExchangedDocument()
        .withIssuerSpsParty(null);

    assertThatCode(() -> spsExchangedDocumentStaticMapper.apply(doc))
        .doesNotThrowAnyException();
    assertThat(doc.getIssuerSpsParty()).isNull();
  }
}
