package uk.gov.defra.stw.mapping.toipaffs.chedpp.commodities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.ApplicableSpsClassification;
import uk.gov.defra.stw.mapping.dto.SequenceNumeric;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.CommoditiesMapperException;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;

class ChedppCommodityComplementMapperTest {

  private ChedppCommodityComplementMapper mapper;
  private SpsCertificate spsCertificate;

  @BeforeEach
  void setup() throws JsonProcessingException {
    mapper = new ChedppCommodityComplementMapper();

    spsCertificate = JsonDeserializer.get(
        "chedpp/partone/commodities/chedpp_trade_commodity_complement.json", SpsCertificate.class);
  }

  @Test
  void map_ReturnsCommodityComplement_WhenComplete() {
    List<CommodityComplement> actual = mapper.map(spsCertificate);

    assertThat(actual).containsExactly(
        CommodityComplement.builder()
            .commodityID("0808108090")
            .commodityDescription("Other")
            .complementID(1)
            .complementName("Malus angustifolia")
            .eppoCode("MABAN")
            .speciesName("Malus angustifolia")
            .speciesNomination("Malus angustifolia")
            .build());
  }

  @Test
  void map_FiltersOutSequenceNumericZero() throws JsonProcessingException {
    spsCertificate = JsonDeserializer.get(
        "chedpp/partone/commodities/chedpp_trade_commodity_complement_sequence_zero.json",
        SpsCertificate.class);

    List<CommodityComplement> actual = mapper.map(spsCertificate);

    assertThat(actual).containsExactly(
        CommodityComplement.builder()
            .commodityID("0808108090")
            .commodityDescription("Other")
            .complementID(1)
            .complementName("Malus angustifolia")
            .eppoCode("MABAN")
            .speciesName("Malus angustifolia")
            .speciesNomination("Malus angustifolia")
            .build());
  }

  @Test
  void map_ReturnsCommodityComplement_WhenMultipleSpeciesVarietyAndClass()
      throws JsonProcessingException {
    spsCertificate = JsonDeserializer.get(
        "chedpp/partone/commodities/chedpp_trade_commodity_complement_multiple.json",
        SpsCertificate.class);

    List<CommodityComplement> actual = mapper.map(spsCertificate);

    assertThat(actual).containsExactly(
        CommodityComplement.builder()
            .commodityID("0808108090")
            .commodityDescription("Other")
            .complementID(1)
            .complementName("Malus angustifolia")
            .eppoCode("MABAN")
            .speciesName("Malus angustifolia")
            .speciesNomination("Malus angustifolia")
            .build(),
        CommodityComplement.builder()
            .commodityID("0808108090")
            .commodityDescription("Other")
            .complementID(2)
            .complementName("Malus domestica")
            .eppoCode("MABSD")
            .speciesName("Malus domestica")
            .speciesNomination("Malus domestica")
            .build(),
        CommodityComplement.builder()
            .commodityID("0808108090")
            .commodityDescription("Other")
            .complementID(3)
            .complementName("Malus domestica")
            .eppoCode("MABSD")
            .speciesName("Malus domestica")
            .speciesNomination("Malus domestica")
            .build());
  }

  @Test
  void map_ThrowsCommoditiesMapperException_WhenNotCombinedNomenclature() {
    // Given
    getFirstApplicableSpsClassification().getSystemName().get(0).setValue("Another CN Code");

    // When // Then
    Assertions.assertThrows(CommoditiesMapperException.class, () -> mapper.map(spsCertificate));
  }

  @Test
  void map_ThrowsCommoditiesMapperException_WhenNullClassCode() {
    // Given
    getFirstApplicableSpsClassification().setClassCode(null);

    // When // Then
    Assertions.assertThrows(CommoditiesMapperException.class, () -> mapper.map(spsCertificate));
  }

  @Test
  void map_ReturnsNullEPPOCode_WhenNoEPPOCodePresent() {
    // Given
    ApplicableSpsClassification eppoCodeContainer = spsCertificate.getSpsConsignment()
        .getIncludedSpsConsignmentItem().get(0)
        .getIncludedSpsTradeLineItem().get(0)
        .getApplicableSpsClassification().get(1);
    eppoCodeContainer.getSystemID().setValue(null);

    // When
    List<CommodityComplement> actualCommodityComplements = mapper.map(spsCertificate);

    // Then
    assertThat(actualCommodityComplements.get(0).getEppoCode()).isNull();
  }

  @Test
  void map_ReturnsNullEPPOCode_WhenSystemIdIsNull() {
    // Given
    ApplicableSpsClassification eppoCodeContainer = spsCertificate.getSpsConsignment()
        .getIncludedSpsConsignmentItem().get(0)
        .getIncludedSpsTradeLineItem().get(0)
        .getApplicableSpsClassification().get(1);
    eppoCodeContainer.setSystemID(null);

    // When
    List<CommodityComplement> actualCommodityComplements = mapper.map(spsCertificate);

    // Then
    assertThat(actualCommodityComplements.get(0).getEppoCode()).isNull();
  }

  @Test
  void map_ReturnsNullEPPOCode_WhenNoEPPOClassCodePresent() {
    // Given
    ApplicableSpsClassification eppoCodeContainer = spsCertificate.getSpsConsignment()
        .getIncludedSpsConsignmentItem().get(0)
        .getIncludedSpsTradeLineItem().get(0)
        .getApplicableSpsClassification().get(1);
    eppoCodeContainer.setClassCode(null);

    // When
    List<CommodityComplement> actualCommodityComplements = mapper.map(spsCertificate);

    // Then
    assertThat(actualCommodityComplements.get(0).getEppoCode()).isNull();
  }

  @Test
  void map_ReturnsEmptyList_WhenSequenceNumericIsZero() {
    // Given
    spsCertificate.getSpsConsignment()
        .getIncludedSpsConsignmentItem().get(0)
        .getIncludedSpsTradeLineItem().get(0)
        .setSequenceNumeric(new SequenceNumeric().withValue(0));

    // When
    List<CommodityComplement> actual = mapper.map(spsCertificate);

    // Then
    assertThat(actual).isEqualTo(Collections.emptyList());
  }

  @Test
  void map_ThrowsCommoditiesMapperException_WhenNullClassNameValue() {
    // Given
    getFirstApplicableSpsClassification().getClassName().get(0).setValue(null);

    // When // Then
    assertThrows(CommoditiesMapperException.class, () -> mapper.map(spsCertificate));
  }

  private ApplicableSpsClassification getFirstApplicableSpsClassification() {
    return spsCertificate.getSpsConsignment()
        .getIncludedSpsConsignmentItem().get(0)
        .getIncludedSpsTradeLineItem().get(0)
        .getApplicableSpsClassification().get(0);
  }
}
