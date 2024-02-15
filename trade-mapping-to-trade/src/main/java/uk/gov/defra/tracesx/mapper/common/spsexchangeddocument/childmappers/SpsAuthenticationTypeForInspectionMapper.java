package uk.gov.defra.tracesx.mapper.common.spsexchangeddocument.childmappers;

import static uk.gov.defra.tracesx.mapper.common.utils.Format.localDateTime;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;
import uk.gov.defra.tracesx.notificationschema.representation.Party;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum;
import uk.gov.defra.tracesx.trade.dto.CodeType;
import uk.gov.defra.tracesx.trade.dto.DateTime;
import uk.gov.defra.tracesx.trade.dto.DateTimeType;
import uk.gov.defra.tracesx.trade.dto.GovernmentActionCodeType;
import uk.gov.defra.tracesx.trade.dto.RoleCode;
import uk.gov.defra.tracesx.trade.dto.RoleCode.Value;
import uk.gov.defra.tracesx.trade.dto.SpecifiedSpsAddress;
import uk.gov.defra.tracesx.trade.dto.SpecifiedSpsPerson;
import uk.gov.defra.tracesx.trade.dto.SpsAuthenticationType;
import uk.gov.defra.tracesx.trade.dto.SpsPartyType;
import uk.gov.defra.tracesx.trade.dto.TextType;

public abstract class SpsAuthenticationTypeForInspectionMapper implements
    Mapper<PartOne, SpsAuthenticationType> {

  private static final String INSPECTION_TYPE_CODE = "4";
  private static final String OPERATOR = "OPERATOR";

  private static final String INTERNAL_MARKET = "INTERNAL_MARKET";

  protected Map<PurposeGroupEnum, TextType> purposeMap;

  protected SpsAuthenticationTypeForInspectionMapper() {
    purposeMap = new EnumMap<>(PurposeGroupEnum.class);
    purposeMap.put(PurposeGroupEnum.IMPORT, createTextType(INTERNAL_MARKET));
  }

  @Override
  public SpsAuthenticationType map(PartOne data) {
    Party party = data.getPersonResponsible();
    return new SpsAuthenticationType()
        .withTypeCode(new GovernmentActionCodeType()
            .withValue(INSPECTION_TYPE_CODE))
        .withActualDateTime(new DateTimeType()
            .withDateTime(new DateTime()
                .withValue(localDateTime.apply(data.getSubmissionDate()))))
        .withProviderSpsParty(new SpsPartyType()
            .withId(SpsTypeConverter.getIdType(party.getContactId()))
            .withName(SpsTypeConverter.getTextType(party.getCompanyName()))
            .withRoleCode(new RoleCode()
                .withValue(Value.VJ))
            .withTypeCode(Collections.singletonList(new CodeType()
                .withValue(OPERATOR)))
            .withSpecifiedSpsAddress(getSpsAddress(party))
            .withSpecifiedSpsPerson(
                new SpecifiedSpsPerson()
                    .withName(SpsTypeConverter.getTextType(party.getName()))));
  }

  private SpecifiedSpsAddress getSpsAddress(Party party) {
    return new SpecifiedSpsAddress()
        .withLineOne(SpsTypeConverter.getTextType(
            String.join(", ", party.getAddress())))
        .withCityName(SpsTypeConverter.getTextType(party.getCity()))
        .withCountryID(SpsTypeConverter.getIdType(party.getCountry()));
  }

  protected TextType createTextType(String contentType) {
    return SpsTypeConverter.getTextType(contentType);
  }
}
