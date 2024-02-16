package uk.gov.defra.stw.mapping.totrade.chedpp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.totrade.chedpp.spsconsignment.ChedppSpsConsignmentMapper;
import uk.gov.defra.stw.mapping.totrade.chedpp.spsexchangeddocument.ChedppSpsExchangedDocumentMapper;
import uk.gov.defra.stw.mapping.totrade.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

@Component
public class ChedppSpsCertificateMapper implements Mapper<Notification, SpsCertificate> {

  private final ChedppSpsConsignmentMapper consignmentMapper;
  private final ChedppSpsExchangedDocumentMapper exchangedDocumentMapper;

  @Autowired
  public ChedppSpsCertificateMapper(
      ChedppSpsConsignmentMapper consignmentMapper,
      ChedppSpsExchangedDocumentMapper exchangedDocumentMapper) {
    this.consignmentMapper = consignmentMapper;
    this.exchangedDocumentMapper = exchangedDocumentMapper;
  }

  @Override
  public SpsCertificate map(Notification data) {
    return new SpsCertificate()
        .withSpsConsignment(consignmentMapper.map(data))
        .withSpsExchangedDocument(exchangedDocumentMapper.map(data));
  }
}
