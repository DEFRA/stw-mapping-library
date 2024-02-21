package uk.gov.defra.stw.mapping.schema;

import java.io.InputStream;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaClient;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Component;

@Component
public class EnotificationValidator implements SchemaValidator {

  private static final String ENOTIFICATION_JSON = "/json/enotification/SPSCertificate.json";
  private final Schema enotificationSchema;

  public EnotificationValidator() {
    enotificationSchema =
        SchemaLoader.builder()
            .schemaClient(SchemaClient.classPathAwareClient())
            .schemaJson(new JSONObject(new JSONTokener(getInputStream())))
            .resolutionScope("classpath:///json/enotification/")
            .build()
            .load()
            .build();
  }

  @Override
  public void validate(String json) {
    JSONObject jsonObject = new JSONObject(new JSONTokener(json));
    enotificationSchema.validate(jsonObject);
  }

  private InputStream getInputStream() {
    return getClass().getResourceAsStream(ENOTIFICATION_JSON);
  }
}
