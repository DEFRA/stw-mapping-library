package uk.gov.defra.stw.mapping.toipaffs.cloning.common;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.toipaffs.common.common.commodities.NumberOfPackagesKeyDataMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;

@ExtendWith(MockitoExtension.class)
class NumberOfPackagesMapperTest {

  @Mock
  private PhysicalSpsPackageMapper physicalSpsPackageMapper;
  @Mock
  private NumberOfPackagesKeyDataMapper keyDataMapper;

  @InjectMocks
  private NumberOfPackagesMapper numberOfPackagesMapper;

  @Test
  void map_CallsPhysicalSpsPackageMapper() throws NotificationMapperException {
    numberOfPackagesMapper.map(new IncludedSpsTradeLineItem());
    verify(physicalSpsPackageMapper, times(1)).map(new IncludedSpsTradeLineItem(), keyDataMapper);
  }

}
