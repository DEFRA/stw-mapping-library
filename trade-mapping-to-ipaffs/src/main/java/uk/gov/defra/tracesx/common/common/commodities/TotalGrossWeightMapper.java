package uk.gov.defra.tracesx.common.common.commodities;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;
import uk.gov.defra.tracesx.trade.dto.SpsNoteType;

@Component
public class TotalGrossWeightMapper implements Mapper<SpsCertificate, BigDecimal> {

  private static final String SUBJECT_CODE_VALE = "total_gross_weight";

  @Override
  public BigDecimal map(SpsCertificate spsCertificate) {
    return spsCertificate.getSpsExchangedDocument().getIncludedSpsNote().stream()
        .filter(this::hasRequiredFields)
        .filter(note -> SUBJECT_CODE_VALE.equals(note.getSubjectCode().getValue()))
        .findFirst()
        .map(note -> new BigDecimal(note.getContent().get(0).getValue()))
        .orElse(null);
  }

  private boolean hasRequiredFields(SpsNoteType note) {
    return note.getContent() != null
        && !note.getContent().isEmpty()
        && note.getContent().get(0).getValue() != null
        && note.getSubjectCode() != null;
  }
}
