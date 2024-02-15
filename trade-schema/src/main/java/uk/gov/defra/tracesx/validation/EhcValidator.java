package uk.gov.defra.tracesx.validation;

import java.io.InputStream;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaClient;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Component;

@Component
public class EhcValidator implements SchemaValidator {

  private static final String EHC_JSON = "/json/cloning/SPSCertificate.json";
  private final Schema ehcSchema;

  public EhcValidator() {
    ehcSchema = SchemaLoader.builder()
        .schemaClient(SchemaClient.classPathAwareClient())
        .schemaJson(
            new JSONObject(new JSONTokener(getInputStream())))
        .resolutionScope("classpath:///json/cloning/")
        .build().load().build();
  }

  @Override
  public void validate(String json) {
    JSONObject jsonObject = new JSONObject(new JSONTokener(json));
    ehcSchema.validate(jsonObject);
  }

  private InputStream getInputStream() {
    return getClass().getResourceAsStream(EHC_JSON);
  }
}
