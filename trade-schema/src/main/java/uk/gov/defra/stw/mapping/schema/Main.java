package uk.gov.defra.stw.mapping.schema;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.everit.json.schema.ValidationException;

public class Main {

  private static final List<String> FILES = List.of(
      "trade-mapping-to-ipaffs/src/test/resources/cheda/cheda_trade_eu_complete.json",
      "trade-mapping-to-ipaffs/src/test/resources/cheda/cheda_trade_eu_minimal.json",
      "trade-mapping-to-ipaffs/src/test/resources/cheda/cheda_trade_row_complete.json",
      "trade-mapping-to-ipaffs/src/test/resources/cheda/cheda_trade_row_minimal.json",
      "trade-mapping-to-ipaffs/src/test/resources/chedp/chedp_ehc_complete.json",
      "trade-mapping-to-ipaffs/src/test/resources/chedp/chedp_ehc_minimum.json",
      "trade-mapping-to-ipaffs/src/test/resources/chedpp/chedpp_trade_complete.json",
      "trade-mapping-to-ipaffs/src/test/resources/chedpp/chedpp_trade_minimal.json",
      "trade-schema/src/test/resources/enotification/test.json"
  );

  public static UncefactValidator uncefactValidator = new UncefactValidator();
  public static EnotificationValidator enotificationValidator = new EnotificationValidator();

  public static void main(String[] args) throws IOException {
    for (String file : FILES) {
      String payload = new String(Files.readAllBytes(Path.of(file)));
      System.out.println("File: " + file);
      try {
        System.out.println("Validating against UN/CEFACT");
        uncefactValidator.validate(payload);
        System.out.println("No errors");
      } catch (ValidationException exception) {
        List<String> validationErrors = exception.getAllMessages();
        System.out.println(String.join("\n", validationErrors));
      }
      try {
        System.out.println("Validating against eNotification");
        enotificationValidator.validate(payload);
        System.out.println("No errors");
      } catch (ValidationException exception) {
        List<String> validationErrors = exception.getAllMessages();
        System.out.println(String.join("\n", validationErrors));
      }
      System.out.println();
    }
  }
}
