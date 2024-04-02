package uk.gov.defra.stw.mapping.toipaffs.chedd.commodities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.gov.defra.stw.mapping.dto.IncludedSpsConsignmentItem;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.SequenceNumeric;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.ComplementParameterSetMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.NetWeightMeasureKeyDataMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.NumberOfPackagesKeyDataMapper;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

@ExtendWith(MockitoExtension.class)
class CheddComplementParameterSetMapperTest {

  private final UUID TEST_UUID = UUID.fromString("12345678-1234-1234-1234-123456789012");

  @Mock
  private ComplementParameterSetMapper complementParameterSetMapper;
  @Mock
  private NetWeightMeasureKeyDataMapper netWeightMeasureKeyDataMapper;
  @Mock
  private NumberOfPackagesKeyDataMapper numberOfPackagesKeyDataMapper;
  @Mock
  private CheddPackageTypeKeyDataMapper cheddPackageTypeMapper;

  @InjectMocks
  private CheddComplementParameterSetMapper mapper;

  private SpsCertificate spsCertificate;
  private IncludedSpsTradeLineItem includedSpsTradeLineItem;
  private MockedStatic<UUID> mockedUuid;


  @BeforeEach
  void setup() {
    includedSpsTradeLineItem = new IncludedSpsTradeLineItem()
        .withSequenceNumeric(new SequenceNumeric().withValue(1));
    spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withIncludedSpsConsignmentItem(List.of(
                new IncludedSpsConsignmentItem()
                    .withIncludedSpsTradeLineItem(List.of(
                        includedSpsTradeLineItem
                    ))
            )));

    mockedUuid = Mockito.mockStatic(UUID.class);
    mockedUuid.when(UUID::randomUUID).thenReturn(TEST_UUID);
    mockedUuid.when(() -> UUID.fromString(anyString())).thenCallRealMethod();
  }

  @AfterEach
  void teardown() {
    mockedUuid.close();
  }

  @Test
  void map_ReturnsComplementParameterSet_WhenAllMappersReturnValues() {
    List<ComplementParameterSetKeyDataPair> complementParameterSetKeyDataPairs = new ArrayList<>();
    complementParameterSetKeyDataPairs.add(createPair("TEST"));
    when(complementParameterSetMapper.create(
        netWeightMeasureKeyDataMapper,
        numberOfPackagesKeyDataMapper,
        cheddPackageTypeMapper,
        includedSpsTradeLineItem)
    ).thenReturn(complementParameterSetKeyDataPairs);

    List<ComplementParameterSet> actual = mapper.map(spsCertificate);

    assertThat(actual).hasSize(1);
    ComplementParameterSet actualParameterSet = actual.get(0);
    assertThat(actualParameterSet.getUniqueComplementID()).isEqualTo(TEST_UUID);
    assertThat(actualParameterSet.getComplementID()).isEqualTo(1);
  }

  @Test
  void map_ReturnsEmptyKeyDataPair_WhenNoMappersReturnValues() {
    when(complementParameterSetMapper.create(
        netWeightMeasureKeyDataMapper,
        numberOfPackagesKeyDataMapper,
        cheddPackageTypeMapper,
        includedSpsTradeLineItem)
    ).thenReturn(new ArrayList<>());

    List<ComplementParameterSet> actual = mapper.map(spsCertificate);

    assertThat(actual).hasSize(1);
    ComplementParameterSet actualParameterSet = actual.get(0);
    assertThat(actualParameterSet.getUniqueComplementID()).isEqualTo(TEST_UUID);
    assertThat(actualParameterSet.getComplementID()).isEqualTo(1);
    assertThat(actualParameterSet.getKeyDataPair()).isEmpty();
  }


  private ComplementParameterSetKeyDataPair createPair(String prefix) {
    return ComplementParameterSetKeyDataPair.builder()
        .key(prefix + "_KEY")
        .data(prefix + "_VALUE")
        .build();
  }
}