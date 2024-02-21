package uk.gov.defra.stw.mapping.totrade.common.utils;

import java.math.BigDecimal;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.ItemQuantity;
import uk.gov.defra.stw.mapping.dto.MeasureType;
import uk.gov.defra.stw.mapping.dto.RoleCode;
import uk.gov.defra.stw.mapping.dto.RoleCode.Value;
import uk.gov.defra.stw.mapping.dto.TextType;

public class SpsTypeConverter {

  public static final String WEIGHT_UNIT_CODE_KGM = "KGM";

  private SpsTypeConverter() { }

  private static final Map<String, String> volumeUnitMap = Map.of(
      "litres", "LTR",
      "metres cubed", "MTQ");

  private static final Map<String, String> quantityUnitMap = Map.of(
      "Stems", "STM",
      "Bulbs", "BLB",
      "Corms and rhizomes", "CRZ",
      "Plants in tissue culture", "PTC",
      "Seeds", "SDS",
      "Kilograms", WEIGHT_UNIT_CODE_KGM,
      "Pieces", "PCS"
  );

  public static CodeType getCodeType(String value) {
    if (StringUtils.isEmpty(value)) {
      return null;
    }
    return new CodeType().withValue(value);
  }

  public static CodeType getCodeType(String name, String value) {
    if (StringUtils.isEmpty(name) || StringUtils.isEmpty(value)) {
      return null;
    }
    return new CodeType().withName(name).withValue(value);
  }

  public static CodeType getCodeTypeWithListId(String listId, String value) {
    if (StringUtils.isEmpty(listId) || StringUtils.isEmpty(value)) {
      return null;
    }
    return new CodeType().withListID(listId).withValue(value);
  }

  public static CodeType getCodeTypeWithListIdAndName(String listId, String name, String value) {
    if (StringUtils.isEmpty(listId)
        || StringUtils.isEmpty(name)
        || StringUtils.isEmpty(value)) {
      return null;
    }
    return new CodeType().withListID(listId).withName(name).withValue(value);
  }

  public static RoleCode getRoleCode(String name, Value value) {
    if (StringUtils.isEmpty(name) || value == null) {
      return null;
    }
    return new RoleCode().withName(name).withValue(value);
  }

  public static TextType getTextType(String value) {
    if (StringUtils.isEmpty(value)) {
      return null;
    }
    return new TextType().withValue(value);
  }

  public static IDType getIdType(String value) {
    if (StringUtils.isEmpty(value)) {
      return null;
    }
    return new IDType().withValue(value);
  }

  public static IDType getIdType(Integer value) {
    if (value == null) {
      return null;
    }

    return getIdType(value.toString());
  }

  public static IDType getIdTypeWithSchemeId(String schemeId, String value) {
    if (StringUtils.isEmpty(schemeId) || StringUtils.isEmpty(value)) {
      return null;
    }
    return new IDType().withValue(value).withSchemeID(schemeId);
  }

  public static IDType getIdTypeWithSchemeIdAndSchemeAgencyIdAndSchemeAgencyName(String schemeId,
      String value, String schemeAgencyId, String schemeAgencyName) {
    if (StringUtils.isEmpty(schemeId) || StringUtils.isEmpty(value)
        || StringUtils.isEmpty(schemeAgencyId) || StringUtils.isEmpty(schemeAgencyName)) {
      return null;
    }
    return new IDType()
        .withValue(value)
        .withSchemeID(schemeId)
        .withSchemeAgencyID(schemeAgencyId)
        .withSchemeAgencyName(schemeAgencyName);
  }

  public static MeasureType getWeightMeasureType(String value) {
    BigDecimal num = stringToBigDecimal(value);
    if (num == null) {
      return null;
    }
    return getMeasureType(num).withUnitCode(WEIGHT_UNIT_CODE_KGM);
  }

  public static MeasureType getWeightMeasureType(BigDecimal value) {
    if (value == null) {
      return null;
    }
    return getMeasureType(value).withUnitCode(WEIGHT_UNIT_CODE_KGM);
  }

  public static MeasureType getVolumeMeasureType(String unit, BigDecimal value) {
    if (unit == null || value == null) {
      return null;
    }
    return getMeasureType(value).withUnitCode(volumeUnitMap.get(unit));
  }

  public static MeasureType getQuantityMeasureType(String unit, String value) {
    if (unit == null || value == null) {
      return null;
    }
    return getMeasureType(value).withUnitCode(quantityUnitMap.get(unit));
  }

  public static ItemQuantity getItemQuantity(String value) {
    Double num = stringToDouble(value);
    if (num == null) {
      return null;
    }
    return new ItemQuantity().withValue(num);
  }

  private static MeasureType getMeasureType(BigDecimal value) {
    return new MeasureType().withValue(value.doubleValue());
  }

  private static MeasureType getMeasureType(String value) {
    return new MeasureType().withValue(Double.valueOf(value));
  }

  private static Double stringToDouble(String value) {
    if (value == null) {
      return null;
    }
    return Double.valueOf(value);
  }

  private static BigDecimal stringToBigDecimal(String value) {
    if (value == null) {
      return null;
    }
    return new BigDecimal(value);
  }
}
