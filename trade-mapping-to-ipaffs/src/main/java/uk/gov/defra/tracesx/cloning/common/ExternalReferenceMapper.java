package uk.gov.defra.tracesx.cloning.common;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.notificationschema.representation.ExternalReference;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.ExternalSystem;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

@Component
public class ExternalReferenceMapper {

  public List<ExternalReference> mapExternalReference(SpsCertificate data,
      ExternalSystem externalSystem) {
    return Collections.singletonList(
        ExternalReference.builder()
            .system(externalSystem)
            .reference(data.getSpsExchangedDocument().getId().getValue())
            .build());
  }
}
