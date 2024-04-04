package uk.gov.defra.stw.mapping.toipaffs.common.commodities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.ApplicableSpsClassification;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.SequenceNumeric;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.CommoditiesMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;

class CommodityComplementUtilTest {

  private final CommodityComplementUtil commodityComplementUtil = new CommodityComplementUtil();

  @Test
  void createCommonCommodityComplement_ReturnsBuilderWithAllCommonFields_WhenComplete() {
    IncludedSpsTradeLineItem tradeLineItem = new IncludedSpsTradeLineItem()
        .withSequenceNumeric(new SequenceNumeric().withValue(1))
        .withApplicableSpsClassification(List.of(
            new ApplicableSpsClassification()
                .withSystemName(List.of(new TextType()
                    .withValue("CN Code (Combined Nomenclature)")))
                .withClassCode(new CodeType().withValue("Commodity code 1"))
                .withClassName(List.of(new TextType().withValue("Commodity description 1"))),
            new ApplicableSpsClassification()
                .withSystemName(List.of(new TextType()
                    .withValue("IPAFFS_CCT")))
                .withClassName(List.of(new TextType().withValue("Species type 1"))),
            new ApplicableSpsClassification()
                .withSystemName(List.of(new TextType()
                    .withValue("IPAFFS_CCF")))
                .withClassName(List.of(new TextType().withValue("Species family 1"))),
            new ApplicableSpsClassification()
                .withSystemName(List.of(new TextType()
                    .withValue("IPAFFS_CCC")))
                .withClassName(List.of(new TextType().withValue("Species class 1")))
        ))
        .withScientificName(List.of(new TextType().withValue("Species 1")));

    CommodityComplement actual = commodityComplementUtil.createCommonCommodityComplement(
        tradeLineItem).build();

    assertThat(actual).isEqualTo(CommodityComplement.builder()
        .complementID(1)
        .commodityID("Commodity code 1")
        .commodityDescription("Commodity description 1")
        .speciesName("Species 1")
        .speciesTypeName("Species type 1")
        .speciesClassName("Species class 1")
        .speciesFamilyName("Species family 1")
        .speciesNomination("Species 1")
        .complementName("Species 1")
        .build());
  }

  @Test
  void createCommonCommodityComplement_ReturnsBuilderWithRequiredCommonFields_WhenMinimal() {
    IncludedSpsTradeLineItem tradeLineItem = new IncludedSpsTradeLineItem()
        .withSequenceNumeric(new SequenceNumeric().withValue(1))
        .withApplicableSpsClassification(List.of(
            new ApplicableSpsClassification()
                .withSystemName(List.of(new TextType()
                    .withValue("CN Code (Combined Nomenclature)")))
                .withClassCode(new CodeType().withValue("Commodity code 1"))
                .withClassName(List.of(new TextType().withValue("Commodity description 1")))
        ));

    CommodityComplement actual = commodityComplementUtil.createCommonCommodityComplement(
        tradeLineItem).build();

    assertThat(actual).isEqualTo(CommodityComplement.builder()
        .complementID(1)
        .commodityID("Commodity code 1")
        .commodityDescription("Commodity description 1")
        .speciesName("")
        .speciesNomination("")
        .complementName("")
        .build());
  }

  @Test
  void createCommonCommodityComplement_ThrowsException_WhenNoCommodityCode() {
    IncludedSpsTradeLineItem tradeLineItem = new IncludedSpsTradeLineItem()
        .withSequenceNumeric(new SequenceNumeric().withValue(1))
        .withApplicableSpsClassification(List.of(
            new ApplicableSpsClassification()
                .withSystemName(List.of(new TextType()
                    .withValue("CN Code (Combined Nomenclature)")))
                .withClassName(List.of(new TextType().withValue("Commodity description 1")))
        ))
        .withScientificName(List.of(new TextType().withValue("Species 1")));

    assertThatThrownBy(() -> commodityComplementUtil.createCommonCommodityComplement(tradeLineItem))
        .isInstanceOf(CommoditiesMapperException.class)
        .hasMessage("Unable to map the commodityID");
  }

  @Test
  void createCommonCommodityComplement_ThrowsException_WhenNoDescription() {
    IncludedSpsTradeLineItem tradeLineItem = new IncludedSpsTradeLineItem()
        .withSequenceNumeric(new SequenceNumeric().withValue(1))
        .withApplicableSpsClassification(List.of(
            new ApplicableSpsClassification()
                .withSystemName(List.of(new TextType()
                    .withValue("CN Code (Combined Nomenclature)")))
                .withClassCode(new CodeType().withValue("Commodity code 1"))
        ))
        .withScientificName(List.of(new TextType().withValue("Species 1")));

    assertThatThrownBy(() -> commodityComplementUtil.createCommonCommodityComplement(tradeLineItem))
        .isInstanceOf(CommoditiesMapperException.class)
        .hasMessage("Unable to map the commodity description");
  }

  @Test
  void createCommonCommodityComplement_ThrowsException_WhenNoCnCodeClassification() {
    IncludedSpsTradeLineItem tradeLineItem = new IncludedSpsTradeLineItem()
        .withSequenceNumeric(new SequenceNumeric().withValue(1))
        .withApplicableSpsClassification(List.of(
            new ApplicableSpsClassification()
                .withSystemName(List.of(new TextType()
                    .withValue("IPAFFS_CCT")))
                .withClassName(List.of(new TextType().withValue("Species type 1")))
        ))
        .withScientificName(List.of(new TextType().withValue("Species 1")));

    assertThatThrownBy(() -> commodityComplementUtil.createCommonCommodityComplement(tradeLineItem))
        .isInstanceOf(CommoditiesMapperException.class)
        .hasMessage("Unable to map the commodityID");
  }
}
