package uk.gov.defra.stw.mapping.toipaffs.common;

import java.util.Optional;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;

@Component
public class ReferenceNumberMapper implements Mapper<SpsCertificate, String> {

  @Override
  public String map(SpsCertificate data) throws NotificationMapperException {
    return Optional.ofNullable(data.getSpsConsignment().getId())
        .map(IDType::getValue)
        .orElse(null);
  }
}
