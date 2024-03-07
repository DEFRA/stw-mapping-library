package uk.gov.defra.stw.mapping.toipaffs.testutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class JsonDeserializer {
  private static final ObjectMapper objectMapper = TestUtils.initObjectMapper();

  public static <T> T get(String filename, CollectionType collectionType)
      throws JsonProcessingException {
    String jsonString = ResourceUtils.readFileToString("classpath:" + filename);
    return objectMapper.readValue(jsonString, collectionType);
  }

  public static <T> T get(String filename, Class<T> clazz)
      throws JsonProcessingException {
    String jsonString = ResourceUtils.readFileToString("classpath:" + filename);
    return objectMapper.readValue(jsonString, clazz);
  }
}
