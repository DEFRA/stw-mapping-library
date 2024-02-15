package uk.gov.defra.tracesx.mapper.chedpp.spsconsignment;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.TRANSHIPMENT_TO;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.TRANSIT_TO_3RD_COUNTRY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.trade.dto.CodeType;
import uk.gov.defra.tracesx.trade.dto.FunctionTypeCode;
import uk.gov.defra.tracesx.trade.dto.RoleCode;
import uk.gov.defra.tracesx.trade.dto.SpsCountrySubDivisionType;
import uk.gov.defra.tracesx.trade.dto.SpsCountryType;
import uk.gov.defra.tracesx.trade.dto.SpsPartyType;

@Component
public class ChedppTransitSpsCountryMapper implements Mapper<Purpose, List<SpsCountryType>> {

  private static final String HIERARCHICAL_LEVEL_CODE_VALUE = "0";
  private static final String FUNCTION_TYPE_CODE_FOR_TRANSHIPMENT = "13";
  private static final String FUNCTION_TYPE_CODE_FOR_DIRECT_TRANSIT = "42";

  @Override
  public List<SpsCountryType> map(Purpose purpose) {
    List<SpsCountryType> spsCountryTypes = new ArrayList<>();

    if (purpose.getPurposeGroup() == TRANSHIPMENT_TO) {
      spsCountryTypes.add(mapSpsCountryType(purpose.getFinalBIP()));
    } else if (purpose.getPurposeGroup() == TRANSIT_TO_3RD_COUNTRY) {
      for (String country : purpose.getTransitThirdCountries()) {
        spsCountryTypes.add(mapSpsCountryType(purpose.getExitBIP(), country));
      }
    }
    return spsCountryTypes;
  }

  private SpsCountryType mapSpsCountryType(String bip) {
    return new SpsCountryType()
        .withSubordinateSpsCountrySubDivision(
            mapSubordinateSpsCountrySubDivision(bip, FUNCTION_TYPE_CODE_FOR_TRANSHIPMENT));
  }

  private SpsCountryType mapSpsCountryType(String bip, String country) {
    return new SpsCountryType()
        .withId(SpsTypeConverter.getIdType(country))
        .withSubordinateSpsCountrySubDivision(
            mapSubordinateSpsCountrySubDivision(bip, FUNCTION_TYPE_CODE_FOR_DIRECT_TRANSIT));
  }

  private List<SpsCountrySubDivisionType> mapSubordinateSpsCountrySubDivision(String bip,
      String functionTypeCode) {
    return Collections.singletonList(new SpsCountrySubDivisionType()
        .withHierarchicalLevelCode(new CodeType()
            .withValue(HIERARCHICAL_LEVEL_CODE_VALUE))
        .withFunctionTypeCode(new FunctionTypeCode()
            .withValue(functionTypeCode))
        .withActivityAuthorizedSpsParty(mapActivityAuthorizedSpsParty(bip)));
  }

  private List<SpsPartyType> mapActivityAuthorizedSpsParty(String bip) {
    return Collections.singletonList(new SpsPartyType()
        .withId(SpsTypeConverter.getIdType(bip))
        .withRoleCode(new RoleCode()
            .withValue(RoleCode.Value.CM)));
  }
}
