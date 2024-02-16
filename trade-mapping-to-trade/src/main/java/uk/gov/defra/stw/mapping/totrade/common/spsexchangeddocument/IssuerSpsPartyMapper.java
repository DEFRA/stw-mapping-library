package uk.gov.defra.stw.mapping.totrade.common.spsexchangeddocument;

import java.util.Collections;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.RoleCode;
import uk.gov.defra.stw.mapping.dto.RoleCode.Value;
import uk.gov.defra.stw.mapping.dto.SpecifiedSpsAddress;
import uk.gov.defra.stw.mapping.dto.SpecifiedSpsPerson;
import uk.gov.defra.stw.mapping.dto.SpsPartyType;
import uk.gov.defra.stw.mapping.totrade.common.Mapper;
import uk.gov.defra.stw.mapping.totrade.common.utils.SpsTypeConverter;
import uk.gov.defra.tracesx.notificationschema.representation.Party;

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
