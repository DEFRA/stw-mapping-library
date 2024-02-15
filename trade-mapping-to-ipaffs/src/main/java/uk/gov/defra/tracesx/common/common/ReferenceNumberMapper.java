package uk.gov.defra.tracesx.common.common;

import java.util.Optional;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.trade.dto.IDType;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

@Component
public class ReferenceNumberMapper implements Mapper<SpsCertificate, String> {

  @Override
  public String map(SpsCertificate data) throws NotificationMapperException {
    return Optional.ofNullable(data.getSpsConsignment().getId())
        .map(IDType::getValue)
        .orElse(null);
  }
}
