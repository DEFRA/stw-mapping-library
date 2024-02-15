package uk.gov.defra.tracesx.mapper.chedpp.spsexchangeddocument;

import static uk.gov.defra.tracesx.mapper.common.utils.Format.localDateTime;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.mapper.common.spsexchangeddocument.StatusCodeMapper;
import uk.gov.defra.tracesx.mapper.common.spsexchangeddocument.SubmittedByMapper;
import uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.trade.dto.DateTime;
import uk.gov.defra.tracesx.trade.dto.DateTimeType;
import uk.gov.defra.tracesx.trade.dto.DocumentCodeType;
import uk.gov.defra.tracesx.trade.dto.IDType;
import uk.gov.defra.tracesx.trade.dto.SpsExchangedDocument;

@Component
public class ChedppSpsExchangedDocumentMapper
    implements Mapper<Notification, SpsExchangedDocument> {

  private static final String DOCUMENT_TYPE_CODE = "636";

  private final ChedppIncludedSpsNoteMapper includedSpsNoteMapper;
  private final ChedppSignatorySpsAuthenticationMapper signatorySpsAuthenticationMapper;
  private final ChedppReferenceSpsReferencedDocumentMapper referenceSpsReferencedDocumentMapper;
  private final StatusCodeMapper statusCodeMapper;
  private final ChedppIssuerSpsPartyMapper issuerSpsPartyMapper;
  private final SubmittedByMapper submittedByMapper;

  @Autowired
  public ChedppSpsExchangedDocumentMapper(
      ChedppIncludedSpsNoteMapper includedSpsNoteMapper,
      ChedppSignatorySpsAuthenticationMapper signatorySpsAuthenticationMapper,
      ChedppReferenceSpsReferencedDocumentMapper referenceSpsReferencedDocumentMapper,
      StatusCodeMapper statusCodeMapper,
      ChedppIssuerSpsPartyMapper issuerSpsPartyMapper, SubmittedByMapper submittedByMapper) {
    this.includedSpsNoteMapper = includedSpsNoteMapper;
    this.signatorySpsAuthenticationMapper = signatorySpsAuthenticationMapper;
    this.referenceSpsReferencedDocumentMapper = referenceSpsReferencedDocumentMapper;
    this.statusCodeMapper = statusCodeMapper;
    this.issuerSpsPartyMapper = issuerSpsPartyMapper;
    this.submittedByMapper = submittedByMapper;
  }

  @Override
  public SpsExchangedDocument map(Notification data) {
    return new SpsExchangedDocument()
        .withIncludedSpsNote(includedSpsNoteMapper.map(data))
        .withIssueDateTime(mapDateTimeType(data.getPartOne().getSubmissionDate()))
        .withIssuerSpsParty(issuerSpsPartyMapper.map(data.getPartOne().getPersonResponsible()))
        .withSignatorySpsAuthentication(signatorySpsAuthenticationMapper.map(data))
        .withReferenceSpsReferencedDocument(referenceSpsReferencedDocumentMapper.map(data))
        .withStatusCode(statusCodeMapper.map(data.getStatus()))
        .withTypeCode(mapDocumentTypeCode())
        .withSubmittedBy(submittedByMapper.map(data.getPartOne().getSubmittedBy()))
        .withId(mapIdType(data));
  }

  private DocumentCodeType mapDocumentTypeCode() {
    return new DocumentCodeType()
        .withName("Health certificate (Common Health Entry Document)")
        .withValue(DOCUMENT_TYPE_CODE);
  }

  private IDType mapIdType(Notification data) {
    return SpsTypeConverter.getIdType(data.getReferenceNumber());
  }

  private DateTimeType mapDateTimeType(LocalDateTime dateTime) {
    return new DateTimeType()
        .withDateTime(new DateTime()
            .withValue(localDateTime.apply(dateTime)));
  }
}
