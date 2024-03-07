package uk.gov.defra.stw.mapping.toipaffs.chedpp.commodities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import uk.gov.defra.stw.mapping.toipaffs.exceptions.CommoditiesMapperException;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

@ExtendWith(MockitoExtension.class)
class ChedppComplementParameterSetMapperTest {

  private final UUID TEST_UUID = UUID.fromString("3f8bd1d2-199c-447f-955e-c5a5a9160c95");

  private SpsCertificate spsCertificate;
  private IncludedSpsTradeLineItem includedSpsTradeLineItem;
  private MockedStatic<UUID> mockedUuid;

  @Mock
  private ComplementParameterSetMapper complementParameterSetMapper;
  @Mock
  private ChedppQuantityTypeMapper chedppQuantityTypeMapper;
  @Mock
  private ChedppQuantityMapper chedppQuantityMapper;
  @Mock
  private NetWeightMeasureKeyDataMapper netWeightMeasureKeyDataMapper;
  @Mock
  private NumberOfPackagesKeyDataMapper numberOfPackagesKeyDataMapper;
  @Mock
  private ChedppPackageTypeMapper chedppPackageTypeMapper;
  @Mock
  private ChedppControlledAtmosphereContainerMapper chedppControlledAtmosphereContainerMapper;
  @Mock
  private FinishedOrPropagatedMapper finishedOrPropagatedMapper;
  @Mock
  private VarietyMapper varietyMapper;
  @Mock
  private ClassMapper classMapper;
  @Mock
  private ChedppSequenceNumericMapper chedppSequenceNumericMapper;

  @InjectMocks
  private ChedppComplementParameterSetMapper mapper;

  @BeforeEach
  void setup() throws JsonProcessingException {
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
        chedppPackageTypeMapper,
        includedSpsTradeLineItem)
    ).thenReturn(complementParameterSetKeyDataPairs);
    when(chedppQuantityMapper.map(includedSpsTradeLineItem)).thenReturn(createPair("QUANTITY"));
    when(chedppQuantityTypeMapper.map(includedSpsTradeLineItem))
        .thenReturn(createPair("QUANTITY_TYPE"));
    when(chedppControlledAtmosphereContainerMapper.map(includedSpsTradeLineItem)).
        thenReturn(createPair("CONTAINER"));
    when(finishedOrPropagatedMapper.map(includedSpsTradeLineItem))
        .thenReturn(createPair("FINISHED_PROPAGATED"));
    when(varietyMapper.map(includedSpsTradeLineItem)).thenReturn(createPair("VARIETY"));
    when(classMapper.map(includedSpsTradeLineItem)).thenReturn(createPair("CLASS"));
    when(chedppSequenceNumericMapper.map(includedSpsTradeLineItem))
        .thenReturn(createPair("SEQUENCE"));

    List<ComplementParameterSet> actual = mapper.map(spsCertificate);

    assertThat(actual).hasSize(1);
    ComplementParameterSet actualParameterSet = actual.get(0);
    assertThat(actualParameterSet.getUniqueComplementID()).isEqualTo(TEST_UUID);
    assertThat(actualParameterSet.getComplementID()).isEqualTo(1);
    assertThat(actualParameterSet.getKeyDataPair()).containsExactly(
        createPair("TEST"),
        createPair("QUANTITY"),
        createPair("QUANTITY_TYPE"),
        createPair("CONTAINER"),
        createPair("FINISHED_PROPAGATED"),
        createPair("VARIETY"),
        createPair("CLASS"),
        createPair("SEQUENCE")
    );
  }

  @Test
  void map_ReturnsEmptyKeyDataPair_WhenNoMappersReturnValues() {
    when(complementParameterSetMapper.create(
        netWeightMeasureKeyDataMapper,
        numberOfPackagesKeyDataMapper,
        chedppPackageTypeMapper,
        includedSpsTradeLineItem)
    ).thenReturn(new ArrayList<>());

    List<ComplementParameterSet> actual = mapper.map(spsCertificate);

    assertThat(actual).hasSize(1);
    ComplementParameterSet actualParameterSet = actual.get(0);
    assertThat(actualParameterSet.getUniqueComplementID()).isEqualTo(TEST_UUID);
    assertThat(actualParameterSet.getComplementID()).isEqualTo(1);
    assertThat(actualParameterSet.getKeyDataPair()).isEmpty();
  }

//  @Test
//  void map_ThrowsException_WhenMapperThrowsException() {
//    when(complementParameterSetMapper.create(
//        netWeightMeasureKeyDataMapper,
//        numberOfPackagesKeyDataMapper,
//        chedppPackageTypeMapper,
//        includedSpsTradeLineItem)
//    ).thenReturn(new ArrayList<>());
//    when(chedppQuantityMapper.map(includedSpsTradeLineItem)).thenThrow(new NotificationMapperException("Message"));
//
//    assertThatThrownBy(() -> mapper.map(spsCertificate))
//        .isInstanceOf(CommoditiesMapperException.class);
//  }

  private ComplementParameterSetKeyDataPair createPair(String prefix) {
    return ComplementParameterSetKeyDataPair.builder()
        .key(prefix + "_KEY")
        .data(prefix + "_VALUE")
        .build();
  }
}
