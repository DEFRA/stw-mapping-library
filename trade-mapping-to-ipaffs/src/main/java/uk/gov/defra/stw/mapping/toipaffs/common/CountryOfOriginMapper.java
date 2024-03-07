package uk.gov.defra.stw.mapping.toipaffs.common;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;

@Component
public class CountryOfOriginMapper implements Mapper<SpsCertificate, String> {

  @Override
  public String map(SpsCertificate spsCertificate) throws NotificationMapperException {
    return spsCertificate.getSpsConsignment().getIncludedSpsConsignmentItem()
        .stream()
        .flatMap(item -> item.getIncludedSpsTradeLineItem().stream())
        .toList()
        .get(0)
        .getOriginSpsCountry()
        .get(0)
        .getId()
        .getValue();
  }
}
