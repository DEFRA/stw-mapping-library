package uk.gov.defra.tracesx.common.chedp;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.CommodityTemperature.AMBIENT;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.CommodityTemperature.CHILLED;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.CommodityTemperature.FROZEN;

import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.CommodityTemperature;
import uk.gov.defra.tracesx.trade.dto.CodeType;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;
import uk.gov.defra.tracesx.trade.dto.SpsNoteType;

@Component
public class ChedpTemperatureMapper implements Mapper<SpsCertificate, CommodityTemperature> {

  private static final Map<String, CommodityTemperature> temperatureMap = Map.of(
      "AMBIENT", AMBIENT,
      "CHILLED", CHILLED,
      "FROZEN", FROZEN);

  @Override
  public CommodityTemperature map(SpsCertificate spsCertificate)
      throws NotificationMapperException {
    return spsCertificate.getSpsExchangedDocument().getIncludedSpsNote()
        .stream()
        .filter(ChedpTemperatureMapper::isTemperature)
        .map(SpsNoteType::getContentCode)
        .filter(Objects::nonNull)
        .findFirst()
        .filter(items -> !items.isEmpty())
        .map(codeTypes -> codeTypes.get(0))
        .map(CodeType::getValue)
        .map(temperatureMap::get)
        .orElse(null);
  }

  private static boolean isTemperature(SpsNoteType spsNoteType) {
    return spsNoteType.getSubjectCode() != null
        && spsNoteType.getSubjectCode().getValue() != null
        && spsNoteType.getSubjectCode().getValue().equals("PRODUCT_TEMPERATURE");
  }
}
