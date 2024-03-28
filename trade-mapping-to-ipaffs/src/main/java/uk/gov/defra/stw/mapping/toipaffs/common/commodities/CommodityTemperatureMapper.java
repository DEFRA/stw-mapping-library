package uk.gov.defra.stw.mapping.toipaffs.common.commodities;

import static uk.gov.defra.stw.mapping.toipaffs.utils.SpsNoteTypeHelper.findNoteBySubjectCode;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.CommodityTemperature.AMBIENT;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.CommodityTemperature.CHILLED;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.CommodityTemperature.FROZEN;

import java.util.Map;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.CommodityTemperature;

@Component
public class CommodityTemperatureMapper implements Mapper<SpsCertificate, CommodityTemperature> {

  private static final Map<String, CommodityTemperature> temperatureMap = Map.of(
      "AMBIENT", AMBIENT,
      "CHILLED", CHILLED,
      "FROZEN", FROZEN);

  @Override
  public CommodityTemperature map(SpsCertificate spsCertificate)
      throws NotificationMapperException {
    return findNoteBySubjectCode(spsCertificate, "PRODUCT_TEMPERATURE")
        .map(SpsNoteType::getContentCode)
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
