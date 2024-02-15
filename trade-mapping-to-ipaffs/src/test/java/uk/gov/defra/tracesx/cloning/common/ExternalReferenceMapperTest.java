package uk.gov.defra.tracesx.cloning.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.notificationschema.representation.ExternalReference;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.ExternalSystem;
import uk.gov.defra.tracesx.trade.dto.IDType;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;
import uk.gov.defra.tracesx.trade.dto.SpsExchangedDocument;

class ExternalReferenceMapperTest {

  private ExternalReferenceMapper externalReferenceMapper;

  @BeforeEach
  void setup() {
    externalReferenceMapper = new ExternalReferenceMapper();
  }

  @Test
  void mapExternalReference_ReturnsExternalReference_WhenEhcIsComplete() {
    SpsCertificate spsCertificate = new SpsCertificate().withSpsExchangedDocument(
        new SpsExchangedDocument().withId(new IDType().withValue("reference")));

    List<ExternalReference> externalReferences = externalReferenceMapper.mapExternalReference(
        spsCertificate, ExternalSystem.ECERT);

    assertThat(externalReferences).isEqualTo(
        List.of(new ExternalReference(ExternalSystem.ECERT, "reference")));
  }
}
