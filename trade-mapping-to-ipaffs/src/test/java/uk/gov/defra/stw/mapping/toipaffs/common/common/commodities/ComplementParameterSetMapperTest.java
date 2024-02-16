package uk.gov.defra.stw.mapping.toipaffs.common.common.commodities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.MeasureType;
import uk.gov.defra.stw.mapping.dto.PhysicalSpsPackage;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.CommoditiesMapperException;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

@ExtendWith(MockitoExtension.class)
class ComplementParameterSetMapperTest {

  @Mock
  private NetWeightMeasureKeyDataMapper netWeightMeasureKeyDataMapper;
  @Mock
  private NumberOfPackagesKeyDataMapper numberOfPackagesKeyDataMapper;
  @Mock
  private PackageTypeMapper packageTypeMapper;

  private ComplementParameterSetMapper mapper;

  @BeforeEach
  void setup() {
    mapper = new ComplementParameterSetMapper();
  }

  @Test
  void create_ReturnsComplementParameterSetKeyDataPairList_WhenComplete()
      throws NotificationMapperException {
    IncludedSpsTradeLineItem includedSpsTradeLineItem = new IncludedSpsTradeLineItem()
        .withPhysicalSpsPackage(Collections.singletonList(new PhysicalSpsPackage()
            .withLevelCode(new CodeType().withValue("4"))))
        .withNetWeightMeasure(new MeasureType());

    when(netWeightMeasureKeyDataMapper.map(any(IncludedSpsTradeLineItem.class)))
        .thenReturn(createComplementParameterSetKeyDataPair("netweight", "929.26"));
    when(numberOfPackagesKeyDataMapper.map(any(PhysicalSpsPackage.class)))
        .thenReturn(createComplementParameterSetKeyDataPair("number_package", "2"));
    when(packageTypeMapper.map(any(PhysicalSpsPackage.class)))
        .thenReturn(createComplementParameterSetKeyDataPair("type_package", "Bag"));

    List<ComplementParameterSetKeyDataPair> complementParameterSetKeyDataPairs = mapper.create(
        netWeightMeasureKeyDataMapper,
        numberOfPackagesKeyDataMapper,
        packageTypeMapper,
        includedSpsTradeLineItem);

    assertThat(complementParameterSetKeyDataPairs)
        .isEqualTo(Arrays.asList(
            createComplementParameterSetKeyDataPair("netweight", "929.26"),
            createComplementParameterSetKeyDataPair("number_package", "2"),
            createComplementParameterSetKeyDataPair("type_package", "Bag")));
  }

  @Test
  void create_ReturnsParameterSetKeyDataPairListWithoutNetWeight_WhenNullNetWeight()
      throws NotificationMapperException {
    IncludedSpsTradeLineItem includedSpsTradeLineItem = new IncludedSpsTradeLineItem()
        .withPhysicalSpsPackage(Collections.singletonList(new PhysicalSpsPackage()
            .withLevelCode(new CodeType().withValue("4"))))
        .withNetWeightMeasure(new MeasureType());

    when(netWeightMeasureKeyDataMapper.map(any(IncludedSpsTradeLineItem.class)))
        .thenReturn(null);
    when(numberOfPackagesKeyDataMapper.map(any(PhysicalSpsPackage.class)))
        .thenReturn(createComplementParameterSetKeyDataPair("number_package", "2"));
    when(packageTypeMapper.map(any(PhysicalSpsPackage.class)))
        .thenReturn(createComplementParameterSetKeyDataPair("type_package", "Bag"));

    List<ComplementParameterSetKeyDataPair> complementParameterSetKeyDataPairs = mapper.create(
        netWeightMeasureKeyDataMapper,
        numberOfPackagesKeyDataMapper,
        packageTypeMapper,
        includedSpsTradeLineItem);

    assertThat(complementParameterSetKeyDataPairs)
        .isEqualTo(Arrays.asList(
            createComplementParameterSetKeyDataPair("number_package", "2"),
            createComplementParameterSetKeyDataPair("type_package", "Bag")));
  }

  @Test
  void create_ThrowsCommoditiesMapperException_WhenNullPhysicalSpsPackage()
      throws NotificationMapperException {
    IncludedSpsTradeLineItem includedSpsTradeLineItem = new IncludedSpsTradeLineItem()
        .withPhysicalSpsPackage(null)
        .withNetWeightMeasure(new MeasureType());

    when(netWeightMeasureKeyDataMapper.map(any(IncludedSpsTradeLineItem.class)))
        .thenReturn(createComplementParameterSetKeyDataPair("netweight", "929.26"));

    assertThrows(CommoditiesMapperException.class, () -> mapper.create(
        netWeightMeasureKeyDataMapper,
        numberOfPackagesKeyDataMapper,
        packageTypeMapper,
        includedSpsTradeLineItem));
  }

  @Test
  void create_ThrowsCommoditiesMapperException_WhenEmptyPhysicalSpsPackage()
      throws NotificationMapperException {
    IncludedSpsTradeLineItem includedSpsTradeLineItem = new IncludedSpsTradeLineItem()
        .withPhysicalSpsPackage(Collections.emptyList())
        .withNetWeightMeasure(new MeasureType());

    when(netWeightMeasureKeyDataMapper.map(any(IncludedSpsTradeLineItem.class)))
        .thenReturn(createComplementParameterSetKeyDataPair("netweight", "929.26"));

    assertThrows(CommoditiesMapperException.class, () -> mapper.create(
        netWeightMeasureKeyDataMapper,
        numberOfPackagesKeyDataMapper,
        packageTypeMapper,
        includedSpsTradeLineItem));
  }

  @Test
  void create_ThrowsCommoditiesMapperException_WhenNotificationMapperExceptionIsThrown()
      throws NotificationMapperException {
    IncludedSpsTradeLineItem includedSpsTradeLineItem = new IncludedSpsTradeLineItem()
        .withPhysicalSpsPackage(Collections.emptyList())
        .withNetWeightMeasure(new MeasureType());

    when(netWeightMeasureKeyDataMapper.map(any(IncludedSpsTradeLineItem.class)))
        .thenThrow(new NotificationMapperException(""));

    assertThrows(CommoditiesMapperException.class, () -> mapper.create(
        netWeightMeasureKeyDataMapper,
        numberOfPackagesKeyDataMapper,
        packageTypeMapper,
        includedSpsTradeLineItem));
  }

  @Test
  void create_ThrowsCommoditiesMapperException_WhenOtherThanNoPackagingHierarchy() {
    IncludedSpsTradeLineItem includedSpsTradeLineItem = new IncludedSpsTradeLineItem()
        .withPhysicalSpsPackage(Collections.singletonList(new PhysicalSpsPackage()
            .withLevelCode(new CodeType().withValue("99"))))
        .withNetWeightMeasure(new MeasureType());

    assertThrows(CommoditiesMapperException.class, () -> mapper.create(
        netWeightMeasureKeyDataMapper,
        numberOfPackagesKeyDataMapper,
        packageTypeMapper,
        includedSpsTradeLineItem));
  }

  private ComplementParameterSetKeyDataPair createComplementParameterSetKeyDataPair(String key, String data) {
    return ComplementParameterSetKeyDataPair.builder()
        .key(key)
        .data(data)
        .build();
  }

  public static class PackageTypeMapper implements
      Mapper<PhysicalSpsPackage, ComplementParameterSetKeyDataPair> {
    @Override
    public ComplementParameterSetKeyDataPair map(PhysicalSpsPackage data)
        throws NotificationMapperException {
      return ComplementParameterSetKeyDataPair.builder().build();
    }
  }
}
