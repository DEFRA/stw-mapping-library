package uk.gov.defra.tracesx.common.chedp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.CommodityTemperature;
import uk.gov.defra.tracesx.trade.dto.CodeType;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;
import uk.gov.defra.tracesx.trade.dto.SpsExchangedDocument;
import uk.gov.defra.tracesx.trade.dto.SpsNoteType;

class ChedpTemperatureMapperTest {

  private static final String PRODUCT_TEMPERATURE = "PRODUCT_TEMPERATURE";

  private ChedpTemperatureMapper chedpTemperatureMapper;

  @BeforeEach
  void setup() {
    chedpTemperatureMapper = new ChedpTemperatureMapper();
  }

  @Test
  void map_ReturnsAmbient_WhenAmbientTemperatureInTradeJson()
      throws NotificationMapperException {
    CommodityTemperature commodityTemperature =
        chedpTemperatureMapper.map(createSpsCertificate(PRODUCT_TEMPERATURE, "AMBIENT"));
    assertThat(commodityTemperature).isEqualTo(CommodityTemperature.AMBIENT);
  }

  @Test
  void map_ReturnsChilled_WhenChilledTemperatureInTradeJson()
      throws NotificationMapperException {
    CommodityTemperature commodityTemperature =
        chedpTemperatureMapper.map(createSpsCertificate(PRODUCT_TEMPERATURE, "CHILLED"));
    assertThat(commodityTemperature).isEqualTo(CommodityTemperature.CHILLED);
  }

  @Test
  void map_ReturnsFrozen_WhenFrozenTemperatureInTradeJson()
      throws NotificationMapperException {
    CommodityTemperature commodityTemperature =
        chedpTemperatureMapper.map(createSpsCertificate(PRODUCT_TEMPERATURE, "FROZEN"));
    assertThat(commodityTemperature).isEqualTo(CommodityTemperature.FROZEN);
  }

  @Test
  void map_ReturnsNull_WhenInvalidTemperatureInTradeJson()
      throws NotificationMapperException {
    CommodityTemperature commodityTemperature =
        chedpTemperatureMapper.map(createSpsCertificate(PRODUCT_TEMPERATURE, "INVALID"));
    assertThat(commodityTemperature).isNull();
  }

  @Test
  void map_ReturnsNull_WhenSubjectCodeIsInvalid()
      throws NotificationMapperException {
    CommodityTemperature commodityTemperature =
        chedpTemperatureMapper.map(createSpsCertificate("INVALID", "AMBIENT"));
    assertThat(commodityTemperature).isNull();
  }

  @Test
  void map_ReturnsNull_WhenSubjectCodeIsNull()
      throws NotificationMapperException {
    List<SpsNoteType> includedSpsNote = List.of(new SpsNoteType()
        .withSubjectCode(null));
    SpsExchangedDocument spsExchangedDocument = new SpsExchangedDocument();
    spsExchangedDocument.setIncludedSpsNote(includedSpsNote);
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(spsExchangedDocument);
    CommodityTemperature commodityTemperature = chedpTemperatureMapper.map(spsCertificate);
    assertThat(commodityTemperature).isNull();
  }

  @Test
  void map_ReturnsNull_WhenSubjectCodeValueIsNull()
      throws NotificationMapperException {
    CommodityTemperature commodityTemperature =
        chedpTemperatureMapper.map(createSpsCertificate(null, "AMBIENT"));
    assertThat(commodityTemperature).isNull();
  }

  @Test
  void map_ReturnsNull_WhenContentCodeIsEmpty()
      throws NotificationMapperException {
    List<SpsNoteType> list = List.of(new SpsNoteType()
        .withSubjectCode(new CodeType().withValue(PRODUCT_TEMPERATURE))
        .withContentCode(List.of()));
    SpsExchangedDocument spsExchangedDocument = new SpsExchangedDocument();
    spsExchangedDocument.setIncludedSpsNote(list);
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(spsExchangedDocument);

    CommodityTemperature commodityTemperature =
        chedpTemperatureMapper.map(spsCertificate);
    assertThat(commodityTemperature).isNull();
  }

  private SpsCertificate createSpsCertificate(String subjectCode, String contentCode) {
    SpsExchangedDocument spsExchangedDocument = new SpsExchangedDocument();
    spsExchangedDocument
        .setIncludedSpsNote(List.of(new SpsNoteType()
            .withSubjectCode(new CodeType().withValue(subjectCode))
            .withContentCode(List.of(new CodeType().withValue(contentCode)))));
    return new SpsCertificate()
        .withSpsExchangedDocument(spsExchangedDocument);
  }
}
