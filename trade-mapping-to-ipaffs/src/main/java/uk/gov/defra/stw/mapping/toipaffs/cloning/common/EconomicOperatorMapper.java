package uk.gov.defra.stw.mapping.toipaffs.cloning.common;

import static java.util.Optional.ofNullable;

import java.util.StringJoiner;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.SpecifiedSpsAddress;
import uk.gov.defra.stw.mapping.dto.SpsPartyType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperator;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperatorAddress;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorStatus;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType;

@Component
public class EconomicOperatorMapper implements Mapper<SpsPartyType, EconomicOperator> {

  private static final String NOT_SPECIFIED = "Not specified";
  private static final String EMAIL_NOT_SPECIFIED = "not@specified.com";
  private static final int MAX_STRING_LENGTH = 255;

  @Override
  public EconomicOperator map(SpsPartyType spsPartyType) throws NotificationMapperException {
    EconomicOperator economicOperator = EconomicOperator.builder()
        .id(spsPartyType.getId().getValue())
        .companyName(spsPartyType.getName().getValue())
        .address(mapAddress(spsPartyType))
        .status(EconomicOperatorStatus.NON_APPROVED)
        .otherIdentifier(mapOtherIdentifier(spsPartyType))
        .build();

    if (!isValidEconomicOperator(economicOperator)) {
      throw new NotificationMapperException("Invalid Economic Operator data");
    }

    return economicOperator;
  }

  public EconomicOperator mapEconomicOperator(SpsPartyType spsPartyType,
      EconomicOperatorType economicOperatorType) throws NotificationMapperException {
    if (shouldMapEconomicOperator(spsPartyType)) {
      return setEconomicOperatorType(map(spsPartyType), economicOperatorType);
    }
    return null;
  }

  private EconomicOperator setEconomicOperatorType(EconomicOperator economicOperator,
      EconomicOperatorType economicOperatorType) {
    if (economicOperator != null) {
      economicOperator.setType(economicOperatorType);
    }
    return economicOperator;
  }

  private boolean shouldMapEconomicOperator(SpsPartyType spsPartyType) {
    return spsPartyType != null
        && !isTextTypeEmpty(spsPartyType.getSpecifiedSpsAddress().getLineOne())
        && !isTextTypeEmpty(spsPartyType.getSpecifiedSpsAddress().getCityName());
  }

  private boolean isTextTypeEmpty(TextType textType) {
    return textType == null || StringUtils.isEmpty(textType.getValue());
  }

  private EconomicOperatorAddress mapAddress(SpsPartyType spsPartyType)
      throws NotificationMapperException {
    if (spsPartyType.getSpecifiedSpsAddress() == null) {
      return null;
    }

    SpecifiedSpsAddress specifiedSpsAddress = spsPartyType.getSpecifiedSpsAddress();
    return EconomicOperatorAddress.builder()
        .postalZipCode(mapPostalZipCode(specifiedSpsAddress))
        .addressLine1(mapAddressLine1(specifiedSpsAddress))
        .addressLine2(mapAddressLine2(specifiedSpsAddress))
        .addressLine3(mapAddressLine3(specifiedSpsAddress))
        .city(mapCity(specifiedSpsAddress))
        .countryISOCode(mapCountryIsoCode(specifiedSpsAddress))
        .telephone(NOT_SPECIFIED)
        .email(EMAIL_NOT_SPECIFIED)
        .build();
  }

  private String mapOtherIdentifier(SpsPartyType data)
      throws NotificationMapperException {
    if (data.getId() == null || StringUtils.isEmpty(data.getId().getValue())) {
      throw new NotificationMapperException("Invalid EHC Economic Operator ID");
    }
    return data.getId().getValue();
  }

  private boolean isValidEconomicOperator(EconomicOperator economicOperator) {
    return economicOperator != null && economicOperator.getAddress() != null
        && StringUtils.isNotEmpty(economicOperator.getAddress().getAddressLine1())
        && StringUtils.isNotEmpty(economicOperator.getAddress().getCity())
        && StringUtils.isNotEmpty(economicOperator.getAddress().getCountryISOCode());
  }

  private String mapPostalZipCode(SpecifiedSpsAddress specifiedSpsAddress) {
    return ofNullable(specifiedSpsAddress.getPostcodeCode())
        .map(CodeType::getValue).orElse(null);
  }

  private String mapAddressLine1(SpecifiedSpsAddress specifiedSpsAddress) {
    return ofNullable(specifiedSpsAddress.getLineOne())
        .map(TextType::getValue).orElse(null);
  }

  private String mapAddressLine2(SpecifiedSpsAddress specifiedSpsAddress) {
    return ofNullable(specifiedSpsAddress.getLineTwo())
        .map(TextType::getValue).orElse(null);
  }

  private String mapAddressLine3(SpecifiedSpsAddress specifiedSpsAddress)
      throws NotificationMapperException {
    StringJoiner addressLineJoined = new StringJoiner(", ");
    ofNullable(specifiedSpsAddress.getLineThree())
        .map(TextType::getValue).ifPresent(addressLineJoined::add);
    ofNullable(specifiedSpsAddress.getLineFour())
        .map(TextType::getValue).ifPresent(addressLineJoined::add);
    ofNullable(specifiedSpsAddress.getLineFive())
        .map(TextType::getValue).ifPresent(addressLineJoined::add);
    if (addressLineJoined.length() > MAX_STRING_LENGTH) {
      throw new NotificationMapperException(
          "Unable to map to the addressLine3 because it is too long");
    }
    if (!addressLineJoined.toString().isEmpty()) {
      return addressLineJoined.toString();
    }
    return null;
  }

  private String mapCity(SpecifiedSpsAddress specifiedSpsAddress) {
    return ofNullable(specifiedSpsAddress.getCityName())
        .map(TextType::getValue).orElse(null);
  }

  private String mapCountryIsoCode(SpecifiedSpsAddress specifiedSpsAddress) {
    return ofNullable(specifiedSpsAddress.getCountryID())
        .map(IDType::getValue).orElse(null);
  }
}
