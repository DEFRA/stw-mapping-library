package uk.gov.defra.tracesx.validation;

import java.io.InputStream;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaClient;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Component;

@Component
public class UncefactValidator implements SchemaValidator {

  private static final String UNCEFACT_JSON = "/json/uncefact/SPSCertificate.json";
  private final Schema uncefactSchema;

  public UncefactValidator() {
    uncefactSchema = SchemaLoader.builder()
        .schemaClient(SchemaClient.classPathAwareClient())
        .schemaJson(
            new JSONObject(new JSONTokener(getInputStream())))
        .resolutionScope("classpath:///json/uncefact/")
        .build().load().build();
  }

  public void validate(String json) {
    JSONObject jsonObject = new JSONObject(new JSONTokener(json));
    uncefactSchema.validate(jsonObject);
  }

  private InputStream getInputStream() {
    return getClass().getResourceAsStream(UNCEFACT_JSON);
  }
}
