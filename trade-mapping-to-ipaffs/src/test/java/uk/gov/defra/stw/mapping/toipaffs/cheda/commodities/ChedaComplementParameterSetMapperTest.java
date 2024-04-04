package uk.gov.defra.stw.mapping.toipaffs.cheda.commodities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.IncludedSpsConsignmentItem;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.ItemQuantity;
import uk.gov.defra.stw.mapping.dto.PhysicalSpsPackage;
import uk.gov.defra.stw.mapping.dto.SequenceNumeric;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.NumberOfPackagesKeyDataMapper;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.notificationschema.representation.Identifier;

@ExtendWith(MockitoExtension.class)
class ChedaComplementParameterSetMapperTest {

  private final UUID TEST_UUID = UUID.fromString("12345678-1234-1234-1234-123456789012");

  @Mock
  private NumberOfPackagesKeyDataMapper numberOfPackagesKeyDataMapper;
  @Mock
  private NumberOfAnimalsKeyDataMapper numberOfAnimalsKeyDataMapper;
  @Mock
  private IdentifiersMapper identifiersMapper;

  @InjectMocks
  private ChedaComplementParameterSetMapper mapper;

  @Test
  void map_ReturnsComplementParameterSet_WhenAllMappersReturnValues() {
    PhysicalSpsPackage physicalSpsPackage = new PhysicalSpsPackage()
        .withItemQuantity(new ItemQuantity().withValue(1.0));
    IncludedSpsTradeLineItem tradeLineItem = new IncludedSpsTradeLineItem()
        .withPhysicalSpsPackage(List.of(physicalSpsPackage))
        .withSequenceNumeric(new SequenceNumeric().withValue(1));
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withIncludedSpsConsignmentItem(List.of(new IncludedSpsConsignmentItem()
                .withIncludedSpsTradeLineItem(List.of(tradeLineItem)))));

    when(numberOfPackagesKeyDataMapper.map(physicalSpsPackage)).thenReturn(createPair("NUMBER_OF_PACKAGES"));
    when(numberOfAnimalsKeyDataMapper.map(tradeLineItem)).thenReturn(createPair("NUMBER_OF_ANIMALS"));
    when(identifiersMapper.map(tradeLineItem)).thenReturn(List.of(Identifier.builder()
        .speciesNumber(1)
        .build()));

    try (MockedStatic<UUID> mockedUuid = mockStatic(UUID.class)) {
      mockedUuid.when(UUID::randomUUID).thenReturn(TEST_UUID);
      List<ComplementParameterSet> actual = mapper.map(spsCertificate);

      assertThat(actual).hasSize(1);
      ComplementParameterSet actualParameterSet = actual.get(0);
      assertThat(actualParameterSet.getUniqueComplementID()).isEqualTo(TEST_UUID);
      assertThat(actualParameterSet.getComplementID()).isEqualTo(1);
      assertThat(actualParameterSet.getKeyDataPair()).containsExactly(
          createPair("NUMBER_OF_PACKAGES"),
          createPair("NUMBER_OF_ANIMALS")
      );
      assertThat(actualParameterSet.getIdentifiers()).containsExactly(Identifier.builder()
          .speciesNumber(1)
          .build());
    }
  }

  private ComplementParameterSetKeyDataPair createPair(String prefix) {
    return ComplementParameterSetKeyDataPair.builder()
        .key(prefix + "_KEY")
        .data(prefix + "_VALUE")
        .build();
  }
}
