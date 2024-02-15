package uk.gov.defra.tracesx.validation.trade.rules;

public class ValidationConstraintsConstants {
  public static final String ISO_CODE_REGEX = "^([A-Z]{2,3}||[A-Z]{2}-[A-Z]{2,3})$";
  public static final int MAX_COMPANY_NAME_LENGTH = 255;
  public static final int MAX_ADDRESS_LINE_LENGTH = 255;
  public static final int MAX_CITY_LENGTH = 58;
  public static final int MAX_ZIPCODE_LENGTH = 32;
  public static final int MAX_CONTAINER_NUMBER_LENGTH = 32;
  public static final int MAX_SEAL_NUMBER_LENGTH = 32;
  public static final int MAX_CONTACT_NAME_LENGTH = 255;
  public static final int MAX_CONTACT_TELEPHONE_LENGTH = 30;
  public static final int MAX_CONTACT_EMAIL_LENGTH = 255;
  public static final int MAX_CONTACT_AGENT_LENGTH = 255;

  private ValidationConstraintsConstants() {
  }
}
