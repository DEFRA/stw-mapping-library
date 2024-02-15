package uk.gov.defra.tracesx.mapper.exceptions;

public class TradeValidationException extends Exception {
  public TradeValidationException(String exception) {
    super(exception);
  }
}