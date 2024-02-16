package uk.gov.defra.stw.mapping.toipaffs.common.common.commodities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.ApplicableSpsClassification;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.CommoditiesMapperException;
import uk.gov.defra.stw.mapping.toipaffs.testutils.ResourceUtils;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;

class CommodityComplementFieldMapperTest {

  private static final String COMMODITY_CODE = "01903493";


  private CommodityComplementFieldMapper commodityComplementFieldMapper;
  private List<ApplicableSpsClassification> applicableSpsClassification;

  @BeforeEach
  void setup() throws JsonProcessingException {
    commodityComplementFieldMapper = new CommodityComplementFieldMapper();
    ObjectMapper objectMapper = TestUtils.initObjectMapper();
    String jsonString = ResourceUtils.readFileToString("classpath:common/chedp/partone/commodities/chedp_applicable_sps_classification.json");
    applicableSpsClassification = objectMapper.readValue(jsonString, new TypeReference<>() {});
  }

  @Test
  void mapDescription_ReturnsDescription_WhenApplicableSpsPassed() {
    // Given / When
    String actual = commodityComplementFieldMapper.mapDescription(applicableSpsClassification);

    // Then
    assertThat(actual)
        .isEqualTo(applicableSpsClassification.get(0).getClassName().get(0).getValue());
  }

  @Test
  void mapDescription_ThrowsCommodityMapperException_WhenApplicableSpsNotPassed() {
    // Given / When
    ApplicableSpsClassification applicableSpsClassification = new ApplicableSpsClassification();
    applicableSpsClassification.setClassName(List.of(new TextType()));

    assertThrows(
        CommoditiesMapperException.class,
        () -> commodityComplementFieldMapper.mapDescription(List.of(applicableSpsClassification)));
  }

  @Test
  void mapSpeciesClassName_ReturnsNull_WhenApplicableSpsNotPassed() {
    // Given
    ApplicableSpsClassification applicableSpsClassification = new ApplicableSpsClassification();
    applicableSpsClassification.setClassName(List.of(new TextType()));

    // When
    String actual =
        commodityComplementFieldMapper.mapSpeciesClassName(List.of(applicableSpsClassification));

    // Then
    assertThat(actual).isNull();
  }

  @Test
  void mapSpeciesFamilyName_ReturnsNull_WhenApplicableSpsNotPassed() {
    // Given
    ApplicableSpsClassification applicableSpsClassification = new ApplicableSpsClassification();
    applicableSpsClassification.setClassName(List.of(new TextType()));

    // When
    String actual =
        commodityComplementFieldMapper.mapSpeciesFamilyName(List.of(applicableSpsClassification));

    // Then
    assertThat(actual).isNull();
  }

  @Test
  void mapSpeciesTypeName_ReturnsNull_WhenApplicableSpsNotPassed() {
    // Given / When
    ApplicableSpsClassification applicableSpsClassification = new ApplicableSpsClassification();
    applicableSpsClassification.setClassName(List.of(new TextType()));

    // When
    String actual =
        commodityComplementFieldMapper.mapSpeciesTypeName(List.of(applicableSpsClassification));

    // Then
    assertThat(actual).isNull();
  }

  @Test
  void mapSpeciesClassName_ReturnsNull_WhenSystemNameNotPresent() {
    // Given
    ApplicableSpsClassification applicableSpsClassification = new ApplicableSpsClassification();
    applicableSpsClassification.setSystemName(List.of(new TextType()));

    // When
    String actual =
        commodityComplementFieldMapper.mapSpeciesClassName(List.of(applicableSpsClassification));

    // Then
    assertThat(actual).isNull();
  }

  @Test
  void mapSpeciesFamilyName_ReturnsNull_WhenSystemNameNotPresent() {
    // Given
    ApplicableSpsClassification applicableSpsClassification = new ApplicableSpsClassification();
    applicableSpsClassification.setSystemName(List.of(new TextType()));

    // When
    String actual =
        commodityComplementFieldMapper.mapSpeciesFamilyName(List.of(applicableSpsClassification));

    // Then
    assertThat(actual).isNull();
  }

  @Test
  void mapSpeciesTypeName_ReturnsNull_WhenSystemNameNotPresent() {
    // Given
    ApplicableSpsClassification applicableSpsClassification = new ApplicableSpsClassification();
    applicableSpsClassification.setSystemName(List.of(new TextType()));

    // When
    String actual =
        commodityComplementFieldMapper.mapSpeciesTypeName(List.of(applicableSpsClassification));

    // Then
    assertThat(actual).isNull();
  }

  @Test
  void mapSpeciesTypeName_ReturnsSpeciesTypeName_WhenApplicableSpsPassed() {
    // Given //When
    String actual = commodityComplementFieldMapper.mapSpeciesTypeName(applicableSpsClassification);

    // Then
    assertThat(actual)
        .isEqualTo(applicableSpsClassification.get(1).getClassName().get(0).getValue());
  }

  @Test
  void mapSpeciesClassName_ReturnsSpeciesClassName_WhenApplicableSpsPassed() {
    // Given / When
    String actual =
        commodityComplementFieldMapper.mapSpeciesClassName(applicableSpsClassification);

    // Then
    assertThat(actual)
        .isEqualTo(applicableSpsClassification.get(2).getClassName().get(0).getValue());
  }

  @Test
  void mapSpeciesFamilyName_ReturnsSpeciesFamilyName_WhenApplicableSpsPassed() {
    // Given / When
    String actual =
        commodityComplementFieldMapper.mapSpeciesFamilyName(applicableSpsClassification);

    // Then
    assertThat(actual)
        .isEqualTo(applicableSpsClassification.get(3).getClassName().get(0).getValue());
  }

  @Test
  void mapCommodityIdFromCNCode_ReturnsCommodityId_WhenApplicableSpsPassed() {
    // Given / When
    String actual =
        commodityComplementFieldMapper.mapCommodityIdFromCnCode(applicableSpsClassification);

    // Then
    assertThat(actual).isEqualTo(applicableSpsClassification.get(0).getClassCode().getValue());
  }

  @Test
  void map_ThrowsCommoditiesMapperException_WhenNotCombinedNomenclature() {
    applicableSpsClassification.get(0).getSystemName().get(0).setValue("Another CN Code");

    assertThrows(
        CommoditiesMapperException.class,
        () -> commodityComplementFieldMapper.mapCommodityIdFromCnCode(applicableSpsClassification));
  }

  @Test
  void map_ThrowsCommoditiesMapperException_WhenNullClassCode() {
    applicableSpsClassification.get(0).setClassCode(null);

    assertThrows(
        CommoditiesMapperException.class,
        () -> commodityComplementFieldMapper.mapCommodityIdFromCnCode(applicableSpsClassification));
  }

  @Test
  void mapScientificName_ReturnsScientificName_WhenScientificNamePresent() {
    // Given / When
    IncludedSpsTradeLineItem spsTradeLineItem = new IncludedSpsTradeLineItem();
    spsTradeLineItem.setScientificName(List.of(new TextType().withValue("ScientificName")));
    String actual =
        commodityComplementFieldMapper.mapScientificName(spsTradeLineItem.getScientificName());

    // Then
    assertThat(actual).isEqualTo("ScientificName");
  }

  @Test
  void mapScientificName_ReturnsEmpty_WhenScientificNameEmpty() {
    // Given / When
    IncludedSpsTradeLineItem spsTradeLineItem = new IncludedSpsTradeLineItem();
    spsTradeLineItem.setScientificName(List.of(new TextType()));
    String actual =
        commodityComplementFieldMapper.mapScientificName(spsTradeLineItem.getScientificName());

    // Then
    assertThat(actual).isEmpty();
  }

  @Test
  void mapScientificName_ReturnsFirstScientificName_WhenScientificNamePresent() {
    // Given / When
    TextType textType = new TextType();
    textType.setValue("ScientificName");
    TextType textType1 = new TextType();
    textType1.setValue("ScientificName1");

    IncludedSpsTradeLineItem spsTradeLineItem = new IncludedSpsTradeLineItem();
    spsTradeLineItem.setScientificName(List.of(textType, textType1));
    String actual =
        commodityComplementFieldMapper.mapScientificName(spsTradeLineItem.getScientificName());

    // Then
    assertThat(actual).isEqualTo("ScientificName");
  }

  @Test
  void mapCommodityIdFromCNCode_ReturnsCommodityCode_WithoutSpsClassificationHavingToBeFirstIndex() {
    // Given
    List<ApplicableSpsClassification> applicableSpsClassifications = List.of(
        new ApplicableSpsClassification()
            .withClassName(List.of(new TextType().withValue("Farmed game")))
            .withSystemName(List.of(new TextType().withValue("IPAFFS_CCT"))),
        new ApplicableSpsClassification()
            .withSystemName(List.of(new TextType().withValue("CN Code (Combined Nomenclature)")))
            .withClassCode(new CodeType().withValue(COMMODITY_CODE)));

    // When
    String actual = commodityComplementFieldMapper.mapCommodityIdFromCnCode(applicableSpsClassifications);

    // Then
    assertThat(actual).isEqualTo(COMMODITY_CODE);
  }

  @Test
  void mapCommodityIdFromIpaffsCode_ReturnsCommodityId_WhenSystemNameIsIpaffsCode() {
    applicableSpsClassification.get(0).getSystemName().get(0).setValue("IPAFFS_CC");

    String actual =
        commodityComplementFieldMapper.mapCommodityIdFromIpaffsCode(applicableSpsClassification);

    assertThat(actual).isEqualTo(applicableSpsClassification.get(0).getClassCode().getValue());
  }

  @Test
  void mapCommodityIdFromIpaffsCode_ThrowsCommoditiesMapperException_WhenSystemNameIsNotIpaffsCode() {
    assertThrows(CommoditiesMapperException.class, () -> commodityComplementFieldMapper.mapCommodityIdFromIpaffsCode(applicableSpsClassification));
  }
}
