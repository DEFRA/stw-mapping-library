package uk.gov.defra.stw.mapping.toipaffs.cloning.common;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.tracesx.notificationschema.representation.ExternalReference;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.ExternalSystem;

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
