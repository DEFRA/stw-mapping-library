package uk.gov.defra.tracesx.mapper.chedpp.spsexchangeddocument;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.mapper.common.spsexchangeddocument.AccompanyingDocumentMapper.AccompanyingDocumentInformation;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.trade.dto.SpsReferencedDocumentType;
import uk.gov.defra.tracesx.validation.trade.utils.NotificationUtils;

@Component
public class ChedppReferenceSpsReferencedDocumentPartTwoMapper implements
    Mapper<Notification, List<SpsReferencedDocumentType>> {

  private static final String AT_INSPECTION = "AT_INSPECTION";

  private final ChedppAccompanyingDocumentMapper accompanyingDocumentMapper;

  public ChedppReferenceSpsReferencedDocumentPartTwoMapper(
      ChedppAccompanyingDocumentMapper accompanyingDocumentMapper) {
    this.accompanyingDocumentMapper = accompanyingDocumentMapper;
  }

  @Override
  public List<SpsReferencedDocumentType> map(Notification data) {
    List<SpsReferencedDocumentType> documents = new ArrayList<>();

    if (NotificationUtils.isPartTwoEmpty(data.getPartTwo())) {
      return Collections.emptyList();
    }

    if (data.getPartTwo().getAccompanyingDocuments() != null
        && data.getPartOne().getCommodities() != null) {
      documents.addAll(accompanyingDocumentMapper
          .map(createSpsReferencedDocumentTypeInformation(data)));
    }

    return documents;
  }

  private AccompanyingDocumentInformation createSpsReferencedDocumentTypeInformation(
      Notification data) {
    return new AccompanyingDocumentInformation(
        data.getPartTwo().getAccompanyingDocuments(),
        data.getPartOne().getCommodities().getCountryOfOrigin(),
        AT_INSPECTION);
  }
}
