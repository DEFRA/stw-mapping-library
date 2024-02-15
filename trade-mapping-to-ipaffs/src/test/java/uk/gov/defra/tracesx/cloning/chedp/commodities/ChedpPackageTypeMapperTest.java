package uk.gov.defra.tracesx.cloning.chedp.commodities;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.tracesx.cloning.common.PhysicalSpsPackageMapper;
import uk.gov.defra.tracesx.common.chedp.commodities.ChedpPackageTypeKeyDataMapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;

@ExtendWith(MockitoExtension.class)
class ChedpPackageTypeMapperTest {

  @Mock
  private PhysicalSpsPackageMapper physicalSpsPackageMapper;
  @Mock
  private ChedpPackageTypeKeyDataMapper keyDataMapper;

  @InjectMocks
  private ChedpPackageTypeMapper packageTypeMapper;

  @Test
  void map_CallsPhysicalSpsPackageMapper() throws NotificationMapperException {
    packageTypeMapper.map(new IncludedSpsTradeLineItem());
    verify(physicalSpsPackageMapper, times(1)).map(new IncludedSpsTradeLineItem(), keyDataMapper);
  }
}
