package uk.gov.defra.tracesx.validation.testutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.stream.Collectors;

public class ResourceUtil {

  public static String readFileToString(String path) {
    return asString(getInputStream(path));
  }

  public static InputStream getInputStream(String path) {
    return ResourceUtil.class.getResourceAsStream(path);
  }

  private static String asString(InputStream inputStream) {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      return readAllLinesWithStream(reader);
    } catch (IOException exception) {
      throw new UncheckedIOException(exception);
    }
  }

  private static String readAllLinesWithStream(BufferedReader reader) {
    return reader.lines()
        .collect(Collectors.joining(System.lineSeparator()));
  }
}
