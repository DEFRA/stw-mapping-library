package uk.gov.defra.stw.mapping.totrade.chedpp.spsexchangeddocument;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsReferencedDocumentType;
import uk.gov.defra.stw.mapping.totrade.common.Mapper;
import uk.gov.defra.stw.mapping.totrade.common.spsexchangeddocument.AccompanyingDocumentMapper.AccompanyingDocumentInformation;
import uk.gov.defra.stw.mapping.totrade.common.spsexchangeddocument.ReferenceSpsReferencedDocumentPartOneMapper;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;

@Component
public class ChedppReferenceSpsReferencedDocumentPartOneMapper implements
    Mapper<Notification, List<SpsReferencedDocumentType>> {

  private final ChedppAccompanyingDocumentMapper accompanyingDocumentMapper;
  private final ReferenceSpsReferencedDocumentPartOneMapper
      referenceSpsReferencedDocumentPartOneMapper;

  @Autowired
  public ChedppReferenceSpsReferencedDocumentPartOneMapper(
      ChedppAccompanyingDocumentMapper accompanyingDocumentMapper,
      ReferenceSpsReferencedDocumentPartOneMapper referenceSpsReferencedDocumentPartOneMapper) {
    this.accompanyingDocumentMapper = accompanyingDocumentMapper;
    this.referenceSpsReferencedDocumentPartOneMapper = referenceSpsReferencedDocumentPartOneMapper;
  }

  @Override
  public List<SpsReferencedDocumentType> map(Notification data) {
    List<SpsReferencedDocumentType> documents = new ArrayList<>();

    PartOne partOne = data.getPartOne();

    if (partOne.getVeterinaryInformation() != null && partOne.getCommodities() != null) {
      documents.addAll(accompanyingDocumentMapper
          .map(createSpsReferencedDocumentTypeInformation(partOne)));
    }

    documents.addAll(referenceSpsReferencedDocumentPartOneMapper.map(data));

    return documents;
  }

  private AccompanyingDocumentInformation createSpsReferencedDocumentTypeInformation(
      PartOne partOne) {
    return new AccompanyingDocumentInformation(
        partOne.getVeterinaryInformation().getAccompanyingDocuments(),
        partOne.getCommodities().getCountryOfOrigin());
  }
}
