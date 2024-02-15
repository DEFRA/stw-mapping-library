package uk.gov.defra.tracesx.testutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class JsonDeserializer {
  public static <T> T get(CollectionType collectionType, String filename, ObjectMapper objectMapper)
      throws JsonProcessingException {
    String jsonString = ResourceUtils.readFileToString("classpath:" + filename);
    return objectMapper.readValue(jsonString, collectionType);
  }

  public static <T> T get(Class<T> clazz, String filename, ObjectMapper objectMapper)
      throws JsonProcessingException {
    String jsonString = ResourceUtils.readFileToString("classpath:" + filename);
    return objectMapper.readValue(jsonString, clazz);
  }
}
