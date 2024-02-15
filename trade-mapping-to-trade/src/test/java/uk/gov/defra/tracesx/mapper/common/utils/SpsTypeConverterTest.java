package uk.gov.defra.tracesx.mapper.common.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter.WEIGHT_UNIT_CODE_KGM;
import static uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter.getCodeType;
import static uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter.getIdType;
import static uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter.getIdTypeWithSchemeId;
import static uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter.getItemQuantity;
import static uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter.getQuantityMeasureType;
import static uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter.getTextType;
import static uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter.getVolumeMeasureType;
import static uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter.getWeightMeasureType;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.trade.dto.CodeType;
import uk.gov.defra.tracesx.trade.dto.IDType;
import uk.gov.defra.tracesx.trade.dto.ItemQuantity;
import uk.gov.defra.tracesx.trade.dto.MeasureType;
import uk.gov.defra.tracesx.trade.dto.RoleCode;
import uk.gov.defra.tracesx.trade.dto.RoleCode.Value;
import uk.gov.defra.tracesx.trade.dto.TextType;

class SpsTypeConverterTest {

  private static final String SCHEME_ID_VALUE = "mockSchemeId";
  private static final String VALUE = "mockValue";
  private static final String NAME = "mockName";
  private static final String LIST_ID = "mockListId";
  private static final String LITRES = "litres";
  private static final Double DECIMAL_VALUE = 2.0;

  @Test
  void getCodeType_ReturnsNull_WhenEmptyValue() {
    assertThat(getCodeType("")).isNull();
  }

  @Test
  void getCodeType_ReturnsCodeType_WhenPresentValue() {
    CodeType codeType = new CodeType().withValue(VALUE);

    assertThat(getCodeType(VALUE)).isEqualTo(codeType);
  }

  @Test
  void getCodeType_ReturnsNull_WhenNullName() {
    assertThat(getCodeType(null, VALUE)).isNull();
  }

  @Test
  void getCodeType_ReturnsNull_WhenNullValue() {
    assertThat(getCodeType(NAME, null)).isNull();
  }

  @Test
  void getCodeType_ReturnsCodeType_WhenPresentNameAndValue() {
    CodeType codeType = new CodeType()
        .withName(NAME)
        .withValue(VALUE);

    assertThat(getCodeType(NAME, VALUE)).isEqualTo(codeType);
  }

  @Test
  void getCodeTypeWithListId_ReturnsCodeType_WhenPresentListIdAndValue() {
    CodeType codeType = new CodeType()
        .withListID(LIST_ID)
        .withValue(VALUE);

    assertThat(SpsTypeConverter.getCodeTypeWithListId(LIST_ID, VALUE)).isEqualTo(codeType);
  }

  @Test
  void getCodeTypeWithListId_ReturnsNull_WhenNullListId() {
    assertThat(SpsTypeConverter.getCodeTypeWithListId(null, VALUE)).isNull();
  }

  @Test
  void getCodeTypeWithListId_ReturnsNull_WhenNullValue() {
    assertThat(SpsTypeConverter.getCodeTypeWithListId(LIST_ID, null)).isNull();
  }

  @Test
  void getCodeTypeWithListIdAndName_ReturnsCodeType_WhenPresentListIdAndNameAndValue() {
    CodeType codeType = new CodeType()
        .withListID(LIST_ID)
        .withName(NAME)
        .withValue(VALUE);

    assertThat(SpsTypeConverter.getCodeTypeWithListIdAndName(LIST_ID, NAME, VALUE)).isEqualTo(codeType);
  }

  @Test
  void getCodeTypeWithListIdAndName_ReturnsNull_WhenNullListId() {
    assertThat(SpsTypeConverter.getCodeTypeWithListIdAndName(null, NAME, VALUE)).isNull();
  }

  @Test
  void getCodeTypeWithListIdAndName_ReturnsNull_WhenNullName() {
    assertThat(SpsTypeConverter.getCodeTypeWithListIdAndName(LIST_ID, null, VALUE)).isNull();
  }

  @Test
  void getCodeTypeWithListIdAndName_ReturnsNull_WhenNullValue() {
    assertThat(SpsTypeConverter.getCodeTypeWithListIdAndName(LIST_ID, NAME, null)).isNull();
  }

  @Test
  void getRoleCode_ReturnsRoleCode_WhenPresentNameAndValue() {
    RoleCode roleCode = new RoleCode()
        .withName(NAME)
        .withValue(Value.EX);

    assertThat(SpsTypeConverter.getRoleCode(NAME, Value.EX)).isEqualTo(roleCode);
  }

  @Test
  void getRoleCode_ReturnsNull_WhenNullName() {
    assertThat(SpsTypeConverter.getRoleCode(null, Value.EX)).isNull();
  }

  @Test
  void getRoleCode_ReturnsNull_WhenNullValue() {
    assertThat(SpsTypeConverter.getRoleCode(NAME, null)).isNull();
  }

  @Test
  void getTextType_ReturnsNull_WhenEmptyValue() {
    assertThat(getTextType("")).isNull();
  }

  @Test
  void getTextType_ReturnsTypeText_WhenPresentValue() {
    TextType textType = new TextType().withValue(VALUE);

    assertThat(getTextType(VALUE)).isEqualTo(textType);
  }

  @Test
  void getIdType_ReturnsNull_WhenEmptyValue() {
    assertThat(getIdType("")).isNull();
  }

  @Test
  void getIdType_ReturnsIdType_WhenPresentValue() {
    IDType idType = new IDType().withValue(VALUE);

    assertThat(getIdType(VALUE)).isEqualTo(idType);
  }

  @Test
  void getIdType_ReturnsNull_WhenNullInteger() {
    Integer value = null;
    assertThat(getIdType(value)).isNull();
  }

  @Test
  void getIdType_ReturnsNull_WhenPresentInteger() {
    Integer value = 1234;
    IDType idType = new IDType().withValue("1234");
    assertThat(getIdType(value)).isEqualTo(idType);
  }

  @Test
  void getIdTypeWithSchemeId_ReturnsNull_WhenEmptySchemeId() {
    assertThat(getIdTypeWithSchemeId("", VALUE)).isNull();
  }

  @Test
  void getIdTypeWithSchemeId_ReturnsNull_WhenEmptyValue() {
    assertThat(getIdTypeWithSchemeId(SCHEME_ID_VALUE, "")).isNull();
  }

  @Test
  void getIdTypeWithSchemeId_ReturnsIdTypeWithSchemeID_WhenPresentValue() {
    IDType expectedIdTypeWithSchemeId = new IDType().withValue(VALUE).withSchemeID(SCHEME_ID_VALUE);

    assertThat(getIdTypeWithSchemeId(SCHEME_ID_VALUE, VALUE)).isEqualTo(expectedIdTypeWithSchemeId);
  }

  @Test
  void getVolumeMeasureType_ReturnsNull_WhenNullUnit() {
    assertThat(getVolumeMeasureType(null, new BigDecimal(DECIMAL_VALUE))).isNull();
  }

  @Test
  void getVolumeMeasureType_ReturnsNull_WhenPresentUnitWithNullValue() {
    assertThat(getVolumeMeasureType(LITRES, null)).isNull();
  }

  @Test
  void getVolumeMeasureType_ReturnsMeasureType_WhenLitresAndValue() {
    MeasureType measureType = new MeasureType()
        .withUnitCode("LTR")
        .withValue(DECIMAL_VALUE);

    assertThat(getVolumeMeasureType(LITRES, new BigDecimal(DECIMAL_VALUE)))
        .isEqualTo(measureType);
  }

  @Test
  void getVolumeMeasureType_ReturnsMeasureType_WhenMetresCubedAndValue() {
    MeasureType measureType = new MeasureType()
        .withUnitCode("MTQ")
        .withValue(DECIMAL_VALUE);

    assertThat(getVolumeMeasureType("metres cubed", new BigDecimal(DECIMAL_VALUE)))
        .isEqualTo(measureType);
  }

  @Test
  void getWeightMeasureType_ReturnsMeasureType_WhenUnitAndValue() {
    MeasureType measureType = new MeasureType()
        .withUnitCode(WEIGHT_UNIT_CODE_KGM)
        .withValue(DECIMAL_VALUE);

    assertThat(getWeightMeasureType(
        new BigDecimal(DECIMAL_VALUE))).isEqualTo(measureType);
  }

  @Test
  void getWeightMeasureType_ReturnsMeasureType_WhenStringValue() {
    MeasureType measureType = new MeasureType()
        .withUnitCode(WEIGHT_UNIT_CODE_KGM)
        .withValue(DECIMAL_VALUE);

    assertThat(getWeightMeasureType(DECIMAL_VALUE.toString())).isEqualTo(measureType);
  }

  @Test
  void getWeightMeasureType_ReturnsNull_WhenNullValue() {
    assertThat(getWeightMeasureType((BigDecimal) null)).isNull();
  }

  @Test
  void getWeightMeasureType_ReturnsNull_WhenNullString() {
    assertThat(getWeightMeasureType((String) null)).isNull();
  }

  @Test
  void getQuantityMeasureType_ReturnsNull_WhenUnitNull() {
    MeasureType result = getQuantityMeasureType(null, "1234.56");
    assertThat(result).isNull();
  }

  @Test
  void getQuantityMeasureType_ReturnsNull_WhenValueNull() {
    MeasureType result = getQuantityMeasureType("Stems", null);
    assertThat(result).isNull();
  }

  @Test
  void getQuantityMeasureType_ThrowsException_WhenValueNotANumber() {
    assertThatThrownBy(() -> getQuantityMeasureType("Stems", "notANumber"))
        .isInstanceOf(NumberFormatException.class);
  }

  @Test
  void getQuantityMeasureType_ReturnsMeasureType_WhenUnitNotInMapAndValue() {
    MeasureType result = getQuantityMeasureType("unitNotInMap", "1234.56");
    assertThat(result).isEqualTo(new MeasureType().withUnitCode(null).withValue(1234.56));
  }

  @Test
  void getQuantityMeasureType_ReturnsMeasureType_WhenStemsUnitAndValue() {
    MeasureType result = getQuantityMeasureType("Stems", "25");
    assertThat(result).isEqualTo(new MeasureType().withUnitCode("STM").withValue(25.0));
  }

  @Test
  void getQuantityMeasureType_ReturnsMeasureType_WhenBulbsAndValue() {
    MeasureType result = getQuantityMeasureType("Bulbs", "25");
    assertThat(result).isEqualTo(new MeasureType().withUnitCode("BLB").withValue(25.0));
  }

  @Test
  void getQuantityMeasureType_ReturnsMeasureType_WhenCormsAndRhizomesAndValue() {
    MeasureType result = getQuantityMeasureType("Corms and rhizomes", "25");
    assertThat(result).isEqualTo(new MeasureType().withUnitCode("CRZ").withValue(25.0));
  }

  @Test
  void getQuantityMeasureType_ReturnsMeasureType_WhenPlantsInTissueCultureAndValue() {
    MeasureType result = getQuantityMeasureType("Plants in tissue culture", "25");
    assertThat(result).isEqualTo(new MeasureType().withUnitCode("PTC").withValue(25.0));
  }

  @Test
  void getQuantityMeasureType_ReturnsMeasureType_WhenSeedsAndValue() {
    MeasureType result = getQuantityMeasureType("Seeds", "25");
    assertThat(result).isEqualTo(new MeasureType().withUnitCode("SDS").withValue(25.0));
  }

  @Test
  void getQuantityMeasureType_ReturnsMeasureType_WhenKilogramsAndValue() {
    MeasureType result = getQuantityMeasureType("Kilograms", "25");
    assertThat(result).isEqualTo(new MeasureType().withUnitCode("KGM").withValue(25.0));
  }

  @Test
  void getQuantityMeasureType_ReturnsMeasureType_WhenPiecesAndValue() {
    MeasureType result = getQuantityMeasureType("Pieces", "25");
    assertThat(result).isEqualTo(new MeasureType().withUnitCode("PCS").withValue(25.0));
  }

  @Test
  void getItemQuantity_ReturnsItemQuantity_WhenPresentValue() {
    ItemQuantity itemQuantity = new ItemQuantity().withValue(DECIMAL_VALUE);

    assertThat(getItemQuantity(DECIMAL_VALUE.toString())).isEqualTo(itemQuantity);
  }

  @Test
  void getItemQuantity_ReturnsNull_WhenNullValue() {
    assertThat(getItemQuantity(null)).isNull();
  }
}
