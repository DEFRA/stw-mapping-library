package uk.gov.defra.stw.mapping.toipaffs.cloning.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.CommoditiesMapperException;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

@ExtendWith(MockitoExtension.class)
class CommoditySetKeyDataPairMapperTest {

  @Mock
  private NumberOfPackagesMapper numberOfPackagesMapper;

  @InjectMocks
  private CommoditySetKeyDataPairMapper keyDataPairMapper;

  @Test
  void map_ReturnsKeyDataPairList_WhenNoExceptionIsThrown()
      throws NotificationMapperException {
    when(numberOfPackagesMapper.map(new IncludedSpsTradeLineItem())).thenReturn(createKeyDataPair());
    List<ComplementParameterSetKeyDataPair> keyDataPairs = keyDataPairMapper.map(new IncludedSpsTradeLineItem(), numberOfPackagesMapper);
    assertThat(keyDataPairs).hasSize(1);
    assertThat(keyDataPairs.get(0)).isEqualTo(createKeyDataPair());
  }

  @Test
  void map_ReturnsKeyDataPairListWithNoNulls_WhenNullIsReturnedFromMapper()
      throws NotificationMapperException {
    when(numberOfPackagesMapper.map(new IncludedSpsTradeLineItem())).thenReturn(createKeyDataPair(), null);
    List<ComplementParameterSetKeyDataPair> keyDataPairs = keyDataPairMapper.map(new IncludedSpsTradeLineItem(), numberOfPackagesMapper, numberOfPackagesMapper);
    assertThat(keyDataPairs).hasSize(1);
    assertThat(keyDataPairs.get(0)).isEqualTo(createKeyDataPair());
  }

  @Test
  void map_ThrowsException_WhenMapperThrowsException()
      throws NotificationMapperException {
    when(numberOfPackagesMapper.map(new IncludedSpsTradeLineItem())).thenThrow(new NotificationMapperException("Exception thrown"));
    assertThrows(CommoditiesMapperException.class, () -> keyDataPairMapper.map(new IncludedSpsTradeLineItem(), numberOfPackagesMapper));
  }

  private ComplementParameterSetKeyDataPair createKeyDataPair() {
    return new ComplementParameterSetKeyDataPair("number_package", "5");
  }
}
