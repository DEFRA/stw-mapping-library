package uk.gov.defra.tracesx.validation.trade.utils;

import uk.gov.defra.tracesx.notificationschema.representation.Decision;
import uk.gov.defra.tracesx.notificationschema.representation.PartTwo;

public class NotificationUtils {

  private NotificationUtils() { }

  public static boolean isPartTwoEmpty(PartTwo partTwo) {
    PartTwo emptyPartTwo = PartTwo.builder().build();
    return partTwo == null || partTwo.equals(emptyPartTwo);
  }

  public static boolean isDecisionEmpty(Decision decision) {
    Decision emptyDecision = Decision.builder().build();
    return decision == null || decision.equals(emptyDecision);
  }
}
