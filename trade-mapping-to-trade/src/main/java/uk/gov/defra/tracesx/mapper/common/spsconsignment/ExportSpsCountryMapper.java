package uk.gov.defra.tracesx.mapper.common.spsconsignment;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.mapper.common.utils.IpaffsRegionsUtil;
import uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;
import uk.gov.defra.tracesx.trade.dto.CodeType;
import uk.gov.defra.tracesx.trade.dto.FunctionTypeCode;
import uk.gov.defra.tracesx.trade.dto.SpsCountrySubDivisionType;
import uk.gov.defra.tracesx.trade.dto.SpsCountryType;
import uk.gov.defra.tracesx.trade.dto.TextType;

@Component
public class ExportSpsCountryMapper implements Mapper<Commodities, SpsCountryType> {

  private static final String HIERARCHICAL_LEVEL_CODE_VALUE = "1";
  private static final String SUBDIVISION_TYPE_CODE = "106";

  @Override
  public SpsCountryType map(Commodities commodity) {
    SpsCountryType spsCountryType = new SpsCountryType();

    if (IpaffsRegionsUtil.isUkRegion(commodity.getConsignedCountry())) {
      spsCountryType.withId(SpsTypeConverter.getIdType(IpaffsRegionsUtil.GB));
      spsCountryType.setSubordinateSpsCountrySubDivision(
          mapSubordinateSpsCountrySubDivision(
              commodity.getConsignedCountry(), SUBDIVISION_TYPE_CODE));
    } else {
      spsCountryType.withId(SpsTypeConverter.getIdType(commodity.getConsignedCountry()));
    }

    return spsCountryType;
  }

  private List<SpsCountrySubDivisionType> mapSubordinateSpsCountrySubDivision(
      String country, String functionTypeCode) {
    return Collections.singletonList(
        new SpsCountrySubDivisionType()
            .withName(
                Collections.singletonList(
                    new TextType()
                        .withValue(IpaffsRegionsUtil.getIsoRegionFromIpaffsRegion(country))))
            .withHierarchicalLevelCode(new CodeType().withValue(HIERARCHICAL_LEVEL_CODE_VALUE))
            .withFunctionTypeCode(new FunctionTypeCode().withValue(functionTypeCode)));
  }
}
