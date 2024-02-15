package uk.gov.defra.tracesx.mapper.staticmappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;
import uk.gov.defra.tracesx.trade.dto.SpsConsignment;
import uk.gov.defra.tracesx.trade.dto.SpsExchangedDocument;

@Component
public class SpsCertificateStaticMapper implements StaticDataMapper<SpsCertificate> {

  public static final String REPLACEMENT_GUID = "#{34b7a82a-83e3-4c64-a561-d648860fbe8a}";

  private final StaticDataMapper<SpsExchangedDocument> spsExchangedDocumentStaticDataMapper;
  private final StaticDataMapper<SpsConsignment> spsConsignmentStaticDataMapper;

  @Autowired
  public SpsCertificateStaticMapper(
      StaticDataMapper<SpsExchangedDocument> spsExchangedDocumentStaticDataMapper,
      StaticDataMapper<SpsConsignment> spsConsignmentStaticDataMapper) {
    this.spsExchangedDocumentStaticDataMapper = spsExchangedDocumentStaticDataMapper;
    this.spsConsignmentStaticDataMapper = spsConsignmentStaticDataMapper;
  }

  @Override
  public void apply(SpsCertificate spsCertificate) {
    spsExchangedDocumentStaticDataMapper.apply(spsCertificate.getSpsExchangedDocument());
    spsConsignmentStaticDataMapper.apply(spsCertificate.getSpsConsignment());
  }
}