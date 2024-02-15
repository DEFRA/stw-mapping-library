package uk.gov.defra.tracesx.mapper.common.spsexchangeddocument;

import java.util.Collections;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter;
import uk.gov.defra.tracesx.notificationschema.representation.Party;
import uk.gov.defra.tracesx.trade.dto.RoleCode;
import uk.gov.defra.tracesx.trade.dto.RoleCode.Value;
import uk.gov.defra.tracesx.trade.dto.SpecifiedSpsAddress;
import uk.gov.defra.tracesx.trade.dto.SpecifiedSpsPerson;
import uk.gov.defra.tracesx.trade.dto.SpsPartyType;

@Component
public class IssuerSpsPartyMapper implements Mapper<Party, SpsPartyType> {

  private static final String OPERATOR = "OPERATOR";

  @Override
  public SpsPartyType map(Party data) {
    if (data == null) {
      return null;
    }

    return new SpsPartyType()
        .withId(SpsTypeConverter.getIdType(data.getContactId()))
        .withName(SpsTypeConverter.getTextType(data.getCompanyName()))
        .withRoleCode(new RoleCode()
            .withValue(Value.VJ))
        .withTypeCode(Collections.singletonList(SpsTypeConverter.getCodeType(OPERATOR)))
        .withSpecifiedSpsAddress(getSpsAddress(data))
        .withSpecifiedSpsPerson(
            new SpecifiedSpsPerson()
                .withName(SpsTypeConverter.getTextType(data.getName())));
  }

  private SpecifiedSpsAddress getSpsAddress(Party party) {
    return new SpecifiedSpsAddress()
        .withLineOne(SpsTypeConverter.getTextType(
            String.join(", ", party.getAddress())))
        .withCityName(SpsTypeConverter.getTextType(party.getCity()))
        .withCountryID(SpsTypeConverter.getIdType(party.getCountry()));
  }
}
