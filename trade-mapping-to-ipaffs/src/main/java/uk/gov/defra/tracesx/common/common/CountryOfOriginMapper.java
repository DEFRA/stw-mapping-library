package uk.gov.defra.tracesx.common.common;

import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

@Component
public class CountryOfOriginMapper implements Mapper<SpsCertificate, String> {

  @Override
  public String map(SpsCertificate spsCertificate) throws NotificationMapperException {
    return spsCertificate.getSpsConsignment().getIncludedSpsConsignmentItem()
        .stream()
        .flatMap(item -> item.getIncludedSpsTradeLineItem().stream())
        .collect(Collectors.toList()).get(0)
        .getOriginSpsCountry().get(0)
        .getId().getValue();
  }
}
