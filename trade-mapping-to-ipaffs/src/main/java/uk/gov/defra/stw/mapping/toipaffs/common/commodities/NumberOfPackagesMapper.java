package uk.gov.defra.stw.mapping.toipaffs.common.commodities;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.ItemQuantity;
import uk.gov.defra.stw.mapping.dto.PhysicalSpsPackage;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;

@Component
public class NumberOfPackagesMapper implements Mapper<SpsCertificate, Integer> {

  @Override
  public Integer map(SpsCertificate spsCertificate) {
    return spsCertificate.getSpsConsignment().getIncludedSpsConsignmentItem().stream()
        .flatMap(consignmentItem -> consignmentItem.getIncludedSpsTradeLineItem().stream())
        .map(IncludedSpsTradeLineItem::getPhysicalSpsPackage)
        .map(physicalSpsPackages -> physicalSpsPackages.get(0))
        .map(PhysicalSpsPackage::getItemQuantity)
        .map(ItemQuantity::getValue)
        .map(Double::intValue)
        .reduce(0, Integer::sum);
  }
}
