package uk.gov.defra.tracesx.mapper.staticmappers;

import static uk.gov.defra.tracesx.mapper.staticmappers.SpsCertificateStaticMapper.REPLACEMENT_GUID;

import java.util.Collections;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.trade.dto.SpsAuthenticationType;
import uk.gov.defra.tracesx.trade.dto.SpsExchangedDocument;
import uk.gov.defra.tracesx.trade.dto.SpsNoteType;
import uk.gov.defra.tracesx.trade.dto.SpsPartyType;
import uk.gov.defra.tracesx.trade.dto.TextType;

@Component
public class SpsExchangedDocumentStaticMapper
    implements StaticDataMapper<SpsExchangedDocument> {

  @Override
  public void apply(SpsExchangedDocument spsExchangedDocumentType) {
    setSpsExchangedDocument(spsExchangedDocumentType);
  }

  private void setSpsExchangedDocument(SpsExchangedDocument spsExchangedDocument) {
    spsExchangedDocument.setName(Collections.singletonList(new TextType().withValue(
        REPLACEMENT_GUID)));
    spsExchangedDocument.setDescription(Collections.singletonList(new TextType().withValue(
        REPLACEMENT_GUID)));
    setIncludedSpsNote(spsExchangedDocument);
    setIssuerSpsParty(spsExchangedDocument);
    setSpsAuthenticationType(spsExchangedDocument);
  }

  private void setIncludedSpsNote(SpsExchangedDocument spsExchangedDocument) {
    if (spsExchangedDocument.getIncludedSpsNote() != null) {
      for (SpsNoteType spsNoteType : spsExchangedDocument.getIncludedSpsNote()) {
        if (spsNoteType.getContent() == null || spsNoteType.getContent().isEmpty()) {
          spsNoteType.setContent(Collections.singletonList(new TextType().withValue(
              REPLACEMENT_GUID)));
        }
      }
    }
  }

  private void setIssuerSpsParty(SpsExchangedDocument spsExchangedDocument) {
    SpsPartyType issuerSpsParty = spsExchangedDocument.getIssuerSpsParty();
    if (issuerSpsParty != null && issuerSpsParty.getName() == null) {
      issuerSpsParty.setName(new TextType().withValue(REPLACEMENT_GUID));
    }
  }

  private void setSpsAuthenticationType(SpsExchangedDocument spsExchangedDocument) {
    for (SpsAuthenticationType spsAuthenticationType :
        spsExchangedDocument.getSignatorySpsAuthentication()) {
      if (spsAuthenticationType.getProviderSpsParty().getName() == null) {
        spsAuthenticationType.getProviderSpsParty().setName(new TextType().withValue(
            REPLACEMENT_GUID));
      }
    }
  }
}
