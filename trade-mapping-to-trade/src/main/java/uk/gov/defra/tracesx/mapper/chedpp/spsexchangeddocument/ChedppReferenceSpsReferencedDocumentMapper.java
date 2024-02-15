package uk.gov.defra.tracesx.mapper.chedpp.spsexchangeddocument;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.trade.dto.SpsReferencedDocumentType;

@Component
public class ChedppReferenceSpsReferencedDocumentMapper implements
    Mapper<Notification, List<SpsReferencedDocumentType>> {

  private final ChedppReferenceSpsReferencedDocumentPartOneMapper partOneMapper;
  private final ChedppReferenceSpsReferencedDocumentPartTwoMapper partTwoMapper;
  private final ChedppSplitConsignmentMapper splitConsignmentMapper;

  @Autowired
  public ChedppReferenceSpsReferencedDocumentMapper(
      ChedppReferenceSpsReferencedDocumentPartOneMapper partOneMapper,
      ChedppReferenceSpsReferencedDocumentPartTwoMapper partTwoMapper,
      ChedppSplitConsignmentMapper splitConsignmentMapper) {
    this.partOneMapper = partOneMapper;
    this.partTwoMapper = partTwoMapper;
    this.splitConsignmentMapper = splitConsignmentMapper;
  }

  @Override
  public List<SpsReferencedDocumentType> map(Notification data) {
    List<SpsReferencedDocumentType> documents = new ArrayList<>(partOneMapper.map(data));
    documents.addAll(splitConsignmentMapper.map(data));
    documents.addAll(partTwoMapper.map(data));
    return documents;
  }
}
