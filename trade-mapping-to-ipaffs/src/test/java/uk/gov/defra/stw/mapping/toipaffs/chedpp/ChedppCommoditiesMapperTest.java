package uk.gov.defra.stw.mapping.toipaffs.chedpp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.chedpp.commodities.ChedppCommodityComplementMapper;
import uk.gov.defra.stw.mapping.toipaffs.chedpp.commodities.ChedppComplementParameterSetMapper;
import uk.gov.defra.stw.mapping.toipaffs.chedpp.commodities.TotalGrossVolumeMapper;
import uk.gov.defra.stw.mapping.toipaffs.chedpp.commodities.TotalGrossVolumeUnitMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.ConsignedCountryMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.CountryOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.RegionOfOriginMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.NumberOfPackagesMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.TotalGrossWeightMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.TotalNetWeightMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.CommoditiesMapperException;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;

@ExtendWith(MockitoExtension.class)
class ChedppCommoditiesMapperTest {

  @Mock
  private ChedppCommodityComplementMapper chedppCommodityComplementMapper;
  @Mock
  private ChedppComplementParameterSetMapper chedppComplementParameterSetMapper;
  @Mock
  private RegionOfOriginMapper regionOfOriginMapper;
  @Mock
  private TotalGrossWeightMapper totalGrossWeightMapper;
  @Mock
  private CountryOfOriginMapper countryOfOriginMapper;
  @Mock
  private TotalNetWeightMapper totalNetWeightMapper;
  @Mock
  private TotalGrossVolumeMapper totalGrossVolumeMapper;
  @Mock
  private TotalGrossVolumeUnitMapper totalGrossVolumeUnitMapper;
  @Mock
  private ConsignedCountryMapper consignedCountryMapper;
  @Mock
  private NumberOfPackagesMapper numberOfPackagesMapper;

  @InjectMocks
  private ChedppCommoditiesMapper mapper;

  private SpsCertificate spsCertificate;

  @BeforeEach
  void setup() {
    spsCertificate = new SpsCertificate();
  }

  @Test
  void map_ReturnsCommodities() throws NotificationMapperException {
    CommodityComplement commodityComplement = new CommodityComplement();
    ComplementParameterSet complementParameterSet = new ComplementParameterSet();
    when(chedppCommodityComplementMapper.map(spsCertificate)).thenReturn(
        List.of(commodityComplement));
    when(chedppComplementParameterSetMapper.map(spsCertificate)).thenReturn(
        List.of(complementParameterSet));
    when(countryOfOriginMapper.map(spsCertificate)).thenReturn("Country of origin");
    when(regionOfOriginMapper.map(spsCertificate)).thenReturn("Region of origin");
    when(consignedCountryMapper.map(spsCertificate)).thenReturn("Consigned country");
    when(totalGrossWeightMapper.map(spsCertificate)).thenReturn(BigDecimal.valueOf(1.0));
    when(totalNetWeightMapper.map(spsCertificate)).thenReturn(BigDecimal.valueOf(2.0));
    when(totalGrossVolumeMapper.map(spsCertificate)).thenReturn(BigDecimal.valueOf(3.0));
    when(totalGrossVolumeUnitMapper.map(spsCertificate)).thenReturn("Total gross volume unit");
    when(numberOfPackagesMapper.map(spsCertificate)).thenReturn(1);

    Commodities actual = mapper.map(spsCertificate);

    assertThat(actual).isEqualTo(Commodities.builder()
        .commodityComplement(List.of(commodityComplement))
        .complementParameterSet(List.of(complementParameterSet))
        .countryOfOrigin("Country of origin")
        .regionOfOrigin("Region of origin")
        .consignedCountry("Consigned country")
        .totalGrossWeight(BigDecimal.valueOf(1.0))
        .totalNetWeight(BigDecimal.valueOf(2.0))
        .totalGrossVolume(BigDecimal.valueOf(3.0))
        .totalGrossVolumeUnit("Total gross volume unit")
        .numberOfPackages(1)
        .build());
  }

  @Test
  void map_ThrowsNotificationMapperException_WhenMapperThrowsException() {
    when(chedppCommodityComplementMapper.map(spsCertificate))
        .thenThrow(new CommoditiesMapperException("Message"));

    assertThatThrownBy(() -> mapper.map(spsCertificate))
        .isInstanceOf(NotificationMapperException.class)
        .hasMessage(
            "uk.gov.defra.stw.mapping.toipaffs.exceptions.CommoditiesMapperException: Message")
        .hasRootCauseInstanceOf(CommoditiesMapperException.class)
        .hasRootCauseMessage("Message");
  }
}
