package uk.gov.defra.stw.mapping.toipaffs.exceptions;

public class NotificationMapperException extends Exception {

  public NotificationMapperException(String message) {
    super(message);
  }

  public NotificationMapperException(Throwable cause) {
    super(cause);
  }

  public NotificationMapperException(String message, Throwable cause) {
    super(message, cause);
  }
}
