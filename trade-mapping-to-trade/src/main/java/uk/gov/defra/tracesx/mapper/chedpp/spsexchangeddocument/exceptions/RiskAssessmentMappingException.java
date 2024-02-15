package uk.gov.defra.tracesx.mapper.chedpp.spsexchangeddocument.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;

public class RiskAssessmentMappingException extends RuntimeException {

  public RiskAssessmentMappingException(String message, JsonProcessingException cause) {
    super(message, cause);
  }
}
