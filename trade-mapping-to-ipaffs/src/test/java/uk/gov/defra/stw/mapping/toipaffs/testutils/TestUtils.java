package uk.gov.defra.stw.mapping.toipaffs.testutils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class TestUtils {

  public static ObjectMapper initObjectMapper() {
    return new ObjectMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
        .enable(SerializationFeature.INDENT_OUTPUT);
  }
}
