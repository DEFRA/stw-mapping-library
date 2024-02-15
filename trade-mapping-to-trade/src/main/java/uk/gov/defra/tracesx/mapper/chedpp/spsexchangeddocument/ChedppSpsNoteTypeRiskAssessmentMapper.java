package uk.gov.defra.tracesx.mapper.chedpp.spsexchangeddocument;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.mapper.chedpp.spsexchangeddocument.exceptions.RiskAssessmentMappingException;
import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;
import uk.gov.defra.tracesx.notificationschema.representation.RiskAssessment;
import uk.gov.defra.tracesx.trade.dto.CodeType;
import uk.gov.defra.tracesx.trade.dto.SpsNoteType;
import uk.gov.defra.tracesx.trade.dto.TextType;

@Component
@Slf4j
public class ChedppSpsNoteTypeRiskAssessmentMapper implements Mapper<Notification, SpsNoteType> {

  private static final String RISK_ASSESSMENT = "RISK_ASSESSMENT";
  private final ObjectMapper objectMapper;

  public ChedppSpsNoteTypeRiskAssessmentMapper() {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(Include.NON_EMPTY);
  }

  @Override
  public SpsNoteType map(Notification data) {
    SpsNoteType spsNoteType;
    try {
      spsNoteType = new SpsNoteType()
          .withContent(Collections.singletonList(new TextType()
              .withValue(objectMapper.writeValueAsString(isArticle72Country(data)
                  ? getRiskAssessmentWithoutArticle72Results(data) : data.getRiskAssessment()))))
          .withSubjectCode(new CodeType()
              .withValue(RISK_ASSESSMENT));
    } catch (JsonProcessingException ex) {
      throw new RiskAssessmentMappingException("Failed to parse risk assessment JSON to string",
          ex);
    }
    return spsNoteType;
  }

  private boolean isArticle72Country(Notification data) {
    return Optional.ofNullable(data.getPartOne())
        .map(PartOne::getCommodities)
        .map(Commodities::getIsLowRiskArticle72Country)
        .orElse(false);
  }

  private RiskAssessment getRiskAssessmentWithoutArticle72Results(Notification data) {
    RiskAssessment riskAssessment = cloneRiskAssessment(data);
    List<UUID> nonArticle72Uuids = getNonArticle72Uuids(data);
    if (riskAssessment.getCommodityResults() != null && !nonArticle72Uuids.isEmpty()) {
      riskAssessment.setCommodityResults(riskAssessment.getCommodityResults()
          .stream()
          .filter(Objects::nonNull)
          .filter(commodityRiskResult ->
              nonArticle72Uuids.contains(commodityRiskResult.getUniqueId()))
          .toList());
      return riskAssessment;
    } else {
      return null;
    }
  }

  private List<UUID> getNonArticle72Uuids(Notification data) {
    if (data.getPartOne() != null
        && data.getPartOne().getCommodities() != null
        && data.getPartOne().getCommodities().getComplementParameterSet() != null) {
      return data.getPartOne().getCommodities().getComplementParameterSet()
          .stream()
          .filter(Objects::nonNull)
          .filter(complementParameterSet -> !complementParameterSet.isArticle72())
          .map(ComplementParameterSet::getUniqueComplementID).toList();
    } else {
      return Collections.emptyList();
    }
  }

  private RiskAssessment cloneRiskAssessment(Notification data) {
    return RiskAssessment.builder()
        .assessmentDateTime(data.getRiskAssessment().getAssessmentDateTime())
        .commodityResults(data.getRiskAssessment().getCommodityResults())
        .build();
  }
}
