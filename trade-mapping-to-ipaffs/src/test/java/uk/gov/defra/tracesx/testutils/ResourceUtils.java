package uk.gov.defra.tracesx.testutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.stream.Collectors;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class ResourceUtils {

  public static String readFileToString(String path) {
    ResourceLoader resourceLoader = new DefaultResourceLoader();
    Resource resource = resourceLoader.getResource(path);
    return asString(resource);
  }

  private static String asString(Resource resource) {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
      return readAllLinesWithStream(reader);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private static String readAllLinesWithStream(BufferedReader reader) {
    return reader.lines()
        .collect(Collectors.joining(System.lineSeparator()));
  }
}
