package uk.gov.defra.stw.mapping.totrade;

import static uk.gov.defra.stw.mapping.totrade.staticmappers.SpsCertificateStaticMapper.REPLACEMENT_GUID;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;
import org.everit.json.schema.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.schema.UncefactValidator;
import uk.gov.defra.stw.mapping.totrade.exceptions.TradeValidationException;
import uk.gov.defra.stw.mapping.totrade.staticmappers.StaticDataMapper;
import uk.gov.defra.stw.mapping.validation.rules.TradeValidationRule;
import uk.gov.defra.stw.mapping.validation.validator.TradeValidatorFactory;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

@Component
public class SpsCertificateMapper {

  private final TradeValidatorFactory validatorFactory;
  private final SpsCertificateMapperFactory spsCertificateMapperFactory;
  private final StaticDataMapper<SpsCertificate> spsCertificateStaticMapper;
  private final UncefactValidator tradeSchemaValidator;
  private ObjectMapper objectMapper;

  @Autowired
  public SpsCertificateMapper(
      TradeValidatorFactory validatorFactory,
      SpsCertificateMapperFactory spsCertificateMapperFactory,
      StaticDataMapper<SpsCertificate> spsCertificateStaticMapper,
      UncefactValidator tradeSchemaValidator) {
    this.validatorFactory = validatorFactory;
    this.spsCertificateMapperFactory = spsCertificateMapperFactory;
    this.tradeSchemaValidator = tradeSchemaValidator;
    this.spsCertificateStaticMapper = spsCertificateStaticMapper;
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(Include.NON_EMPTY);
  }

  public String processNotification(Notification notification)
      throws TradeValidationException, JsonProcessingException {

    Set<TradeValidationRule> ruleConstraints = validatorFactory
        .getValidator(notification.getType())
        .validate(notification);

    if (!ruleConstraints.isEmpty()) {
      throw new TradeValidationException(getValidationMessages(ruleConstraints));
    }

    SpsCertificate spsCertificate = spsCertificateMapperFactory
        .getMapper(notification.getType())
        .map(notification);

    spsCertificateStaticMapper.apply(spsCertificate);

    String spsCertificateString = objectMapper.writeValueAsString(spsCertificate)
        .replace(REPLACEMENT_GUID, "");

    try {
      tradeSchemaValidator.validate(spsCertificateString);
    } catch (ValidationException ve) {
      throw new TradeValidationException(String.join(", ", ve.getAllMessages()));
    }

    return spsCertificateString;
  }

  private String getValidationMessages(Set<TradeValidationRule> rules) {
    StringBuilder stringBuilder = new StringBuilder();
    for (TradeValidationRule rule : rules) {
      stringBuilder.append(rule.getMessage());
      stringBuilder.append("\n");
    }
    return stringBuilder.toString();
  }
}
