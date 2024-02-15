package uk.gov.defra.tracesx.mapper.common.spsconsignment;

import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperator;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperatorAddress;
import uk.gov.defra.tracesx.trade.dto.SpecifiedSpsAddress;
import uk.gov.defra.tracesx.trade.dto.SpsPartyType;

@Component
public class SpsPartyMapper implements Mapper<EconomicOperator, SpsPartyType> {

  @Override
  public SpsPartyType map(EconomicOperator data) {
    if (data == null) {
      return null;
    }
    return new SpsPartyType()
        .withId(SpsTypeConverter.getIdType(data.getId()))
        .withName(SpsTypeConverter.getTextType(data.getCompanyName()))
        .withSpecifiedSpsAddress(getSpsAddress(data.getAddress()));
  }

  private SpecifiedSpsAddress getSpsAddress(EconomicOperatorAddress address) {
    if (address == null) {
      return null;
    }
    return new SpecifiedSpsAddress()
        .withPostcodeCode(SpsTypeConverter.getCodeType(address.getPostalZipCode()))
        .withLineOne(SpsTypeConverter.getTextType(address.getAddressLine1()))
        .withLineTwo(SpsTypeConverter.getTextType(address.getAddressLine2()))
        .withLineThree(SpsTypeConverter.getTextType(address.getAddressLine3()))
        .withCityName(SpsTypeConverter.getTextType(address.getCity()))
        .withCountryID(SpsTypeConverter.getIdType(address.getCountryISOCode()));
  }
}
