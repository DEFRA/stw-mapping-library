package uk.gov.defra.stw.mapping.toipaffs.common.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.ApplicableSpsClassification;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsConsignmentItem;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.SequenceNumeric;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;

class CommodityComplementMapperTest {
  private final CommodityComplementUtil commodityComplementUtil = new CommodityComplementUtil();

  private final CommodityComplementMapper mapper =
      new CommodityComplementMapper(commodityComplementUtil);

  @Test
  void map_ReturnsCommodityComplements() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withIncludedSpsConsignmentItem(List.of(new IncludedSpsConsignmentItem()
                .withIncludedSpsTradeLineItem(List.of(createTradeLineItem(1),
                    createTradeLineItem(2))))));

    List<CommodityComplement> actual = mapper.map(spsCertificate);

    assertThat(actual).containsExactly(
        CommodityComplement.builder()
            .complementID(1)
            .commodityID("Commodity code 1")
            .commodityDescription("Commodity description 1")
            .speciesName("Species 1")
            .speciesTypeName("Species type 1")
            .speciesClassName("Species class 1")
            .speciesFamilyName("Species family 1")
            .speciesNomination("Species 1")
            .complementName("Species 1")
            .build(),
        CommodityComplement.builder()
            .complementID(2)
            .commodityID("Commodity code 2")
            .commodityDescription("Commodity description 2")
            .speciesName("Species 2")
            .speciesTypeName("Species type 2")
            .speciesClassName("Species class 2")
            .speciesFamilyName("Species family 2")
            .speciesNomination("Species 2")
            .complementName("Species 2")
            .build()
    );
  }

  @Test
  void map_FiltersOutSequenceNumericZero() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withIncludedSpsConsignmentItem(List.of(new IncludedSpsConsignmentItem()
                .withIncludedSpsTradeLineItem(List.of(createTradeLineItem(0),
                    createTradeLineItem(1))))));

    List<CommodityComplement> actual = mapper.map(spsCertificate);

    assertThat(actual).containsExactly(
        CommodityComplement.builder()
            .complementID(1)
            .commodityID("Commodity code 1")
            .commodityDescription("Commodity description 1")
            .speciesName("Species 1")
            .speciesTypeName("Species type 1")
            .speciesClassName("Species class 1")
            .speciesFamilyName("Species family 1")
            .speciesNomination("Species 1")
            .complementName("Species 1")
            .build()
    );
  }

  private IncludedSpsTradeLineItem createTradeLineItem(int index) {
    return new IncludedSpsTradeLineItem()
        .withSequenceNumeric(new SequenceNumeric().withValue(index))
        .withApplicableSpsClassification(List.of(
            new ApplicableSpsClassification()
                .withSystemName(List.of(new TextType()
                    .withValue("CN Code (Combined Nomenclature)")))
                .withClassCode(new CodeType().withValue("Commodity code " + index))
                .withClassName(List.of(new TextType().withValue("Commodity description " + index))),
            new ApplicableSpsClassification()
                .withSystemName(List.of(new TextType()
                    .withValue("IPAFFS_CCT")))
                .withClassName(List.of(new TextType().withValue("Species type " + index))),
            new ApplicableSpsClassification()
                .withSystemName(List.of(new TextType()
                    .withValue("IPAFFS_CCF")))
                .withClassName(List.of(new TextType().withValue("Species family " + index))),
            new ApplicableSpsClassification()
                .withSystemName(List.of(new TextType()
                    .withValue("IPAFFS_CCC")))
                .withClassName(List.of(new TextType().withValue("Species class " + index)))
        ))
        .withScientificName(List.of(new TextType().withValue("Species " + index)));
  }
}
