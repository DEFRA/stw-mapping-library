package uk.gov.defra.stw.mapping.totrade.chedpp.spsexchangeddocument;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.totrade.common.Mapper;
import uk.gov.defra.stw.mapping.totrade.common.spsexchangeddocument.IncludedSpsNoteMapper;
import uk.gov.defra.stw.mapping.totrade.common.spsexchangeddocument.SpsNoteTypeCertificateTypeMapper;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;

@Component
public class ChedppIncludedSpsNoteMapper implements Mapper<Notification, List<SpsNoteType>> {

  private final ChedppIncludedSpsNoteFromPartOneMapper includedSpsNoteFromPartOneMapper;
  private final SpsNoteTypeCertificateTypeMapper spsNoteTypeCertificateTypeMapper;
  private final IncludedSpsNoteMapper includedSpsNoteMapper;
  private final ChedppSpsNoteTypeChildNotificationMapper spsNoteTypeChildNotificationMapper;
  private final ChedppSpsNoteTypeRiskAssessmentMapper spsNoteTypeRiskAssessmentMapper;

  @Autowired
  public ChedppIncludedSpsNoteMapper(
      ChedppIncludedSpsNoteFromPartOneMapper includedSpsNoteFromPartOneMapper,
      SpsNoteTypeCertificateTypeMapper spsNoteTypeCertificateTypeMapper,
      IncludedSpsNoteMapper includedSpsNoteMapper,
      ChedppSpsNoteTypeChildNotificationMapper spsNoteTypeChildNotificationMapper,
      ChedppSpsNoteTypeRiskAssessmentMapper spsNoteTypeRiskAssessmentMapper) {
    this.includedSpsNoteMapper = includedSpsNoteMapper;
    this.includedSpsNoteFromPartOneMapper = includedSpsNoteFromPartOneMapper;
    this.spsNoteTypeCertificateTypeMapper = spsNoteTypeCertificateTypeMapper;
    this.spsNoteTypeChildNotificationMapper = spsNoteTypeChildNotificationMapper;
    this.spsNoteTypeRiskAssessmentMapper = spsNoteTypeRiskAssessmentMapper;
  }

  @Override
  public List<SpsNoteType> map(Notification notification) {
    List<SpsNoteType> notes = new ArrayList<>(includedSpsNoteMapper.map(notification));

    notes.add(spsNoteTypeCertificateTypeMapper.map(notification));
    notes.add(spsNoteTypeChildNotificationMapper.map(notification));
    if (notification.getRiskAssessment() != null) {
      notes.add(spsNoteTypeRiskAssessmentMapper.map(notification));
    }

    PartOne partOne = notification.getPartOne();
    List<SpsNoteType> notesFromPartOne = includedSpsNoteFromPartOneMapper.map(partOne);
    if (!notesFromPartOne.isEmpty()) {
      notes.addAll(notesFromPartOne);
    }

    return notes;
  }
}
