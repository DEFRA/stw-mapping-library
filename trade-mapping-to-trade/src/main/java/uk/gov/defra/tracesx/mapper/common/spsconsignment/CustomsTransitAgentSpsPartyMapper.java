package uk.gov.defra.tracesx.mapper.common.spsconsignment;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter;
import uk.gov.defra.tracesx.notificationschema.representation.Party;
import uk.gov.defra.tracesx.trade.dto.CodeType;
import uk.gov.defra.tracesx.trade.dto.RoleCode.Value;
import uk.gov.defra.tracesx.trade.dto.SpecifiedSpsAddress;
import uk.gov.defra.tracesx.trade.dto.SpsPartyType;
import uk.gov.defra.tracesx.trade.dto.TextType;

@Component
public class CustomsTransitAgentSpsPartyMapper implements Mapper<Party, SpsPartyType> {

  private static final String OPERATOR_ACTIVITY_ID = "operator_activity_id";
  private static final String OPERATOR_ACTIVITY_TYPE = "operator_activity_type";
  private static final String CLASSIFICATION_SECTION_CODE = "classification_section_code";
  private static final String DEFAULT_CITY = "London";

  @Override
  public SpsPartyType map(Party data) {
    return new SpsPartyType()
        .withId(SpsTypeConverter.getIdTypeWithSchemeId(OPERATOR_ACTIVITY_ID, data.getContactId()))
        .withName(SpsTypeConverter.getTextType(data.getName()))
        .withRoleCode(SpsTypeConverter.getRoleCode("Customs broker", Value.CB))
        .withTypeCode(getCustomsTypeCodes())
        .withSpecifiedSpsAddress(getSpsAddress(data));
  }

  private static List<CodeType> getCustomsTypeCodes() {
    return Arrays.asList(SpsTypeConverter.getCodeTypeWithListIdAndName(
        OPERATOR_ACTIVITY_TYPE,
        "Responsible for the load",
        "RESPONSIBLE_FOR_THE_LOAD"),
        SpsTypeConverter.getCodeTypeWithListIdAndName(
            CLASSIFICATION_SECTION_CODE,
            "Responsible for the load (Freight Forwarder)",
            "RFL"));
  }

  private static SpecifiedSpsAddress getSpsAddress(Party party) {
    return new SpecifiedSpsAddress()
        .withLineOne(SpsTypeConverter.getTextType(
            String.join(", ", party.getAddress())))
        .withCityName(mapCityName(party.getCity()))
        .withCountryID(SpsTypeConverter.getIdType(party.getCountry()));
  }

  private static TextType mapCityName(String city) {
    return StringUtils.isEmpty(city) ? new TextType().withValue(DEFAULT_CITY)
        : new TextType().withValue(city);
  }
}
